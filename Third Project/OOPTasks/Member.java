package OOPTasks;

public class Member {

    private static final int MAX_ITEMS_HELD = 3;
    private static final double MAX_BALANCE_TO_BORROW = 100.00;

    private String name;
    private final String membershipId;
    private final MembershipType category;
    private double balance;
    private int itemsHeld;

    public Member(String name, String membershipId, MembershipType category) {
        this(name, membershipId, category, 0.0);
    }

    public Member(String name, String membershipId, MembershipType category, double openingBalance) {
        this.name = name;
        this.membershipId = membershipId;
        this.category = category;
        this.balance = Math.max(openingBalance, 0.0);
        this.itemsHeld = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public MembershipType getCategory() {
        return category;
    }

    public double getBalance() {
        return balance;
    }

    public int getItemsHeld() {
        return itemsHeld;
    }

    public boolean chargeFine(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    public boolean payFine(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public boolean canBorrow() {
        return itemsHeld < MAX_ITEMS_HELD && balance <= MAX_BALANCE_TO_BORROW;
    }

    public void recordBorrowing() {
        itemsHeld++;
    }

    public void recordReturn() {
        if (itemsHeld > 0) {
            itemsHeld--;
        }
    }
}
