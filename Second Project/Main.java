import OOPTasks.*;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void Menu(){
        System.out.println("""
                -----------------------------------------
                | Option |       Operation              |
                -----------------------------------------
                |   1    |   Register New Customer      |
                |   2    |   Open New Account           |
                |   3    |   Deposit Money              |
                |   4    |   Withdraw Money             |
                |   5    |   Transfer Between Accounts  |
                |   6    |   Display Customer Accounts  |
                |   7    |   Display All Branch Accounts|
                |   8    |   Search Account by Number   |
                |   9    |   Search Accounts by Type    |
                |   10   |   Close an Account           |
                |   0    |   Exit                       |
                -----------------------------------------
                """);
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Customers customers = new Customers(20);
        Menu();
        int choose = 1;
        while(choose !=0){
            System.out.print("\nEnter Your Operation : ");
            if(scan.hasNextInt()){
                choose = scan.nextInt();
                switch (choose){

                    //Exit
                    case 0:
                        System.out.println("Good Bye :)");
                        break;

                    //Create New Customers
                    case 1:
                        scan.nextLine();
                        System.out.print("Customer full name : ");
                        String fullName = scan.nextLine();
                        System.out.print("Customer national id : ");
                        String nationalID = scan.nextLine();
                        System.out.print("Enter phone number (press Enter to skip): ");
                        String phoneNumber = scan.nextLine();
                        String customerTiersInput;
                        CustomerTiers customerTiers = null;
                        do {
                            System.out.print("Input customer type " + Arrays.toString(CustomerTiers.values()) + " : ");
                            customerTiersInput = scan.nextLine();
                            for (CustomerTiers tier : CustomerTiers.values()) {
                                if (tier.name().equalsIgnoreCase(customerTiersInput)) {
                                    customerTiers = tier;
                                    break;
                                }
                            }
                            if (customerTiers == null)
                                System.out.println("It must choose from list :(");
                        } while (customerTiers == null);

                        if(!customers.registerNewCustomer(fullName,nationalID,phoneNumber, customerTiers))
                            System.out.println("Failed create new customer :(");
                        break;

                    //Create New Accounts
                    case 2:
                        System.out.print("Input customer id : ");
                        if(scan.hasNextInt()){
                            int customerID = scan.nextInt();
                            if(customers.validateCustomerID(customerID)) {
                                int accountTypeInput = -1;
                                do {
                                    System.out.print("Input account type number " + Arrays.toString(AccountTypes.values()) + " : ");
                                    if (scan.hasNextInt()) {
                                        accountTypeInput = scan.nextInt();

                                        // ---------------------------------------------------------
                                        // Saving Account
                                        // ---------------------------------------------------------
                                        if (accountTypeInput == 1) {

                                            SavingAccount savingAccount = new SavingAccount();

                                            System.out.print("Input your balance : ");

                                            if (scan.hasNextDouble()) {

                                                double balance = scan.nextDouble();

                                                if (savingAccount.validateAccount(balance)) {

                                                    System.out.print("Input annual interest rate : ");

                                                    if (scan.hasNextInt()) {

                                                        int annualInterestRate = scan.nextInt();

                                                        if (savingAccount.validateAnnualInterestRate(annualInterestRate)) {

                                                            savingAccount =
                                                                    new SavingAccount(balance, annualInterestRate);

                                                            if (customers.openNewAccount(savingAccount, customerID)) {

                                                                System.out.println(
                                                                        "Successfully add saving account to customer id "
                                                                                + customerID
                                                                );

                                                            } else {

                                                                System.out.println(
                                                                        "Failed add saving account to customer id "
                                                                                + customerID
                                                                );
                                                            }

                                                        }
                                                    } else {

                                                        System.out.println(
                                                                "You must input integer number :("
                                                        );

                                                        scan.next();
                                                    }

                                                }

                                            } else {

                                                System.out.println(
                                                        "You must input double number :("
                                                );

                                                scan.next();
                                            }

                                        }

                                        // ---------------------------------------------------------
                                        // Current Account
                                        // ---------------------------------------------------------
                                        else if (accountTypeInput == 2) {

                                            CurrentAccount currentAccount =
                                                    new CurrentAccount();

                                            System.out.print("Input your balance : ");

                                            if (scan.hasNextDouble()) {

                                                double balance = scan.nextDouble();

                                                if (currentAccount.validateAccount(balance)) {

                                                    currentAccount.setCurrentBalance(balance);

                                                    if (customers.openNewAccount(
                                                            currentAccount,
                                                            customerID
                                                    )) {

                                                        System.out.println(
                                                                "Successfully add current account to customer id "
                                                                        + customerID
                                                                        + " with overdraft current account : "
                                                                        + currentAccount.getOverdraftCurrentAccount()
                                                        );

                                                    } else {

                                                        System.out.println(
                                                                "Failed add current account to customer id "
                                                                        + customerID
                                                        );
                                                    }
                                                }

                                            } else {

                                                System.out.println(
                                                        "You must input double number :("
                                                );

                                                scan.next();
                                            }

                                        }

                                        // ---------------------------------------------------------
                                        // Fixed Deposit Account
                                        // ---------------------------------------------------------
                                        else if (accountTypeInput == 3) {

                                            FixedDepositAccount fixedDepositAccount =
                                                    new FixedDepositAccount();

                                            // ---------------- Balance ----------------

                                            System.out.print("Input your balance : ");

                                            if (scan.hasNextDouble()) {

                                                double balance = scan.nextDouble();

                                                if (fixedDepositAccount.validateAccount(balance)) {

                                                    // ---------------- Duration ----------------

                                                    System.out.print(
                                                            "Input number of months before withdraw : "
                                                    );

                                                    if (scan.hasNextInt()) {

                                                        int numberOfMonths = scan.nextInt();

                                                        if (numberOfMonths > 0) {

                                                            // ---------------- Interest Rate ----------------

                                                            System.out.print(
                                                                    "Input annual interest rate : "
                                                            );

                                                            if (scan.hasNextInt()) {

                                                                int annualInterestRate =
                                                                        scan.nextInt();

                                                                if (annualInterestRate >= 0
                                                                        && annualInterestRate <= 100) {

                                                                    fixedDepositAccount.setCurrentBalance(
                                                                            balance
                                                                    );

                                                                    fixedDepositAccount.setNumberOfMonthsBeforeWithdraw(
                                                                            numberOfMonths
                                                                    );

                                                                    fixedDepositAccount.setAnnualInterestRate(
                                                                            annualInterestRate
                                                                    );

                                                                    if (customers.openNewAccount(
                                                                            fixedDepositAccount,
                                                                            customerID
                                                                    )) {

                                                                        System.out.println(
                                                                                "Successfully add fixed deposit account to customer id "
                                                                                        + customerID
                                                                                        + " with duration "
                                                                                        + fixedDepositAccount
                                                                                        .getNumberOfMonthsBeforeWithdraw()
                                                                                        + " months"
                                                                        );

                                                                    } else {

                                                                        System.out.println(
                                                                                "Failed add fixed deposit account to customer id "
                                                                                        + customerID
                                                                        );
                                                                    }

                                                                } else {

                                                                    System.out.println(
                                                                            "Annual interest rate must be from 0 to 100 :("
                                                                    );
                                                                }

                                                            } else {

                                                                System.out.println(
                                                                        "You must input integer number :("
                                                                );

                                                                scan.next();
                                                            }

                                                        } else {

                                                            System.out.println(
                                                                    "Number of months must be greater than 0 :("
                                                            );
                                                        }

                                                    } else {

                                                        System.out.println(
                                                                "You must input integer number :("
                                                        );

                                                        scan.next();
                                                    }
                                                }

                                            } else {

                                                System.out.println(
                                                        "You must input double number :("
                                                );

                                                scan.next();
                                            }

                                        }

                                        // ---------------------------------------------------------
                                        // Invalid Account Type
                                        // ---------------------------------------------------------
                                        else {

                                            System.out.println(
                                                    "You must input from 1 to 3 integer one digit :("
                                            );

                                            accountTypeInput = -1;
                                        }

                                    } else {

                                        System.out.println("You must input integer number :(");
                                        scan.next();
                                    }

                                } while (accountTypeInput == -1);
                            }
                        }else{
                            System.out.println("You must input integer number :(");
                            scan.next();
                        }
                        break;

                    //Deposit Money
                    case 3:
                        System.out.print("Input customer id : ");
                        if(scan.hasNextInt()){
                            int customerID = scan.nextInt();
                            if(customers.validateCustomerID(customerID)) {
                                System.out.print("Enter customer deposit : ");
                                if(scan.hasNextDouble()){
                                    double balanceDeposit = scan.nextDouble();
                                    customers.getAllCustomerAccount(customerID);
                                    System.out.print("Enter customer account id : ");
                                    if(scan.hasNextInt()) {
                                        int accountCustomerID = scan.nextInt();
                                        if(customers.validateAccountCustomerID(customerID,accountCustomerID)){
                                            if(!customers.depositMoney(customerID,accountCustomerID,balanceDeposit))
                                                System.out.println("Failed to add new balance :(");
                                        }
                                    }
                                }else {
                                    System.out.println("You must input double number :(");
                                    scan.next();
                                }
                            }
                        } else {
                            System.out.println("You must input integer number :(");
                            scan.next();
                        }
                        break;


                    //Withdraw Money
                    case 4:

                        System.out.print("Input customer id : ");

                        if (scan.hasNextInt()) {

                            int customerID = scan.nextInt();

                            if (customers.validateCustomerID(customerID)) {

                                System.out.print("Enter customer withdraw : ");

                                if (scan.hasNextDouble()) {

                                    double withdraw = scan.nextDouble();

                                    System.out.print("Enter customer account id : ");

                                    if (scan.hasNextInt()) {

                                        int accountCustomerID = scan.nextInt();

                                        if (customers.validateAccountCustomerID(customerID, accountCustomerID)) {

                                            if (customers.withdrawMoney(
                                                    customerID,
                                                    accountCustomerID,
                                                    withdraw)) {

                                                System.out.println("Successfully withdraw money :)");

                                            } else {
                                                System.out.println("Failed to withdraw money :(");
                                            }

                                        }

                                    } else {

                                        System.out.println("You must input integer number for account id :(");
                                        scan.next();

                                    }

                                } else {

                                    System.out.println("You must input double number for withdraw :(");
                                    scan.next();

                                }
                            }

                        } else {

                            System.out.println("You must input integer number :(");
                            scan.next();

                        }

                        break;

                    //Transfer Between Accounts
                    case 5:
                        System.out.print("Input source account number : ");
                        if (scan.hasNextInt()) {
                            int sourceAccountID = scan.nextInt();
                            System.out.print("Input destination account number : ");
                            if (scan.hasNextInt()) {
                                int destinationAccountID = scan.nextInt();
                                System.out.print("Input transfer amount : ");
                                if (scan.hasNextDouble()) {
                                    double amount = scan.nextDouble();
                                    if(!customers.transferMoney(sourceAccountID, destinationAccountID, amount))
                                        System.out.println("Failed transfer :(");
                                } else {
                                    System.out.println("You must input double number for transfer amount :(");
                                    scan.next();
                                }
                            } else {
                                System.out.println("You must input integer number for destination account :(");
                                scan.next();
                            }
                        } else {
                            System.out.println("You must input integer number for source account :(");
                            scan.next();
                        }

                        break;

                    //Display Customer Accounts
                    case 6:
                        System.out.print("Input customer id : ");
                        if (scan.hasNextInt()) {
                            int customerID = scan.nextInt();
                            if (customers.validateCustomerID(customerID)) {
                                customers.displayCustomerAccounts(customerID);
                            }

                        } else {
                            System.out.println("You must input integer number for customer id :(");
                            scan.next();
                        }
                        break;

                    //Display All Branch Accounts
                    case 7:
                        customers.displayAllBranchAccounts();
                        break;

                    // Search Account by Number
                    case 8:

                        System.out.print("Enter account number : ");

                        if (scan.hasNextInt()) {

                            int accountID = scan.nextInt();
                            if(!customers.searchAccountByID(accountID))
                                System.out.println("Failed to find account :(");
                        } else {

                            System.out.println("You must input integer account number :(");
                            scan.next();
                        }

                        break;

                    // Search Accounts by Type
                    case 9:

                        int accountTypeChoice = -1;

                        do {

                            System.out.println("\nSelect Account Type:");

                            AccountTypes[] accountTypes = AccountTypes.values();

                            for (int i = 0; i < accountTypes.length; i++) {
                                System.out.println((i + 1) + " - " + accountTypes[i]);
                            }

                            System.out.print("Enter account type number : ");

                            if (scan.hasNextInt()) {

                                accountTypeChoice = scan.nextInt();

                                if (accountTypeChoice >= 1 &&
                                        accountTypeChoice <= accountTypes.length) {

                                    AccountTypes selectedType =
                                            accountTypes[accountTypeChoice - 1];

                                    customers.searchAccountsByType(selectedType);

                                } else {

                                    System.out.println("You must choose a valid account type :(");

                                    accountTypeChoice = -1;
                                }

                            } else {

                                System.out.println("You must input integer number :(");

                                scan.next();

                            }

                        } while (accountTypeChoice == -1);

                        break;

                    // Close an Account
                    case 10:
                        System.out.print("Enter account number : ");
                        if (scan.hasNextInt()) {
                            int accountID = scan.nextInt();
                            if (customers.closeAccount(accountID))
                                System.out.println("Account closed successfully.");
                        } else {

                            System.out.println("You must input integer account number :(");
                            scan.next();
                        }

                        break;

                    default:
                        System.out.println("You must input from 0 to 10 :(");
                        Menu();
                        break;
                }
            }
            else{
                System.out.println("You must input integer number :(");
                scan.next();
            }
        }
    }
}