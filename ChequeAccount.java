public class ChequeAccount extends Account {
    private double overdraftLimit;
    private String companyName;
    private String companyAddress;

    public ChequeAccount(String holderName, long accNo, double balance, String branch, double overdraftLimit, String companyName, String companyAddress) {
        super(holderName, accNo, balance, branch, "Cheque");
        this.overdraftLimit = overdraftLimit;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: BWP " + String.format("%.2f", amount) + " to Cheque Account " + accNo);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= -overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrawn: BWP " + String.format("%.2f", amount) + " from Cheque Account " + accNo);
        } else {
            System.out.println("Withdrawal exceeds overdraft limit");
        }
    }

    public boolean checkoverdraft() {
        return balance < 0;
    }

    public String getaccountdetails() {
        return getDetails() + "\nOverdraft Limit: BWP " + String.format("%.2f", overdraftLimit) +
               "\nIn Overdraft: " + checkoverdraft() +
               "\nCompany: " + companyName +
               "\nCompany Address: " + companyAddress;
    }

    // Getters and Setters
    public double getOverdraftLimit() { return overdraftLimit; }
    public void setOverdraftLimit(double overdraftLimit) { this.overdraftLimit = overdraftLimit; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyAddress() { return companyAddress; }
    public void setCompanyAddress(String companyAddress) { this.companyAddress = companyAddress; }
}