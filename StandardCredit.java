/**
 * Class representing a Standard Credit Card Account, extending the CardAccount class.
 */
public class StandardCredit extends CardAccount {
    // Instance variables specific to StandardCredit class
    // private double balance; // This line is commented out since it's not being used
    private static int numOfCardAccounts = 0; // Static variable to track the number of StandardCredit accounts
    private static final int MAX_NUM_ACCOUNTS = 2; // Maximum number of accounts allowed
    public CreditCard[] aCreditCards = new CreditCard[MAX_NUM_ACCOUNTS]; // Array to store CreditCard objects
    public DebitCard[] aDebitCards = new DebitCard[MAX_NUM_ACCOUNTS]; // Array to store DebitCard objects

    /**
     * Default constructor.
     */
    public StandardCredit(){}

    /**
     * Parameterized constructor to initialize StandardCredit details.
     * 
     * @param name The name of the account holder.
     * @param dateOfBirth The date of birth of the account holder.
     * @param streetAddress The street address of the account holder.
     * @param username The username associated with the account.
     * @param password The password associated with the account.
     * @param creditCard The CreditCard object associated with the account.
     * @param debitCard The DebitCard object associated with the account.
     */
    public StandardCredit(String name, String dateOfBirth, String streetAddress, String username, String password, CreditCard creditCard, DebitCard debitCard){
        super(name, dateOfBirth, streetAddress, username, password);
        aCreditCards[0] = creditCard;
        aDebitCards[0] = debitCard;
        numOfCardAccounts ++;
    }

    /**
     * Getter for the array of CreditCard objects.
     * 
     * @return The array of CreditCard objects.
     */
    public CreditCard[] getaCreditCards() {
        return aCreditCards;
    }

    /**
     * Getter for the array of DebitCard objects.
     * 
     * @return The array of DebitCard objects.
     */
    public DebitCard[] getaDebitCards() {
        return aDebitCards;
    }

    /**
     * Getter for the number of StandardCredit accounts.
     * 
     * @return The number of StandardCredit accounts.
     */
    public static int getnumOfCardAccounts(){
        return numOfCardAccounts;
    }

    /**
     * Method to process a transaction for the StandardCredit account.
     * 
     * This method currently charges $200 to the associated CreditCard object.
     */
    @Override
    public void processTransaction() {
        // Implement transaction processing for standard credit accounts
        this.aCreditCards[0].charge(200);
    }

    /**
     * Method to generate a string representation of the StandardCredit account details.
     * 
     * @return A string containing the StandardCredit account details.
     */
    public String toString() {
        return "[******** Standard Card Class Information of Individual********]\n\n" +
        "Full Name: " + super.getName() + "\n" +
        "Date of Birth: " + super.getDateOfBirth() + "\n" +
        "Street Address: " + super.getStreetAddress() + "\n\n"+
        aCreditCards[0].toString()+"\n"+
        aDebitCards[0].toString()+"\n"+
        "Declared Username: " + super.getUsername() + "\n" +
        "Declared Password: " + super.getPassword() + 
        "\nTotal Card Account number: "+ super.getNumOfUserAccounts();
    }
}
