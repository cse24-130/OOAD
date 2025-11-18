import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Customer persistence using file system
 */
public class CustomerDAO {
    private static final String DATA_DIR = "data";
    private static final String CUSTOMER_FILE = DATA_DIR + File.separator + "customers.txt";
    
    public CustomerDAO() {
        // Create data directory if it doesn't exist
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Save a customer to file
     */
    public void saveCustomer(Customer customer) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMER_FILE, true))) {
            writer.println(serializeCustomer(customer));
        }
    }
    
    /**
     * Load all customers from file
     */
    public List<Customer> loadAllCustomers() throws IOException {
        List<Customer> customers = new ArrayList<>();
        File file = new File(CUSTOMER_FILE);
        
        if (!file.exists()) {
            return customers;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                Customer customer = deserializeCustomer(line);
                if (customer != null) {
                    customers.add(customer);
                }
            }
        }
        
        return customers;
    }
    
    /**
     * Find customer by ID number
     */
    public Customer findCustomerById(String idNumber) throws IOException {
        List<Customer> customers = loadAllCustomers();
        for (Customer customer : customers) {
            if (customer.getIDNumber().equals(idNumber)) {
                return customer;
            }
        }
        return null;
    }
    
    /**
     * Update customer in file
     */
    public void updateCustomer(Customer updatedCustomer) throws IOException {
        List<Customer> customers = loadAllCustomers();
        boolean found = false;
        
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getIDNumber().equals(updatedCustomer.getIDNumber())) {
                customers.set(i, updatedCustomer);
                found = true;
                break;
            }
        }
        
        if (found) {
            // Rewrite all customers
            try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMER_FILE, false))) {
                for (Customer customer : customers) {
                    writer.println(serializeCustomer(customer));
                }
            }
        }
    }
    
    /**
     * Serialize customer to string format
     */
    private String serializeCustomer(Customer customer) {
        return String.format("%s|%s|%s|%s|%s|%s",
            escape(customer.getFullname()),
            escape(customer.getIDNumber()),
            escape(customer.getEmail()),
            escape(customer.getAddress()),
            escape(customer.getPhoneNumber()),
            escape(customer.getGender())
        );
    }
    
    /**
     * Deserialize customer from string format
     */
    private Customer deserializeCustomer(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 6) {
                return new Customer(
                    unescape(parts[0]),
                    unescape(parts[1]),
                    unescape(parts[2]),
                    unescape(parts[3]),
                    unescape(parts[4]),
                    unescape(parts[5])
                );
            }
        } catch (Exception e) {
            System.err.println("Error deserializing customer: " + e.getMessage());
        }
        return null;
    }
    
    private String escape(String str) {
        if (str == null) return "";
        return str.replace("|", "\\|").replace("\n", "\\n");
    }
    
    private String unescape(String str) {
        if (str == null) return "";
        return str.replace("\\|", "|").replace("\\n", "\n");
    }
}

