package library;

import IO.*;
import persons.manager;
import persons.operations;

/**
 *
 * @author Ahmedezzat
 */
public class Library {

    public static void main(String[] args) {
        items.operations.build();
        operations.build();
        while (true) {
            int choose = jReader.showChooseOptions("Welcome in Assiut library management system", "Welcome",
                    new String[]{"Sing up", "Login"});
            switch (choose) {
                case 0: {
                    try {
                        persons.operations.signUp();
                    } catch (cancelOperationException ex) {
                        continue;
                    }
                }
                break;
                case 1: {
                    try {
                        String email = jReader.next("email", "user informatin");
                        String password = jReader.next("password", "user informatin");
                        if (operations.logIn(email, password) == false) {
                            jReader.showMessage("unsuccessful login", "unsuccessful login , try again");
                            continue;
                        }
                    } catch (cancelOperationException ex) {
                        continue;
                    }
                }
                break;
                default:
                    items.operations.end();
                    operations.end();
                    return;
            }
            String[] options;
            if (operations.activeUser instanceof persons.manager) {
                options = new String[]{"publications menu", "personal menu", "manager menu"};
            } else {
                options = new String[]{"publications menu", "personal menu"};

            }
            boolean stayHere = true;
            while (stayHere) {
                choose = jReader.showChooseOptions("Welcome " + operations.activeUser.getUserName(),
                        "Welcome in Assiut library management system", options);
                switch (choose) {
                    case 0: {
                        try {
                            if (operations.activeUser instanceof manager) {
                                items.operations.managerMenu();
                            } else {
                                items.operations.userMenu();
                            }
                        } catch (cancelOperationException ex) {
                        }
                    }
                    break;
                    case 1: {
                        try {
                            operations.personalMenu();
                        } catch (cancelOperationException ex) {
                        }
                    }
                    break;
                    case 2:
                        operations.managerMenu();
                        break;
                    default:
                        stayHere = false;
                }
            }
        }
    }

}
