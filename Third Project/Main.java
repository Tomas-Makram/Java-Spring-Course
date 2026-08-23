import java.util.Scanner;
import OOPTasks.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Library library = new Library();

    static {
        System.out.println("""
                
                      __...--~~~~~-._   _.-~~~~~--...__
                    //               `V'               \\\\\s
                   //                 |                 \\\\\s
                  //__...--~~~~~~-._  |  _.-~~~~~~--...__\\\\\s
                 //__.....----~~~~._\\ | /_.~~~~----.....__\\\\
                ====================\\\\|//====================
                       Tomas Makram `---`
                """);
    }

    public static void main(String[] args) {
        seedData();

        boolean running = true;

        while (running) {
            printMenu();

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewCatalogue();
                    break;

                case "2":
                    registerMember();
                    break;

                case "3":
                    borrowItem();
                    break;

                case "4":
                    returnItem();
                    break;

                case "5":
                    renewLoan();
                    break;

                case "6":
                    searchItemById();
                    break;

                case "7":
                    viewItemsByStatus();
                    break;

                case "8":
                    payOutstandingFines();
                    break;

                case "9":
                    viewAllMembers();
                    break;

                case "10":
                    libraryReport();
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Not a valid option. Try again :(");
            }

            System.out.println();
        }

        System.out.println("Goodbye :)");
    }

    // ---------------------------------------------------------------
    // Seed data
    // ---------------------------------------------------------------

    private static void seedData() {
        library.registerItem(new Book("B001", "Clean Architecture", "Robert C. Martin", 432));
        library.registerItem(new Book("B002", "The Old Man and the Sea", "Ernest Hemingway", 127));

        library.registerItem(new Magazine("M001", "National Geographic", "Aug 2026"));

        library.registerItem(new DVD("D001", "Spirited Away", 125));

        library.registerMember(new Member("Sara Adel", "1001", MembershipType.STUDENT));

        library.registerMember(new Member("Omar Hassan", "1002", MembershipType.STAFF));

        library.registerMember(new Member("Nourhan Fathy", "1003", MembershipType.PUBLIC, 20.00));
    }

    // ---------------------------------------------------------------
    // Menu
    // ---------------------------------------------------------------

    private static void printMenu() {
        System.out.println("=== " + LibraryItem.getLibraryName() + " ===");
        System.out.println(" 1  - View catalogue");
        System.out.println(" 2  - Register member");
        System.out.println(" 3  - Borrow item");
        System.out.println(" 4  - Return item");
        System.out.println(" 5  - Renew loan");
        System.out.println(" 6  - Search item by ID");
        System.out.println(" 7  - View items by status");
        System.out.println(" 8  - Pay outstanding fines");
        System.out.println(" 9  - View all members");
        System.out.println(" 10 - Library report");
        System.out.println(" 0  - Exit");
        System.out.print("Choose an option: ");
    }

    // ---------------------------------------------------------------
    // Operations
    // ---------------------------------------------------------------

    private static void viewCatalogue() {
        LibraryItem[] items = library.listCatalogue();

        if (items.length == 0) {
            System.out.println("The catalogue is empty :(");
            return;
        }

        for (LibraryItem item : items) {
            item.display();
        }
    }

    private static void registerMember() {
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Membership ID: ");
        String id = scanner.nextLine().trim();

        MembershipType category = askCategory();

        if (category == null) {
            System.out.println("Invalid category :(");
            return;
        }

        Member member = new Member(name, id, category);

        if (library.registerMember(member)) {
            System.out.println("Member registered: " + name + " (" + id + ", " + category + ") :)");
        } else {
            System.out.println("Could not register - that membership ID is already in use, " + "or the register is full. :(");
        }
    }

    private static void borrowItem() {
        System.out.print("Item catalogue ID: ");
        String itemId = scanner.nextLine().trim();

        System.out.print("Membership ID: ");
        String memberId = scanner.nextLine().trim();

        String result = library.lendItem(itemId, memberId);

        System.out.println(result);
    }

    private static void returnItem() {
        System.out.print("Item catalogue ID: ");
        String itemId = scanner.nextLine().trim();

        int daysOverdue = askInt("Days overdue (0 if on time): ");

        String result = library.returnItem(itemId, daysOverdue);

        System.out.println(result);
    }

    private static void renewLoan() {
        System.out.print("Item catalogue ID: ");
        String itemId = scanner.nextLine().trim();

        String result = library.renewLoan(itemId);

        System.out.println(result);
    }

    private static void searchItemById() {
        System.out.print("Item catalogue ID: ");
        String itemId = scanner.nextLine().trim();

        LibraryItem item = library.findItemById(itemId);

        if (item == null) {
            System.out.println(
                    "No item found with catalogue ID "
                            + itemId
                            + ". :("
            );
        } else {
            item.display();
        }
    }

    private static void viewItemsByStatus() {
        System.out.print(
                "Status (AVAILABLE, ON_LOAN, RESERVED, LOST): "
        );

        String input = scanner.nextLine()
                .trim()
                .toUpperCase();

        ItemStatus status;

        try {
            status = ItemStatus.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Not a valid status. :(");
            return;
        }

        LibraryItem[] items =
                library.listItemsByStatus(status);

        if (items.length == 0) {
            System.out.println(
                    "No items are currently "
                            + status
                            + ". :("
            );
            return;
        }

        for (LibraryItem item : items) {
            item.display();
        }
    }

    private static void payOutstandingFines() {
        System.out.print("Membership ID: ");
        String memberId = scanner.nextLine().trim();

        Member member =
                library.findMemberById(memberId);

        if (member == null) {
            System.out.println(
                    "No member found with membership ID "
                            + memberId
                            + ". :("
            );
            return;
        }

        double amount =
                askDouble("Amount to pay (EGP): ");

        if (member.payFine(amount)) {
            System.out.printf(
                    "Payment accepted. New balance: %.2f EGP :)%n",
                    member.getBalance()
            );
        } else {
            System.out.println(
                    "Payment refused - must be positive and cannot exceed "
                            + "the balance owed ("
                            + String.format(
                            "%.2f",
                            member.getBalance()
                    )
                            + " EGP). :("
            );
        }
    }

    private static void viewAllMembers() {
        Member[] members = library.listMembers();

        if (members.length == 0) {
            System.out.println(
                    "No members registered yet. :("
            );
            return;
        }

        for (Member m : members) {
            System.out.printf(
                    "[%s] %-20s %-8s Items held: %d  Balance owed: %.2f EGP%n",
                    m.getMembershipId(),
                    m.getName(),
                    m.getCategory(),
                    m.getItemsHeld(),
                    m.getBalance()
            );
        }
    }

    private static void libraryReport() {
        library.generateReport();
    }

    // ---------------------------------------------------------------
    // Input helpers
    // ---------------------------------------------------------------

    private static MembershipType askCategory() {
        System.out.print(
                "Category (STUDENT, STAFF, PUBLIC): "
        );

        String input = scanner.nextLine()
                .trim()
                .toUpperCase();

        try {
            return MembershipType.valueOf(input);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static int askInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            String input =
                    scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Please enter a whole number. :("
                );
            }
        }
    }

    private static double askDouble(String prompt) {
        while (true) {
            System.out.print(prompt);

            String input =
                    scanner.nextLine().trim();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Please enter a number. :("
                );
            }
        }
    }
}