public class ChequeAccount extends Account {
    private double overdraftLimit;

    public ChequeAccount(String holderName, long accNo, double balance, String branch, double overdraftLimit) {
        super(holderName, accNo, balance, branch, "Cheque");
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount + " to Cheque Account " + accNo);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= -overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount + " from Cheque Account " + accNo);
        } else {
            System.out.println("Withdrawal exceeds overdraft limit");
        }
    }

    public boolean checkoverdraft() {
        return balance < 0;
    }

    public String getaccountdetails() {
        return getDetails() + "\nOverdraft Limit: $" + overdraftLimit +
               "\nIn Overdraft: " + checkoverdraft();
    }

    // Getters and Setters
    public double getOverdraftLimit() { return overdraftLimit; }
    public void setOverdraftLimit(double overdraftLimit) { this.overdraftLimit = overdraftLimit; }
}