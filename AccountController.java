import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.List;

/**
 * Controller for the Account Management View
 * Handles all account operations (create, deposit, withdraw)
 */
public class AccountController {
    @FXML private TextField chequeAccNumberField;
    @FXML private TextField chequeHolderField;
    @FXML private TextField chequeBalanceField;
    @FXML private TextField chequeBranchField;
    @FXML private TextField chequeCompanyField;
    @FXML private Button createChequeAccButton;
    @FXML private Button depositChequeButton;
    @FXML private Button withdrawChequeButton;
    @FXML private TableView<AccountTableModel> chequeTable;
    @FXML private TableColumn<AccountTableModel, Long> chequeAccontNoCol;
    @FXML private TableColumn<AccountTableModel, String> chequeHolderCol;
    @FXML private TableColumn<AccountTableModel, String> campanyNameCol;
    @FXML private TableColumn<AccountTableModel, Double> chequeBalanceCol;
    
    @FXML private TextField savingsAccNoField;
    @FXML private TextField savingsAccHolderField;
    @FXML private TextField savingsBranchField;
    @FXML private TextField savingsBalanceField;
    @FXML private Button createSavingsAccBtn;
    @FXML private Button savingsAccBtn;
    @FXML private TableView<AccountTableModel> savingsTable;
    @FXML private TableColumn<AccountTableModel, Long> savingsAccCol;
    @FXML private TableColumn<AccountTableModel, String> savingsAccHolderCol;
    @FXML private TableColumn<AccountTableModel, String> savingsBranchCol;
    @FXML private TableColumn<AccountTableModel, Double> savingsBalanceCol;
    
    @FXML private TextField investmentAccNoField;
    @FXML private TextField investmentAccHolderField;
    @FXML private TextField investmentBranchField;
    @FXML private TextField investmentBalance;
    @FXML private Button createInvestmentAccBtn;
    @FXML private Button investmentDepositBtn;
    @FXML private Button withdrawInvestmentBtn;
    @FXML private TableView<AccountTableModel> investmentTable;
    @FXML private TableColumn<AccountTableModel, Long> investmentAccNoCol;
    @FXML private TableColumn<AccountTableModel, String> investmentAccHolderCol;
    @FXML private TableColumn<AccountTableModel, String> investmentBranchCol;
    @FXML private TableColumn<AccountTableModel, Double> investmentBalanceCol;
    
