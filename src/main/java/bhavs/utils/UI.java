package bhavs.utils;

import bhavs.tasks.*;

import java.util.Scanner;

/*

the UI was made so i
can consolidate where the input and the output of the
code functions is


*/
public class UI {

    private String userName;

    private Storage storage;
    private Scanner scanner = new Scanner(System.in);
    private String filePath;
    private TaskList taskList; // List of tasks

    public UI(String filePath, TaskList taskList) {
        this.taskList = taskList;
        this.filePath = filePath;
        this.storage = new Storage(filePath, taskList);
    }

    public void printAllComands() {
        System.out.println("Here are the available commands:");
        System.out.println("-----------------------------------");
        System.out.println("1. list          - Display all tasks");
        System.out.println("2. mark          - Mark a task as completed");
        System.out.println("3. unmark        - Unmark a completed task");
        System.out.println("4. delete        - Delete a task");
        System.out.println("5. save          - Manually save tasks to file");
        System.out.println("6. quit          - Exit the program");
        System.out.println("7. commands      - Show this list of commands");
        System.out.println("8. [Task input]  - Add a new ToDo, Deadline, or Event");

        System.out.println("-----------------------------------");
        System.out.println("\nTask Input Formats:");
        System.out.println("ToDo:        read book");
        System.out.println("Deadline:    return book, 2025/01/30 1600");
        System.out.println("Event:       birthday, 2020/05/05 0500, 2025/06/04 1900");
        System.out.println("you have to separate the task commands by the comma to differentiate it");
    }

    public void printWelcomeMessage() {
        System.out.println("Hello! I help keep track of your tasks.");
        System.out.println("Type 'list' to see tasks, 'command' to see the list of commands, 'bye' to exit.");
        System.out.println("____________________________________________________________");
    }

    public void personalWelcomeToGuest() {

        String userName = getUserInput(scanner, "What is your name?");
        this.userName = userName;
        System.out.println("Hi " + userName + "! You have a cool name.");
        System.out.println("What can I add to the list?");
    }

    public String getUserInput(Scanner scanner, String prompt) {
        if (prompt != null) {
            System.out.println(prompt);
        }
        return scanner.nextLine();
    }

    public void processComands() {
        while (true) {
            String userCommand = getUserInput(scanner, null);

            if ("bye".equalsIgnoreCase(userCommand)) {
                System.out.println("Bye, " + userName + "! Hope to see you again soon!");
                break;
            }

            switch (userCommand.toLowerCase()) {
                case "list":
                    displayTasks();
                    break;
                case "mark":
                    this.taskList.markTask(scanner);
                    break;
                case "unmark":
                    this.taskList.unmarkTask(scanner);
                    break;
                case "delete":
                    this.taskList.deleteTask(scanner);
                    break;
                case "save":
                    this.storage.saveTasksToFile();
                    System.out.println("Tasks saved successfully.");
                    break;
                case "quit":
                    System.out.println("Ending the program.");
                    return;
                case "commands":
                    printAllComands();
                    break;
                case "find":
                    System.out.println("input what do you want to find?");
                    this.taskList.findTasks(scanner);
                default:
                    processRequest(userCommand);
            }
        }

    }

    public void processRequest(String userCommand) {
        Task newTask = make_correct_entry(userCommand);

        if (newTask != null) {
            taskList.add(newTask);
            storage.saveTasksToFile();
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + newTask);
            System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        } else {
            System.out.println("Invalid task format. Please try again.");
        }
    }

    public void displayTasks() {
        if (taskList.isEmpty()) {
            System.out.println("Your task list is empty.");
        } else {
            System.out.println("Here are your tasks:");
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println((i + 1) + ". " + taskList.get(i));
            }
        }
    }

    public Task make_correct_entry(String userCommand) {
        String[] parts = userCommand.split(",\\s*"); // Split by comma, allowing spaces

        if (parts.length == 3) {
            return new Events(parts[0].trim(), parts[1].trim(), parts[2].trim());
        } else if (parts.length == 2) {
            return new Deadlines(parts[0].trim(), parts[1].trim());
        } else if (parts.length == 1) {
            return new ToDos(parts[0].trim());
        } else {
            System.out.println("Invalid input format! Use 'description, date' for deadlines or 'description, start, end' for events.");
            return null;
        }
    }

    public void loadTasks() {
        this.storage.loadTasksFromFile();
    }

}

