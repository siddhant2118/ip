package duke;

public class Command {
    private final CommandType type;
    private final int taskNumber;
    private final String description;
    private final String by;
    private final String from;
    private final String to;
    private final String keyword;

    private Command(CommandType type, int taskNumber, String description,
                    String by, String from, String to, String keyword) {
        this.type = type;
        this.taskNumber = taskNumber;
        this.description = description;
        this.by = by;
        this.from = from;
        this.to = to;
        this.keyword = keyword;
    }

    public static Command ofType(CommandType type) {
        return new Command(type, -1, null, null, null, null, null);
    }

    public static Command forTaskNumber(CommandType type, int taskNumber) {
        return new Command(type, taskNumber, null, null, null, null, null);
    }

    public static Command forTodo(String description) {
        return new Command(CommandType.TODO, -1, description, null, null, null, null);
    }

    public static Command forDeadline(String description, String by) {
        return new Command(CommandType.DEADLINE, -1, description, by, null, null, null);
    }

    public static Command forEvent(String description, String from, String to) {
        return new Command(CommandType.EVENT, -1, description, null, from, to, null);
    }

    public static Command forFind(String keyword) {
        return new Command(CommandType.FIND, -1, null, null, null, null, keyword);
    }

    public CommandType getType() {
        return type;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getBy() {
        return by;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getKeyword() {
        return keyword;
    }
}
