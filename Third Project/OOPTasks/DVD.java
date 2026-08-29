package OOPTasks;

public class DVD extends LibraryItem {

    private static final int LOAN_PERIOD_DAYS = 3;
    private static final double DAILY_FINE = 15.00;

    private final int runtimeMinutes;

    public DVD(String catalogueId, String title, int runtimeMinutes) {
        super(catalogueId, title);
        this.runtimeMinutes = runtimeMinutes;
    }

    public int getRuntimeMinutes() {
        return runtimeMinutes;
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
        return "DVD";
    }
}
