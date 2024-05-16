/* Abstract Superclass */

public abstract class BankAccount {
    // Instance Variables
    private String accountID;
    private double currentBalance;

    // Default Constructor
    BankAccount() {}

    // Specific Constructor
    BankAccount(double depositAmount) {
        depositIntoAccount(depositAmount);
        generateAccountID();
    }

    // Accessors
    public String getAccountID() { return this.accountID; }
    public double getCurrentBalance() { return this.currentBalance; }


    // Special Purpose Methods

    /* Generates an Account ID (by generating a random number between 0-20) */
    public void generateAccountID() {
        int randomNumber = (int) (Math.random() * 21); // Generates a random integer between 0 and 20
        this.accountID = Integer.toString(randomNumber);
    }    
    
    /* Deposits money into the account */
    public void depositIntoAccount(double depositAmount) {
        if (depositAmount < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative.");
        }

        this.currentBalance += depositAmount;
    }

    /* Withdraws money from the account */
    public void withdrawFromAccount(double withdrawAmount) {
        if (withdrawAmount > this.getCurrentBalance()) {
            throw new IllegalArgumentException("The amount you entered is greater than your balance:\n" +
            "$" + String.format("%.2f", this.getCurrentBalance()));
        } else if (withdrawAmount < 0) {
            throw new IllegalArgumentException("Withdraw amount cannot be negative.");
        }

        this.currentBalance -= withdrawAmount;
    }

    /* Returns bank account information */
    public String toString() {
        return "Unique Bank Account ID: " + this.getAccountID() + "\n" +
        "Current Balance: $" + String.format("%.2f", this.getCurrentBalance());
    }
}
