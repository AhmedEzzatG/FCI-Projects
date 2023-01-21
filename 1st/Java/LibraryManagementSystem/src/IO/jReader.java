package IO;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Ahmedezzat
 */
public interface jReader {

    public static String next(String message) throws cancelOperationException {
        String s = JOptionPane.showInputDialog(message);
        if (s == null) {
            throw new cancelOperationException();
        }
        if (s.isEmpty()) {
            showInvalidInputMessage();
            return next(message);
        }
        return s;
    }

    public static String next(String message, String title) throws cancelOperationException {
        String s = JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
        if (s == null) {
            throw new cancelOperationException();
        }
        if (s.isEmpty()) {
            showInvalidInputMessage();
            return next(message, title);
        }
        return s;
    }

    public static int nextInt(String message) throws cancelOperationException {
        try {
            return Integer.parseInt(next(message));
        } catch (NumberFormatException e) {
            showInvalidInputMessage();
            return nextInt(message);
        }
    }

    public static int nextInt(String message, String title) throws cancelOperationException {
        try {
            return Integer.parseInt(next(message, title));
        } catch (NumberFormatException e) {
            showInvalidInputMessage();
            return nextInt(message, title);
        }
    }

    public static long nextLong(String message) throws cancelOperationException {
        try {
            return Long.parseLong(next(message));
        } catch (NumberFormatException e) {
            showInvalidInputMessage();
            return nextLong(message);
        }
    }

    public static long nextLong(String message, String title) throws cancelOperationException {
        try {
            return Long.parseLong(next(message, title));
        } catch (NumberFormatException e) {
            showInvalidInputMessage();
            return nextLong(message, title);
        }
    }

    public static int showChooseOptions(String massage, String title, String[] options) {
        int x = JOptionPane.showOptionDialog(null, massage, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        return x;
    }

    public static void showInvalidInputMessage() {
        JOptionPane.showMessageDialog(null, "invalid input ,please try again.", "ERROR", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showNotFoundMessage(String message) {
        JOptionPane.showMessageDialog(null, "this " + message + " NOT FOUND , please try again", "ERROR NOT FOUND", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showMessage(String massage, String title) {
        JOptionPane.showMessageDialog(null, massage, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static <E> void showListOfItems(ArrayList<E> arr) {
        String s = "";
        for (E p : arr) {
            s += p + "\n\n";
        }
        JTextArea textArea = new JTextArea(s);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(600, 600));
        JOptionPane.showMessageDialog(null, scrollPane, "list", JOptionPane.PLAIN_MESSAGE);
    }

}
