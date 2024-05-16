import javax.swing.JOptionPane;
import java.util.Random;
import java.util.Calendar;
import java.time.LocalDate;

/**
 * Class representing a Credit Card.
 */
public class CreditCard {
    // Instance variables specific to CreditCard class
    private String companyName;
    private String cardNumber;
    private double creditLimit;
    private double availableCredit;
    private double interestRate;
    private int rewardsPoints;
    private LocalDate billingCycle;
    private String expirationDate;
    private int cvv;

    // Constructor

    /**
     * Default constructor.
     */
    public CreditCard(){}

    /**
     * Parameterized constructor to initialize CreditCard details.
     * 
     * @param depositAmount The initial deposit amount.
     * @param companyName The name of the credit card company.
     * @param creditLimit The credit limit of the card.
     * @param interestRate The interest rate of the card.
     */
    public CreditCard(double depositAmount, String companyName, double creditLimit, double interestRate) {
        this.companyName = companyName;
        this.generateCardNumber();
        this.generateCVV();
        this.generateExpirationDate(); 
        this.creditLimit = creditLimit;
        this.availableCredit = creditLimit; // Initially available credit is equal to credit limit
        this.interestRate = interestRate;
        this.rewardsPoints = this.cvv;
        this.billingCycle = LocalDate.now().plusMonths(1); // Set initial billing cycle to next month
    }

    // Accessor methods

    /**
     * Getter for the card number.
     * 
     * @return The card number.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Getter for the credit limit.
     * 
     * @return The credit limit.
     */
    public double getCreditLimit() {
        return creditLimit;
    }

    /**
     * Getter for the available credit.
     * 
     * @return The available credit.
     */
    public double getAvailableCredit() {
        return availableCredit;
    }

    /**
     * Getter for the interest rate.
     * 
     * @return The interest rate.
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Getter for the rewards points.
     * 
     * @return The rewards points.
     */
    public int getRewardsPoints() {
        return rewardsPoints;
    }

    /**
     * Getter for the billing cycle date.
     * 
     * @return The billing cycle date.
     */
    public LocalDate getBillingCycle() {
        return billingCycle;
    }

    // Method to make a purchase with the credit card

    /**
     * Method to make a purchase with the credit card.
     * 
     * @param amount The amount of the purchase.
     */
    public void charge(double amount) {
        if (amount <= availableCredit) {
            // Deduct the amount from available credit
            availableCredit -= amount;
            // Increase rewards points based on amount spent
            rewardsPoints += (int) (amount / 10); // For example, 1 point for every $10 spent
            JOptionPane.showMessageDialog(null, "Purchase of $" + amount + " made successfully with credit card.");
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient available credit to make the purchase.",
                    "Credit Card Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to make a payment towards the credit card balance

    /**
     * Method to make a payment towards the credit card balance.
     * 
     * @param amount The payment amount.
     */
    public void makePayment(double amount) {
        // Increase available credit by the payment amount
        availableCredit += amount;
        JOptionPane.showMessageDialog(null,"Payment of $" + amount + " made successfully.");
    }

    // Method to calculate interest accrued on the outstanding balance

    /**
     * Method to calculate interest accrued on the outstanding balance.
     * 
     * @return The calculated interest.
     */
    public double calculateInterest() {
        // Calculate interest based on the outstanding balance and interest rate
        // For simplicity, let's assume monthly compounding interest
        double outstandingBalance = creditLimit - availableCredit;
        double monthlyInterest = outstandingBalance * (interestRate / 12); // Monthly interest rate
        return monthlyInterest;
    }

    /**
     * Method to generate expiration date for the card.
     */
    public void generateExpirationDate(){
        int currentYear=Calendar.getInstance().get((Calendar.YEAR));
        int expirationYear = currentYear+(int)(Math.random()*10);
        int expirationMonth = (int)(Math.random()*12)+1;

        this.expirationDate = String.format("%02d/%d",expirationMonth, expirationYear);        
    }

    /**
     * Method to generate a random card number.
     */
    public void generateCardNumber(){
       String randomNumber="";
       Random random = new Random();

       for(int i=0; i<4; i++){
            randomNumber+=String.format("%04d", random.nextInt(10000));
            if(i<3){
                randomNumber+="-";
            }
       }
       this.cardNumber=randomNumber;
    }

    /**
     * Method to generate a random CVV.
     */
    public void generateCVV(){
        Random random = new Random();
        this.cvv=100+random.nextInt(900);
    }

    /**
     * Method to check if the card is expired.
     * 
     * @return True if the card is expired, false otherwise.
     */
    public boolean isCardExpired(){
        String[] parts = expirationDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);
        LocalDate expiration = LocalDate.of(year, month,1);
        LocalDate currentDate = LocalDate.now();

        return expiration.isBefore(currentDate);
    }

    /**
     * Method to check if the provided CVV is valid.
     * 
     * @param inputCvv The CVV to be validated.
     * @return True if the CVV is valid, false otherwise.
     */
    public boolean isCvvValid(int inputCvv) {
        return inputCvv == cvv;
    }

    /**
     * Method to check if the card is valid.
     * 
     * @return True if the card is valid, false otherwise.
     */
    public boolean isCardValid() {
        boolean expired = isCardExpired();
        return !expired && cvv > 0; 
    }

    // Method to generate a monthly statement

    /**
     * Method to generate a monthly statement.
     * 
     * @return The generated statement.
     */
    public String generateStatement() {
        String statement = "Credit Card Statement for " + billingCycle.getMonth() + " " + billingCycle.getYear() + "\n"
                + "Credit Limit: $" + creditLimit + "\n"
                + "Available Credit: $" + availableCredit + "\n"
                + "Interest Rate: " + interestRate + "%\n"
                + "Rewards Points: " + rewardsPoints + "\n"
                + "Billing Cycle End Date: " + billingCycle + "\n";
        return statement;        
    }

    // Method to increase credit limit

    /**
     * Method to increase the credit limit.
     * 
     * @param increaseAmount The amount by which to increase the credit limit.
     */
    public void increaseCreditLimit(double increaseAmount) {
        creditLimit += increaseAmount;
        availableCredit += increaseAmount; // Increase available credit proportionally
        JOptionPane.showMessageDialog(null,"Credit limit increased by $" + increaseAmount);
    }

    // Special purpose methods

    /**
     * Method to generate a string representation of the credit card details.
     * 
     * @return A string containing the credit card details.
     */
    public String toString() {
        String result ="<====== Credit Card Class Information =======>\n";
        result += "Company Name: " + companyName + "\n";
        result += "Card Number: " + cardNumber + "\n";
        result += "Expiration Date: " + expirationDate + "\n";
        result += "CVV: " + cvv + "\n";
        result += this.generateStatement();
        return result;   
    }
}
