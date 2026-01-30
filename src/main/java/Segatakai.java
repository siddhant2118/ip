import java.util.Scanner;

public class Segatakai {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Storage for tasks
        String[] tasks = new String[100];  //Array to store tasks
        int taskCount = 0;                 // Counter for number of tasks

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
            else if (input.equals("list")){
                //Display the tasks
                System.out.println("____________________________________________________________");
                for (int i=0; i<taskCount; i++){
                    System.out.println(" " + (i+1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            }
            else{
                //Add task to the list
                tasks[taskCount] = input;
                taskCount++;

                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        //Goodbye message
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}
