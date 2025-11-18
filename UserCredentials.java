/**
 * Represents user login credentials
 */
public class UserCredentials {
    private String username;
    private String password;
    private String customerId; // Links to Customer IDNumber
    
    public UserCredentials(String username, String password, String customerId) {
        this.username = username;
        this.password = password;
        this.customerId = customerId;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    /**
     * Check if password matches
     */
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}

