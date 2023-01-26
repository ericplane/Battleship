package battleship;

import java.lang.Exception;

public class battleshipException extends Exception {
    public battleshipException(String msg) {
        super(msg);
    }

    public battleshipException(Exception cause) {
        super(cause);
    }
}