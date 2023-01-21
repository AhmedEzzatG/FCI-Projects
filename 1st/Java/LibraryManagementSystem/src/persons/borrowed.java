package persons;

import items.publication;
import java.util.Scanner;

/**
 *
 * @author Ahmedezzat
 */
public class borrowed {

    private long itemSerialNumber;
    private long dayOfBorrowed;

    public borrowed(long serialNumber) {
        this.itemSerialNumber = serialNumber;
        dayOfBorrowed = System.currentTimeMillis() / 86400000;
    }

    public borrowed(Scanner in) {
        itemSerialNumber = in.nextLong();
        in.nextLine();
        dayOfBorrowed = in.nextLong();
        in.nextLine();
    }

    public boolean overPeriod() {
        int index = items.operations.searchPublication(itemSerialNumber);
        return borrowedSince() > getItem().getMaxTime();
    }

    @Override
    public String toString() {
        return getItem() + "\nborrowed since : " + borrowedSince() + " days ago";
    }

    public String printInFile() {
        return itemSerialNumber + "\n" + dayOfBorrowed;
    }

    public publication getItem() {
        int index = items.operations.searchPublication(itemSerialNumber);
        return items.operations.publications.get(index);
    }

    public long borrowedSince() {
        return System.currentTimeMillis() / 86400000 - dayOfBorrowed;
    }
}
