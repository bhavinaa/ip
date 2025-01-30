
import java.util.ArrayList;
import java.util.List;

class bhavs {
    private static final String FILE_PATH = "./data/duke.txt"; // File path for saving tasks (not sure how to give this)
    private TaskList taskList; // List of tasks

    public static void main(String[] args) {
        bhavs chatBot = new bhavs();
        chatBot.run();
    }

    public bhavs() {
        this.taskList = new TaskList();
    }

    public void run() {
        UI ui = new UI(FILE_PATH, taskList);
        ui.loadTasks();
        ui.printWelcomeMessage();
        ui.personalWelcomeToGuest();
        ui.processComands();
    }
}
