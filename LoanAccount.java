public class LoanAccount extends BankAccount {
    // Instance variables
    private double loanAmount;
    private double interestRate;
    private double totalAmount;

    // Default Constructor
    LoanAccount() {}

    // Specific Constructor
    LoanAccount(double loanAmount, double interestRate) {
        super(0); // Call the superclass constructor with a deposit of 0
        setLoanAmount(loanAmount);
        setInterestRate(interestRate);
        calculateTotalAmount();
    }

    // Mutators
    public void setLoanAmount(double loanAmount) {
        if (loanAmount < 0) {
            throw new IllegalArgumentException("Loan amount cannot be negative.");
        }
        this.loanAmount = loanAmount;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        this.interestRate = interestRate;
    }

    // Accessors
    public double getLoanAmount() { return this.loanAmount; }
    public double getInterestRate() { return this.interestRate; }
    public double getTotalAmount() { return this.totalAmount; }


    // Special Purpose Methods

    /* Method to calculate total amount (loan amount + interest) */
    private void calculateTotalAmount() {
        double interestAmount = this.getLoanAmount() * this.getInterestRate();
        this.totalAmount = this.getLoanAmount() + interestAmount;
    }

    /* Method to pay total amount */
    public void payTotalAmount(double paymentAmount) {
        if (paymentAmount < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }

        if (paymentAmount > this.getTotalAmount()) {
            throw new IllegalArgumentException("The payment amount exceeds the total amount.");
        }

        this.totalAmount -= paymentAmount;
    }

    /* Returns loan account information */
    public String toString() {
        return "***Golden Bank Official Loan Account Information***\n" +
        super.toString() + "(N/A)\n" +
        "Loan Amount: $" + String.format("%.2f", this.getLoanAmount()) + "\n" +
        "Interest Rate: " + String.format("%.2f%%", this.getInterestRate() * 100) + "\n" +
        "Total Amount: $" + String.format("%.2f", this.getTotalAmount());
    }
}