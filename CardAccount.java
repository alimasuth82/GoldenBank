/**
 * Abstract class representing a generic card account.
 */
public abstract class CardAccount {
    // Constant for maximum number of card accounts
    public static final int MAX_CARD_ACCOUNTS = 10;
    
    // Instance variables
    private String name;
    private String dateOfBirth;
    private String streetAddress;
    private String username;
    private String password;
    private static int numOfUserAccounts;

    // Constructors
    
    /**
     * Default constructor.
     */
    public CardAccount(){ }
    
    /**
     * Parameterized constructor to initialize card account details.
     * 
     * @param name The name of the account holder.
     * @param dateOfBirth The date of birth of the account holder.
     * @param streetAddress The street address of the account holder.
     * @param username The username for the account.
     * @param password The password for the account.
     */
    public CardAccount(String name, String dateOfBirth, String streetAddress, String username, String password) {
        setName(name);
        setDateOfBirth(dateOfBirth);
        setStreetAddress(streetAddress);
        setUsername(username);
        setPassword(password);
        numOfUserAccounts++;
    }

    // Accessors
    
    /**
     * Getter for the name of the account holder.
     * 
     * @return The name of the account holder.
     */
    public String getName() { return this.name; }
    
    /**
     * Getter for the date of birth of the account holder.
     * 
     * @return The date of birth of the account holder.
     */
    public String getDateOfBirth() { return this.dateOfBirth; }
    
    /**
     * Getter for the street address of the account holder.
     * 
     * @return The street address of the account holder.
     */
    public String getStreetAddress() { return this.streetAddress; }
    
    /**
     * Getter for the username of the account.
     * 
     * @return The username of the account.
     */
    public String getUsername() { return this.username; }
    
    /**
     * Getter for the password of the account.
     * 
     * @return The password of the account.
     */
    public String getPassword() { return this.password; }

    /**
     * Static method to get the total number of user accounts.
     * 
     * @return The total number of user accounts.
     */
    public static int getNumOfUserAccounts() { return numOfUserAccounts; }

    // Mutators
    
    /**
     * Setter for the name of the account holder.
     * 
     * @param name The name of the account holder.
     */
    public void setName(String name) {
        // Validate the name
        for (int i = 0; i < name.length(); i++) {
            // Check if the character is not a letter or a whitespace
            if (!Character.isLetter(name.charAt(i)) && !Character.isWhitespace(name.charAt(i))) {
                throw new IllegalArgumentException("The name cannot contain any numbers or special characters.");
            }
        }

        this.name = name;
    }    

    /**
     * Setter for the date of birth of the account holder.
     * 
     * @param dateOfBirth The date of birth of the account holder.
     */
    public void setDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            throw new IllegalArgumentException("Date of birth has to be provided.");
        }
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Setter for the street address of the account holder.
     * 
     * @param streetAddress The street address of the account holder.
     */
    public void setStreetAddress(String streetAddress) {
        if (streetAddress == null || streetAddress.isEmpty()) {
            throw new IllegalArgumentException("Street address cannot be empty.");
        }

        this.streetAddress = streetAddress;
    }

    /**
     * Setter for the username of the account.
     * 
     * @param username The username for the account.
     */
    public void setUsername(String username) {
        // Check if username is null or empty
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
    
        this.username = username;
    }

    /**
     * Setter for the password of the account.
     * 
     * @param password The password for the account.
     */
    public void setPassword(String password) {
        // Counter to keep track of the number of digits in the password
        int digitCount = 0;
    
        // Iterate through each character in the password
        for (int i = 0; i < password.length(); i++) {
            // Get the character at index i
            char c = password.charAt(i);
    
            // Check if the character is a digit
            if (Character.isDigit(c)) {
                // If it's a digit, increment the digit count
                digitCount++;
            }
        }
    
        // Check if the digit count is less than 2
        if (digitCount < 2) {
            throw new IllegalArgumentException("Password must contain at least 2 digits.");
        }
    
        // If the password meets the conditions, set it
        this.password = password;
    }

    // Special Purpose Methods

    /**
     * Abstract method to process transactions specific to each type of card account.
     */
    public abstract void processTransaction();

    /**
     * Method to return a string representation of the card account details.
     * 
     * @return A string containing the card account details.
     */
    public String toString() {
        return "[ ######## Card Account Class Information of Individual ####### ]\n\n" +
        "Full Name: " + this.getName() + "\n" +
        "Date of Birth: " + this.getDateOfBirth() + "\n" +
        "Street Address: " + this.getStreetAddress() + "\n\n" +
        "Declared Username: " + this.getUsername() + "\n" +
        "Declared Password: " + this.getPassword() +
        "\nTotal Card Account number: "+ getNumOfUserAccounts();
    }
}
