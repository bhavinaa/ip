package bhavs;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import bhavs.utils.UI;
import bhavs.tasks.TaskList;
/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Bhavs duke = new Bhavs();

    // @Override
    // public void start(Stage stage) {
    //     try {
    //         FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
    //         AnchorPane ap = fxmlLoader.load();
    //         Scene scene = new Scene(ap);
    //         stage.setScene(scene);
    //         stage.setMinHeight(220);
    //         stage.setMinWidth(417);
    //         stage.setTitle("Bhavs");
    //
    //         fxmlLoader.<MainWindow>getController().setDuke(duke);  // inject the Duke instance
    //         fxmlLoader.<MainWindow>getController().setScene(scene);
    //         fxmlLoader.<MainWindow>getController().greetUser();
    //         fxmlLoader.<MainWindow>getController().askUserName();
    //         stage.show();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setTitle("Bhavs");

            // Get MainWindow controller
            MainWindow controller = fxmlLoader.getController();

            // ✅ Initialize UI and inject it into MainWindow
            UI ui = new UI("path/to/tasks.txt", new TaskList());
            controller.setUI(ui);  // ✅ Set UI before calling chatbot methods

            // ✅ Inject Duke instance (if required)
            controller.setDuke(duke);

            // ✅ Ensure name is asked before any chatbot interaction
            controller.askUserName();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
