package OOPTasks;

public class CurrentAccount extends Accounts{

    private final double overdraftCurrentAccount = 2000;
    private boolean overdraft = false;

    public CurrentAccount(){
        this.setCurrentBalance(0);
        this.setAnnualInterestRate(0);
        this.setAccountType(AccountTypes.CURRENT_ACCOUNT);
    }

    public CurrentAccount(double currentBalance) {
        this.setCurrentBalance(currentBalance);
        this.setAccountType(AccountTypes.CURRENT_ACCOUNT);
    }

    @Override
    public boolean validateAccount(double balance) {
        if(balance < 0){
            System.out.println("The balance must never become negative :(");
            return false;
        }
        return true;
    }

    @Override
    public boolean validateWithdrawalAccount(double balance) {
        if((this.getCurrentBalance() >= balance) || ((this.getCurrentBalance() + this.overdraftCurrentAccount >= balance))){
            if(this.getCurrentBalance() < balance)
                System.out.println("You now use overdraft");
            return true;
        }
        else{
            if(this.overdraft)
                System.out.println("You already use overdraft :(");
            else
                System.out.println("Balance with overdraft must be bigger than withdraw :(");
        }
        return false;
    }

    public double getOverdraftCurrentAccount() {
        return overdraftCurrentAccount;
    }

    public boolean getOverdraft(){
        return overdraft;
    }
}