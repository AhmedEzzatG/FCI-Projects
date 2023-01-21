package items;

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
public interface operations {

    public static ArrayList<publication> publications = new ArrayList<publication>();
    File file = new File("publications.txt");

    public static publication readPublication(Scanner in) {
        String type = in.nextLine();
        switch (type) {
            case "book":
                return new book(in);
            case "booklet":
                return new booklet(in);
            case "magazine":
                return new magazine(in);
        }
        return null;
    }

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
                publications.add(readPublication(in));
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
            for (publication p : publications) {
                out.println(p.printInFile());
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException ex) {

        }

    }

    public static void managerMenu() throws cancelOperationException {
        while (true) {
            int x = jReader.showChooseOptions("", "Publication menu",
                    new String[]{"Add new Publication", "modfiy Publication","delete Publication",
                        "search of Publication", "list of available publications"});
            switch (x) {
                case 0:
                    addPublication();
                    break;
                case 1:
                    modifyPublication();
                    break;
                case 2:
                    deletePublication();
                    break;
                case 3:
                    searchPublication();
                    break;
                case 4:
                    listOfAvailablePublications();
                    break;
                default:
                    return;
            }
        }
    }

    public static void userMenu() throws cancelOperationException {
        while (true) {
            int x = jReader.showChooseOptions("", "Publication menu",
                    new String[]{"search of Publication", "list of available publications"});
            switch (x) {
                case 0:
                    searchPublication();
                    break;
                case 1:
                    listOfAvailablePublications();
                    break;
                default:
                    return;
            }
        }
    }

    public static void addPublication() throws cancelOperationException {
        String type = null;
        int choose = jReader.showChooseOptions("choose type of publication", "add publication", 
                new String[]{"book","booklet","magazine"});
        switch (choose) {
            case 0:
                publications.add(new book());
                break;
            case 1:
                publications.add(new booklet());
                break;
            case 2:
                publications.add(new magazine());
                break;
            default:
                throw new cancelOperationException();
        }
    }

    public static void modifyPublication() throws cancelOperationException {
        long serialNumber = jReader.nextInt("serial number of Publication you want to modify", "modify Publications");
        int index = searchPublication(serialNumber);
        if (index == -1) {
            jReader.showNotFoundMessage("serial number");
            return;
        }
        String type = publications.get(index).getType();
        if (publications.get(index).getNumberAvaliable() < publications.get(index).getNumberOfCopies()) {
            jReader.showMessage("there some copies of this" + type + " borrowed,you can't modify", "modify " + type);
            return;
        }
        jReader.showMessage("are you sure you want to modify this " + type, "modify " + type);
        publications.get(index).modify();
    }

    public static void searchPublication() throws cancelOperationException {
        int x = jReader.showChooseOptions("", "Publication menu",
                new String[]{"search by title", "search by serial number"});
        int index;
        switch (x) {
            case 0:
                String title = jReader.next("enter title of Publication", "search");
                index = searchPublication(title);
                if (index == -1) {
                    jReader.showNotFoundMessage("title");
                    return;
                }
                if (publications.get(index).Avaliable()) {
                    jReader.showMessage("this Publication is Available\n" + publications.get(index), "search");
                } else {
                    jReader.showMessage("sorry this Publication is not availablein the present time\n"
                            + publications.get(index), "search");
                }
                break;
            case 1:
                long serialNumber = jReader.nextLong("enter serial number of Publication", "search");
                index = searchPublication(serialNumber);
                if (index == -1) {
                    jReader.showNotFoundMessage("serial number");
                    return;
                }
                if (publications.get(index).Avaliable()) {
                    jReader.showMessage("this Publication is Available\n" + publications.get(index), "search");
                } else {
                    jReader.showMessage("sorry this Publication is not available in the present time\n"
                            + publications.get(index), "search");
                }
                break;
        }
    }

    public static int searchPublication(String title) {
        for (int i = 0; i < publications.size(); i++) {
            if (publications.get(i).getTitle().equals(title)) {
                return i;
            }
        }
        return -1;
    }

    public static int searchPublication(long serialNumber) {
        for (int i = 0; i < publications.size(); i++) {
            if (publications.get(i).getSerialNumber() == serialNumber) {
                return i;
            }
        }
        return -1;
    }

    public static void deletePublication() throws cancelOperationException {
        int x = jReader.showChooseOptions("", "delete Publication menu",
                new String[]{"search by title", "search by serial number"});
        switch (x) {
            case 0:
                String title = jReader.next("enter title of Publication", "delete publication");
                deletePublication(title);
                break;
            case 1:
                long serialNumber = jReader.nextLong("enter serial number of Publication", "delete publication");
                deletePublication(serialNumber);
                break;
            default:
                break;
        }
    }

    public static void deletePublication(String title) {
        int index = searchPublication(title);
        if (index != -1) {
            publications.remove(index);
            jReader.showMessage("This Publication was deleted successfully", "delete Publication");
        } else {
            jReader.showNotFoundMessage("title");
        }
    }

    public static void deletePublication(long serialNumber) {
        int index = searchPublication(serialNumber);
        if (index != -1) {
            publications.remove(index);
            jReader.showMessage("This Publication was deleted successfully", "delete Publication");
        } else {
            jReader.showNotFoundMessage("serial number");
        }
    }

    public static void listOfAvailablePublications() {
        ArrayList<publication> tmp = new ArrayList<>();
        for (publication a : publications) {
            if (a.Avaliable()) {
                tmp.add(a);
            }
        }
        jReader.showListOfItems(tmp);
    }

}
