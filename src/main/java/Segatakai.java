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
    
    private static Random random = new Random();
    
    private static String getRandomMessage(String[] messages) {
        return messages[random.nextInt(messages.length)];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Storage for tasks
        Task[] tasks = new Task[100];  // Array to store tasks
        int taskCount = 0;             // Counter for number of tasks

        // Greeting
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

        // Loop to read commands
        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                // Display the tasks
                System.out.println(LINE);
                if (taskCount == 0) {
                    System.out.println(" No tasks yet! Time to add some adventures.");
                } else {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + "." + tasks[i]);
                    }
                }
                System.out.println(LINE);
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                System.out.println(LINE);
                if (tasks[index].isDone()) {
                    System.out.println(" Hey, this task is already done!");
                    System.out.println("   " + tasks[index]);
                } else {
                    tasks[index].markAsDone();
                    System.out.println(" " + getRandomMessage(MARK_MESSAGES));
                    System.out.println("   " + tasks[index]);
                }
                System.out.println(LINE);
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                System.out.println(LINE);
                if (!tasks[index].isDone()) {
                    System.out.println(" Uhh, this task isn't marked as done yet!");
                    System.out.println("   " + tasks[index]);
                } else {
                    tasks[index].markAsNotDone();
                    System.out.println(" " + getRandomMessage(UNMARK_MESSAGES));
                    System.out.println("   " + tasks[index]);
                }
                System.out.println(LINE);
            } else if (input.startsWith("todo ")) {
                // Create Todo task
                String description = input.substring(5);
                tasks[taskCount] = new Todo(description);
                taskCount++;

                System.out.println(LINE);
                System.out.println(" " + getRandomMessage(ADD_MESSAGES));
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " task(s) in the list.");
                System.out.println(LINE);
            } else if (input.startsWith("deadline ")) {
                // Create Deadline task: deadline <desc> /by <date>
                String[] parts = input.substring(9).split(" /by ");
                String description = parts[0];
                String by = parts[1];
                tasks[taskCount] = new Deadline(description, by);
                taskCount++;

                System.out.println(LINE);
                System.out.println(" " + getRandomMessage(ADD_MESSAGES));
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " task(s) in the list.");
                System.out.println(LINE);
            } else if (input.startsWith("event ")) {
                // Create Event task: event <desc> /from <start> /to <end>
                String[] parts = input.substring(6).split(" /from ");
                String description = parts[0];
                String[] times = parts[1].split(" /to ");
                String from = times[0];
                String to = times[1];
                tasks[taskCount] = new Event(description, from, to);
                taskCount++;

                System.out.println(LINE);
                System.out.println(" " + getRandomMessage(ADD_MESSAGES));
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " task(s) in the list.");
                System.out.println(LINE);
            } else {
                // Unknown command
                System.out.println(LINE);
                System.out.println(" Hmm, I don't understand that command.");
                System.out.println(" Try: todo, deadline, event, list, mark, unmark, bye");
                System.out.println(LINE);
            }
        }
        // Goodbye message
        System.out.println(LINE);
        System.out.println(" " + getRandomMessage(BYE_MESSAGES));
        if (taskCount > 0) {
            System.out.println(" You have " + taskCount + " task(s) remaining. Don't forget them!");
        }
        System.out.println(LINE);

        scanner.close();
    }
}
