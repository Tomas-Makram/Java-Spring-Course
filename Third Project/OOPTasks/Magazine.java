package OOPTasks;

public class Magazine extends LibraryItem implements Renewable {

    private static final int LOAN_PERIOD_DAYS = 7;
    private static final double DAILY_FINE = 3.00;
    private static final double MAX_FINE = 30.00;
    private static final int RENEWAL_LIMIT = 1;

    private final String issueNumber;

    public Magazine(String catalogueId, String title, String issueNumber) {
        super(catalogueId, title);
        this.issueNumber = issueNumber;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    @Override
    public double calculateFine(int daysOverdue) {
        double fine = DAILY_FINE * daysOverdue;
        return Math.min(fine, MAX_FINE);
    }

    @Override
    public int getLoanPeriodDays() {
        return LOAN_PERIOD_DAYS;
    }

    @Override
    public String getCategoryName() {
        return "Magazine";
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
