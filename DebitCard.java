import java.util.Random;
import java.util.Calendar;
import java.time.LocalDate;

/**
 * Class representing a Debit Card.
 */
public class DebitCard {
    // Instance variables specific to DebitCard class
    private String companyName;
    private String cardNumber;
    private String expirationDate;
    private int cvv;
    private static int numOfAcct = 0;

    // Defined Specific Constructor

    /**
     * Default constructor.
     */
    public DebitCard(){}

    /**
     * Parameterized constructor to initialize DebitCard details.
     * 
     * @param depositAmount The initial deposit amount.
     * @param companyName The name of the debit card company.
     */
    public DebitCard(double depositAmount, String companyName) {
        this.companyName = companyName;
        this.generateCardNumber();
        this.generateCVV();
        this.generateExpirationDate();  
        numOfAcct++; 
    }

    // Accessors(Getter Methods)

    /**
     * Getter for the company name.
     * 
     * @return The company name.
     */
    public String getCompanyName(){
        return this.companyName;
    }

    /**
     * Getter for the card number.
     * 
     * @return The card number.
     */
    public String getCardNumber(){
        return this.cardNumber;
    }

    /**
     * Getter for the expiration date.
     * 
     * @return The expiration date.
     */
    public String getExpirationDate(){
        return this.expirationDate;
    }

    /**
     * Getter for the CVV.
     * 
     * @return The CVV.
     */
    public int getCVV(){
        return this.cvv;
    }

    /**
     * Getter for the number of DebitCard accounts.
     * 
     * @return The number of DebitCard accounts.
     */
    public static int getNumOfAcct(){
        return numOfAcct;
    }

    // Mutators(Setter methods)

    /**
     * Setter for the company name.
     * 
     * @param companyName The company name to set.
     */
    public void setCompanyname(String companyName){
        this.companyName=companyName;
    }

    // Special Purpose Methods

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
    
    /**
     * Method to generate a string representation of the DebitCard details.
     * 
     * @return A string containing the DebitCard details.
     */
    public String toString(){
        String result ="[---- Debit Card Class Information ----]\n";
        result += "Company Name: " + companyName + "\n";
        result += "Card Number: " + cardNumber + "\n";
        result += "Expiration Date: " + expirationDate + "\n";
        result += "CVV: " + cvv + "\n";
        return result;
    }
}
