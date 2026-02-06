import java.util.Scanner;
import java.util.Random;


public class Segatakai {
    // Custom separator
    private static final String LINE = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";

    // Random messages arrays
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
    private static final int INVALID_INDEX = -1;
    private static final int COMMAND_INDEX_OFFSET = 1;
    private static final String UNKNOWN_COMMAND_MSG = " Hmm, I don't understand that command.";
    private static final String COMMAND_HINTS = " Try: todo, deadline, event, list, mark, unmark, bye";

    private static final Random random = new Random();

    /**
     * Returns a random message from the given array.
     */
    private static String getRandomMessage(String[] messages) {
        return messages[random.nextInt(messages.length)];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();

        printGreeting();

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                break;
            }
            if (input.equals("list")) {
                printList(taskList);
                continue;
            }
            if (input.startsWith("mark ")) {
                handleMark(input, taskList);
                continue;
            }
            if (input.startsWith("unmark ")) {
                handleUnmark(input, taskList);
                continue;
            }
            if (input.startsWith("todo ")) {
                handleAddTodo(input, taskList);
                continue;
            }
            if (input.startsWith("deadline ")) {
                handleAddDeadline(input, taskList);
                continue;
            }
            if (input.startsWith("event ")) {
                handleAddEvent(input, taskList);
                continue;
            }
            printUnknownCommand();
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

    private static void handleMark(String input, TaskList taskList) {
        int index = parseIndex(input, "mark ");
        System.out.println(LINE);
        if (!isValidIndex(index, taskList.size())) {
            System.out.println(" Invalid task number.");
        } else if (taskList.getTask(index).isDone()) {
            System.out.println(" Hey, this task is already done!");
            System.out.println("   " + taskList.getTask(index));
        } else {
            taskList.getTask(index).markAsDone();
            System.out.println(" " + getRandomMessage(MARK_MESSAGES));
            System.out.println("   " + taskList.getTask(index));
        }
        System.out.println(LINE);
    }

    private static void handleUnmark(String input, TaskList taskList) {
        int index = parseIndex(input, "unmark ");
        System.out.println(LINE);
        if (!isValidIndex(index, taskList.size())) {
            System.out.println(" Invalid task number.");
        } else if (!taskList.getTask(index).isDone()) {
            System.out.println(" Uhh, this task isn't marked as done yet!");
            System.out.println("   " + taskList.getTask(index));
        } else {
            taskList.getTask(index).markAsNotDone();
            System.out.println(" " + getRandomMessage(UNMARK_MESSAGES));
            System.out.println("   " + taskList.getTask(index));
        }
        System.out.println(LINE);
    }

    private static void handleAddTodo(String input, TaskList taskList) {
        String description = input.substring(5).trim();
        taskList.addTask(new Todo(description));
        printAddConfirmation(taskList);
    }

    private static void handleAddDeadline(String input, TaskList taskList) {
        String[] parts = input.substring(9).split(" /by ", 2);
        if (parts.length < 2) {
            System.out.println(LINE);
            System.out.println(" Invalid deadline format. Use: deadline <desc> /by <date>");
            System.out.println(LINE);
            return;
        }
        String description = parts[0].trim();
        String by = parts[1].trim();
        taskList.addTask(new Deadline(description, by));
        printAddConfirmation(taskList);
    }

    private static void handleAddEvent(String input, TaskList taskList) {
        String[] parts = input.substring(6).split(" /from ", 2);
        if (parts.length < 2) {
            System.out.println(LINE);
            System.out.println(" Invalid event format. Use: event <desc> /from <start> /to <end>");
            System.out.println(LINE);
            return;
        }
        String description = parts[0].trim();
        String[] times = parts[1].split(" /to ", 2);
        if (times.length < 2) {
            System.out.println(LINE);
            System.out.println(" Invalid event format. Use: event <desc> /from <start> /to <end>");
            System.out.println(LINE);
            return;
        }
        String from = times[0].trim();
        String to = times[1].trim();
        taskList.addTask(new Event(description, from, to));
        printAddConfirmation(taskList);
    }

    private static void printAddConfirmation(TaskList taskList) {
        System.out.println(LINE);
        System.out.println(" " + getRandomMessage(ADD_MESSAGES));
        System.out.println("   " + taskList.getTask(taskList.size() - 1));
        System.out.println(" Now you have " + taskList.size() + " task(s) in the list.");
        System.out.println(LINE);
    }

    private static void printUnknownCommand() {
        System.out.println(LINE);
        System.out.println(UNKNOWN_COMMAND_MSG);
        System.out.println(COMMAND_HINTS);
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

    private static int parseIndex(String input, String commandPrefix) {
        try {
            return Integer.parseInt(input.substring(commandPrefix.length()).trim()) - COMMAND_INDEX_OFFSET;
        } catch (NumberFormatException e) {
            return INVALID_INDEX;
        }
    }

    private static boolean isValidIndex(int index, int size) {
        return index >= 0 && index < size;
    }
}
