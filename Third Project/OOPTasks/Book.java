package OOPTasks;

public class Book extends LibraryItem implements Renewable {

    private static final int LOAN_PERIOD_DAYS = 14;
    private static final double DAILY_FINE = 5.00;
    private static final int RENEWAL_LIMIT = 2;

    private final String author;
    private final int pageCount;

    public Book(String catalogueId, String title, String author, int pageCount) {
        super(catalogueId, title);
        this.author = author;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public double calculateFine(int daysOverdue) {
        return DAILY_FINE * daysOverdue;
    }

    @Override
    public int getLoanPeriodDays() {
        return LOAN_PERIOD_DAYS;
    }

    @Override
    public String getCategoryName() {
        return "Book";
    }

    @Override
    public boolean renewLoan() {
        return attemptRenewal(RENEWAL_LIMIT);
    }

    @Override
    public int getRenewalLimit() {
        return RENEWAL_LIMIT;
    }
}
