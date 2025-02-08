import bhavs.tasks.TaskList;
import bhavs.utils.UI;


/**
 * The main entry point for the Bhavs chatbot application.
 * This class initializes and runs the chatbot, handling user interactions
 * and task management.
 */
class Bhavs {

    /** File path for saving tasks. */
    private static final String FILE_PATH = "./data/bhavs.txt";

    /** List of tasks managed by the chatbot. */
    private TaskList taskList;

    /**
     * Constructs a new instance of the chatbot with an empty task list.
     */
    public Bhavs() {
        this.taskList = new TaskList();
    }

    /**
     * The main method that starts the chatbot application.
     *
     * @param args Command-line arguments are taken in from the other side of the application.
     */

    public static void main(String[] args) {
        Bhavs chatBot = new Bhavs();
        chatBot.run();
    }

    /**
     * Runs the chatbot, handling user interactions and task management.
     * Initializes the user interface, loads tasks from storage, and processes user commands.
     */
    public void run() {
        UI ui = new UI(FILE_PATH, taskList);
        ui.loadTasks();
        ui.printWelcomeMessage();
        ui.personalWelcomeToGuest();
        ui.processComands();
    }

    public String getResponse(String input) {
        return "Bhavs heard: " + input;
    }
}
