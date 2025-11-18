import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for user credentials stored in customerlog.txt
 */
public class CredentialsDAO {
    private static final String DATA_DIR = "data";
    private static final String CREDENTIALS_FILE = DATA_DIR + File.separator + "customerlog.txt";
    
    public CredentialsDAO() {
        // Create data directory if it doesn't exist
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Save credentials to file
     */
    public void saveCredentials(UserCredentials credentials) throws IOException {
        // Check if username already exists
        if (findByUsername(credentials.getUsername()) != null) {
            throw new IOException("Username already exists");
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(CREDENTIALS_FILE, true))) {
            writer.println(serializeCredentials(credentials));
        }
    }
    
    /**
     * Find credentials by username
     */
    public UserCredentials findByUsername(String username) throws IOException {
        File file = new File(CREDENTIALS_FILE);
        
        if (!file.exists()) {
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                UserCredentials creds = deserializeCredentials(line);
                if (creds != null && creds.getUsername().equals(username)) {
                    return creds;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Verify login credentials
     */
    public UserCredentials verifyCredentials(String username, String password) throws IOException {
        UserCredentials creds = findByUsername(username);
        if (creds != null && creds.checkPassword(password)) {
            return creds;
        }
        return null;
    }
    
    /**
     * Load all credentials
     */
    public List<UserCredentials> loadAllCredentials() throws IOException {
        List<UserCredentials> credentials = new ArrayList<>();
        File file = new File(CREDENTIALS_FILE);
        
        if (!file.exists()) {
            return credentials;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                UserCredentials creds = deserializeCredentials(line);
                if (creds != null) {
                    credentials.add(creds);
                }
            }
        }
        
        return credentials;
    }
    
    /**
     * Serialize credentials to string format
     */
    private String serializeCredentials(UserCredentials credentials) {
        return String.format("%s|%s|%s",
            escape(credentials.getUsername()),
            escape(credentials.getPassword()),
            escape(credentials.getCustomerId())
        );
    }
    
    /**
     * Deserialize credentials from string format
     */
    private UserCredentials deserializeCredentials(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length >= 3) {
                return new UserCredentials(
                    unescape(parts[0]),
                    unescape(parts[1]),
                    unescape(parts[2])
                );
            }
        } catch (Exception e) {
            System.err.println("Error deserializing credentials: " + e.getMessage());
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

