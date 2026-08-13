package OOPTasks;

public class Customers {
    private int customerID;
    private String fullName;
    private String nationalID;
    private String phoneNumber;
    private CustomerTiers customerTiers;
    private int countAccount = 0;
    private Accounts[] customerAccounts;
    private int customerAccountCount;

    private static int countCustomer = 0;
    private static Customers[] customersArray;

    public Customers(int countCustomerInArray){
        Customers.customersArray = new Customers[countCustomerInArray];
    }

    private Customers(String fullName,String nationalID,String phoneNumber,CustomerTiers customerTiers){
        this.fullName =fullName;
        this.nationalID =nationalID;
        this.phoneNumber = phoneNumber;
        this.customerTiers =customerTiers;
        this.customerID = countCustomer;
        this.customerAccountCount=0;
    }

    public void getAllCustomerAccount(int customerID) {
        if (customersArray[customerID].customerAccounts == null) {
            System.out.println("Customer has no accounts :(");
            return;
        }

        for (Accounts account : customersArray[customerID].customerAccounts)
            account.getAccountActiveDetails();
    }

    //---------------------------------------------------------------//
    //-------------------------- OPERATION --------------------------//
    //---------------------------------------------------------------//

    //Register New Customer
    public boolean registerNewCustomer(String fullName,String nationalID,String phoneNumber,CustomerTiers customerTiers){
        boolean result = this.validateFullName(fullName) && this.validateNationalID(nationalID) && this.validatePhoneNumber(phoneNumber);

        if(result){
            Customers newCustomer = new Customers(fullName,nationalID,phoneNumber,customerTiers);
            customersArray[countCustomer++] = newCustomer;
            System.out.println("Customer registered successfully.\n" +
                    "Customer ID: "+newCustomer.customerID);
            return true;
        }
        return false;
    }

    //Open New Account
    public boolean openNewAccount(Accounts accountCustomer, int customerID) {

        Accounts[] newAccount = new Accounts[customersArray[customerID].customerAccountCount + 1];

        if (customersArray[customerID].customerAccounts != null)
            System.arraycopy(customersArray[customerID].customerAccounts, 0, newAccount, 0, customersArray[customerID].customerAccountCount);

        accountCustomer.setAccountID(countAccount++);

        newAccount[customersArray[customerID].customerAccountCount] = accountCustomer;

        customersArray[customerID].customerAccounts = newAccount;
        customersArray[customerID].customerAccountCount++;

        return true;
    }

    //Deposit Money
    public boolean depositMoney(int customerID, int accountCustomerID, double deposit) {

        Accounts account = customersArray[customerID].customerAccounts[accountCustomerID];

        if (account.validateDepositMoney(deposit)) {

            System.out.print("Current balance in account id " + accountCustomerID + " = " + account.getCurrentBalance() + " Deposit = " + deposit + ". New balance = ");

            account.setCurrentBalance(account.getCurrentBalance() + deposit);

            account.increaseTransactionCount();

            System.out.println(account.getCurrentBalance() + " :)");

            return true;
        }

        return false;
    }

    //Withdraw Money
    public boolean withdrawMoney(int customerID, int accountCustomerID, double withdraw) {

        Accounts account = customersArray[customerID].customerAccounts[accountCustomerID];

        if (account.validateWithdrawalAccount(withdraw)) {

            System.out.print("Current balance in account id " + accountCustomerID + " = " + account.getCurrentBalance() + " Withdraw = " + withdraw + ". New balance = ");

            account.setCurrentBalance(account.getCurrentBalance() - withdraw);

            account.increaseSuccessfulWithdrawals();

            account.increaseTransactionCount();

            System.out.println(account.getCurrentBalance() + " :)");

            return true;
        }

        return false;
    }

    // Transfer Between Accounts
    public boolean transferMoney(int sourceAccountID, int destinationAccountID, double amount) {

        if (amount <= 0) {
            System.out.println("Transfer amount must be greater than zero :(");
            return false;
        }

        Accounts sourceAccount = getAccountByID(sourceAccountID);

        if (sourceAccount == null) {
            System.out.println("Source account not found :(");
            return false;
        }

        Accounts destinationAccount = getAccountByID(destinationAccountID);

        if (destinationAccount == null) {
            System.out.println("Destination account not found :(");
            return false;
        }

        if (sourceAccount == destinationAccount) {
            System.out.println("Source and destination accounts must be different :(");
            return false;
        }

        if (sourceAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            System.out.println("Source account is inactive :(");
            return false;
        }

        if (destinationAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            System.out.println("Destination account is inactive :(");
            return false;
        }

        if (!sourceAccount.validateWithdrawalAccount(amount)) {
            System.out.println("Transfer failed because source account cannot withdraw this amount :(");
            return false;
        }

        if (!destinationAccount.validateDepositMoney(amount)) {
            System.out.println("Transfer failed because destination account cannot receive this amount :(");
            return false;
        }

        sourceAccount.setCurrentBalance(sourceAccount.getCurrentBalance() - amount);

        destinationAccount.setCurrentBalance(destinationAccount.getCurrentBalance() + amount);

        sourceAccount.increaseSuccessfulWithdrawals();
        sourceAccount.increaseTransactionCount();
        destinationAccount.increaseTransactionCount();

        System.out.println("Transfer successfully completed :)");

        return true;
    }

