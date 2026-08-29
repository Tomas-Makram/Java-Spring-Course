package OOPTasks;

public class Library {

    private static final int INITIAL_CAPACITY = 5;
    private static final int GROWTH_AMOUNT = 5;
    private static final int PROJECTED_FINE_DAYS = 5;

    private LibraryItem[] catalogue =
            new LibraryItem[INITIAL_CAPACITY];

    private int itemCount = 0;

    private Member[] members =
            new Member[INITIAL_CAPACITY];

    private int memberCount = 0;

    // ---------------------------------------------------------------
    // Registering things
    // ---------------------------------------------------------------

    public boolean registerItem(LibraryItem item) {

        if (findItemById(item.getCatalogueId()) != null) {
            return false;
        }

        if (itemCount == catalogue.length) {
            growCatalogue();
        }

        catalogue[itemCount++] = item;

        return true;
    }

    public boolean registerMember(Member member) {

        if (findMemberById(member.getMembershipId()) != null) {
            return false;
        }

        if (memberCount == members.length) {
            growMembers();
        }

        members[memberCount++] = member;

        return true;
    }

    private void growCatalogue() {

        LibraryItem[] biggerCatalogue =
                new LibraryItem[
                        catalogue.length + GROWTH_AMOUNT
                        ];

        for (int i = 0; i < catalogue.length; i++) {
            biggerCatalogue[i] = catalogue[i];
        }

        catalogue = biggerCatalogue;
    }

    private void growMembers() {

        Member[] biggerMembers =
                new Member[
                        members.length + GROWTH_AMOUNT
                        ];

        for (int i = 0; i < members.length; i++) {
            biggerMembers[i] = members[i];
        }

        members = biggerMembers;
    }

    // ---------------------------------------------------------------
    // Finding things
    // ---------------------------------------------------------------

