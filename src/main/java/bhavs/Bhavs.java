
import bhavs.utils.UI;
import bhavs.tasks.TaskList;

/**
 * The main logic for the Bhavs chatbot.
 */
public class Bhavs {

    private static final String FILE_PATH = "./data/bhavs.txt";
    private TaskList taskList;
    private UI ui;

    /**
     * Constructs a new instance of the chatbot.
     */
    public Bhavs() {
        this.taskList = new TaskList();
        this.ui = new UI(FILE_PATH, taskList);
        ui.loadTasks();
    }

    /**
     * Processes a single command from the GUI and returns the response.
     *
     * @param input The user input command.
     * @return The chatbot's response as a string.
     */
    public String getResponse(String input) {
        return ui.processComands();
    }

    /**
     * Launches the GUI.
     */
    public static void main(String[] args) {
        Main.launch(Main.class, args);
    }
}