    //Get Account
    public Accounts getAccountByID(int accountID) {

        for (Customers customer : customersArray) {

            if (customer != null && customer.customerAccounts != null) {

                for (Accounts account : customer.customerAccounts) {

                    if (account != null && account.getAccountID() == accountID)
                        return account;
                }
            }
        }

        return null;
    }

    //Display Customer Accounts
    public void displayCustomerAccounts(int customerID) {

        Customers customer = customersArray[customerID];

        System.out.println("\n========================================");
        System.out.println("          CUSTOMER INFORMATION");
        System.out.println("========================================");

        System.out.println("Customer ID : " + customer.customerID);
        System.out.println("Full Name   : " + customer.fullName);
        System.out.println("National ID : " + customer.nationalID);
        System.out.println("Phone Number: " + (customer.phoneNumber.isEmpty() ? "Not provided" : customer.phoneNumber));
        System.out.println("Customer Tier: " + customer.customerTiers);

        System.out.println("\n========================================");
        System.out.println("             CUSTOMER ACCOUNTS");
        System.out.println("========================================");

        if (customer.customerAccounts == null || customer.customerAccountCount == 0) {

            System.out.println("Customer has no accounts :(");
            return;
        }

        double totalBalance = 0;

        for (Accounts account : customer.customerAccounts) {

            System.out.println("----------------------------------------");

            System.out.println("Account Number   : " + account.getAccountID());
            System.out.println("Account Type     : " + account.getAccountType());
            System.out.println("Balance          : " + account.getCurrentBalance());
            System.out.println("Status           : " + account.getAccountStatus());
            System.out.println("Transaction Count: " + account.getTransactionCount());

            totalBalance += account.getCurrentBalance();
        }

        System.out.println("----------------------------------------");
        System.out.println("Combined Balance : " + totalBalance);
        System.out.println("========================================");
    }

    // Display All Branch Accounts
    public void displayAllBranchAccounts() {

        boolean hasAccounts = false;

        System.out.println("\n==============================================================");
        System.out.println("                    ALL BRANCH ACCOUNTS");
        System.out.println("==============================================================");
        for (Customers customer : customersArray) {
            if (customer == null || customer.customerAccounts == null)
                continue;
            for (Accounts account : customer.customerAccounts) {
                if (account == null)
                    continue;
                hasAccounts = true;
                System.out.println("----------------------------------------");
                System.out.println("Account Number    : " + account.getAccountID());
                System.out.println("Owner Name        : " + customer.fullName);
                System.out.println("Account Type      : " + account.getAccountType());
                System.out.println("Balance           : " + account.getCurrentBalance());
                System.out.println("Status            : " + account.getAccountStatus());
                System.out.println("Transaction Count : " + account.getTransactionCount());
            }
        }
        if (!hasAccounts) {
            System.out.println("There are no accounts in the branch :(");
        }
        System.out.println("==============================================================");
    }

    // Search Account by Number
    public boolean searchAccountByID(int accountID) {

        for (Customers customer : customersArray) {

            if (customer == null || customer.customerAccounts == null)
                continue;

            for (Accounts account : customer.customerAccounts) {

                if (account == null)
                    continue;

                if (account.getAccountID() == accountID) {

                    System.out.println("\n========================================");
                    System.out.println("          ACCOUNT INFORMATION");
                    System.out.println("========================================");

                    System.out.println("Account Number    : " + account.getAccountID());
                    System.out.println("Owner Name        : " + customer.fullName);
                    System.out.println("Account Type      : " + account.getAccountType());
                    System.out.println("Balance           : " + account.getCurrentBalance());
                    System.out.println("Status            : " + account.getAccountStatus());
                    System.out.println("Transaction Count : " + account.getTransactionCount());

                    // Current Account
                    if (account instanceof CurrentAccount currentAccount) {
                        System.out.println("Overdraft Limit   : " + currentAccount.getOverdraftCurrentAccount());

                        System.out.println("Using Overdraft   : " + currentAccount.getOverdraft());
                    }

                    // Fixed Deposit Account
                    else if (account instanceof FixedDepositAccount fixedAccount) {

                        System.out.println("Duration          : " + fixedAccount.getNumberOfMonthsBeforeWithdraw() + " months");

                        int elapsedMonths = fixedAccount.getElapsedMonths();

                        System.out.println("Elapsed Months    : " + elapsedMonths);

                        System.out.println("Matured           : " + fixedAccount.isMatured());
                    }

                    // Saving Account
                    else if (account instanceof SavingAccount)
                        System.out.println("Annual Interest   : " + account.getAnnualInterestRate() + "%");

                    System.out.println("========================================");

                    return true;
                }
            }
        }
        System.out.println("Account number not found :(");
        return false;
    }

