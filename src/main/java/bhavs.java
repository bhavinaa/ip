import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class bhavs {
    public static void main(String[] args) {

        bhavs chatBot = new bhavs();
        chatBot.run();
    }



    public void run(){
        String logo = "bhavs";
        List<Task> taskList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        printWelcomeMessage(logo);
        String userName = getUserInput(scanner, "What is your name?");
        System.out.println("Hi " + userName + "! You have a cool name.");
        System.out.println("What can I add to the list?");

        while (true) {
            String userCommand = getUserInput(scanner, null);


            if ("bye".equalsIgnoreCase(userCommand)) {
                System.out.println("Bye, " + userName + "! Hope to see you again soon!");
                break;
            }

            if ("list".equalsIgnoreCase(userCommand)) {
                displayTasks(taskList);
            } else if ("mark".equalsIgnoreCase(userCommand)) {
                markTask(taskList, scanner);
            } else if ("unmark".equalsIgnoreCase(userCommand)) {
                unmarkTask(taskList, scanner);
            } else if ("all_completed_tasks".equalsIgnoreCase(userCommand)) {
                displayCompletedTasks(taskList);
            } else if ("uncompleted_tasks".equalsIgnoreCase(userCommand)) {
                displayIncompleteTasks(taskList);
            } else {
                addTask(taskList, userCommand);
            }
        }
        System.out.println("____________________________________________________________");
    }

    public void printWelcomeMessage(String logo) {
        System.out.println("Hello from\n" + logo);
        System.out.println("I help keep track of what you said.");
        System.out.println("Type 'list' to see the current list or 'bye' to exit.");
        System.out.println("____________________________________________________________");
    }

    public String getUserInput(Scanner scanner, String prompt) {
        if (prompt != null) {
            System.out.println(prompt);
        }
        return scanner.nextLine();
    }

    public void displayTasks(List<Task> taskList) {
        if (taskList.isEmpty()) {
            System.out.println("Your task list is empty.");
        } else {
            System.out.println("Here are your tasks:");
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println((i + 1) + ". " + taskList.get(i));
            }
        }
    }

    // Method to mark a task as complete
    public void markTask(List<Task> taskList, Scanner scanner) {
        String question =  "Which task number would you like to mark as done? there are " + taskList.size() + " items in your list";
        int taskIndex = getTaskIndex(scanner, question , taskList.size());
        taskList.get(taskIndex).markAsComplete();
        System.out.println("Task marked as done: " + taskList.get(taskIndex));
    }

    // Method to unmark a task
    public void unmarkTask(List<Task> taskList, Scanner scanner) {
        String question =  "Which task number would you like to mark as done? there are " + taskList.size() + " items in your list";
        int taskIndex = getTaskIndex(scanner, question , taskList.size());
        taskList.get(taskIndex).markAsIncomplete();
        System.out.println("Task unmarked: " + taskList.get(taskIndex));
    }

    // Method to display completed tasks
    public void displayCompletedTasks(List<Task> taskList) {
        System.out.println("Completed tasks:");
        for (Task task : taskList) {
            if (task.isCompleted()) {
                System.out.println(task);
            }
        }
    }

    // Method to display incomplete tasks
    public void displayIncompleteTasks(List<Task> taskList) {
        System.out.println("Incomplete tasks:");
        for (Task task : taskList) {
            if (!task.isCompleted()) {
                System.out.println(task);
            }
        }
    }

    // Method to add a task to the list
    public void addTask(List<Task> taskList, String taskDescription) {
        Task newTask = new Task(taskDescription);
        taskList.add(newTask);
        System.out.println("Added: " + taskDescription);
    }

    public int getTaskIndex(Scanner scanner, String prompt, int listSize) {
        System.out.println(prompt);
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline
        if (index < 0 || index >= listSize) {
            System.out.println("Invalid task number. Please try again.");
            return getTaskIndex(scanner, prompt, listSize);
        }
        return index;
    }

}
