package bhavs;

import bhavs.tasks.TaskList;
import bhavs.utils.Storage;
import bhavs.utils.UI;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main chatbot application class.
 * Initializes the chatbot, handles user interactions, and manages tasks.
 */
public class Bhavs {

    /** Logger for error handling */
    private static final Logger LOGGER = Logger.getLogger(Bhavs.class.getName());

    /** Default file path for saving tasks modify this to create a new list*/

    private static String DEFAULT_FILE_PATH = "./data/dukeee.txt";

    /** List of tasks managed by the chatbot */
    private TaskList taskList;

    /** Handles UI interactions */
    private final UI ui;

    /** Handles data storage */
    private Storage storage;

    @FXML
    private TextField userInputField;

    @FXML
    private TextArea outputArea;

    /**
     * Constructs a new instance of the chatbot.
     * Initializes task list, UI, and storage.
     */
    public Bhavs() {
        this.storage = new Storage(DEFAULT_FILE_PATH);
        this.taskList = storage.getTaskList();
        this.ui = new UI(storage, taskList);
    }

    /**
     * Provides a greeting message to the user.
     *
     * @return A welcome message.
     */
    public String getGreeting() {
        return """
                Hello! I help keep track of your tasks.
                Type 'list' to see tasks, 'commands' to see available commands, 
                'newlist <filename>' to create a new task list, or 'bye' to exit.
                """;
    }

    /**
     * Initializes UI components and sets up event listeners.
     */
    @FXML
    public void initialize() {
        userInputField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleUserCommand();
            }
        });
    }

    /**
     * Handles user input, processes commands, and updates the output area.
     */
    @FXML
    private void handleUserCommand() {
        String userCommand = userInputField.getText().trim();
        userInputField.clear(); // Clear input field after entry

        if (userCommand.isEmpty()) {
            return;
        }

        try {
            if (userCommand.startsWith("newlist ")) {
                outputArea.appendText(handleNewListCommand(userCommand) + "\n");
            } else {
                outputArea.appendText(processCommand(userCommand) + "\n");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing command: " + userCommand, e);
            outputArea.appendText("An error occurred while processing your request.\n");
        }
    }

    /**
     * Processes user commands and returns a response.
     *
     * @param userCommand The command entered by the user.
     * @return The chatbot response.
     */
    private String processCommand(String userCommand) {
        return switch (userCommand.toLowerCase()) {
            case "list" -> displayTasks();
            case "mark" -> "Enter task number to mark.";
            case "unmark" -> "Enter task number to unmark.";
            case "delete" -> "Enter task number to delete.";
            case "save" -> {
                storage.saveTasksToFile();
                yield "Tasks saved successfully.";
            }
            case "quit", "bye" -> "Bye! Hope to see you again soon.";
            case "commands" -> getAllCommands();
            case "find" -> "Enter keyword to search.";
            default -> processUserCommand(userCommand);
        };
    }

    /**
     * Handles creating a new task list dynamically.
     *
     * @param userCommand The user command (e.g., "newlist myTasks.txt").
     * @return A response message indicating success or failure.
     */
    private String handleNewListCommand(String userCommand) {
        String[] parts = userCommand.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            return "Invalid command! Use 'newlist <filename>' to create a new task list.";
        }

        String newFilePath = "./data/" + parts[1].trim();
        String response = storage.createNewTaskList(newFilePath);

        this.storage = new Storage(newFilePath);
        this.taskList = storage.getTaskList();

        return response;
    }

    /**
     * Displays the list of tasks currently stored.
     *
     * @return A formatted list of tasks.
     */
    private String displayTasks() {
        if (taskList.isEmpty()) {
            return "Your task list is empty.";
        }

        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append((i + 1)).append(". ").append(taskList.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Provides a list of available commands.
     *
     * @return A formatted command list.
     */
    private String getAllCommands() {
        return """
                Available Commands:
                -----------------------------------
                1. list       - Display all tasks
                2. mark       - Mark a task as completed
                3. unmark     - Unmark a completed task
                4. delete     - Delete a task
                5. save       - Manually save tasks to file
                6. quit/bye   - Exit the program
                7. commands   - Show this list of commands
                -----------------------------------
                """;
    }

    /**
     * Runs the chatbot, initializing the UI and loading tasks from storage.
     */
    public void run() {
        ui.getTaskManager().loadTasks();
    }

    /**
     * Handles unknown user commands.
     *
     * @param userCommand The user command.
     * @return A response indicating the command is being processed.
     */
    private String processUserCommand(String userCommand) {
        return "Processing request: " + userCommand;
    }

    public static void main(String[] args) {
        new Bhavs().run();
    }
}
