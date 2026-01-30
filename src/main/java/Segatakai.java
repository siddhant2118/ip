import java.util.Scanner;

public class Segatakai {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Greeting
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm SEGATAKAI");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");

        //Loop to read commands
        while(true){
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            }

            //Echo the input back
            System.out.println("____________________________________________________________");
            System.out.println(" " + input);
            System.out.println("____________________________________________________________");
        }
        //Goodbye message
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}
