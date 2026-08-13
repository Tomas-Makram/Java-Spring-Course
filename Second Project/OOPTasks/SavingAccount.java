package OOPTasks;

import java.time.LocalDateTime;

public class SavingAccount extends Accounts{

    public SavingAccount() {
        this.setCurrentBalance(0);
        this.setAnnualInterestRate(0);
        this.setAccountType(AccountTypes.SAVING_ACCOUNT);
    }

    public SavingAccount(double currentBalance,int annualInterestRate) {
        this.setCurrentBalance(currentBalance);
        this.setAnnualInterestRate(annualInterestRate);
        this.setAccountType(AccountTypes.SAVING_ACCOUNT);
    }

    @Override
    public boolean validateAccount(double balance) {
        if(balance < 0){
            System.out.println("The balance must never become negative :(");
            return false;
        }
        return true;
    }

    public boolean validateAnnualInterestRate(int annualInterestRate){
        if(annualInterestRate >=0 && annualInterestRate<=100)
            return true;
        else{
            System.out.println("Annual interest rate must be bigger or equal zero and smaller or equal 100");
            return false;
        }
    }

    @Override
    public boolean validateWithdrawalAccount(double balance) {
        if(this.getCurrentBalance() >= balance)
            return true;
        else
            System.out.println("Balance must be bigger than withdraw");
        return false;
    }

    public boolean annualInterestRate() {

        LocalDateTime now = LocalDateTime.now();

        if (!this.getLastYearOfAddInterestRate().plusYears(1).isAfter(now)) {

            double interest = getCurrentBalance() * this.getAnnualInterestRate() / 100.0;

            this.setCurrentBalance(getCurrentBalance() + interest);

            this.setLastYearOfAddInterestRate(now);

            return true;
        }

        return false;
    }
}