package duke;

/**
 * Represents a todo task.
 */
public class Todo extends Task {

    /**
     * Creates a todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getTypeIcon() {
        return "T";
    }
}
