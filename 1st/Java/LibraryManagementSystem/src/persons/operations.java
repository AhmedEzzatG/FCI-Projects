package persons;

import IO.cancelOperationException;
import IO.jReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ahmedezzat
 */
public class operations {

    public static ArrayList<user> users = new ArrayList<user>();
    static File file = new File("users.txt");
    public static user activeUser;

    public static void build() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {

            }
        }
        try {
            Scanner in = new Scanner(file);
            while (in.hasNext()) {
                String type = in.nextLine();
                switch (type) {
                    case "user":
                        users.add(new user(in));
                        break;
                    case "manager":
                        users.add(new manager(in));
                        break;
                    default:
                        break;
                }
            }
        } catch (FileNotFoundException ex) {

        }
    }

    public static void end() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {

            }
        }
        try {
            PrintWriter out = new PrintWriter(file);
            for (user a : users) {
                if (a instanceof manager) {
                    out.println("manager");
                } else {
                    out.println("user");
                }
                out.println(a.printInFile());
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException ex) {

        }

    }

    public static void signUp() throws cancelOperationException {
        user p = new user();
        users.add(p);
        activeUser = p;
    }

    public static int search(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean logIn(String email, String password) {
        int i = search(email);
        if (i != -1) {
            if (users.get(i).login(password)) {
                activeUser = users.get(i);
                return true;
            }
            jReader.showMessage("wrong password", "login");
            return false;
        }
        jReader.showNotFoundMessage("email");
        return false;
    }

    public static void personalMenu() throws cancelOperationException {
        while (true) {
            int x = jReader.showChooseOptions("choose the operation you want to do", "personal menu",
                    new String[]{"borrow Publication", "return borrowed Publication",
                        "list of your borrowed Publications"});
            long serialNumber;
            switch (x) {
                case 0:
                    serialNumber = jReader.nextLong("serial number of Publication you want to borrow", "borrowed Publications");
                    activeUser.Borrow(serialNumber);
                    break;
                case 1:
                    serialNumber = jReader.nextLong("serial number of Publication you want to return", "return borrowed Publications");
                    activeUser.returnBorrowed(serialNumber);
                    break;
                case 2:
                    jReader.showListOfItems(activeUser.arr);
                    break;
                default:
                    return;
            }
        }
    }

    public static void managerMenu() {
        while (true) {
            int x = jReader.showChooseOptions("choose the operation you want to do", "manager menu",
                    new String[]{"show list Of Borrowed Publications",
                        "show list Of Over Period Borrowed Publications"});
            switch (x) {
                case 0:
                    ((manager) activeUser).listOfBorrowedPublications();
                    break;
                case 1:
                    ((manager) activeUser).listOfOverPeriodBorrowedPublications();
                    break;
                default:
                    return;
            }
        }
    }
}
