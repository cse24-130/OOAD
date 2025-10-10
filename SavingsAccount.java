public class SavingsAccount extends Account implements InterestBearing {
    private int PIN;
    private static final double INTEREST_RATE = 0.05;

    public SavingsAccount(String holderName, long accNo, double balance, String branch, int PIN) {
        super(holderName, accNo, balance, branch, "Savings");
        this.PIN = PIN;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount + " to Savings Account " + accNo);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount + " from Savings Account " + accNo);
        } else {
            System.out.println("Insufficient funds or invalid amount");
        }
    }

    @Override
    public double CalculateMonthlyInterest() {
        double monthlyInterest = balance * (INTEREST_RATE / 12);
        balance += monthlyInterest;
        return monthlyInterest;
    }

    // Getters and Setters
    public int getPIN() { return PIN; }
    public void setPIN(int PIN) { this.PIN = PIN; }
    public double getInterestRate() { return INTEREST_RATE; }
}