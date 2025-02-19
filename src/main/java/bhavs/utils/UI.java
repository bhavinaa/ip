package bhavs.utils;

import bhavs.tasks.*;
import java.util.logging.Logger;

/**
 * Manages user interactions and processes commands for the Bhavs chatbot.
 * Handles input processing, task management, and file storage interactions.
 */
public class UI {

    private static final Logger LOGGER = Logger.getLogger(UI.class.getName());

    private final TaskList taskList;
    private final Storage storage;
    private String userName;

    /**
     * Constructs a UI instance to handle user interactions.
     *
     * @param storage The storage handler for saving and loading tasks.
     * @param taskList The task list to manage tasks.
     */
    public UI(Storage storage, TaskList taskList) {
        assert storage!= null: "Storage instance cannot be null";
        assert taskList != null: "TaskList instance cannot be null";

        this.taskList = taskList;
        this.storage = storage;
    }

    /**
     * Loads tasks from the storage file into memory.
     */
    public void loadTasks() {
        storage.loadTasksFromFile();
    }

    /**
     * Retrieves the storage instance.
     *
     * @return The storage instance.
     */
    public Storage getStorage() {
        return this.storage;
    }

    /**
     * Returns a welcome message.
     *
     * @return The welcome message string.
     */
    public String getWelcomeMessage() {
        return "Hello! I help keep track of your tasks.\n"
                + "Type 'list' to see tasks, 'commands' to see available commands, 'bye' to exit.";
    }

    /**
     * Returns a personalized welcome message.
     *
     * @param userName The user's name.
     * @return The personalized greeting.
     */
    public String getPersonalWelcomeMessage(String userName) {
        this.userName = userName;
        return "Hi " + userName + "! You have a cool name.\nWhat can I add to the list?";
    }

    /**
     * Processes user commands and executes corresponding actions.
     *
     * @param userCommand The command entered by the user.
     * @return The response string.
     */
    public String processCommand(String userCommand) {
        String[] parts = userCommand.split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String argument = (parts.length > 1) ? parts[1] : "";

        switch (command) {
            case "list":
                return displayTasks();
            case "mark":
                return markTask(argument);
            case "unmark":
                return unmarkTask(argument);
            case "delete":
                return deleteTask(argument);
            case "save":
                storage.saveTasksToFile();
                return "Tasks saved successfully.";
            case "quit":
            case "bye":
                return "Bye! Hope to see you again soon.";
            case "commands":
                return getAllCommands();
            default:
                return addTask(userCommand);
        }
    }

    /**
     * Marks a task as completed.
     *
     * @param argument The task number as a string.
     * @return The response message.
     */
    private String markTask(String argument) {
        try {
            int index = Integer.parseInt(argument) - 1;
            assert index >= 0 && index < taskList.size() : "Task index out of bounds: " + index;
            taskList.markTask(index);
            storage.saveTasksToFile();
            return "Nice! I've marked this task as done:\n" + taskList.get(index);
        } catch (NumberFormatException e) {
            return "Invalid task number. Use 'mark <number>'!";
        } catch (IndexOutOfBoundsException e) {
            return "Task number out of range!";
        }
    }

    /**
     * Unmarks a completed task.
     *
     * @param argument The task number as a string.
     * @return The response message.
     */
    private String unmarkTask(String argument) {
        try {
            int index = Integer.parseInt(argument) - 1;
            taskList.unmarkTask(index);
            storage.saveTasksToFile();
            return "OK! I've unmarked this task:\n" + taskList.get(index);
        } catch (NumberFormatException e) {
            return "Invalid task number. Use 'unmark <number>'!";
        } catch (IndexOutOfBoundsException e) {
            return "Task number out of range!";
        }
    }

    /**
     * Deletes a task from the list.
     *
     * @param argument The task number as a string.
     * @return The response message.
     */
    private String deleteTask(String argument) {
        try {
            int index = Integer.parseInt(argument) - 1;
            assert !taskList.isEmpty() : "Cannot delete from an empty task list";
            assert index >= 0 && index < taskList.size() : "Invalid index for deletion: " + index;

            Task removed = taskList.get(index);
            taskList.deleteTask(index);
            storage.saveTasksToFile();
            return "Noted. I've removed this task:\n" + removed;
        } catch (NumberFormatException e) {
            return "Invalid task number. Use 'delete <number>'!";
        } catch (IndexOutOfBoundsException e) {
            return "Task number out of range!";
        }
    }

    /**
     * Adds a new task to the list.
     *
     * @param input The task description.
     * @return The response message.
     */
    private String addTask(String input) {
        Task newTask = createTask(input);
        if (newTask != null) {
            taskList.add(newTask);
            storage.saveTasksToFile();
            return "Got it. I've added this task:\n" + newTask;
        }
        return "Invalid task format. Try again.";
    }

    /**
     * Creates a task from user input.
     *
     * @param input The task description and details.
     * @return The created Task object.
     */
    private Task createTask(String input) {
        assert input != null && !input.trim().isEmpty() : "Task input cannot be null or empty";
        Task newTask = null;
        String[] parts = input.split(",\\s*");
        if (parts.length == 3) {
            newTask = new Events(parts[0].trim(), parts[1].trim(), parts[2].trim());
        } else if (parts.length == 2) {
            newTask =  new Deadlines(parts[0].trim(), parts[1].trim());
        } else if (parts.length == 1) {
            newTask =  new ToDos(parts[0].trim());
        }

        assert newTask != null : "Failed to create task from input: " + input;
        return newTask;
    }

    /**
     * Displays the list of tasks.
     *
     * @return The formatted task list.
     */
    private String displayTasks() {
        assert taskList != null : "Task list is null while displaying";
        if (taskList.isEmpty()) {
            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < taskList.size(); i++) {
            assert taskList.get(i) != null : "Null task found in list at index " + i;
            sb.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the list of available commands.
     *
     * @return The formatted command list.
     */
    private String getAllCommands() {
        return """
                Available Commands:
                1. list - Display all tasks
                2. mark <number> - Mark a task as completed
                3. unmark <number> - Unmark a completed task
                4. delete <number> - Delete a task
                5. save - Save tasks to file
                6. commands - Show available commands
                7. [Task input] - Add a new task
                """;
    }
}
