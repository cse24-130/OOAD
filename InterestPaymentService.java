import java.io.IOException;
import java.util.List;

/**
 * Service class for paying monthly interest to interest-bearing accounts
 */
public class InterestPaymentService {
    private AccountDAO accountDAO;
    private CustomerDAO customerDAO;
    
    public InterestPaymentService(AccountDAO accountDAO, CustomerDAO customerDAO) {
        this.accountDAO = accountDAO;
        this.customerDAO = customerDAO;
    }
    
    /**
     * Pay monthly interest to all interest-bearing accounts
     */
    public void payMonthlyInterest() throws IOException {
        List<AccountDAO.AccountData> allAccounts = accountDAO.loadAllAccounts();
        
        for (AccountDAO.AccountData accountData : allAccounts) {
            Account account = accountData.getAccount();
            
            if (account instanceof InterestBearing) {
                InterestBearing interestAccount = (InterestBearing) account;
                double interest = interestAccount.CalculateMonthlyInterest();
                
                // Update account in storage
                accountDAO.updateAccount(account, accountData.getCustomerId());
                
                System.out.println("Interest of BWP " + String.format("%.2f", interest) + 
                                 " paid to " + account.getAccType() + " account " + account.getAccNo());
            }
        }
    }
    
    /**
     * Pay interest to a specific account
     */
    public void payInterestToAccount(Account account, String customerId) throws IOException {
        if (account instanceof InterestBearing) {
            InterestBearing interestAccount = (InterestBearing) account;
            double interest = interestAccount.CalculateMonthlyInterest();
            
            accountDAO.updateAccount(account, customerId);
            
            System.out.println("Interest of BWP " + String.format("%.2f", interest) + 
                             " paid to " + account.getAccType() + " account " + account.getAccNo());
        }
    }
}

