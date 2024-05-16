/* Subclass of BankAccount */

public class CheckingAccount extends BankAccount {
    // Default Constructor
    CheckingAccount() {}
    
    // Specific Constructor
    CheckingAccount(double depositAmount) {
        super(depositAmount);
    }

    // Returns checking account information
    public String toString() {
        return "***Golden Bank Official Checking Account Information***\n" +
        super.toString();
    }
}