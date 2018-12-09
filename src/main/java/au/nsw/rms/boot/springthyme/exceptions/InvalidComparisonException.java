package au.nsw.rms.boot.springthyme.exceptions;

/**
 * Created by pavan on 9/12/18.
 */
public class InvalidComparisonException extends Exception {

    private String message;

    public InvalidComparisonException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
