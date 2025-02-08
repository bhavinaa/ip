package bhavs.utils;

import bhavs.tasks.*;

import java.util.Scanner;

/*

the UI class contains all the helper functions that allows you to interact with
the system and access items from the storage


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

    // used AI to generate a code block for this (and had created this)
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

    public String guiGetAllCommands() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the available commands:\n");
        sb.append("-----------------------------------\n");
        sb.append("1. list          - Display all tasks\n");
        sb.append("2. mark          - Mark a task as completed\n");
        sb.append("3. unmark        - Unmark a completed task\n");
        sb.append("4. delete        - Delete a task\n");
        sb.append("5. save          - Manually save tasks to file\n");
        sb.append("6. quit          - Exit the program\n");
        sb.append("7. commands      - Show this list of commands\n");
        sb.append("8. [Task input]  - Add a new ToDo, Deadline, or Event\n");
        sb.append("-----------------------------------\n\n");
        sb.append("Task Input Formats:\n");
        sb.append("ToDo:        read book\n");
        sb.append("Deadline:    return book, 2025/01/30 1600\n");
        sb.append("Event:       birthday, 2020/05/05 0500, 2025/06/04 1900\n");
        sb.append("You have to separate the task commands by a comma to differentiate them\n");

        return sb.toString();
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

    public String guiGetWelcomeMessage() {
        return "Hello! I help keep track of your tasks.\n" +
                "Type 'list' to see tasks, 'commands' to see the list of commands, 'bye' to exit.\n" +
                "____________________________________________________________";
    }

    public String guiGetPersonalWelcomeMessage(String userName) {
        this.userName = userName;
        return "Hi " + userName + "! You have a cool name.\n" +
                "What can I add to the list?";
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
                    break;
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

    public String guiProcessRequest(String userCommand) {
        Task newTask = make_correct_entry(userCommand);

        if (newTask != null) {
            taskList.add(newTask);
            storage.saveTasksToFile();
            return "Got it. I've added this task:\n  " + newTask + "\nNow you have " + taskList.size() + " tasks in the list.";
        } else {
            return "Invalid task format. Please try again.";
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

    public String guidisplayTasks() {
        if (taskList.isEmpty()) {
            return "Your task list is empty.";
        }

        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append((i + 1)).append(". ").append(taskList.get(i)).append("\n");
        }
        return sb.toString();
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

    public String getPersonalWelcomeMessage(String userName) {
        this.userName = userName;
        return "Hi " + userName + "! You have a cool name.\nWhat can I add to the list?";
    }

    // public String processCommand(String userCommand) {
    //     switch (userCommand.toLowerCase()) {
    //         case "list":
    //             return guidisplayTasks();
    //         case "mark":
    //             return "Enter task number to mark.";
    //         case "unmark":
    //             return "Enter task number to unmark.";
    //         case "delete":
    //             return "Enter task number to delete.";
    //         case "save":
    //             storage.saveTasksToFile();
    //             return "Tasks saved successfully.";
    //         case "quit":
    //         case "bye":
    //             return "Bye! Hope to see you again soon.";
    //         case "commands":
    //             return guiGetAllCommands();
    //         case "find":
    //             return "Enter keyword to search.";
    //         default:
    //             return guiProcessRequest(userCommand);
    //     }
    // }

    public String processCommand(String userCommand) {
        switch (userCommand.toLowerCase()) {
            case "list":
                return guidisplayTasks();
            case "mark":
                return "Enter task number to mark.";
            case "unmark":
                return "Enter task number to unmark.";
            case "delete":
                return "Enter task number to delete.";
            case "save":
                storage.saveTasksToFile();
                return "Tasks saved successfully.";
            case "quit":
            case "bye":
                return "Bye! Hope to see you again soon.";
            case "commands":
                return guiGetAllCommands();
            case "find":
                return "Enter keyword to search.";
            default:
                return guiProcessRequest(userCommand);
        }
    }




    public void loadTasks() {
        this.storage.loadTasksFromFile();
    }

}

