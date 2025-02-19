package bhavs.utils;

import bhavs.tasks.*;
import java.util.logging.Logger;

/**
 * Manages user interactions and processes commands for the Bhavs chatbot.
 * Handles input processing, task management, and file storage interactions.
 */
public class UI {

    private static final Logger LOGGER = Logger.getLogger(UI.class.getName());

    private final TaskManager taskManager;

    private TaskList taskList;

    private String userName;

    /**
     * Constructs a UI instance to handle user interactions.
     *
     * @param storage
     *         The storage handler for saving and loading tasks.
     * @param taskList
     *         The task list to manage tasks.
     */
    public UI(Storage storage, TaskList taskList) {
        assert storage != null: "the storage has not been initalised";
        assert taskList != null: "the taskList has not been initalised";
       this.taskManager = new TaskManager(storage, taskList);
    }

    public TaskManager getTaskManager() {
        return this.taskManager;
    }

    /**
     * Returns a welcome message.
     *
     * @return The welcome message string.
     */
    public String getWelcomeMessage() {
        return "Hello! I help keep track of your tasks.\n" + "Type 'list' to see tasks, 'commands' to see available commands, 'bye' to exit.";
    }

    /**
     * Returns a personalized welcome message.
     *
     * @param userName
     *         The user's name.
     *
     * @return The personalized greeting.
     */
    public String getPersonalWelcomeMessage(String userName) {
        this.userName = userName;
        return "Hi " + userName + "! You have a cool name.\nWhat can I add to the list?";
    }

    public String processCommand(String userCommand) {
        return this.taskManager.getCommandProcessor().processCommand(userCommand);
    }
}

