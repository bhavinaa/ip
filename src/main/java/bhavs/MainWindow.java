package bhavs;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.stage.Stage;

import bhavs.utils.UI;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private UI ui;
    private Bhavs bhavs;
    private Scene scene;
    private String userName = null;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image bhavsImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Bhavs chatbot instance into the UI.
     *
     * @param bhavs The chatbot instance.
     */
    public void setBhavs(Bhavs bhavs) {
        this.bhavs = bhavs;
    }

    /**
     * Sets the scene for the application window.
     *
     * @param scene The JavaFX scene.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Injects the UI instance.
     *
     * @param ui The UI instance.
     */
    public void setUI(UI ui) {
        this.ui = ui;
    }

    /**
     * Displays a welcome prompt asking for the user's name.
     */
    public void askUserName() {
        dialogContainer.getChildren().add(DialogBox.getBhavsDialog("Hello! What is your name?", bhavsImage));
    }

    /**
     * Handles user input and chatbot responses.
     */
    @FXML
    private void handleUserInput() {
        if (ui == null) {
            System.err.println("⚠️ Error: UI is not initialized in MainWindow!");
            return;
        }

        String input = userInput.getText().trim();
        userInput.clear();

        if (input.isEmpty()) {
            return;
        }

        // Ask for user's name first
        if (userName == null) {
            userName = input;
            dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));
            dialogContainer.getChildren().add(DialogBox.getBhavsDialog(ui.getPersonalWelcomeMessage(userName), bhavsImage));
            return;
        }

        // Process user input
        String response = ui.processCommand(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBhavsDialog(response, bhavsImage)
        );

        // If command modifies tasks, refresh task list display
        if (input.matches("mark \\d+") || input.matches("unmark \\d+") || input.matches("delete \\d+")) {
            dialogContainer.getChildren().add(DialogBox.getBhavsDialog(ui.processCommand("list"), bhavsImage));
        }

        // Close chatbot if "bye" is entered
        if (isExitCommand(input)) {
            closeScreen();
        }
    }

    /**
     * Checks if the input is a chatbot exit command.
     *
     * @param input User input.
     * @return True if the user wants to exit, false otherwise.
     */
    private boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("bye");
    }

    /**
     * Closes the application after a short delay.
     */
    private void closeScreen() {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            if (scene != null) {
                Stage stage = (Stage) scene.getWindow();
                if (stage != null) {
                    stage.close();
                }
            }
        });
        pause.play();
    }
}
