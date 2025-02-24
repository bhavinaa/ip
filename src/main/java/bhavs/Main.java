package bhavs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import bhavs.utils.Storage;
import bhavs.utils.UI;
import bhavs.tasks.TaskList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main entry point for the Bhavs chatbot application.
 * Initializes the GUI using JavaFX.
 */
public class Main extends Application {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static String DEFAULT_FILE_PATH = "./data/duk.txt";

    private final Bhavs bhavs = new Bhavs();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setTitle("Bhavs");

            initializeController(fxmlLoader);

            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading FXML file: /view/MainWindow.fxml", e);
        }
    }

    /**
     * Initializes the MainWindow controller and injects necessary dependencies.
     *
     * @param fxmlLoader The FXMLLoader instance.
     */
    private void initializeController(FXMLLoader fxmlLoader) {
        MainWindow controller = fxmlLoader.getController();

        Storage storage = new Storage(DEFAULT_FILE_PATH);
        TaskList taskList = storage.getTaskList();
        UI ui = new UI(storage, taskList);

        controller.setUI(ui);
        controller.setBhavs(bhavs);
        controller.greetUser();
        controller.askUserName();
    }
}