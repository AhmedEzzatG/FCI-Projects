package persons;

import IO.jReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ahmedezzat
 */
public class manager extends user {

    public manager() throws IO.cancelOperationException {
    }

    public manager(Scanner in) {
        super(in);
    }

    public void listOfBorrowedPublications() {
        ArrayList<String> a = new ArrayList<String>();
        for (user e : operations.users) {
            for (borrowed b : e.arr) {
                a.add(e.getUserName() + "\n" + b.toString());
            }
        }
        jReader.showListOfItems(a);
    }

    public void listOfOverPeriodBorrowedPublications() {
        ArrayList<String> a = new ArrayList<String>();
        for (user e : operations.users) {
            for (borrowed b : e.arr) {
                if (b.overPeriod()) {
                    a.add(e.getUserName() + "\n" + b.toString());
                }
            }
        }
        jReader.showListOfItems(a);

    }
}
