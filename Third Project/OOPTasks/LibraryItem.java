package OOPTasks;

public abstract class LibraryItem {

    private static String libraryName = "Bayt Al Hekma";
    private static final double ADMINISTRATIVE_CHARGE = 10.00;
    private static int itemsEverCatalogued = 0;

    private final String catalogueId;
    private final String title;
    private ItemStatus status;
    private String borrowerName;
    private int renewalCount;

    protected LibraryItem(String catalogueId, String title) {
        this.catalogueId = catalogueId;
        this.title = title;
        this.status = ItemStatus.AVAILABLE;
        this.borrowerName = null;
        this.renewalCount = 0;
        itemsEverCatalogued++;
    }

    public static String getLibraryName() {
        return libraryName;
    }

    public static void setLibraryName(String name) {
        libraryName = name;
    }

    public static double getAdministrativeCharge() {
        return ADMINISTRATIVE_CHARGE;
    }

    public static int getItemsEverCatalogued() {
        return itemsEverCatalogued;
    }

    public String getCatalogueId() {
        return catalogueId;
    }

    public String getTitle() {
        return title;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public int getRenewalCount() {
        return renewalCount;
    }

    public void markReserved() {
        status = ItemStatus.RESERVED;
    }

    public void markLost() {
        status = ItemStatus.LOST;
    }

    public void markAvailable() {
        status = ItemStatus.AVAILABLE;
    }

    public boolean lendTo(String memberName) {
        if (status != ItemStatus.AVAILABLE) {
            return false;
        }
        status = ItemStatus.ON_LOAN;
        borrowerName = memberName;
        return true;
    }

    public final void returnItem() {
        status = ItemStatus.AVAILABLE;
        borrowerName = null;
        renewalCount = 0;
    }

    protected void recordRenewal() {
        renewalCount++;
    }

    protected boolean attemptRenewal(int limit) {
        if (status != ItemStatus.ON_LOAN) {
            return false;
        }
        if (renewalCount >= limit) {
            return false;
        }
        recordRenewal();
        return true;
    }

    public abstract double calculateFine(int daysOverdue);

    public abstract int getLoanPeriodDays();

    public abstract String getCategoryName();

    public void display() {
        String borrower = (borrowerName == null) ? "-" : borrowerName;
        System.out.printf("[%s] %-9s %-30s %-10s Borrower: %-15s Loan period: %2d day(s)  1-day fine: %.2f EGP%n", catalogueId, getCategoryName(), title, status, borrower, getLoanPeriodDays(), calculateFine(1));
    }
}
