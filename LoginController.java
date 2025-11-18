import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for the Login View
 * Handles user authentication, registration, and navigation to account management
 */
public class LoginController {
    // Login fields
    @FXML
    private TextField loginUsernameField;
    
    @FXML
    private PasswordField loginPasswordField;
    
    // Registration fields
    @FXML
    private TextField regFullNameField;
    
    @FXML
    private TextField regIdNumberField;
    
    @FXML
    private TextField regEmailField;
    
    @FXML
    private TextField regAddressField;
    
    @FXML
    private TextField regPhoneField;
    
    @FXML
    private TextField regGenderField;
    
    @FXML
    private TextField regUsernameField;
    
    @FXML
    private PasswordField regPasswordField;
    
    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;
    private CredentialsDAO credentialsDAO;
    private Customer currentCustomer;
    
    public LoginController() {
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();
        this.credentialsDAO = new CredentialsDAO();
    }
    
    @FXML
    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = loginPasswordField.getText().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Login Error", "Please enter both username and password");
            return;
        }
        
        try {
            // Verify credentials
            UserCredentials credentials = credentialsDAO.verifyCredentials(username, password);
            
            if (credentials != null) {
                // Load customer by ID
                Customer customer = customerDAO.findCustomerById(credentials.getCustomerId());
                
                if (customer != null) {
                    currentCustomer = customer;
                    openAccountView();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Customer account not found");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password");
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to access data: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleRegister() {
        // Get registration data
        String fullName = regFullNameField.getText().trim();
        String idNumber = regIdNumberField.getText().trim();
        String email = regEmailField.getText().trim();
        String address = regAddressField.getText().trim();
        String phone = regPhoneField.getText().trim();
        String gender = regGenderField.getText().trim();
        String username = regUsernameField.getText().trim();
        String password = regPasswordField.getText().trim();
        
        // Validate all fields
        if (fullName.isEmpty() || idNumber.isEmpty() || email.isEmpty() || 
            address.isEmpty() || phone.isEmpty() || gender.isEmpty() || 
            username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Registration Error", "Please fill in all fields");
            return;
        }
        
        // Validate username (should be unique)
        try {
            if (credentialsDAO.findByUsername(username) != null) {
                showAlert(Alert.AlertType.ERROR, "Registration Error", "Username already exists. Please choose another.");
                return;
            }
            
            // Check if customer with this ID already exists
            Customer existingCustomer = customerDAO.findCustomerById(idNumber);
            if (existingCustomer != null) {
                showAlert(Alert.AlertType.ERROR, "Registration Error", "A customer with this ID number already exists.");
                return;
            }
            
            // Create new customer
            Customer newCustomer = new Customer(fullName, idNumber, email, address, phone, gender);
            customerDAO.saveCustomer(newCustomer);
            
            // Create credentials
            UserCredentials credentials = new UserCredentials(username, password, idNumber);
            credentialsDAO.saveCredentials(credentials);
            
            // Clear registration fields
            clearRegistrationFields();
            
            showAlert(Alert.AlertType.INFORMATION, "Registration Success", 
                     "Registration successful! You can now login with your username and password.");
            
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "Failed to register: " + e.getMessage());
        }
    }
    
    private void clearRegistrationFields() {
        regFullNameField.clear();
        regIdNumberField.clear();
        regEmailField.clear();
        regAddressField.clear();
        regPhoneField.clear();
        regGenderField.clear();
        regUsernameField.clear();
        regPasswordField.clear();
    }
    
    private void openAccountView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountView.fxml"));
            Parent root = loader.load();
            
            AccountController accountController = loader.getController();
            accountController.setCurrentCustomer(currentCustomer);
            accountController.setDAOs(customerDAO, accountDAO);
            accountController.loadAccountData();
            
            Stage stage = (Stage) loginUsernameField.getScene().getWindow();
            Scene accountScene = new Scene(root, 900, 700);
            try {
                java.net.URL cssUrl = getClass().getResource("styles.css");
                if (cssUrl != null) {
                    accountScene.getStylesheets().add(cssUrl.toExternalForm());
                } else {
                    System.err.println("Warning: Could not find styles.css file");
                }
            } catch (Exception e) {
                System.err.println("Warning: Could not load stylesheet: " + e.getMessage());
            }
            stage.setScene(accountScene);
            stage.setTitle("Milli Banking - Account Management");
            stage.setResizable(true);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load account view: " + e.getMessage());
        }
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
