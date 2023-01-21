package persons;

import IO.cancelOperationException;
import IO.jReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ahmedezzat
 */
public class user {

    private String email, userName, password;

    ArrayList<borrowed> arr = new ArrayList<borrowed>();

    public user() throws cancelOperationException {
        userName = jReader.next("user name", "user information");
        email = jReader.next("email", "user information");
        while (operations.search(email) != -1) {
            jReader.showMessage("this email is used before,please try again with email", "invalid email");
            email = jReader.next("email", "user information");
        }
        password = jReader.next("password", "user information");
    }

    public user(Scanner in) {
        userName = in.nextLine();
        email = in.nextLine();
        password = in.nextLine();
        while (!"#".equals(in.nextLine())) {
            arr.add(new borrowed(in));
        }
    }

    public String printInFile() {
        String s = userName + "\n" + email + "\n" + password + "\n";
        for (borrowed b : arr) {
            s += "*\n" + b.printInFile() + "\n";
        }
        s += "#";
        return s;
    }

    private int searchInBorrowed(long serialNumber) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getItem().getSerialNumber() == serialNumber) {
                return i;
            }
        }
        return -1;
    }

    public void Borrow(long serialNumber) {
        int index = items.operations.searchPublication(serialNumber);
        if (index == -1) {
            jReader.showNotFoundMessage("serial number");
            return;
        }
        String type = items.operations.publications.get(index).getType();
        if (searchInBorrowed(serialNumber) != -1) {
            jReader.showMessage("you already borrow this " + type, "borrow " + type);
            return;
        }
        if (items.operations.publications.get(index).Avaliable() == false) {
            jReader.showMessage("sorry this " + type + " not avaliable at present", "borrow " + type);
            return;
        }
        items.publication p = items.operations.publications.get(index);
        jReader.showMessage(p.toString(), "borrow " + type);
        p.borrowed();
        arr.add(new borrowed(p.getSerialNumber()));
        jReader.showMessage("The borrowing process has been completed , you have a "
                + p.getMaxTime() + " days to return this " + type + " or you will pay " + p.getPenalty() + "$",
                "borrow " + type);
    }

    public void returnBorrowed(long serialNumber) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getItem().getSerialNumber() == serialNumber) {
                arr.get(i).getItem().returnBorrowed();
                String type = arr.get(i).getItem().getType();
                String message = "thank you for return this " + type;
                if (arr.get(i).overPeriod()) {
                    message += "but you have to pay " + arr.get(i).getItem().getPenalty() + "$ because you are late.";
                }
                jReader.showMessage(message, "return " + type);
                arr.remove(i);
                return;
            }
        }
        jReader.showMessage("this serial number NOT FOUND in your borrowed publications, please try again", "ERROR NOT FOUND");
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public boolean login(String password) {
        return this.password.equals(password);
    }
}
