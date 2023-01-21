package items;

import java.util.Scanner;

/**
 *
 * @author Ahmedezzat
 */
public class booklet extends publication {

    public booklet()  throws IO.cancelOperationException {
    }

    public booklet(Scanner in) {
        super(in);
    }

    @Override
    public String getType() {
        return "booklet";
    }

    @Override
    public double getPenalty() {
        return 15;
    }

    @Override
    public long getMaxTime() {
        return 12;
    }
}
