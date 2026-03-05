package duke;

import java.util.Random;
import java.util.Scanner;

public class Ui {
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

    private final Scanner scanner;
    private final Random random;

    public Ui() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    public void showWelcome() {
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

    public void showList(TaskList taskList) {
        System.out.println(LINE);
        System.out.println(taskList.listTasks());
        System.out.println(LINE);
    }

    public void showFindResults(String results) {
        System.out.println(LINE);
        System.out.println(results);
        System.out.println(LINE);
    }

    public void showAddConfirmation(Task task, int size) {
        System.out.println(LINE);
        System.out.println(" " + getRandomMessage(ADD_MESSAGES));
        System.out.println("   " + task);
        System.out.println(" Now you have " + size + " task(s) in the list.");
        System.out.println(LINE);
    }

    public void showMarkConfirmation(Task task) {
        System.out.println(LINE);
        System.out.println(" " + getRandomMessage(MARK_MESSAGES));
        System.out.println("   " + task);
        System.out.println(LINE);
    }

    public void showAlreadyMarked(Task task) {
        System.out.println(LINE);
        System.out.println(" Hey, this task is already done!");
        System.out.println("   " + task);
        System.out.println(LINE);
    }

    public void showUnmarkConfirmation(Task task) {
        System.out.println(LINE);
        System.out.println(" " + getRandomMessage(UNMARK_MESSAGES));
        System.out.println("   " + task);
        System.out.println(LINE);
    }

    public void showAlreadyUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println(" Uhh, this task isn't marked as done yet!");
        System.out.println("   " + task);
        System.out.println(LINE);
    }

    public void showDeleteConfirmation(Task task, int size) {
        System.out.println(LINE);
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + size + " task(s) in the list.");
        System.out.println(LINE);
    }

    public void showError(String message) {
        System.out.println(LINE);
        System.out.println(" OOPS!!! " + message);
        System.out.println(LINE);
    }

    public void showGoodbye(int taskCount) {
        System.out.println(LINE);
        System.out.println(" " + getRandomMessage(BYE_MESSAGES));
        if (taskCount > 0) {
            System.out.println(" You have " + taskCount + " task(s) remaining. Don't forget them!");
        }
        System.out.println(LINE);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }

    private String getRandomMessage(String[] messages) {
        return messages[random.nextInt(messages.length)];
    }
}
