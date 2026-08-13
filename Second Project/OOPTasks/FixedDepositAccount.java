package OOPTasks;

import java.time.LocalDateTime;

public class FixedDepositAccount extends Accounts {

    private int numberOfMonthsBeforeWithdraw;

    public FixedDepositAccount() {
        this.setCurrentBalance(0);
        this.setAnnualInterestRate(0);
        this.setAccountType(AccountTypes.FIXED_DEPOSIT_ACCOUNT);
    }

    @Override
    public boolean validateAccount(double balance) {
        if (balance < 0) {
            System.out.println("The balance must never become negative :(");
            return false;
        }
        return true;
    }

    private LocalDateTime getMaturityDate() {
        return getLastYearOfAddInterestRate().plusMonths(numberOfMonthsBeforeWithdraw);
    }

    @Override
    public boolean validateWithdrawalAccount(double balance) {
        if (isMatured())
            return true;

        System.out.println("You still have time to withdraw balance : "                 + getRemainingMonths());
        return false;
    }

    public boolean annualInterestRate() {

        if (!isMatured())
            return false;

        double interest = getCurrentBalance() * getAnnualInterestRate() / 100.0 * numberOfMonthsBeforeWithdraw / 12.0;

        setCurrentBalance(getCurrentBalance() + interest);

        setLastYearOfAddInterestRate(LocalDateTime.now());

        return true;
    }

    public int getElapsedMonths() {

        LocalDateTime now = LocalDateTime.now();

        int elapsedMonths = (now.getYear() - getLastYearOfAddInterestRate().getYear()) * 12 + now.getMonthValue() - getLastYearOfAddInterestRate().getMonthValue();

        return Math.max(elapsedMonths, 0);
    }

    public int getRemainingMonths() {

        int remainingMonths = numberOfMonthsBeforeWithdraw - getElapsedMonths();

        return Math.max(remainingMonths, 0);
    }

    public boolean isMatured() {
        return !getMaturityDate().isAfter(LocalDateTime.now());
    }

    public void setNumberOfMonthsBeforeWithdraw(int numberOfMonthsBeforeWithdraw) {
        this.numberOfMonthsBeforeWithdraw = numberOfMonthsBeforeWithdraw;
    }

    public int getNumberOfMonthsBeforeWithdraw() {
        return numberOfMonthsBeforeWithdraw;
    }
}