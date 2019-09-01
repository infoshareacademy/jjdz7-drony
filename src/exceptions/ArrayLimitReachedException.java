package exceptions;

public class ArrayLimitReachedException extends Exception {
    private static final String MESSAGE = "Array limit of has been reached";

    public ArrayLimitReachedException() {
        super(MESSAGE);
    }
}
