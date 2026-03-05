package duke;

/**
 * Signals user-facing application errors.
 */
public class DukeException extends Exception {
    /**
     * Creates an exception with the given message.
     */
    public DukeException(String message) {
        super(message);
    }
}
