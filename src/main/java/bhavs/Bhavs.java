package bhavs;
import bhavs.tasks.TaskList;
import bhavs.utils.UI;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;


/**
 * The main entry point for the Bhavs chatbot application.
 * This class initializes and runs the chatbot, handling user interactions
 * and task management.
 */
class Bhavs {

    /** File path for saving tasks. */
    private static final String FILE_PATH = "./data/duke.txt"; // File path for saving tasks (not sure how to give this)

    /** List of tasks managed by the chatbot. */
    public TaskList taskList; // List of tasks
    @FXML
    private TextField userInputField; // Input field for user commands

    @FXML
    private TextArea outputArea; // TextArea to display output

    /**
     * The main method that starts the chatbot application.
     *
     * @param args Command-line arguments are taken in from the other side of the
     *             application.
     */
    // public static void main(String[] args) {
    //     Bhavs chatBot = new Bhavs();
    //     chatBot.run();
    // }

    /**
     * Constructs a new instance of the chatbot with an empty task list.
     */
    public Bhavs() {
        this.taskList = new TaskList();
    }

    /**
     * Runs the chatbot, handling user interactions and task management.
     * Initializes the user interface, loads tasks from storage, and processes user commands.
     */

    public String getResponse(String input) {
        return "Bhavs heard: " + input;
    }

    public String getGreeting() {
        String input = "Hello! I help keep track of your tasks." +
        "Type 'list' to see tasks, 'command' to see the list of commands, 'bye' to exit.";
        return input;
    }

    @FXML
    public void initialize() {
        // Listen for Enter key presses in the TextField
        userInputField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleUserCommand();
            }
        });
    }

    @FXML
    private void handleUserCommand() {
        String userCommand = userInputField.getText().trim();
        userInputField.clear(); // Clear input field after entry

        if (userCommand.isEmpty()) {
            return; // Ignore empty input
        }

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

                // storage.saveTasksToFile();
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
                outputArea.appendText(processRequest(userCommand) + "\n");
        }
    }

    private String processRequest(String userCommand) {
        // Process and return a response instead of printing directly
        return "Processing request: " + userCommand;
    }

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

    private String getAllCommands() {
        return "Here are the available commands:\n"
                + "-----------------------------------\n"
                + "1. list          - Display all tasks\n"
                + "2. mark          - Mark a task as completed\n"
                + "3. unmark        - Unmark a completed task\n"
                + "4. delete        - Delete a task\n"
                + "5. save          - Manually save tasks to file\n"
                + "6. quit          - Exit the program\n"
                + "7. commands      - Show this list of commands\n"
                + "8. find          - Search for a task by keyword\n"
                + "-----------------------------------";
    }

    public void run() {
        UI ui = new UI(FILE_PATH, taskList);
        ui.loadTasks();
        // ui.printWelcomeMessage();
        // ui.personalWelcomeToGuest();
        // ui.processComands();
    }
}