    private Customer currentCustomer;
    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;
    private long nextAccountNumber = 1000;
    
    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
    }
    
    public void setDAOs(CustomerDAO customerDAO, AccountDAO accountDAO) {
        this.customerDAO = customerDAO;
        this.accountDAO = accountDAO;
    }
    
    @FXML
    public void initialize() {
        setupChequeTable();
        setupSavingsTable();
        setupInvestmentTable();
    }
    
    private void setupChequeTable() {
        chequeAccontNoCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        chequeHolderCol.setCellValueFactory(new PropertyValueFactory<>("holderName"));
        campanyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        chequeBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        chequeBalanceCol.setCellFactory(column -> new TableCell<AccountTableModel, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTextFill(null);
                } else {
                    setText(String.format("BWP %.2f", item));
                    setTextFill(javafx.scene.paint.Color.web("#e0e0e0"));
                }
            }
        });
    }
    
    private void setupSavingsTable() {
        savingsAccCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        savingsAccHolderCol.setCellValueFactory(new PropertyValueFactory<>("holderName"));
        savingsBranchCol.setCellValueFactory(new PropertyValueFactory<>("branch"));
        savingsBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        savingsBalanceCol.setCellFactory(column -> new TableCell<AccountTableModel, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTextFill(null);
                } else {
                    setText(String.format("BWP %.2f", item));
                    setTextFill(javafx.scene.paint.Color.web("#e0e0e0"));
                }
            }
        });
    }
    
    private void setupInvestmentTable() {
        investmentAccNoCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        investmentAccHolderCol.setCellValueFactory(new PropertyValueFactory<>("holderName"));
        investmentBranchCol.setCellValueFactory(new PropertyValueFactory<>("branch"));
        investmentBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        investmentBalanceCol.setCellFactory(column -> new TableCell<AccountTableModel, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTextFill(null);
                } else {
                    setText(String.format("BWP %.2f", item));
                    setTextFill(javafx.scene.paint.Color.web("#e0e0e0"));
                }
            }
        });
    }
    
    public void loadAccountData() {
        try {
            if (currentCustomer == null) {
                System.err.println("Error: Current customer is null");
                return;
            }
            
            // Load accounts from file
            List<Account> accounts = accountDAO.findAccountsByCustomerId(currentCustomer.getIDNumber());
            System.out.println("Loaded " + accounts.size() + " accounts for customer: " + currentCustomer.getIDNumber());
            
            // Update customer's account list
            currentCustomer.setAccounts(accounts);
            
            ObservableList<AccountTableModel> chequeAccounts = FXCollections.observableArrayList();
            ObservableList<AccountTableModel> savingsAccounts = FXCollections.observableArrayList();
            ObservableList<AccountTableModel> investmentAccounts = FXCollections.observableArrayList();
            
            for (Account account : accounts) {
                AccountTableModel model = new AccountTableModel(account);
                if (account instanceof ChequeAccount) {
                    chequeAccounts.add(model);
                    System.out.println("Added Cheque Account: " + account.getAccNo() + " - " + account.getHolderName());
                } else if (account instanceof SavingsAccount) {
                    savingsAccounts.add(model);
                    System.out.println("Added Savings Account: " + account.getAccNo() + " - " + account.getHolderName());
                } else if (account instanceof InvestmentAccount) {
                    investmentAccounts.add(model);
                    System.out.println("Added Investment Account: " + account.getAccNo() + " - " + account.getHolderName());
                }
                
                // Update next account number
                if (account.getAccNo() >= nextAccountNumber) {
                    nextAccountNumber = account.getAccNo() + 1;
                }
            }
            
            System.out.println("Cheque accounts: " + chequeAccounts.size());
            System.out.println("Savings accounts: " + savingsAccounts.size());
            System.out.println("Investment accounts: " + investmentAccounts.size());
            
            // Clear and set table items to ensure refresh
            if (chequeTable != null) {
                chequeTable.getItems().clear();
                chequeTable.setItems(chequeAccounts);
                chequeTable.refresh();
            }
            
            if (savingsTable != null) {
                savingsTable.getItems().clear();
                savingsTable.setItems(savingsAccounts);
                savingsTable.refresh();
            }
            
            if (investmentTable != null) {
                investmentTable.getItems().clear();
                investmentTable.setItems(investmentAccounts);
                investmentTable.refresh();
            }
            
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load accounts from file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unexpected error loading accounts: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleCreateChequeAcc() {
        try {
            String holderName = chequeHolderField.getText().trim();
            String branch = chequeBranchField.getText().trim();
            String companyInfo = chequeCompanyField.getText().trim();
            String balanceStr = chequeBalanceField.getText().trim();
            
            if (holderName.isEmpty() || branch.isEmpty() || companyInfo.isEmpty() || balanceStr.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all required fields");
                return;
            }
            
            double balance = Double.parseDouble(balanceStr);
            
            // Parse company info (assuming format: "Company Name, Address")
            String[] companyParts = companyInfo.split(",", 2);
            String companyName = companyParts[0].trim();
            String companyAddress = companyParts.length > 1 ? companyParts[1].trim() : "";
            
            ChequeAccount account = new ChequeAccount(
                holderName, nextAccountNumber++, balance, branch, 1000.0, companyName, companyAddress
            );
            
            currentCustomer.addAccount(account);
            accountDAO.saveAccount(account, currentCustomer.getIDNumber());
            customerDAO.updateCustomer(currentCustomer);
            
            clearChequeFields();
            loadAccountData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Cheque account created successfully. Account Number: " + (nextAccountNumber - 1));
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid balance amount. Please enter a valid number.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create account: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleDepositCheque() {
        AccountTableModel selected = chequeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an account");
            return;
        }
        
        try {
            String amountStr = showInputDialog("Deposit Amount", "Enter deposit amount (BWP):");
            if (amountStr == null || amountStr.trim().isEmpty()) return;
            
            double amount = Double.parseDouble(amountStr.trim());
            Account account = findAccountByNumber(selected.getAccountNumber());
            
            if (account != null) {
                account.deposit(amount);
                accountDAO.updateAccount(account, currentCustomer.getIDNumber());
                loadAccountData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Deposit successful");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to deposit: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleWithdrawCheque() {
        AccountTableModel selected = chequeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an account");
            return;
        }
        
        try {
            String amountStr = showInputDialog("Withdraw Amount", "Enter withdrawal amount (BWP):");
            if (amountStr == null || amountStr.trim().isEmpty()) return;
            
            double amount = Double.parseDouble(amountStr.trim());
            Account account = findAccountByNumber(selected.getAccountNumber());
            
            if (account != null) {
                account.withdraw(amount);
                accountDAO.updateAccount(account, currentCustomer.getIDNumber());
                loadAccountData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Withdrawal successful");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to withdraw: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleCreateSavingsAcc() {
        try {
            String holderName = savingsAccHolderField.getText().trim();
            String branch = savingsBranchField.getText().trim();
            double balance = Double.parseDouble(savingsBalanceField.getText().trim());
            
            if (holderName.isEmpty() || branch.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all required fields");
                return;
            }
            
            SavingsAccount account = new SavingsAccount(
                holderName, nextAccountNumber++, balance, branch, 1234
            );
            
            currentCustomer.addAccount(account);
            accountDAO.saveAccount(account, currentCustomer.getIDNumber());
            customerDAO.updateCustomer(currentCustomer);
            
            clearSavingsFields();
            loadAccountData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Savings account created successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create account: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleDepositSavings() {
        AccountTableModel selected = savingsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an account");
            return;
        }
        
        try {
            String amountStr = showInputDialog("Deposit Amount", "Enter deposit amount (BWP):");
            if (amountStr == null || amountStr.trim().isEmpty()) return;
            
            double amount = Double.parseDouble(amountStr.trim());
            Account account = findAccountByNumber(selected.getAccountNumber());
            
            if (account != null) {
                account.deposit(amount);
                accountDAO.updateAccount(account, currentCustomer.getIDNumber());
                loadAccountData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Deposit successful");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to deposit: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleCreateInvestmentAcc() {
        try {
            String holderName = investmentAccHolderField.getText().trim();
            String branch = investmentBranchField.getText().trim();
            double balance = Double.parseDouble(investmentBalance.getText().trim());
            
            if (holderName.isEmpty() || branch.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all required fields");
                return;
            }
            
            if (balance < 500.0) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Investment account requires minimum initial deposit of BWP 500.00");
                return;
            }
            
            InvestmentAccount account = new InvestmentAccount(
                holderName, nextAccountNumber++, balance, branch, 500.0
            );
            
            currentCustomer.addAccount(account);
            accountDAO.saveAccount(account, currentCustomer.getIDNumber());
            customerDAO.updateCustomer(currentCustomer);
            
            clearInvestmentFields();
            loadAccountData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Investment account created successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create account: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleDepositInvestment() {
        AccountTableModel selected = investmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an account");
            return;
        }
        
        try {
            String amountStr = showInputDialog("Deposit Amount", "Enter deposit amount (BWP):");
            if (amountStr == null || amountStr.trim().isEmpty()) return;
            
            double amount = Double.parseDouble(amountStr.trim());
            Account account = findAccountByNumber(selected.getAccountNumber());
            
            if (account != null) {
                account.deposit(amount);
                accountDAO.updateAccount(account, currentCustomer.getIDNumber());
                loadAccountData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Deposit successful");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to deposit: " + e.getMessage());
        }
    }
    
    @FXML
    private void handlewithdrawInvestment() {
        AccountTableModel selected = investmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select an account");
            return;
        }
        
        try {
            String amountStr = showInputDialog("Withdraw Amount", "Enter withdrawal amount (BWP):");
            if (amountStr == null || amountStr.trim().isEmpty()) return;
            
            double amount = Double.parseDouble(amountStr.trim());
            Account account = findAccountByNumber(selected.getAccountNumber());
            
            if (account != null) {
                account.withdraw(amount);
                accountDAO.updateAccount(account, currentCustomer.getIDNumber());
                loadAccountData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Withdrawal successful");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to withdraw: " + e.getMessage());
        }
    }
    
    private Account findAccountByNumber(long accountNumber) {
        List<Account> accounts = currentCustomer.getAccounts();
        for (Account account : accounts) {
            if (account.getAccNo() == accountNumber) {
                return account;
            }
        }
        return null;
    }
    
    private void clearChequeFields() {
        chequeAccNumberField.clear();
        chequeHolderField.clear();
        chequeBalanceField.clear();
        chequeBranchField.clear();
        chequeCompanyField.clear();
    }
    
    private void clearSavingsFields() {
        savingsAccNoField.clear();
        savingsAccHolderField.clear();
        savingsBranchField.clear();
        savingsBalanceField.clear();
    }
    
    private void clearInvestmentFields() {
        investmentAccNoField.clear();
        investmentAccHolderField.clear();
        investmentBranchField.clear();
        investmentBalance.clear();
    }
    
    private String showInputDialog(String title, String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        return dialog.showAndWait().orElse(null);
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Model class for table display
     */
    public static class AccountTableModel {
        private Long accountNumber;
        private String holderName;
        private String branch;
        private Double balance;
        private String companyName;
        
        public AccountTableModel(Account account) {
            this.accountNumber = account.getAccNo();
            this.holderName = account.getHolderName();
            this.branch = account.getBranch();
            this.balance = account.getBalance();
            if (account instanceof ChequeAccount) {
                this.companyName = ((ChequeAccount) account).getCompanyName();
            } else {
                this.companyName = "";
            }
        }
        
        // Getters
        public Long getAccountNumber() { return accountNumber; }
        public String getHolderName() { return holderName; }
        public String getBranch() { return branch; }
        public Double getBalance() { return balance; }
        public String getCompanyName() { return companyName; }
    }
}

