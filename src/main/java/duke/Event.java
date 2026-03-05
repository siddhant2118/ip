package duke;

/**
 * Represents an event task with start and end details.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Creates an event task.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTypeIcon() {
        return "E";
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toStorageString() {
        return getTypeIcon() + " | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + to;
    }
}
