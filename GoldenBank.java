/* 
    Ali Masuth and Sungho Hong
    April 29, 2024
    IT-206-003
    Golden Bank - Final Project
*/

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GoldenBank {
    public static void main(String[] args) {
        // An official Golden Bank Logo to be displayed in the main menu
        ImageIcon logo = new ImageIcon("icons/logo.png");

        // An array containing all user accounts, with a maximum capacity of 100 accounts allowed
        final int MAX_NUM_ACCOUNTS = 100;
        UserAccount[] allUserAccounts = new UserAccount[MAX_NUM_ACCOUNTS];
        

        /* The main menu, where the user can choose to create an account, sign in, or quit. */
        int userOption = 0;
        boolean quit = false;
        do {
            String[] userChoices = {"Create Account", "Sign In", "Quit"};
            userOption = JOptionPane.showOptionDialog(
                null,
                "Welcome to Golden Bank!\n" +
                "Please select an option to get started:",
                "Golden Bank",
                userOption,
                JOptionPane.QUESTION_MESSAGE,
                logo,
                userChoices,
                userChoices[0]
            );

            
            // Switch case that calls method based on user input
            switch (userOption) {
                case 0:
                    createUserAccount(allUserAccounts);
                    break;
                case 1:
                    signIn(allUserAccounts);
                    break;
                default:
                    quit = true;
                    break;
            }

        } while (!quit);
        System.exit(0);
    }

    /* Method to be reused for gathering user input */
    private static String getInput(String promptMessage, String dialogTitle) {
        String input = JOptionPane.showInputDialog(null, promptMessage, dialogTitle, JOptionPane.QUESTION_MESSAGE);
        return input;
    }
    
    /* Create User Account */
    private static void createUserAccount(UserAccount[] allUserAccounts) {
        // "Check" icon to display after successful creation of the user account
        ImageIcon check = new ImageIcon("icons/check.png");

        // Initialize user account
        allUserAccounts[UserAccount.getNumOfUserAccounts()] = new UserAccount();
    
        // All defined variables within the user account
        String name = "";
        String dateOfBirth = "";
        String streetAddress = "";
        String username = "";
        String password = "";
    
        boolean validInput = false;
    
        // Input for Name
        do {
            try {
                name = getInput("To begin, please enter your full name:", "Create Account");
                if (name == null) {
                    return; // Account creation is stopped if user clicks "cancel"
                }
                allUserAccounts[UserAccount.getNumOfUserAccounts()].setName(name);
    
                validInput = true;
            } catch (IllegalArgumentException ex) {
                // Display message if the name is empty or contains any special characters or numbers
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Input for Date of Birth
        validInput = false;
        do {
            try {
                dateOfBirth = getInput("Next, please enter your date of birth:", "Create Account");
                if (dateOfBirth == null) {
                    return; // Account creation is stopped if user clicks "cancel"
                }
                allUserAccounts[UserAccount.getNumOfUserAccounts()].setDateOfBirth(dateOfBirth);
    
                validInput = true;
            } catch (IllegalArgumentException ex) {
                // Display message if the date of birth is empty
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Input for Street Address
        validInput = false;
        do {
            try {
                streetAddress = getInput("Enter your street address:", "Create Account");
                if (streetAddress == null) {
                    return; // Account creation is stopped if user clicks "cancel"
                }
                allUserAccounts[UserAccount.getNumOfUserAccounts()].setStreetAddress(streetAddress);
    
                validInput = true;
            } catch (IllegalArgumentException ex) {
                // Display message if the street address is empty
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Input for Username
        validInput = false;
        boolean usernameExists;
        do {
            try {
                username = getInput("Enter a unique username for your account:", "Create Account");
                if (username == null) {
                    return; // User canceled account creation
                }
    
                // Checks if the entered username already exists. If it does, the user will be reprompted
                usernameExists = checkUsernameExists(username, allUserAccounts);
                if (usernameExists) {
                    JOptionPane.showMessageDialog(null, "Username already exists. Please try again.",
                    "Username exists", JOptionPane.WARNING_MESSAGE);
                } else {
                    allUserAccounts[UserAccount.getNumOfUserAccounts()].setUsername(username);
                    validInput = true;
                }
            } catch (IllegalArgumentException ex) {
                // Display message if the username is empty
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Input for Password
        validInput = false;
        do {
            try {
                // User will enter their password twice to confirm
                password = getInput("Enter your password (must contain at least 2 digits):", "Create Account");
                if (password == null) {
                    return; // User canceled account creation
                }
    
                String confirmPassword = getInput("Confirm your password:", "Create Account");
                if (confirmPassword == null) {
                    return;
                }
    
                // Check if password matches confirmPassword
                if (password.equals(confirmPassword)) {
                    allUserAccounts[UserAccount.getNumOfUserAccounts()].setPassword(password);
                    validInput = true;
                } else {
                    JOptionPane.showMessageDialog(null, "The password does not match. Please try again.",
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                // Display error if at least two digits are not entered
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // If all inputs are valid, create the UserAccount object and display a success message of the user's information
        int index = UserAccount.getNumOfUserAccounts();
        allUserAccounts[index] = new UserAccount(name, dateOfBirth, streetAddress, username, password);
    
        JOptionPane.showMessageDialog(null, allUserAccounts[index].toString() + "\n\n" +
        "Please log in using your credentials.",
        "Account Creation Success", JOptionPane.INFORMATION_MESSAGE, check);
    }      

    /* A method that compares the username with all accounts created and returns a boolean value accordingly */
    public static boolean checkUsernameExists(String username, UserAccount[] allUserAccounts) {
        boolean usernameExists = false;
        for (int i = 0; i < UserAccount.getNumOfUserAccounts(); i++) {
            if (username.equals(allUserAccounts[i].getUsername())) {
                usernameExists = true;
                break;
            }
        }

        return usernameExists;
    }


    /* Signing In */
    private static void signIn(UserAccount[] allUserAccounts) {
        // To login, users must first enter their username and password

        String usernameToLogin = getInput("Golden Bank Sign In\n" +
        "Enter your username:", "Sign In");
        if (usernameToLogin == null) {
            return;
        }

        String passwordToLogin = getInput("Golden Bank Sign In\n" +
        "Enter your password:", "Sign In");
        if (passwordToLogin == null) {
            return;
        }


        // Compares the entered username and password to all established accounts
        // The account index will also be set in order for the program to access the intended account in the array specifically
        boolean accountFound = false;
        int accountIndex = 0;
        for (int i = 0; i < UserAccount.getNumOfUserAccounts(); i++) {
            if (usernameToLogin.equals(allUserAccounts[i].getUsername()) &&
            passwordToLogin.equals(allUserAccounts[i].getPassword())) {
                accountFound = true;
                accountIndex = i;
                break;
            }
        }

        // Account menu will open once the account is found, or the error message will display
        if (accountFound) {
            accountMenu(allUserAccounts, accountIndex);
        } else {
            JOptionPane.showMessageDialog(null, "The username or password you entered was incorrect.",
            "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }


    /* User Account Menu with a Set of Options: 
        1. Create Bank Account
        2. Delete Bank Account
        3. Access Bank Accounts
        4. Create a Card
        5. Sign Out
    */
    private static void accountMenu(UserAccount[] allUserAccounts, int accountIndex) {
        ImageIcon check = new ImageIcon("icons/check.png");

        // All user choices
        String[] userChoices = {"Create Bank Account", "Delete Bank Account", "Access Bank Accounts", "Create a Card", "Sign Out"};
        int userOption;
    
        boolean signOut = false;
        do {
            // Get user option
            userOption = JOptionPane.showOptionDialog(
                null,
                "Please select an option:",
                "Account Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                userChoices,
                userChoices[0]
            );
            
            // Handle user's choice
            switch (userOption) {
                case 0:
                    createBankAccount(allUserAccounts, accountIndex, check);
                    break;
                case 1:
                    deleteBankAccount(allUserAccounts, accountIndex, check);
                    break;
                case 2:
                    accessBankAccounts(allUserAccounts, accountIndex, check);
                    break;
                case 3:
                    addCardAccount(allUserAccounts, accountIndex, check);
                    break;
                default:
                    // Prompts the user before signing out
                    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to sign out?", 
                    "Signing Out", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        signOut = true;
                    }
                    break;
            }
        } while (!signOut);
    }

    /* Returns a specific account type the user has selected */
    private static int getAccountType(String promptMessage, String dialogTitle) {
        String[] userChoices = {"Checking Account", "Savings Account", "Loan Account", "Back"};
        int userOption = JOptionPane.showOptionDialog(null, promptMessage, dialogTitle,
        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, userChoices, userChoices[0]);

        return userOption;
    }

    /* Menu allowing users to choose a type of bank account they wish to create */
    private static void createBankAccount(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        int userOption = 0;

        boolean back = false;
        do {
            // Get user option
            userOption = getAccountType("Select an account type you wish to create:", "Bank Account Creation");

            // Process user choice based on userOption value
            switch (userOption) {
                case 0:
                    createCheckingAccount(allUserAccounts, accountIndex, check);
                    break;
                case 1:
                    createSavingsAccount(allUserAccounts, accountIndex, check);
                    break;
                case 2:
                    createLoanAccount(allUserAccounts, accountIndex, check);
                    break;
                default:
                    back = true;
                    break;
            }
        } while (!back);
    }

    /* Method for creating and setting a Checking Account */
    private static void createCheckingAccount(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Create a new CheckingAccount instance
        CheckingAccount oneCheckingAccount = new CheckingAccount();
    
        // Initialize variables
        double depositAmount = 0;
        boolean validInput = false;
    
        // Loop until valid input is received
        do {
            try {
                // Prompt the user for the deposit amount
                String input = getInput("Enter the amount you wish to deposit:", "Create Checking Account");
                
                if (input == null) {
                    return; // User cancels the operation
                }
                
                // Parse the deposit amount from the input string
                depositAmount = Double.parseDouble(input);
                
                // Deposit the amount into the checking account
                oneCheckingAccount.depositIntoAccount(depositAmount);
    
                // Input is valid, exit the loop
                validInput = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input Type", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Overrides the CheckingAccount object with the deposited amount and adding it to the user's bank accounts
        oneCheckingAccount = new CheckingAccount(depositAmount);
        allUserAccounts[accountIndex].addBankAccount(oneCheckingAccount);
    
        // Display a success message with the account details
        JOptionPane.showMessageDialog(null, oneCheckingAccount.toString() + "\n\n" +
        "Please remember your bank account ID.\n" +
        "You will need it for further services such as deposit, withdrawing, etc.", "Success Message",
        JOptionPane.INFORMATION_MESSAGE, check);
    }

    /* Method for creating and setting a savings account */
    private static void createSavingsAccount(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Create a new SavingsAccount instance
        SavingsAccount oneSavingsAccount = new SavingsAccount();
    
        // Initialize all variables of the Savings Account
        double depositAmount = 0;
        double preferredInterestRate = 0;
        double minimumBalance = 0;
        int numOfWithdrawalsPermitted = 0;
        boolean validInput = false;
    
        // Prompt the user for the deposit amount
        do {
            try {
                String input = getInput("Enter the amount you wish to deposit", "Create Savings Account");
                // Check if the user canceled the operation
                if (input == null) {
                    return; // User canceled
                }
                // Parse the deposit amount from the input string
                depositAmount = Double.parseDouble(input);
                // Deposit the amount into the savings account
                oneSavingsAccount.depositIntoAccount(depositAmount);
                // Input is valid, exit the loop
                validInput = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input Type", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Prompt the user for the preferred interest rate
        validInput = false;
        do {
            try {
                String input = getInput("Enter your preferred interest rate:", "Create Savings Account");
                if (input == null) {
                    return; // User canceled
                }
                preferredInterestRate = Double.parseDouble(input);
                oneSavingsAccount.setPreferredInterestRate(preferredInterestRate);
                validInput = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input Type", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Prompt the user for the target minimum balance
        validInput = false;
        do {
            try {
                String input = getInput("Enter your target minimum balance:", "Create Savings Account");
                if (input == null) {
                    return; // User canceled
                }
                minimumBalance = Double.parseDouble(input);
                oneSavingsAccount.setMinimumBalance(minimumBalance);
                validInput = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input Type", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Prompt the user for the number of withdrawals permitted
        validInput = false;
        do {
            try {
                String input = getInput("Enter your desired number of withdrawals permitted:", "Create Savings Account");
                if (input == null) {
                    return; // User canceled
                }
                numOfWithdrawalsPermitted = Integer.parseInt(input);
                oneSavingsAccount.setNumOfWithdrawalsPermitted(numOfWithdrawalsPermitted);
                validInput = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input Type", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Overide the SavingsAccount object with the provided details and adding it to the user's bank accounts
        oneSavingsAccount = new SavingsAccount(depositAmount, preferredInterestRate, minimumBalance, numOfWithdrawalsPermitted);
        allUserAccounts[accountIndex].addBankAccount(oneSavingsAccount);
    
        // Display a success message with the account details
        JOptionPane.showMessageDialog(null, oneSavingsAccount.toString() + "\n\n" +
        "Please remember your bank account ID.\n" +
        "You will need it for further services such as deposit, withdrawing, etc.", "Success Message",
        JOptionPane.INFORMATION_MESSAGE, check);
    }

    /* Method for creating and setting a loan account */
    private static void createLoanAccount(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Create a new LoanAccount object
        LoanAccount oneLoanAccount = new LoanAccount();
    
        // Initialize variables
        double loanAmount = 0;
        boolean validInput = false;
    
        // Input for loan amount
        do {
            try {
                String input = getInput("Enter your loan amount:", "Create Loan Account");
                if (input == null) {
                    return; // User canceled
                }

                // Convert user input to double, then stores the loan amount
                loanAmount = Double.parseDouble(input);
                oneLoanAccount.setLoanAmount(loanAmount);
    
                validInput = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Input for interest rate
        double interestRate = 0;
        validInput = false;
        do {
            try {
                String input = getInput("Enter your interest rate:", "Create Loan Account");
                if (input == null) {
                    return; // User canceled
                }
                interestRate = Double.parseDouble(input);
                oneLoanAccount.setInterestRate(interestRate);
    
                validInput = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validInput);
    
        // Overide the LoanAccount object with the provided details and adding it to the user's bank accounts
        oneLoanAccount = new LoanAccount(loanAmount, interestRate);
        allUserAccounts[accountIndex].addBankAccount(oneLoanAccount);
    
        // Display a success message with the account details
        JOptionPane.showMessageDialog(null, oneLoanAccount.toString() + "\n\n" +
        "Please remember your bank account ID.\n" +
        "You will need it for further services such as deposit, withdrawing, etc.", "Success Message",
        JOptionPane.INFORMATION_MESSAGE, check);
    }


    /* Delete Bank Account */
    private static void deleteBankAccount(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Prompts the user to enter their Bank Account ID
        String searchQuery = getInput("Enter the ID of the bank account you wish to delete:", "Delete Bank Account");
        if (searchQuery == null) {
            return; // User canceled the process
        }

        try {
            // Deletes the bank account from the user
            allUserAccounts[accountIndex].deleteBankAccount(searchQuery);
            JOptionPane.showMessageDialog(null, "Bank Account successfully deleted!", 
            "Success Message", JOptionPane.INFORMATION_MESSAGE, check);
        } catch (IllegalArgumentException ex) {
            // Displays error message of Bank Account ID cannot be found
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }


    /* 
        Method to access the user's bank accounts, where the user first must select the type of account they 
        will access.
    */
    private static void accessBankAccounts(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        int userOption = 0;

        boolean back = false;
        do {
            userOption = getAccountType("Select the type of the account you wish to access:", "Access Bank Accounts");

            // Handle each user input accordingly
            switch (userOption) {
                case 0:
                    accessCheckingAccounts(allUserAccounts, accountIndex, check);
                    break;
                case 1:
                    accessSavingsAccounts(allUserAccounts, accountIndex, check);
                    break;
                case 2:
                    accessLoanAccounts(allUserAccounts, accountIndex, check);
                    break;
                default:
                    back = true;
                    break;
            }
        } while (!back);
    }

    /* 
        Displays all checking accounts created, as well as provide an option to deposit, withdraw, or display
        account details.
    */
    private static void accessCheckingAccounts(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Initialize variables
        boolean checkingAccountsExist = false;
        String displayCheckingAccounts = "***All Checking Accounts***\n";
    
        // Iterate through all bank accounts to find and display checking accounts
        for (int i = 0; i < allUserAccounts[accountIndex].getNumBankAccounts(); i++) {
            BankAccount account = allUserAccounts[accountIndex].getAllBankAccounts()[i];
            if (account instanceof CheckingAccount) {
                checkingAccountsExist = true;
                displayCheckingAccounts += "Name: " + allUserAccounts[accountIndex].getName() + "\n" +
                    "Bank Account ID: " + account.getAccountID() + "\n" +
                    "Current Balance: $" + account.getCurrentBalance() + "\n" +
                    "------------------------------------\n";
            }
        }
    
        // If no checking accounts were found, display a specific message
        if (!checkingAccountsExist) {
            displayCheckingAccounts += "(No checking accounts have been created yet.)\n";
        }
    
        displayCheckingAccounts += "Select an option:";
    
        // Define the options for the user
        String[] userChoices = {"Deposit", "Withdraw", "Display Account Details", "Back"};
        int userOption = 0;
        boolean back = false;
    
        // Loop to handle user interaction
        do {
            // Display a dialog box with the available options and capture the user's choice
            userOption = JOptionPane.showOptionDialog(null, displayCheckingAccounts, 
                "Access Checking Accounts", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, userChoices, userChoices[0]);
    
            // Perform actions based on the user's choice
            switch (userOption) {
                case 0:
                    deposit(allUserAccounts, accountIndex, check);
                    break;
                case 1:
                    withdraw(allUserAccounts, accountIndex, check);
                    break;
                case 2:
                    displayBankAccountDetails(allUserAccounts, accountIndex, check);
                    break;
                default:
                    back = true;
                    break;
            }
            
        } while (!back);
    }

    /* 
        Displays all saving accounts created, as well as provide an option to deposit, withdraw, or display
        account details.
    */
    private static void accessSavingsAccounts(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Initialize variables
        boolean savingsAccountsExist = false;
        String displaySavingsAccounts = "***All Savings Accounts***\n";
        
        // Iterate through all bank accounts to find and display all savings accounts
        for (int i = 0; i < allUserAccounts[accountIndex].getNumBankAccounts(); i++) {
            BankAccount account = allUserAccounts[accountIndex].getAllBankAccounts()[i];
            if (account instanceof SavingsAccount) {
                savingsAccountsExist = true;
                displaySavingsAccounts += "Name: " + allUserAccounts[accountIndex].getName() + "\n" +
                    "Bank Account ID: " + account.getAccountID() + "\n" +
                    "Current Balance: $" + account.getCurrentBalance() + "\n" +
                    "Number of Withdrawals Allowed: " + ((SavingsAccount) account).getNumOfWithdrawalsPermitted() + "\n" +
                    "------------------------------------\n";
            }
        }
    
        // If no savings accounts were found, display a specific message
        if (!savingsAccountsExist) {
            displaySavingsAccounts += "(No savings accounts have been created yet.)\n";
        }
    
        displaySavingsAccounts += "Select an option:";
        
        // Define the options for the user
        String[] userChoices = {"Deposit", "Withdraw", "Display Account Details", "Back"};
        int userOption = 0;
        boolean back = false;
    
        // Loop to handle user interaction
        do {
            // Display a dialog box with the available options and capture the user's choice
            userOption = JOptionPane.showOptionDialog(null, displaySavingsAccounts, 
            "Access Savings Accounts", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, userChoices, userChoices[0]);
    
            // Perform actions based on the user's choice
            switch (userOption) {
                case 0:
                    deposit(allUserAccounts, accountIndex, check);
                    break;
                case 1:
                    withdraw(allUserAccounts, accountIndex, check);
                    break;
                case 2:
                    displayBankAccountDetails(allUserAccounts, accountIndex, check);
                    break;
                default:
                    back = true;
                    break;
            }
        } while (!back);
    }
    
    /* 
        Displays all loan accounts created, and provides an option to pay off loan.
    */
    private static void accessLoanAccounts(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Initialize variables
        boolean loanAccountsExist = false;
        String displayLoanAccounts = "***All Loan Accounts***\n";
        
        // Iterate through all bank accounts to find and display loan accounts
        for (int i = 0; i < allUserAccounts[accountIndex].getNumBankAccounts(); i++) {
            BankAccount account = allUserAccounts[accountIndex].getAllBankAccounts()[i];
            if (account instanceof LoanAccount) {
                loanAccountsExist = true;
                displayLoanAccounts += "Name: " + allUserAccounts[accountIndex].getName() + "\n" +
                    "Loan Account ID: " + account.getAccountID() + "\n" +
                    "Current Balance: $" + ((LoanAccount)account).getTotalAmount() + "\n" +
                    "Interest Rate: " + ((LoanAccount) account).getInterestRate() + "%\n" +
                    "------------------------------------\n";
            }
        }
    
        // If no loan accounts were found, display a specific message
        if (!loanAccountsExist) {
            displayLoanAccounts += "(No loan accounts have been created yet.)\n";
        }
    
        displayLoanAccounts += "Select an option:";
        
        // Define the options for the user
        String[] userChoices = {"Repay Loan", "Display Account Details", "Back"};
        int userOption = 0;
        boolean back = false;
    
        // Loop to handle user interaction
        do {
            // Display a dialog box with the available options and capture the user's choice
            userOption = JOptionPane.showOptionDialog(null, displayLoanAccounts, 
                "Access Loan Accounts", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, userChoices, userChoices[0]);
    
            // Perform actions based on the user's choice
            switch (userOption) {
                case 0:
                    repayLoan(allUserAccounts, accountIndex, check);
                    break;
                case 1:
                    displayBankAccountDetails(allUserAccounts, accountIndex, check);
                    break;
                default:
                    back = true;
                    break;
            }
        } while (!back);
    }   

    /* Depositing into a bank account */
    private static void deposit(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Prompt the user to enter the bank account ID for deposit
        String searchQuery = getInput("Enter your Bank Account ID:", "Deposit");
        if (searchQuery == null) {
            return; // Stop operation if user selects "cancel"
        }
    
        // Search for the target bank account using the provided ID
        BankAccount targetAccount = null;
        for (int i = 0; i < allUserAccounts[accountIndex].getNumBankAccounts(); i++) {
            BankAccount account = allUserAccounts[accountIndex].getAllBankAccounts()[i];
            if (account.getAccountID().equals(searchQuery)) {
                targetAccount = account;
                break;
            }
        }
    
        // If the target account is found, proceed with the deposit
        if (targetAccount != null) {
            double depositAmount = 0;
            boolean validInput = false;
            do {
                try {
                    // Prompt the user to enter the amount to deposit
                    depositAmount = Double.parseDouble(getInput("Enter the amount to deposit:", "Deposit"));
                    // Deposit the specified amount into the target account
                    targetAccount.depositIntoAccount(depositAmount);
                    validInput = true;
                } catch (NumberFormatException ex) {
                    // Display an error message if the input is not a valid numerical value
                    JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    // Display an error message for other invalid input cases
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            } while (!validInput);
    
            // Display a success message after depositing the amount
            JOptionPane.showMessageDialog(null, "Amount of $" + depositAmount + " successfully deposited into account.\n\n" +
                targetAccount.toString(), "Deposit Success", JOptionPane.INFORMATION_MESSAGE, check);
        } else {
            // Display a message if the bank account with the provided ID is not found
            JOptionPane.showMessageDialog(null, "Bank account with the provided ID not found.", "ID Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /* Withdrawing from a bank account */
    private static void withdraw(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Prompt the user to enter the bank account ID for withdrawal
        String searchQuery = getInput("Enter your Bank Account ID:", "Withdraw");
        if (searchQuery == null) {
            return; // Stop operation if user selects "cancel"
        }
    
        // Search for the target bank account using the provided ID
        BankAccount targetAccount = null;
        for (int i = 0; i < allUserAccounts[accountIndex].getNumBankAccounts(); i++) {
            BankAccount account = allUserAccounts[accountIndex].getAllBankAccounts()[i];
            if (account.getAccountID().equals(searchQuery)) {
                targetAccount = account;
                break;
            }
        }
    
        // If the target account is found, proceed with the withdrawal
        if (targetAccount != null) {
            double withdrawAmount = 0;
            boolean validInput = false;
            do {
                try {
                    // Prompt the user to enter the amount to withdraw
                    withdrawAmount = Double.parseDouble(getInput("Enter the amount to withdraw:\n" +
                        "Your current balance is $" + targetAccount.getCurrentBalance(), "Withdraw"));
    
                    // Check if the withdrawal exceeds the number of permitted withdrawals for SavingsAccount
                    if (targetAccount instanceof SavingsAccount && !((SavingsAccount) targetAccount).isWithdrawalAllowed(withdrawAmount)) {
                        JOptionPane.showMessageDialog(null, "Withdrawal exceeds number of permitted withdrawals.", "Invalid Withdrawal", JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Withdraw the specified amount from the account
                        targetAccount.withdrawFromAccount(withdrawAmount);
                        // Decrement the number of permitted withdrawals for SavingsAccount
                        if (targetAccount instanceof SavingsAccount) {
                            ((SavingsAccount) targetAccount).decrementNumOfWithdrawalsPermitted();
                        }
                        validInput = true;
                    }
                } catch (NumberFormatException ex) {
                    // Display an error message if the input is not a valid numerical value
                    JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    // Display an error message for other invalid input cases
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            } while (!validInput);
    
            // Display a success message after withdrawing the amount
            JOptionPane.showMessageDialog(null, "Amount of $" + withdrawAmount + " successfully withdrawn from the account:\n\n" +
                targetAccount.toString(), "Withdrawal Success", JOptionPane.INFORMATION_MESSAGE, check);
        } else {
            // Display a message if the bank account with the provided ID is not found
            JOptionPane.showMessageDialog(null, "Bank account with the provided ID not found.", "ID Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /* Method to search by Bank Account ID to display its details */
    private static void displayBankAccountDetails(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Prompt the user to enter the bank account ID for displaying details
        String searchQuery = getInput("Enter your Bank Account ID to display:", "Display Account Details");
        if (searchQuery == null) {
            return; // Cancel operation if user input is null
        }
    
        // Search for the target bank account using the provided ID
        BankAccount targetAccount = null;
        for (int i = 0; i < allUserAccounts[accountIndex].getNumBankAccounts(); i++) {
            BankAccount account = allUserAccounts[accountIndex].getAllBankAccounts()[i];
            if (account.getAccountID().equals(searchQuery)) {
                targetAccount = account;
                break;
            }
        }
    
        // If the target account is found, display its details
        if (targetAccount != null) {
            JOptionPane.showMessageDialog(null, targetAccount.toString(), "Bank Account Details", JOptionPane.INFORMATION_MESSAGE,
            check);
        } else {
            // Display a message if the bank account with the provided ID is not found
            JOptionPane.showMessageDialog(null, "Bank account with the provided ID not found.", "ID Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }

    /* Repayment of a loan */
    private static void repayLoan(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Prompt the user to enter the bank account ID for loan repayment
        String searchQuery = getInput("Enter your Bank Account ID:", "Repay Loan");
        if (searchQuery == null) {
            return; // Stop operation if user selects "cancel"
        }
    
        // Search for the target bank account using the provided ID
        BankAccount targetAccount = null;
        for (int i = 0; i < allUserAccounts[accountIndex].getNumBankAccounts(); i++) {
            BankAccount account = allUserAccounts[accountIndex].getAllBankAccounts()[i];
            if (account.getAccountID().equals(searchQuery)) {
                targetAccount = account;
                break;
            }
        }
    
        // If the target account is found, proceed with loan repayment
        if (targetAccount != null) {
            double paymentAmount = 0;
            boolean validInput = false;
            do {
                try {
                    // Prompt the user to enter the repayment amount
                    paymentAmount = Double.parseDouble(getInput("Enter the amount to repay your loan:\n" +
                    "Your current balance is $" + ((LoanAccount)targetAccount).getTotalAmount(), "Repay Loan"));
                    
                    // Repay the loan amount
                    ((LoanAccount)targetAccount).payTotalAmount(paymentAmount);
                    validInput = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            } while (!validInput);
    
            // Display a success message after loan repayment
            JOptionPane.showMessageDialog(null, "Your loan has been repaid!\n" +
            "Current Balance: $" + String.format("%.2f", ((LoanAccount)targetAccount).getTotalAmount()), "Repayment Success", JOptionPane.INFORMATION_MESSAGE, check);
        }
    }


    /************************************ */
    /****** Methods for Card function ********/   
    /*********************************** */

    /**
     * Method to select the type of card using a JOptionPane dialog.
     * 
     * @param message The message to be displayed in the dialog.
     * @param dialogTitle The title of the dialog window.
     * @return An integer representing the user's selection.
     */
    private static int selectCardType(String message, String dialogTitle) {
        String[] userChoices = {"Standard Membership", "Gold Membership","Delete Membership", "Awards Points" , "Back"};
        int userOption = JOptionPane.showOptionDialog(null, message, dialogTitle,
        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, userChoices, userChoices[0]);

        return userOption;
    }

    /**
    * Method to add a card account.
    * 
    * @param allUserAccounts An array of UserAccount objects containing all user accounts.
    * @param accountIndex The index of the current user account in the allUserAccounts array.
    * @param check An ImageIcon object representing a check mark icon.
    */
    private static void addCardAccount(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        int userOption = 0;

        boolean back = false;
        // Loop until 'Back' option is chosen
        do {
            // Select card type using JOptionPane
            userOption = selectCardType("Select a Card type you wish to create:", "Bank Card Account Creation");

            // Process user choice based on userOption value
            switch (userOption) {
                case 0:
                    AddCard(allUserAccounts, accountIndex, check, userOption);
                    break;
                case 1:
                    AddCard(allUserAccounts, accountIndex, check, userOption);
                    break;
                case 2:
                    deleteCardAccount(allUserAccounts, accountIndex, check);
                    break;
                case 3:
                    AwardsPoints(allUserAccounts, accountIndex, check);
                    break;
                default:
                    back = true;
                    break;
            }
        } while (!back);
    }

    /**
    * Method to add a specific type of card (Standard Membership or Gold Membership).
    * 
    * @param allUserAccounts An array of UserAccount objects containing all user accounts.
    * @param index The index of the current user account in the allUserAccounts array.
    * @param check An ImageIcon object representing a check mark icon.
    * @param type An integer representing the type of card (0 for Standard Membership, 1 for Gold Membership).
    */
    private static void AddCard(UserAccount[] allUserAccounts, int index, ImageIcon check, int type) {
        CreditCard acreditCard = new CreditCard();
        DebitCard adebitCard = new DebitCard();

        double depositAmount = 0;
        boolean validInput = false;

            // Loop until valid input is provided
            do {
                try {
                    String input = getInput("Enter the amount you wish to deposit:", "Create a Card Account");
                    if (input == null) {
                        return;
                    }

                    depositAmount = Double.parseDouble(input);

                    if (depositAmount < 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a value greater than $0.",
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input Type", JOptionPane.WARNING_MESSAGE);
                }
            } while (!validInput);


            // ViSA or Master Card selection
            String[] Choices = {"Visa", "Master"};
            String company="";
            int cardType = JOptionPane.showOptionDialog(null, "Choose card company", "Card Type",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Choices, Choices[0]); 
            if(cardType==0) company = Choices[0];
            else company = Choices[1];


            // If type == 0 is  Addtion of Standard Membership DebitCard, instantiing new Standard Card 
            // else GoldCard Membership 
            if(type == 0){ 
                adebitCard = new DebitCard(depositAmount, company); //  DebitCard instantiation
                acreditCard = new CreditCard(depositAmount, company, 3000.0, 0.05);
                
                /** 4/20 3:13 PM */
                CardAccount acardAccount = new StandardCredit(allUserAccounts[index].getName(), allUserAccounts[index].getDateOfBirth(), allUserAccounts[index].getStreetAddress(),
                                                    allUserAccounts[index].getUsername(), allUserAccounts[index].getPassword(),acreditCard, adebitCard);
            
                allUserAccounts[index].addCardAccount(acardAccount);
                try {
                        JOptionPane.showMessageDialog(null, acardAccount.toString() + "\n\n" +
                        "Please remember your bank account ID.\n" +
                        "You will need it for furthur services, etc.", "Success Message",
                        JOptionPane.INFORMATION_MESSAGE, check);
            
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Max Limit Reached", JOptionPane.WARNING_MESSAGE);
                }  

            }else{  // else Addition Gold Membership Credit and Debit card. 

                double creditLimit = 0;
                validInput = false;
                do {
                    try {
                        String input = getInput("Enter the CreditLimit greater than $5000:", "Create a Golden Card Account");
                        if (input == null) {
                            return;
                        }

                        creditLimit = Double.parseDouble(input);
                        if (creditLimit <= 5000) {
                            JOptionPane.showMessageDialog(null, "Please enter a value greater than or equal to $5000.",
                                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
                        } else {
                            validInput = true;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input Type", JOptionPane.WARNING_MESSAGE);
                    }
                } while (!validInput);

                double interestRate = 0;
                validInput = false;
                do {
                    try {
                        String input = getInput("Enter the Interest Rate:", "Create a Golden Card Account");
                        if (input == null) {
                            return;
                        }

                        interestRate = Double.parseDouble(input);
                        if (interestRate < 0.02) {
                            JOptionPane.showMessageDialog(null, "Please enter a value greater than or equal to 0.02%.",
                                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
                        } else {
                            validInput = true;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input Type", JOptionPane.WARNING_MESSAGE);
                    }
                } while (!validInput);

                adebitCard = new DebitCard(depositAmount, company); //  DebitCard instantiation
                acreditCard = new CreditCard(depositAmount, company, creditLimit, interestRate);
                CardAccount acardAccount = new GoldCredit(allUserAccounts[index].getName(), allUserAccounts[index].getDateOfBirth(), allUserAccounts[index].getStreetAddress(),
                                                        allUserAccounts[index].getUsername(), allUserAccounts[index].getPassword(), acreditCard, adebitCard);
                
                allUserAccounts[index].addCardAccount(acardAccount);
                try {
                    JOptionPane.showMessageDialog(null, acardAccount.toString() + "\n\n" +
                    "Please remember your bank account ID.\n" +
                    "You will need it for furthur services, etc.", "Success Message",
                    JOptionPane.INFORMATION_MESSAGE, check);

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Max Limit Reached", JOptionPane.WARNING_MESSAGE);
                }   
                
            }
    }

    /**
    * Method to delete a card account.
    * 
    * @param allUserAccounts An array of UserAccount objects containing all user accounts.
    * @param accountIndex The index of the current user account in the allUserAccounts array.
    * @param check An ImageIcon object representing a check mark icon.
    */
    private static void deleteCardAccount(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        // Get username for the card account to be deleted
        String searchQuery = getInput("Enter your username you wish to delete a card account:", "Card Account Deletion");
        if (searchQuery == null) {
            return;
        }
        try { // Delete Credit Account 
            allUserAccounts[accountIndex].deleteCardAccount(searchQuery);
            JOptionPane.showMessageDialog(null, "Card Account successfully deleted!", 
            "Success Message", JOptionPane.INFORMATION_MESSAGE, check);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        /******* Listing current card members*/
        String output = "";
        int numCards = allUserAccounts[accountIndex].getNumCardAccounts();
        StandardCredit[] Saccount = new StandardCredit[numCards];
        GoldCredit[] Gaccount = new GoldCredit[numCards];
        CardAccount[] cAccounts = new CardAccount[numCards];        
        cAccounts= allUserAccounts[accountIndex].getAllCardAccounts(); //[accountIndex+1];
        int sindex = 0;
        for(int i=0; i<numCards; i++){
            if (cAccounts[i] instanceof StandardCredit) {
                
                Saccount[sindex] = (StandardCredit)cAccounts[i]; 
                sindex++;           
            }
        }
        int gindex=0;
        for(int i=0; i<numCards; i++){
            if (cAccounts[i] instanceof GoldCredit) {
                Gaccount[gindex] = (GoldCredit)cAccounts[i];  
                gindex++;          
            }
        }

        for (int i = 0; i < sindex; i++) {
            output += "[ Standard Membership Reward points Infomation ]";
            output += "\nCard Number: "+Saccount[i].aCreditCards[0].getCardNumber();
            output += "\nAvailableCredit: "+Saccount[i].aCreditCards[0].getAvailableCredit()+"\n\n";
        } 
        for (int i = 0; i < gindex; i++) {            
            
            output += "[ Gold Membership Reward points Infomation ]";
            output += "\nCard Number: "+Gaccount[i].aCreditCards[0].getCardNumber();                
            output += "\nAvailableCredit: "+Gaccount[i].aCreditCards[0].getAvailableCredit()+"\n\n";                      
        }
        if(sindex==0 && gindex==0){
            JOptionPane.showMessageDialog(null, "All remaining accounts:\n" +
            "No membership accounts are available.", "All Remaining Accounts", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "All remaining accounts:\n" +
            output, "All Remaining Accounts", JOptionPane.INFORMATION_MESSAGE);
        }           
    }

    /**
    * Method to display awards points for card accounts.
    * 
    * @param allUserAccounts An array of UserAccount objects containing all user accounts.
    * @param accountIndex The index of the current user account in the allUserAccounts array.
    * @param check An ImageIcon object representing a check mark icon.
    */
    private static void AwardsPoints(UserAccount[] allUserAccounts, int accountIndex, ImageIcon check) {
        int awardPoint = 0;
        boolean validInput = false;
        // Loop until valid input is provided
        do {
                try {
                    String input = getInput("Enter your award point to display:", "Display Award Points");
                    if (input == null) {
                        return;
                    }

                    awardPoint = Integer.parseInt(input);
                    if (awardPoint < 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a value greater than or equal to 1.",
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    } else {
                        validInput = true;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid numerical value.", "Invalid Input Type", JOptionPane.WARNING_MESSAGE);
                }
            } while (!validInput);

        String output ="";
        int numCards = allUserAccounts[accountIndex].getNumCardAccounts();
        StandardCredit[] Saccount = new StandardCredit[numCards];
        GoldCredit[] Gaccount = new GoldCredit[numCards];
        CardAccount[] cAccounts = new CardAccount[numCards];        
        cAccounts= allUserAccounts[accountIndex].getAllCardAccounts(); //[accountIndex+1];
        int sindex = 0;
        for(int i=0; i<numCards; i++){
            if (cAccounts[i] instanceof StandardCredit) {
                
                Saccount[sindex] = (StandardCredit)cAccounts[i]; 
                sindex++;           
            }
        }
        int gindex=0;
        for(int i=0; i<numCards; i++){
            if (cAccounts[i] instanceof GoldCredit) {
                Gaccount[gindex] = (GoldCredit)cAccounts[i];  
                gindex++;          
            }
        }

        for (int i = 0; i < sindex; i++) {
            if (Saccount[i].aCreditCards[0].getRewardsPoints() > awardPoint ) {
            output += "[ Standard Membership Reward points Infomation ]";
            output += "\nCard Number: "+Saccount[i].aCreditCards[0].getCardNumber();
            output += "\nAvailableCredit: "+Saccount[i].aCreditCards[0].getAvailableCredit()+
            "\nAward Points: "+Saccount[i].aCreditCards[0].getRewardsPoints()+"\n\n";
        }            
                
        } 
        for (int i = 0; i < gindex; i++) {            
            
                if (Gaccount[i].aCreditCards[0].getRewardsPoints() > awardPoint ) {
                    output += "[ Gold Membership Reward points Infomation ]";
                    output += "\nCard Number: "+Gaccount[i].aCreditCards[0].getCardNumber();                
                    output += "\nAvailableCredit: "+Gaccount[i].aCreditCards[0].getAvailableCredit()+
                    "\nAward Points: "+Gaccount[i].aCreditCards[0].getRewardsPoints()+"\n\n";
                }
            
        }
        if(sindex==0 && gindex==0){
            JOptionPane.showMessageDialog(null, "No membership accounts are available.", "Display Award Points", JOptionPane.WARNING_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, output, "Display Award Points", JOptionPane.INFORMATION_MESSAGE, check);
        }
    }
}