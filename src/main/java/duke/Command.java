package duke;

import java.time.LocalDate;

/**
 * Represents a parsed user command together with its arguments.
 */
public class Command {
    private final CommandType type;
    private final int taskNumber;
    private final String description;
    private final LocalDate byDate;
    private final String from;
    private final String to;
    private final String keyword;

    private Command(CommandType type, int taskNumber, String description,
                    LocalDate byDate, String from, String to, String keyword) {
        this.type = type;
        this.taskNumber = taskNumber;
        this.description = description;
        this.byDate = byDate;
        this.from = from;
        this.to = to;
        this.keyword = keyword;
    }

    /**
     * Creates a command with no extra payload.
     */
    public static Command ofType(CommandType type) {
        return new Command(type, -1, null, null, null, null, null);
    }

    /**
     * Creates a command that targets a task number.
     */
    public static Command forTaskNumber(CommandType type, int taskNumber) {
        return new Command(type, taskNumber, null, null, null, null, null);
    }

    /**
     * Creates a todo command.
     */
    public static Command forTodo(String description) {
        return new Command(CommandType.TODO, -1, description, null, null, null, null);
    }

    /**
     * Creates a deadline command.
     */
    public static Command forDeadline(String description, LocalDate byDate) {
        return new Command(CommandType.DEADLINE, -1, description, byDate, null, null, null);
    }

    /**
     * Creates an event command.
     */
    public static Command forEvent(String description, String from, String to) {
        return new Command(CommandType.EVENT, -1, description, null, from, to, null);
    }

    /**
     * Creates a find command.
     */
    public static Command forFind(String keyword) {
        return new Command(CommandType.FIND, -1, null, null, null, null, keyword);
    }

    /**
     * Returns the command type.
     */
    public CommandType getType() {
        return type;
    }

    /**
     * Returns the 1-based task number for task-indexed commands.
     */
    public int getTaskNumber() {
        return taskNumber;
    }

    /**
     * Returns the task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the parsed deadline date.
     */
    public LocalDate getByDate() {
        return byDate;
    }

    /**
     * Returns the event start text.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the event end text.
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the search keyword.
     */
    public String getKeyword() {
        return keyword;
    }
}
