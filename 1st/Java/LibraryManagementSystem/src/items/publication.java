package items;

import IO.cancelOperationException;
import IO.jReader;
import java.util.Scanner;

/**
 *
 * @author Ahmedezzat
 */
public abstract class publication {

    private String title;
    private final long serialNumber;
    private String author, publisher;
    private int numberOfCopies, numberAvaliable;

    public publication() throws cancelOperationException {
        title = jReader.next("title", "Publication information");
        author = jReader.next("author", "Publication information");
        publisher = jReader.next("publisher", "Publication information");
        long x = jReader.nextLong("serial number", "Publication information");
        while (operations.searchPublication(x) != -1) {
            jReader.showMessage("this serial number is used before,please try again with another serial",
                    "invalid serial number");
            x = jReader.nextLong("serial number", "Publication information");
        }
        serialNumber = x;
        numberOfCopies = jReader.nextInt("number of copies", "Publication information");
        numberAvaliable = numberOfCopies;
    }

    public publication(Scanner in) {
        title = in.nextLine();
        author = in.nextLine();
        publisher = in.nextLine();
        serialNumber = in.nextLong();
        in.nextLine();
        numberOfCopies = in.nextInt();
        in.nextLine();
        numberAvaliable = in.nextInt();
        in.nextLine();
    }
    
    public void modify() throws cancelOperationException{
        title = jReader.next("title", "modify Publication information");
        author = jReader.next("author", "modify Publication information");
        publisher = jReader.next("publisher", "modify Publication information");
        numberOfCopies = jReader.nextInt("number of copies", "modify Publication information");
        numberAvaliable = numberOfCopies;
    }

    @Override
    public String toString() {
        return getType() + " " + title
                + "\nauthor : " + author
                + ", publisher : " + publisher
                + ", serial number : " + serialNumber
                + ", number of copies : " + numberOfCopies
                + ", number of Avaliable copies : " + numberAvaliable;
    }

    public String printInFile() {
        return getType() + "\n" + title + "\n" + author + "\n" + publisher + "\n"
                + serialNumber + "\n" + numberOfCopies + "\n" + numberAvaliable;
    }

    public boolean Avaliable() {
        return numberAvaliable > 0;
    }

    public void borrowed() {
        numberAvaliable--;
    }

    public void returnBorrowed() {
        numberAvaliable++;
    }

    public abstract String getType();

    public abstract double getPenalty();

    public abstract long getMaxTime();

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public int getNumberAvaliable() {
        return numberAvaliable;
    }
}
