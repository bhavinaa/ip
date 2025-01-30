import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class bhavs {
    private static final String FILE_PATH = "./data/duke.txt"; // File path for saving tasks
    private List<Task> taskList; // List of tasks

    public static void main(String[] args) {
        bhavs chatBot = new bhavs();
        chatBot.run();
    }

    public bhavs() {
        this.taskList = new ArrayList<>();
        loadTasksFromFile();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        printWelcomeMessage();
        String userName = getUserInput(scanner, "What is your name?");
        System.out.println("Hi " + userName + "! You have a cool name.");
        System.out.println("What can I add to the list?");

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
                    markTask(scanner);
                    break;
                case "unmark":
                    unmarkTask(scanner);
                    break;
                case "delete":
                    deleteTask(scanner);
                    break;
                case "save":
                    saveTasksToFile();
                    System.out.println("Tasks saved successfully.");
                    break;
                case "quit":
                    System.out.println("Ending the program.");
                    return;
                case "commands":
                    printAllComands();
                    break;
                default:
                    processRequest(userCommand);
            }
        }
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
        System.out.println("Deadline:    return book, June 6th");
        System.out.println("Event:       project meeting, Aug 6th, 2-4pm");
        System.out.println("you have to separate the task commands by the comma to differentiate it");
    }


    //  Creates ToDos, Deadlines, or Events based on input format
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

    public void processRequest(String userCommand) {
        Task newTask = make_correct_entry(userCommand);

        if (newTask != null) {
            taskList.add(newTask);
            saveTasksToFile();
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + newTask);
            System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        } else {
            System.out.println("Invalid task format. Please try again.");
        }
    }

    public void printWelcomeMessage() {
        System.out.println("Hello! I help keep track of your tasks.");
        System.out.println("Type 'list' to see tasks, 'bye' to exit.");
        System.out.println("____________________________________________________________");
    }

    public String getUserInput(Scanner scanner, String prompt) {
        if (prompt != null) {
            System.out.println(prompt);
        }
        return scanner.nextLine();
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

    public void deleteTask(Scanner scanner) {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to delete.");
            return;
        }

        int taskIndex = getTaskIndex(scanner, "Which task number to delete?", taskList.size());
        Task removedTask = taskList.remove(taskIndex);
        saveTasksToFile();
        System.out.println("Task removed: " + removedTask);
    }

    public void markTask(Scanner scanner) {
        if (taskList.isEmpty()) {
            System.out.println("Your task list is empty.");
            return;
        }

        int taskIndex = getTaskIndex(scanner, "Which task number to mark as done?", taskList.size());
        taskList.get(taskIndex).markAsComplete();
        saveTasksToFile();
    }

    public void unmarkTask(Scanner scanner) {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to unmark.");
            return;
        }

        int taskIndex = getTaskIndex(scanner, "Which task number to unmark?", taskList.size());
        taskList.get(taskIndex).markAsIncomplete();
        saveTasksToFile();
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


    private void saveTasksToFile() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (Task task : taskList) {
                bw.write(task.toFileFormat());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }


    private void loadTasksFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No previous tasks found. Starting fresh.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }


    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            System.out.println("Skipping corrupted task entry: " + line);
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                return new ToDos(description, isDone);
            case "D":
                return new Deadlines(description, isDone, parts[3]);
            case "E":
                return new Events(description, isDone, parts[3], parts[4]);
            default:
                return null;
        }
    }
}
