import java.io.IOException;

/**
 * Utility class to initialize sample data for testing
 */
public class SampleDataInitializer {
    
    public static void initializeSampleData() {
        CustomerDAO customerDAO = new CustomerDAO();
        AccountDAO accountDAO = new AccountDAO();
        CredentialsDAO credentialsDAO = new CredentialsDAO();
        
        try {
            // Create sample customers
            Customer customer1 = new Customer("John Doe", "ID001", "john.doe@email.com", 
                                             "123 Main Street, Gaborone", "71234567", "Male");
            Customer customer2 = new Customer("Jane Smith", "ID002", "jane.smith@email.com", 
                                             "456 Broad Street, Francistown", "71234568", "Female");
            Customer customer3 = new Customer("Peter Mokoena", "ID003", "peter.m@email.com", 
                                             "789 Independence Avenue, Maun", "71234569", "Male");
            
            // Save customers
            customerDAO.saveCustomer(customer1);
            customerDAO.saveCustomer(customer2);
            customerDAO.saveCustomer(customer3);
            
            // Create accounts for customer1
            SavingsAccount savings1 = new SavingsAccount("John Doe", 1001, 5000.0, "Main Branch", 1234);
            InvestmentAccount investment1 = new InvestmentAccount("John Doe", 1002, 10000.0, "Main Branch", 500.0);
            ChequeAccount cheque1 = new ChequeAccount("John Doe", 1003, 2500.0, "Main Branch", 1000.0, 
                                                      "Tech Solutions Ltd", "123 Business Park, Gaborone");
            
            customer1.addAccount(savings1);
            customer1.addAccount(investment1);
            customer1.addAccount(cheque1);
            
            accountDAO.saveAccount(savings1, customer1.getIDNumber());
            accountDAO.saveAccount(investment1, customer1.getIDNumber());
            accountDAO.saveAccount(cheque1, customer1.getIDNumber());
            
            // Create accounts for customer2
            SavingsAccount savings2 = new SavingsAccount("Jane Smith", 1004, 3000.0, "North Branch", 5678);
            InvestmentAccount investment2 = new InvestmentAccount("Jane Smith", 1005, 7500.0, "North Branch", 500.0);
            
            customer2.addAccount(savings2);
            customer2.addAccount(investment2);
            
            accountDAO.saveAccount(savings2, customer2.getIDNumber());
            accountDAO.saveAccount(investment2, customer2.getIDNumber());
            
            // Create accounts for customer3
            ChequeAccount cheque3 = new ChequeAccount("Peter Mokoena", 1006, 1500.0, "South Branch", 500.0,
                                                      "Mokoena Trading", "456 Market Street, Maun");
            InvestmentAccount investment3 = new InvestmentAccount("Peter Mokoena", 1007, 12000.0, "South Branch", 500.0);
            
            customer3.addAccount(cheque3);
            customer3.addAccount(investment3);
            
            accountDAO.saveAccount(cheque3, customer3.getIDNumber());
            accountDAO.saveAccount(investment3, customer3.getIDNumber());
            
            // Update customers with their accounts
            customerDAO.updateCustomer(customer1);
            customerDAO.updateCustomer(customer2);
            customerDAO.updateCustomer(customer3);
            
            // Create sample login credentials
            try {
                UserCredentials creds1 = new UserCredentials("johndoe", "password123", "ID001");
                UserCredentials creds2 = new UserCredentials("janesmith", "password123", "ID002");
                UserCredentials creds3 = new UserCredentials("peterm", "password123", "ID003");
                
                // Only save if they don't already exist
                if (credentialsDAO.findByUsername("johndoe") == null) {
                    credentialsDAO.saveCredentials(creds1);
                }
                if (credentialsDAO.findByUsername("janesmith") == null) {
                    credentialsDAO.saveCredentials(creds2);
                }
                if (credentialsDAO.findByUsername("peterm") == null) {
                    credentialsDAO.saveCredentials(creds3);
                }
            } catch (IOException e) {
                System.err.println("Warning: Could not create sample credentials: " + e.getMessage());
            }
            
            System.out.println("Sample data initialized successfully!");
            System.out.println("Sample login credentials:");
            System.out.println("  Username: johndoe, Password: password123");
            System.out.println("  Username: janesmith, Password: password123");
            System.out.println("  Username: peterm, Password: password123");
            
        } catch (IOException e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        initializeSampleData();
    }
}

