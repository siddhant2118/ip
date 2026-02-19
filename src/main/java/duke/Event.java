package duke;

public class Event extends Task {

    protected String from;
    protected String to;

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