    public LibraryItem findItemById(String catalogueId) {

        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i]
                    .getCatalogueId()
                    .equals(catalogueId)) {

                return catalogue[i];
            }
        }

        return null;
    }

    public Member findMemberById(String membershipId) {

        for (int i = 0; i < memberCount; i++) {

            if (members[i]
                    .getMembershipId()
                    .equals(membershipId)) {

                return members[i];
            }
        }

        return null;
    }

    // ---------------------------------------------------------------
    // Listing things
    // ---------------------------------------------------------------

    public LibraryItem[] listCatalogue() {

        LibraryItem[] result =
                new LibraryItem[itemCount];

        for (int i = 0; i < itemCount; i++) {
            result[i] = catalogue[i];
        }

        return result;
    }

    public LibraryItem[] listItemsByStatus(
            ItemStatus status) {

        int matchCount = 0;

        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i].getStatus() == status) {
                matchCount++;
            }
        }

        LibraryItem[] result =
                new LibraryItem[matchCount];

        int nextSlot = 0;

        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i].getStatus() == status) {
                result[nextSlot++] = catalogue[i];
            }
        }

        return result;
    }

    public Member[] listMembers() {

        Member[] result =
                new Member[memberCount];

        for (int i = 0; i < memberCount; i++) {
            result[i] = members[i];
        }

        return result;
    }

    // ---------------------------------------------------------------
    // Calculating things
    // ---------------------------------------------------------------

    public int getItemsOnLoan() {

        int count = 0;

        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i].getStatus() ==
                    ItemStatus.ON_LOAN) {

                count++;
            }
        }

        return count;
    }

    public double getLoanRate() {

        if (itemCount == 0) {
            return 0.0;
        }

        return (getItemsOnLoan() * 100.0)
                / itemCount;
    }

    public double getTotalOutstanding() {

        double total = 0.0;

        for (int i = 0; i < memberCount; i++) {
            total += members[i].getBalance();
        }

        return total;
    }

    public double getProjectedFines(
            int daysOverdue) {

        double total = 0.0;

        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i].getStatus() ==
                    ItemStatus.ON_LOAN) {

                total += catalogue[i]
                        .calculateFine(daysOverdue);
            }
        }

        return total;
    }

    // ---------------------------------------------------------------
    // Operations
    // ---------------------------------------------------------------

    public String lendItem(
            String catalogueId,
            String membershipId) {

        LibraryItem item =
                findItemById(catalogueId);

        if (item == null) {

            return "No item found with catalogue ID "
                    + catalogueId
                    + ". :(";
        }

        Member member =
                findMemberById(membershipId);

        if (member == null) {

            return "No member found with membership ID "
                    + membershipId
                    + ". :(";
        }

        if (item.getStatus() !=
                ItemStatus.AVAILABLE) {

            return "Item \""
                    + item.getTitle()
                    + "\" is not available ("
                    + item.getStatus()
                    + "). :(";
        }

        if (!member.canBorrow()) {

            return member.getName()
                    + " is not eligible to borrow "
                    + "(3 items held or balance over 100 EGP). :(";
        }

        item.lendTo(member.getName());

        member.recordBorrowing();

        return "Loaned \""
                + item.getTitle()
                + "\" to "
                + member.getName()
                + ". Due back in "
                + item.getLoanPeriodDays()
                + " day(s). :)";
    }

    public String returnItem(
            String catalogueId,
            int daysOverdue) {

        LibraryItem item =
                findItemById(catalogueId);

        if (item == null) {

            return "No item found with catalogue ID "
                    + catalogueId
                    + ". :(";
        }

        if (item.getStatus() !=
                ItemStatus.ON_LOAN) {

            return "Item \""
                    + item.getTitle()
                    + "\" is not currently on loan. :(";
        }

        if (daysOverdue < 0) {

            return "Days overdue cannot be negative. :(";
        }

        Member member =
                findMemberByName(
                        item.getBorrowerName()
                );

        double totalCharge = 0.0;

        StringBuilder breakdown =
                new StringBuilder();

        breakdown
                .append("Returned \"")
                .append(item.getTitle())
                .append("\".\n");

        if (daysOverdue == 0) {

            breakdown.append(
                    "Returned on time - no fine, "
                            + "no administrative charge.\n"
            );

        } else {

            double baseFine =
                    item.calculateFine(daysOverdue);

            double waiverRate =
                    (member != null)
                            ? member.getCategory()
                            .getWaiverRate()
                            : 0.0;

            double waivedAmount =
                    baseFine * waiverRate;

            double fineAfterWaiver =
                    baseFine - waivedAmount;

            double administrativeCharge =
                    LibraryItem
                            .getAdministrativeCharge();

            totalCharge =
                    fineAfterWaiver
                            + administrativeCharge;

            breakdown.append(
                    String.format(
                            "  Days overdue: %d%n",
                            daysOverdue
                    )
            );

            breakdown.append(
                    String.format(
                            "  Base fine: %.2f EGP%n",
                            baseFine
                    )
            );

            if (member != null) {

                breakdown.append(
                        String.format(
                                "  Waiver (%s, %.0f%%): -%.2f EGP%n",
                                member.getCategory(),
                                waiverRate * 100,
                                waivedAmount
                        )
                );
            }

            breakdown.append(
                    String.format(
                            "  Fine after waiver: %.2f EGP%n",
                            fineAfterWaiver
                    )
            );

            breakdown.append(
                    String.format(
                            "  Administrative charge "
                                    + "(not waivable): %.2f EGP%n",
                            administrativeCharge
                    )
            );

            breakdown.append(
                    String.format(
                            "  Total charged: %.2f EGP",
                            totalCharge
                    )
            );

            if (member != null) {
                member.chargeFine(totalCharge);
            }
        }

        if (member != null) {
            member.recordReturn();
        }

        item.returnItem();

        if (member != null) {

            breakdown.append(
                    String.format(
                            "%n  New balance for %s: %.2f EGP",
                            member.getName(),
                            member.getBalance()
                    )
            );

        } else {

            breakdown.append(
                    "\n  (No matching member record was found "
                            + "for the recorded borrower.)"
            );
        }

        breakdown.append(" :)");

        return breakdown.toString();
    }

    // ---------------------------------------------------------------
    // Find member by name
    // ---------------------------------------------------------------

    private Member findMemberByName(
            String borrowerName) {

        if (borrowerName == null) {
            return null;
        }

        for (int i = 0; i < memberCount; i++) {

            if (members[i]
                    .getName()
                    .equals(borrowerName)) {

                return members[i];
            }
        }

        return null;
    }

    // ---------------------------------------------------------------
    // Renew loan
    // ---------------------------------------------------------------

    public String renewLoan(
            String catalogueId) {

        LibraryItem item =
                findItemById(catalogueId);

        if (item == null) {

            return "No item found with catalogue ID "
                    + catalogueId
                    + ". :(";
        }

        if (!(item instanceof Renewable)) {

            return "\""
                    + item.getTitle()
                    + "\" is a "
                    + item.getCategoryName()
                    + " - this item type cannot be renewed. :(";
        }

        Renewable renewable =
                (Renewable) item;

        if (item.getStatus() !=
                ItemStatus.ON_LOAN) {

            return "\""
                    + item.getTitle()
                    + "\" cannot be renewed because "
                    + "it is not currently on loan. :(";
        }

        boolean success =
                renewable.renewLoan();

        if (!success) {

            return "\""
                    + item.getTitle()
                    + "\" has already reached its renewal limit ("
                    + renewable.getRenewalLimit()
                    + "). :(";
        }

        int remaining =
                renewable.getRenewalLimit()
                        - item.getRenewalCount();

        return "Renewed \""
                + item.getTitle()
                + "\". Renewals remaining: "
                + remaining
                + ". :)";
    }

    // ---------------------------------------------------------------
    // Reporting
    // ---------------------------------------------------------------

    public void generateReport() {

        System.out.println(
                "=== "
                        + LibraryItem.getLibraryName()
                        + " - Library Report ==="
        );

        System.out.printf(
                "Catalogue size: %d%n",
                itemCount
        );

        System.out.printf(
                "Items ever catalogued: %d%n",
                LibraryItem.getItemsEverCatalogued()
        );

        System.out.printf(
                "Items on loan: %d%n",
                getItemsOnLoan()
        );

        System.out.printf(
                "Loan rate: %.1f%%%n",
                getLoanRate()
        );

        System.out.printf(
                "Total outstanding across all members: %.2f EGP%n",
                getTotalOutstanding()
        );

        System.out.printf(
                "Projected fines if all loans came back "
                        + "%d day(s) late (before waivers): %.2f EGP%n",
                PROJECTED_FINE_DAYS,
                getProjectedFines(
                        PROJECTED_FINE_DAYS
                )
        );
    }
}