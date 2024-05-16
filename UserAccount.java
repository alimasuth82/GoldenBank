public class UserAccount {
    // Instance variables
    private String name;
    private String dateOfBirth;
    private String streetAddress;

    private String username;
    private String password;
    private static int numOfUserAccounts;

    // Bank Accounts and Card Accounts
    private BankAccount[] allBankAccounts;
    private CardAccount[] allCardAccounts;
    private int numBankAccounts;
    private int numCardAccounts;
    public static final int MAX_ACCOUNTS = 5; 

    // Default Constructor
    UserAccount() {}

    // Specific Constructor
    UserAccount(String name, String dateOfBirth, String streetAddress, String username, String password) {
        setName(name);
        setDateOfBirth(dateOfBirth);
        setStreetAddress(streetAddress);
        setUsername(username);
        setPassword(password);

        // Initialize the array of bank accounts
        allBankAccounts = new BankAccount[MAX_ACCOUNTS];
        // CardAccount Initializaion
        allCardAccounts = new CardAccount[MAX_ACCOUNTS];
        numBankAccounts = 0;
        numOfUserAccounts++;
    }


    // Accessors
    public String getName() { return this.name; }
    public String getDateOfBirth() { return this.dateOfBirth; }
    public String getStreetAddress() { return this.streetAddress; }
    
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }

    public static int getNumOfUserAccounts() { return numOfUserAccounts; }
    public int getNumBankAccounts() { return this.numBankAccounts; }
    public int getNumCardAccounts() { return this.numCardAccounts; }
    public BankAccount[] getAllBankAccounts() { return this.allBankAccounts; }

    public CardAccount[] getAllCardAccounts() { return this.allCardAccounts; }

    // Mutators
    public void setName(String name) {
        /* Checks to see if name is not empty and doesn't contain any numbers or special characters */

        // Check if the input string is empty
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
    
        // Check each character in the name
        for (int i = 0; i < name.length(); i++) {
            // Check if the character is not a letter or a whitespace
            if (!Character.isLetter(name.charAt(i)) && !Character.isWhitespace(name.charAt(i))) {
                throw new IllegalArgumentException("Name cannot contain any numbers or special characters.");
            }
        }
    
        this.name = name;
    }       

    public void setDateOfBirth(String dateOfBirth) {
        // Check if date of birth is null or empty
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            throw new IllegalArgumentException("Date of birth has to be provided.");
        }
        this.dateOfBirth = dateOfBirth;
    }

    public void setStreetAddress(String streetAddress) {
        // Check if street address is null or empty
        if (streetAddress == null || streetAddress.isEmpty()) {
            throw new IllegalArgumentException("Street address cannot be empty.");
        }

        this.streetAddress = streetAddress;
    }

    public void setUsername(String username) {
        // Check if username is null or empty
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
    
        this.username = username;
    }

    public void setPassword(String password) {
        /* Checks to see if password contains at least 2 digits */

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

    /* Adds a bank account */
    public void addBankAccount(BankAccount bankAccount) {
        if (numBankAccounts < MAX_ACCOUNTS) {
            allBankAccounts[numBankAccounts] = bankAccount;
            numBankAccounts++;
        } else {
            throw new IllegalArgumentException("Cannot add more bank accounts. Maximum limit reached.");
        }
    }
    
    /* Adds a card account */
    public void addCardAccount(CardAccount cardAccount) {
        if (numCardAccounts < MAX_ACCOUNTS) {
            allCardAccounts[numCardAccounts] = cardAccount; 
            numCardAccounts++;           
        } else {
            throw new IllegalArgumentException("Cannot add more Card accounts. Maximum limit reached.");
        }
    }

    /* Deletes a bank account */
    public void deleteBankAccount(String searchQuery) {
        boolean found = false;
        for (int i = 0; i < numBankAccounts; i++) {
            if (allBankAccounts[i].getAccountID().equals(searchQuery)) {
                // Shift all elements to the left to remove the bank account
                for (int j = i; j < numBankAccounts - 1; j++) {
                    allBankAccounts[j] = allBankAccounts[j + 1];
                }
                // Set the last element to null to remove the reference
                allBankAccounts[numBankAccounts - 1] = null;
                numBankAccounts--;
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Bank account with the provided ID not found.");
        }
    }    

    /* Deletes a card account */
    public void deleteCardAccount(String searchQuery) {
        boolean found = false;
        for (int i = 0; i < numCardAccounts; i++) {
            if (allCardAccounts[i].getUsername().equalsIgnoreCase(searchQuery)) {
                // Shift all elements to the left to remove the card account
                for (int j = i; j < numCardAccounts - 1; j++) {
                    allCardAccounts[j] = allCardAccounts[j + 1];
                }
                // Set the last element to null to remove the reference
                allCardAccounts[numCardAccounts - 1] = null;
                numCardAccounts--;
                found = true;
                break;
            }             
        }
        if (!found) {
            throw new IllegalArgumentException("Card account with the provided name not found.");
        }
    }

    /* Returns information of an individual */
    public String toString() {
        return "***Information of Individual***\n\n" +

        "Full Name: " + this.getName() + "\n" +
        "Date of Birth: " + this.getDateOfBirth() + "\n" +
        "Street Address: " + this.getStreetAddress() + "\n\n" +

        "Declared Username: " + this.getUsername() + "\n" +
        "Declared Password: " + this.getPassword();
    }
}