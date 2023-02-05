package pdx.fo76.injection;

public class EditException extends PatcherException {
    public EditException(String message) {
        super(message);
    }

    public EditException(String message, Throwable cause) {
        super(message, cause);
    }
}
