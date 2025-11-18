import java.util.Date;

public abstract class Account {
    protected String holderName;
    protected long accNo;
    protected double balance;
    protected String branch;
    protected String accType;
    protected Date creationDate;

    public Account(String holderName, long accNo, double balance, String branch, String accType) {
        this.holderName = holderName;
        this.accNo = accNo;
        this.balance = balance;
        this.branch = branch;
        this.accType = accType;
        this.creationDate = new Date();
    }

    // Abstract methods to be implemented by subclasses
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    
    // Concrete method
    public String getDetails() {
        return "Account Details:\n" +
               "Holder: " + holderName + "\n" +
               "Account No: " + accNo + "\n" +
               "Balance: BWP " + String.format("%.2f", balance) + "\n" +
               "Branch: " + branch + "\n" +
               "Type: " + accType + "\n" +
               "Created: " + creationDate;
    }

    // Getters
    public String getHolderName() { return holderName; }
    public long getAccNo() { return accNo; }
    public double getBalance() { return balance; }
    public String getBranch() { return branch; }
    public String getAccType() { return accType; }
    public Date getCreationDate() { return creationDate; }
}