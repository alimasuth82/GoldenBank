public class SavingsAccount extends BankAccount {
    // Instance Variables
    private double preferredInterestRate;
    private double minimumBalance;
    private int numOfWithdrawalsPermitted;

    // Default Constructor
    SavingsAccount() {}

    // Specific Constructor
    SavingsAccount(double currentBalance, double preferredInterestRate, double minimumBalance, int numOfWithdrawalsPermitted) {
        super(currentBalance);
        setPreferredInterestRate(preferredInterestRate);
        setMinimumBalance(minimumBalance);
        setNumOfWithdrawalsPermitted(numOfWithdrawalsPermitted);
    }

    // Mutators
    public void setPreferredInterestRate(double preferredInterestRate) {
        if (preferredInterestRate < 0) {
            throw new IllegalArgumentException("Preferred interest rate cannot be negative.");
        }
        this.preferredInterestRate = preferredInterestRate;
    }

    public void setMinimumBalance(double minimumBalance) {
        if (minimumBalance < 0) {
            throw new IllegalArgumentException("Minimum balance cannot be negative.");
        }
        this.minimumBalance = minimumBalance;
    }

    public void setNumOfWithdrawalsPermitted(int numOfWithdrawalsPermitted) {
        if (numOfWithdrawalsPermitted < 0) {
            throw new IllegalArgumentException("Number of withdrawals permitted cannot be negative.");
        }
        this.numOfWithdrawalsPermitted = numOfWithdrawalsPermitted;
    }

    // Accessors
    public double getPreferredInterestRate() { return this.preferredInterestRate; }
    public double getMinimumBalance() { return this.minimumBalance; }
    public int getNumOfWithdrawalsPermitted() { return this.numOfWithdrawalsPermitted; }


    // Special Purpose Methods

    /* Decreases the number of withdrawals allowed at each time a withdrawal has been made */
    public void decrementNumOfWithdrawalsPermitted() {
        this.numOfWithdrawalsPermitted--;
    }

    /* Returns a boolean if the number of withdrawals is 0 or not */
    public boolean isWithdrawalAllowed(double withdrawalAmount) {
        return (numOfWithdrawalsPermitted > 0);
    }

    /* Returns savings account information */
    public String toString() {
        return "***Golden Bank Official Savings Account Information***\n" +
                super.toString() + "\n" +
               "Preferred Interest Rate: " + this.getPreferredInterestRate() + "\n" +
               "Minimum Balance: $" + this.getMinimumBalance() + "\n" +
               "Number of Withdrawals Permitted: " + this.getNumOfWithdrawalsPermitted();
    }
}