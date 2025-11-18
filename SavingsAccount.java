public class SavingsAccount extends Account implements InterestBearing {
    private int PIN;
    private static final double INTEREST_RATE = 0.0005; // 0.05% monthly

    public SavingsAccount(String holderName, long accNo, double balance, String branch, int PIN) {
        super(holderName, accNo, balance, branch, "Savings");
        this.PIN = PIN;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: BWP " + String.format("%.2f", amount) + " to Savings Account " + accNo);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    @Override
    public void withdraw(double amount) {
        // Savings account does not allow withdrawals according to requirements
        System.out.println("Withdrawals are not allowed from Savings Account");
    }

    @Override
    public double CalculateMonthlyInterest() {
        double monthlyInterest = balance * INTEREST_RATE; // 0.05% monthly
        balance += monthlyInterest;
        return monthlyInterest;
    }

    // Getters and Setters
    public int getPIN() { return PIN; }
    public void setPIN(int PIN) { this.PIN = PIN; }
    public double getInterestRate() { return INTEREST_RATE; }
}