    // Search Accounts by Type
    public void searchAccountsByType(AccountTypes accountType) {

        int matchingAccounts = 0;
        double totalBalance = 0;

        System.out.println("\n================================================");
        System.out.println("             ACCOUNTS BY TYPE");
        System.out.println("================================================");

        for (Customers customer : customersArray) {

            if (customer == null || customer.customerAccounts == null)
                continue;

            for (Accounts account : customer.customerAccounts) {

                if (account == null)
                    continue;

                if (account.getAccountType() == accountType) {

                    matchingAccounts++;
                    totalBalance += account.getCurrentBalance();

                    System.out.println("----------------------------------------");
                    System.out.println("Account Number    : " + account.getAccountID());
                    System.out.println("Owner Name        : " + customer.fullName);
                    System.out.println("Account Type      : " + account.getAccountType());
                    System.out.println("Balance           : " + account.getCurrentBalance());
                    System.out.println("Status            : " + account.getAccountStatus());
                    System.out.println("Transaction Count : " + account.getTransactionCount());
                }
            }
        }

        if (matchingAccounts == 0) {
            System.out.println("No accounts found with type : " + accountType);
        }

        System.out.println("----------------------------------------");
        System.out.println("Matching Accounts : " + matchingAccounts);
        System.out.println("Combined Balance  : " + totalBalance);
        System.out.println("================================================");
    }

    // Close Account
    public boolean closeAccount(int accountID) {
        for (Customers customer : customersArray) {
            if (customer == null || customer.customerAccounts == null)
                continue;
            for (Accounts account : customer.customerAccounts) {
                if (account == null)
                    continue;

                // Account found
                if (account.getAccountID() == accountID) {

                    if (account.getAccountStatus() == AccountStatus.CLOSED) {
                        System.out.println("This account is already closed :(");
                        return false;
                    }

                    if (account.getCurrentBalance() != 0) {
                        System.out.println("Account cannot be closed because balance is not zero :(");

                        System.out.println("Current balance : " + account.getCurrentBalance());

                        return false;
                    }

                    // Fixed Deposit must be matured
                    if (account instanceof FixedDepositAccount fixedAccount) {
                        if (!fixedAccount.isMatured()) {
                            System.out.println("Fixed deposit account cannot be closed before maturity :(");
                            System.out.println("Remaining months : " + fixedAccount.getRemainingMonths());
                            return false;
                        }
                    }

                    // Close account
                    account.setAccountStatus(AccountStatus.CLOSED);
                    System.out.println("Account " + accountID + " has been closed successfully :)");
                    return true;
                }
            }
        }
        System.out.println("Account ID not found :(");
        return false;
    }

    //--------------------------------------------------------------//
    //------------------------- VALIDATION -------------------------//
    //--------------------------------------------------------------//

    public boolean validateCustomerID(int customerID){

        for(Customers customer:customersArray)
            if(customer != null && customer.customerID == customerID)
                return true;
        System.out.println("Customer id not found :(");
        return false;
    }

    private boolean validateFullName(String fullName){
        if(fullName.isEmpty()){
            System.out.println("You must input full name......not empty :(");
            return false;
        }
        return true;
    }

    private boolean validateNationalID(String nationalID){

        if(nationalID.length() != 14 && !nationalID.isEmpty()){
            System.out.println("You must input national id 14 digit :(");
            return false;
        }

        if (!nationalID.matches("\\d+")){
            System.out.println("Invalid number.....National id must be integer number :(");
            return false;
        }

        for(Customers customerSearch: customersArray)
            if(customerSearch != null && customerSearch.nationalID.equals(nationalID)){
                System.out.println("National id already found :(");
                return false;
            }
        return true;
    }

    private boolean validatePhoneNumber(String phoneNumber){
        if(phoneNumber.isEmpty())
            return true;

        if(!(phoneNumber.length() >=7 && phoneNumber.length()<=15)){
            System.out.println("phone number must be from 7 to 15 digit of empty :(");
            return false;
        }

        if(phoneNumber.matches("\\d+"))
            return true;
        else{
            System.out.println("You must input phone number with digits integer");
            return false;
        }
    }

    public  boolean validateAccountCustomerID(int customerID,int accountCustomerID) {
        if (customersArray[customerID].customerAccounts.length > accountCustomerID && accountCustomerID >= 0)
            if (customersArray[customerID].customerAccounts[accountCustomerID].getAccountStatus() == AccountStatus.ACTIVE)
                return true;
            else
                System.out.println("This account inactive :(");
        else
            System.out.println("This account unknow :(");
        return false;
    }
}