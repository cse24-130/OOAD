import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String fullname;
    private String IDNumber;
    private String email;
    private String address;
    private String phoneNumber;
    private String gender;
    private List<Account> accounts;

    public Customer(String fullname, String IDNumber, String email, String address, 
                   String phoneNumber, String gender) {
        this.fullname = fullname;
        this.IDNumber = IDNumber;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("Account " + account.getAccNo() + " added for customer " + fullname);
    }

    // Additional useful methods
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts); // Return a copy to preserve encapsulation
    }

    public void displayAllAccounts() {
        System.out.println("Accounts for " + fullname + ":");
        for (Account account : accounts) {
            System.out.println(account.getDetails());
            System.out.println("------------------------");
        }
    }

    // Getters and Setters
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}