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
 * The main entry point for the Bhavs chatbot application.
 * This class initializes and runs the chatbot, handling user interactions
 * and task management.
 */
public class Bhavs {

    /** Logger for error handling */
    private static final Logger LOGGER = Logger.getLogger(Bhavs.class.getName());

    /** File path for saving tasks */
    private static final String FILE_PATH = "./data/duke.txt";

    /** List of tasks managed by the chatbot */
    private final TaskList taskList;

    /** Handles UI interactions */
    private final UI ui;

    /** Handles data storage */
    private final Storage storage;

    @FXML
    private TextField userInputField; // Input field for user commands

    @FXML
    private TextArea outputArea; // TextArea to display output

    /**
     * Constructs a new instance of the chatbot.
     * Initializes task list, UI, and storage.
     */
    public Bhavs() {
        Storage storage = new Storage("./data/duke.txt");
        TaskList taskList = storage.getTaskList();  // Use the same list
        this.taskList = taskList;
        this.ui = new UI(storage, taskList);
        this.storage = storage;
    }

    /**
     * Returns a response from the chatbot.
     *
     * @param input The user input.
     * @return A formatted chatbot response.
     */
    public String getResponse(String input) {
        return "Bhavs heard: " + input;
    }

    /**
     * Provides a greeting message to the user.
     *
     * @return A welcome message.
     */
    public String getGreeting() {
        return "Hello! I help keep track of your tasks.\n"
                + "Type 'list' to see tasks, 'commands' to see the list of commands, or 'bye' to exit.";
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
     * Handles user input from the text field.
     * Processes commands and updates the output area accordingly.
     */
    @FXML
    private void handleUserCommand() {
        String userCommand = userInputField.getText().trim();
        userInputField.clear(); // Clear input field after entry

        if (userCommand.isEmpty()) {
            return; // Ignore empty input
        }

        try {
            switch (userCommand.toLowerCase()) {
                case "list":
                    outputArea.appendText(displayTasks() + "\n");
                    break;
                case "mark":
                    outputArea.appendText("Enter task number to mark.\n");
                    break;
                case "unmark":
                    outputArea.appendText("Enter task number to unmark.\n");
                    break;
                case "delete":
                    outputArea.appendText("Enter task number to delete.\n");
                    break;
                case "save":
                    storage.saveTasksToFile();
                    outputArea.appendText("Tasks saved successfully.\n");
                    break;
                case "quit":
                case "bye":
                    outputArea.appendText("Bye! Hope to see you again soon.\n");
                    break;
                case "commands":
                    outputArea.appendText(getAllCommands() + "\n");
                    break;
                case "find":
                    outputArea.appendText("Enter keyword to search.\n");
                    break;
                default:
                    outputArea.appendText(processUserCommand(userCommand) + "\n");
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
     * @return A formatted response.
     */
    private String processUserCommand(String userCommand) {
        return "Processing request: " + userCommand;
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
                Here are the available commands:
                -----------------------------------
                1. list          - Display all tasks
                2. mark          - Mark a task as completed
                3. unmark        - Unmark a completed task
                4. delete        - Delete a task
                5. save          - Manually save tasks to file
                6. quit          - Exit the program
                7. commands      - Show this list of commands
                8. find          - Search for a task by keyword
                -----------------------------------
                """;
    }

    /**
     * Runs the chatbot, initializing the UI and loading tasks from storage.
     */
    public void run() {
        ui.loadTasks();
    }
}
