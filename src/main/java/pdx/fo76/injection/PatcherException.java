package pdx.fo76.injection;

public class PatcherException extends Exception {
    public PatcherException(String message) {
        super(message);
    }

    public PatcherException(String message, Throwable cause) {
        super(message, cause);
    }
}
