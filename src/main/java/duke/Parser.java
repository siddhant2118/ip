package duke;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final String UNKNOWN_COMMAND_MSG = "I'm sorry, but I don't know what that means :-(";
    private static final String COMMAND_HINTS = "Try: todo, deadline, event, list, mark, unmark, delete, find, bye";

    public Command parse(String input) throws DukeException {
        if (input.equals("bye")) {
            return Command.ofType(CommandType.BYE);
        }
        if (input.equals("list")) {
            return Command.ofType(CommandType.LIST);
        }
        if (input.equals("mark") || input.startsWith("mark ")) {
            return Command.forTaskNumber(CommandType.MARK, parseTaskNumber(input, "mark"));
        }
        if (input.equals("unmark") || input.startsWith("unmark ")) {
            return Command.forTaskNumber(CommandType.UNMARK, parseTaskNumber(input, "unmark"));
        }
        if (input.equals("delete") || input.startsWith("delete ")) {
            return Command.forTaskNumber(CommandType.DELETE, parseTaskNumber(input, "delete"));
        }
        if (input.equals("todo") || input.startsWith("todo ")) {
            return Command.forTodo(parseTodoDescription(input));
        }
        if (input.equals("deadline") || input.startsWith("deadline ")) {
            return parseDeadline(input);
        }
        if (input.equals("event") || input.startsWith("event ")) {
            return parseEvent(input);
        }
        if (input.equals("find") || input.startsWith("find ")) {
            return Command.forFind(parseFindKeyword(input));
        }
        throw new DukeException(UNKNOWN_COMMAND_MSG + "\n " + COMMAND_HINTS);
    }

    private int parseTaskNumber(String input, String commandWord) throws DukeException {
        String numberText = input.substring(commandWord.length()).trim();
        if (numberText.isEmpty()) {
            throw new DukeException("Please provide a task number for '" + commandWord + "'.");
        }
        try {
            return Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            throw new DukeException("Task number must be an integer.");
        }
    }

    private String parseTodoDescription(String input) throws DukeException {
        String description = input.substring("todo".length()).trim();
        if (description.isEmpty()) {
            throw new DukeException("The description of a todo cannot be empty.");
        }
        return description;
    }

    private Command parseDeadline(String input) throws DukeException {
        String commandBody = input.substring("deadline".length()).trim();
        if (commandBody.isEmpty()) {
            throw new DukeException("The description of a deadline cannot be empty.");
        }
        String[] parts = commandBody.split(" /by ", 2);
        if (parts.length < 2) {
            throw new DukeException("Invalid deadline format. Use: deadline <desc> /by <date>");
        }
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty()) {
            throw new DukeException("The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new DukeException("The deadline date/time cannot be empty.");
        }
        LocalDate byDate;
        try {
            byDate = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            throw new DukeException("Deadline date must be in yyyy-MM-dd format.");
        }
        return Command.forDeadline(description, byDate);
    }

    private Command parseEvent(String input) throws DukeException {
        String commandBody = input.substring("event".length()).trim();
        if (commandBody.isEmpty()) {
            throw new DukeException("The description of an event cannot be empty.");
        }
        String[] parts = commandBody.split(" /from ", 2);
        if (parts.length < 2) {
            throw new DukeException("Invalid event format. Use: event <desc> /from <start> /to <end>");
        }
        String description = parts[0].trim();
        String[] times = parts[1].split(" /to ", 2);
        if (times.length < 2) {
            throw new DukeException("Invalid event format. Use: event <desc> /from <start> /to <end>");
        }
        String from = times[0].trim();
        String to = times[1].trim();
        if (description.isEmpty()) {
            throw new DukeException("The description of an event cannot be empty.");
        }
        if (from.isEmpty() || to.isEmpty()) {
            throw new DukeException("The event start/end time cannot be empty.");
        }
        return Command.forEvent(description, from, to);
    }

    private String parseFindKeyword(String input) throws DukeException {
        String keyword = input.substring("find".length()).trim();
        if (keyword.isEmpty()) {
            throw new DukeException("Please provide a keyword for find.");
        }
        return keyword;
    }
}
