public class BankSystem {
    public static void main(String[] args) {
        // Create a customer
        Customer customer = new Customer("John Doe", "ID123456", "john@email.com", 
                                       "123 Main St", "555-1234", "Male");

        // Create different types of accounts
        SavingsAccount savings = new SavingsAccount("John Doe", 1001, 5000.0, "Main Branch", 1234);
        InvestmentAccount investment = new InvestmentAccount("John Doe", 1002, 10000.0, "Main Branch", 2000.0);
        ChequeAccount cheque = new ChequeAccount("John Doe", 1003, 2500.0, "Main Branch", 1000.0, "ABC Company", "123 Business St");

        // Add accounts to customer
        customer.addAccount(savings);
        customer.addAccount(investment);
        customer.addAccount(cheque);

        // Test account operations
        savings.deposit(1000);
        savings.withdraw(500);
        savings.CalculateMonthlyInterest();

        investment.withdraw(8000);
        investment.interest();

        cheque.withdraw(3000); // This should work due to overdraft
        cheque.deposit(1000);

        // Display all accounts
        customer.displayAllAccounts();
    }
}