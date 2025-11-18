import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object for Account persistence using file system
 */
public class AccountDAO {
    private static final String DATA_DIR = "data";
    private static final String ACCOUNT_FILE = DATA_DIR + File.separator + "accounts.txt";
    
    public AccountDAO() {
        // Create data directory if it doesn't exist
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Save an account to file
     */
    public void saveAccount(Account account, String customerId) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNT_FILE, true))) {
            writer.println(serializeAccount(account, customerId));
        }
    }
    
    /**
     * Load all accounts from file
     */
    public List<AccountData> loadAllAccounts() throws IOException {
        List<AccountData> accounts = new ArrayList<>();
        File file = new File(ACCOUNT_FILE);
        
        if (!file.exists()) {
            return accounts;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                AccountData accountData = deserializeAccount(line);
                if (accountData != null) {
                    accounts.add(accountData);
                } else {
                    System.err.println("Warning: Failed to deserialize account on line " + lineNumber + ": " + line);
                }
            }
        }
        
        return accounts;
    }
    
    /**
     * Find accounts by customer ID
     */
    public List<Account> findAccountsByCustomerId(String customerId) throws IOException {
        List<Account> accounts = new ArrayList<>();
        List<AccountData> allAccounts = loadAllAccounts();
        
        for (AccountData accountData : allAccounts) {
            if (accountData.getCustomerId().equals(customerId)) {
                accounts.add(accountData.getAccount());
            }
        }
        
        return accounts;
    }
    
    /**
     * Update account in file
     */
    public void updateAccount(Account updatedAccount, String customerId) throws IOException {
        List<AccountData> accounts = loadAllAccounts();
        boolean found = false;
        
        for (int i = 0; i < accounts.size(); i++) {
            AccountData accountData = accounts.get(i);
            if (accountData.getAccount().getAccNo() == updatedAccount.getAccNo() &&
                accountData.getCustomerId().equals(customerId)) {
                accounts.set(i, new AccountData(updatedAccount, customerId));
                found = true;
                break;
            }
        }
        
        if (found) {
            // Rewrite all accounts
            try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNT_FILE, false))) {
                for (AccountData accountData : accounts) {
                    writer.println(serializeAccount(accountData.getAccount(), accountData.getCustomerId()));
                }
            }
        }
    }
    
    /**
     * Serialize account to string format
     */
    private String serializeAccount(Account account, String customerId) {
        StringBuilder sb = new StringBuilder();
        sb.append(account.getAccType()).append("|");
        sb.append(customerId).append("|");
        sb.append(account.getAccNo()).append("|");
        sb.append(escape(account.getHolderName())).append("|");
        sb.append(account.getBalance()).append("|");
        sb.append(escape(account.getBranch())).append("|");
        sb.append(account.getCreationDate().getTime()).append("|");
        
        // Account-specific fields
        if (account instanceof SavingsAccount) {
            SavingsAccount sa = (SavingsAccount) account;
            sb.append("PIN:").append(sa.getPIN());
        } else if (account instanceof InvestmentAccount) {
            InvestmentAccount ia = (InvestmentAccount) account;
            sb.append("MIN_BAL:").append(ia.getMinimumBalance());
        } else if (account instanceof ChequeAccount) {
            ChequeAccount ca = (ChequeAccount) account;
            sb.append("OVERDRAFT:").append(ca.getOverdraftLimit()).append("|");
            sb.append(escape(ca.getCompanyName())).append("|");
            sb.append(escape(ca.getCompanyAddress()));
        }
        
        return sb.toString();
    }
    
    /**
     * Deserialize account from string format
     */
    private AccountData deserializeAccount(String line) {
        try {
            if (line == null || line.trim().isEmpty()) {
                return null;
            }
            
            String[] parts = line.split("\\|", -1); // Use -1 to preserve trailing empty strings
            if (parts.length < 7) {
                System.err.println("Warning: Account line has insufficient parts: " + line);
                return null;
            }
            
            String accountType = parts[0];
            String customerId = parts[1];
            long accNo = Long.parseLong(parts[2]);
            String holderName = unescape(parts[3]);
            double balance = Double.parseDouble(parts[4]);
            String branch = unescape(parts[5]);
            // Date creationDate = new Date(Long.parseLong(parts[6])); // Not used in constructor
            
            Account account = null;
            
            if ("Savings".equals(accountType) && parts.length > 7) {
                int pin = Integer.parseInt(parts[7].replace("PIN:", ""));
                account = new SavingsAccount(holderName, accNo, balance, branch, pin);
            } else if ("Investment".equals(accountType) && parts.length > 7) {
                double minBal = Double.parseDouble(parts[7].replace("MIN_BAL:", ""));
                account = new InvestmentAccount(holderName, accNo, balance, branch, minBal);
            } else if ("Cheque".equals(accountType)) {
                if (parts.length >= 8) {
                    double overdraft = 1000.0; // Default overdraft
                    String companyName = "";
                    String companyAddress = "";
                    
                    // Parse overdraft from parts[7]
                    if (parts.length > 7 && parts[7].startsWith("OVERDRAFT:")) {
                        try {
                            overdraft = Double.parseDouble(parts[7].replace("OVERDRAFT:", ""));
                        } catch (NumberFormatException e) {
                            System.err.println("Warning: Could not parse overdraft from: " + parts[7]);
                        }
                    }
                    
                    // Parse company name and address
                    if (parts.length > 8 && !parts[8].trim().isEmpty()) {
                        companyName = unescape(parts[8]);
                    }
                    if (parts.length > 9 && !parts[9].trim().isEmpty()) {
                        companyAddress = unescape(parts[9]);
                    }
                    
                    account = new ChequeAccount(holderName, accNo, balance, branch, overdraft, companyName, companyAddress);
                } else {
                    System.err.println("Warning: Cheque account has insufficient data. Expected at least 8 parts, got: " + parts.length);
                    System.err.println("Line: " + line);
                }
            } else {
                System.err.println("Warning: Unknown account type or insufficient data: " + accountType + " (parts: " + parts.length + ")");
            }
            
            if (account != null) {
                return new AccountData(account, customerId);
            }
        } catch (Exception e) {
            System.err.println("Error deserializing account from line: " + line);
            System.err.println("Error details: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private String escape(String str) {
        if (str == null) return "";
        return str.replace("|", "\\|").replace("\n", "\\n");
    }
    
    private String unescape(String str) {
        if (str == null) return "";
        return str.replace("\\|", "|").replace("\\n", "\n");
    }
    
    /**
     * Helper class to store account with customer ID
     */
    public static class AccountData {
        private Account account;
        private String customerId;
        
        public AccountData(Account account, String customerId) {
            this.account = account;
            this.customerId = customerId;
        }
        
        public Account getAccount() {
            return account;
        }
        
        public String getCustomerId() {
            return customerId;
        }
    }
}

