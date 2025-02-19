
package bhavs.utils;

import java.util.logging.Logger;


/**
 * Processes user commands and executes corresponding actions.
 */
public class CommandProcessor {
    private static final Logger LOGGER = Logger.getLogger(CommandProcessor.class.getName());
    private static TaskManager taskManager;
    private static  Storage storage;

    /**
     * Constructs a CommandProcessor instance.
     *
     * @param taskManager The task manager instance.
     * @param storage     The storage handler.
     */
    public CommandProcessor(TaskManager taskManager, Storage storage) {
        assert taskManager != null : "TaskManager cannot be null";
        assert storage != null : "Storage cannot be null";

        this.taskManager = taskManager;
        this.storage = storage;
    }

    /**
     * Processes user commands and delegates them to the appropriate method.
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
                return this.taskManager.displayTasks();
            case "mark":
                return this.taskManager.markTask(argument);
            case "unmark":
                return this.taskManager.unmarkTask(argument);
            case "delete":
                return this.taskManager.deleteTask(argument);
            case "save":
                storage.saveTasksToFile();
                return "Tasks saved successfully.";
            case "quit":
            case "bye":
                return "Bye! Hope to see you again soon.";
            case "commands":
                return this.getAllCommands();
            default:
                return this.taskManager.addTask(userCommand);
        }
    }

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

