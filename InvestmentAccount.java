public class InvestmentAccount extends Account implements InterestBearing {
    private double minimumBalance;
    private static final double INTEREST_RATE = 0.05; // 5% monthly

    public InvestmentAccount(String holderName, long accNo, double balance, String branch, double minimumBalance) {
        super(holderName, accNo, balance, branch, "Investment");
        this.minimumBalance = minimumBalance;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: BWP " + String.format("%.2f", amount) + " to Investment Account " + accNo);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= minimumBalance) {
            balance -= amount;
            System.out.println("Withdrawn: BWP " + String.format("%.2f", amount) + " from Investment Account " + accNo);
        } else {
            System.out.println("Withdrawal would violate minimum balance requirement");
        }
    }

    @Override
    public double CalculateMonthlyInterest() {
        double monthlyInterest = balance * INTEREST_RATE; // 5% monthly
        balance += monthlyInterest;
        return monthlyInterest;
    }

    public boolean isbelowMinimum() {
        return balance < minimumBalance;
    }

    public void interest() {
        double interest = CalculateMonthlyInterest();
        System.out.println("Interest applied: BWP " + String.format("%.2f", interest));
    }

    // Getters and Setters
    public double getMinimumBalance() { return minimumBalance; }
    public void setMinimumBalance(double minimumBalance) { this.minimumBalance = minimumBalance; }
    public double getInterestRate() { return INTEREST_RATE; }
}