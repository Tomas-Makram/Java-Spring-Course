package OOPTasks;

import java.time.LocalDateTime;

public abstract class Accounts {

    private int accountID;
    private AccountTypes accountType;
    private AccountStatus accountStatue;
    private double currentBalance;

    private final LocalDateTime createAt = LocalDateTime.now();
    private LocalDateTime lastYearOfAddInterestRate = createAt;

    private int successfulWithdrawals = 0;
    private int transactionCount = 0;
    private int annualInterestRate;

    private final double maximumLengthDeposit = 10000.0;

    protected Accounts() {
        this.accountStatue = AccountStatus.ACTIVE;
    }

    //---------------------------------------------------------------//
    //-------------------------- OPERATION --------------------------//
    //---------------------------------------------------------------//

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountType(AccountTypes accountType) {
        this.accountType = accountType;
    }

    public AccountTypes getAccountType() {
        return accountType;
    }

    public void setAccountStatus(AccountStatus accountStatue) {
        this.accountStatue = accountStatue;
    }

    public AccountStatus getAccountStatus() {
        return accountStatue;
    }

    public void setCurrentBalance(double newBalance) {
        this.currentBalance = newBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setLastYearOfAddInterestRate(LocalDateTime newYearOfAddInterestRate) {
        this.lastYearOfAddInterestRate = newYearOfAddInterestRate;
    }

    public LocalDateTime getLastYearOfAddInterestRate() {
        return lastYearOfAddInterestRate;
    }

    public void setAnnualInterestRate(int annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public int getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void increaseSuccessfulWithdrawals() {
        this.successfulWithdrawals++;
    }

    public int getSuccessfulWithdrawals() {
        return successfulWithdrawals;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void increaseTransactionCount() {
        transactionCount++;
    }

    public void getAccountActiveDetails() {
        if(this.accountStatue == AccountStatus.ACTIVE)
            System.out.println("Account id : " + this.accountID + "\n" + "Account type : " + this.accountType + "\n");
    }

    //--------------------------------------------------------------//
    //------------------------- VALIDATION -------------------------//
    //--------------------------------------------------------------//

    public abstract boolean validateAccount(double balance);

    public boolean validateDepositMoney(double balance) {
        if (balance > 0 && balance <= maximumLengthDeposit)
            return true;
        System.out.println("Deposit must be positive number and smaller than maximum length deposit : " + maximumLengthDeposit + " :(");
        return false;
    }

    public abstract boolean validateWithdrawalAccount(double balance);
}