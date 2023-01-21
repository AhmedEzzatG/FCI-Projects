package items;

import java.util.Scanner;

/**
 *
 * @author Ahmedezzat
 */
public class book extends publication {

    public book() throws IO.cancelOperationException {
    }

    public book(Scanner in) {
        super(in);
    }
    
    @Override
    public String getType() {
        return "book";
    }

    @Override
    public double getPenalty() {
        return 10.5;
    }

    @Override
    public long getMaxTime() {
        return 20;
    }

}
