package exceptions;

public class ElementIsNotUniqueException extends Throwable {
    public static final String MESSAGE= "Every element need to be unique";

    public ElementIsNotUniqueException() {
        super(MESSAGE);
    }
}
