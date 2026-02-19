package duke;

import java.util.Random;
import java.util.Scanner;
import java.nio.file.Path;


public class Segatakai {
    private static final String LINE = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
    private static final String[] GREETINGS = {
        "Ready to crush some tasks today?",
        "Let's get things done!",
        "Your personal task samurai at your service!"
    };
    private static final String[] ADD_MESSAGES = {
        "Roger that! Task locked in:",
        "Got it! Added to your quest log:",
        "Consider it noted:"
    };
    private static final String[] MARK_MESSAGES = {
        "Victory! Another task conquered:",
        "Mission accomplished! Completed:",
        "Boom! Nailed it:"
    };
    private static final String[] UNMARK_MESSAGES = {
        "No worries, back on the to-do list:",
        "Alright, unmarked for now:",
        "Task reopened:"
    };
    private static final String[] BYE_MESSAGES = {
        "Until next time, warrior! Your tasks await...",
        "Signing off. Stay productive!",
        "See you later! Go conquer the world!"
    };
    private static final String UNKNOWN_COMMAND_MSG = "I'm sorry, but I don't know what that means :-(";
    private static final String COMMAND_HINTS = "Try: todo, deadline, event, list, mark, unmark, delete, bye";

    private static final Random random = new Random();

    private static String getRandomMessage(String[] messages) {
        return messages[random.nextInt(messages.length)];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage(Path.of("data", "duke.txt"));
        TaskList taskList = loadTaskList(storage);

        printGreeting();

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                break;
            }
            try {
                handleCommand(input, taskList, storage);
            } catch (DukeException e) {
                printError(e.getMessage());
            }
        }
        printGoodbye(taskList.size());
        scanner.close();
    }

    private static void printGreeting() {
        String logo = " ____  _____ ____    _  _____  _    _  __    _    ___ \n"
                + "/ ___|| ____/ ___|  / \\|_   _|/ \\  | |/ /   / \\  |_ _|\n"
                + "\\___ \\|  _|| |  _  / _ \\ | | / _ \\ | ' /   / _ \\  | | \n"
                + " ___) | |__| |_| |/ ___ \\| |/ ___ \\| . \\  / ___ \\ | | \n"
                + "|____/|_____\\____/_/   \\_\\_/_/   \\_\\_|\\_\\/_/   \\_\\___|\n";
        System.out.println(LINE);
        System.out.println(logo);
        System.out.println(" Hello! I'm SEGATAKAI");
        System.out.println(" " + getRandomMessage(GREETINGS));
        System.out.println(LINE);
    }

    private static void printList(TaskList taskList) {
        System.out.println(LINE);
        System.out.println(taskList.listTasks());
        System.out.println(LINE);
    }

    private static TaskList loadTaskList(Storage storage) {
        try {
            return new TaskList(storage.load());
        } catch (DukeException e) {
            printError("Unable to load saved tasks: " + e.getMessage());
            return new TaskList();
        }
    }

    private static void saveTaskList(TaskList taskList, Storage storage) throws DukeException {
        storage.save(taskList);
    }

    private static void handleCommand(String input, TaskList taskList, Storage storage) throws DukeException {
        if (input.equals("list")) {
            printList(taskList);
            return;
        }
        if (input.equals("mark") || input.startsWith("mark ")) {
            handleMark(input, taskList, storage);
            return;
        }
        if (input.equals("unmark") || input.startsWith("unmark ")) {
            handleUnmark(input, taskList, storage);
            return;
        }
        if (input.equals("delete") || input.startsWith("delete ")) {
            handleDelete(input, taskList, storage);
            return;
        }
        if (input.equals("todo") || input.startsWith("todo ")) {
            handleAddTodo(input, taskList, storage);
            return;
        }
        if (input.equals("deadline") || input.startsWith("deadline ")) {
            handleAddDeadline(input, taskList, storage);
            return;
        }
        if (input.equals("event") || input.startsWith("event ")) {
            handleAddEvent(input, taskList, storage);
            return;
        }
        throw new DukeException(UNKNOWN_COMMAND_MSG + "\n " + COMMAND_HINTS);
    }

    private static void handleMark(String input, TaskList taskList, Storage storage) throws DukeException {
        int index = parseTaskIndex(input, "mark", taskList.size());
        System.out.println(LINE);
        if (taskList.getTask(index).isDone()) {
            System.out.println(" Hey, this task is already done!");
            System.out.println("   " + taskList.getTask(index));
        } else {
            taskList.getTask(index).markAsDone();
            saveTaskList(taskList, storage);
            System.out.println(" " + getRandomMessage(MARK_MESSAGES));
            System.out.println("   " + taskList.getTask(index));
        }
        System.out.println(LINE);
    }

    private static void handleDelete(String input, TaskList taskList, Storage storage) throws DukeException {
        int index = parseTaskIndex(input, "delete", taskList.size());
        Task deletedTask = taskList.deleteTask(index);
        saveTaskList(taskList, storage);
        System.out.println(LINE);
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + deletedTask);
        System.out.println(" Now you have " + taskList.size() + " task(s) in the list.");
        System.out.println(LINE);
    }

    private static void handleUnmark(String input, TaskList taskList, Storage storage) throws DukeException {
        int index = parseTaskIndex(input, "unmark", taskList.size());
        System.out.println(LINE);
        if (!taskList.getTask(index).isDone()) {
            System.out.println(" Uhh, this task isn't marked as done yet!");
            System.out.println("   " + taskList.getTask(index));
        } else {
            taskList.getTask(index).markAsNotDone();
            saveTaskList(taskList, storage);
            System.out.println(" " + getRandomMessage(UNMARK_MESSAGES));
            System.out.println("   " + taskList.getTask(index));
        }
        System.out.println(LINE);
    }

    private static void handleAddTodo(String input, TaskList taskList, Storage storage) throws DukeException {
        String description = input.substring("todo".length()).trim();
        if (description.isEmpty()) {
            throw new DukeException("The description of a todo cannot be empty.");
        }
        taskList.addTask(new Todo(description));
        saveTaskList(taskList, storage);
        printAddConfirmation(taskList);
    }

    private static void handleAddDeadline(String input, TaskList taskList, Storage storage) throws DukeException {
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
        taskList.addTask(new Deadline(description, by));
        saveTaskList(taskList, storage);
        printAddConfirmation(taskList);
    }

    private static void handleAddEvent(String input, TaskList taskList, Storage storage) throws DukeException {
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
        taskList.addTask(new Event(description, from, to));
        saveTaskList(taskList, storage);
        printAddConfirmation(taskList);
    }

    private static void printAddConfirmation(TaskList taskList) {
        System.out.println(LINE);
        System.out.println(" " + getRandomMessage(ADD_MESSAGES));
        System.out.println("   " + taskList.getTask(taskList.size() - 1));
        System.out.println(" Now you have " + taskList.size() + " task(s) in the list.");
        System.out.println(LINE);
    }

    private static void printError(String message) {
        System.out.println(LINE);
        System.out.println(" OOPS!!! " + message);
        System.out.println(LINE);
    }

    private static void printGoodbye(int taskCount) {
        System.out.println(LINE);
        System.out.println(" " + getRandomMessage(BYE_MESSAGES));
        if (taskCount > 0) {
            System.out.println(" You have " + taskCount + " task(s) remaining. Don't forget them!");
        }
        System.out.println(LINE);
    }

    private static int parseTaskIndex(String input, String commandWord, int taskListSize) throws DukeException {
        if (taskListSize == 0) {
            throw new DukeException("There are no tasks in the list yet.");
        }

        String numberText = input.substring(commandWord.length()).trim();
        if (numberText.isEmpty()) {
            throw new DukeException("Please provide a task number for '" + commandWord + "'.");
        }

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(numberText);
        } catch (NumberFormatException e) {
            throw new DukeException("Task number must be an integer.");
        }

        if (taskNumber < 1 || taskNumber > taskListSize) {
            throw new DukeException("Task number must be between 1 and " + taskListSize + ".");
        }
        return taskNumber - 1;
    }
}
