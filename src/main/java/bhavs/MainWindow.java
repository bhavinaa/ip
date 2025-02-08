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

import bhavs.utils.UI;
/**
 *
 *
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
    // ui = new UI(FILE_PATH, taskList);

    private Bhavs bhavs;
    private Scene scene;

    // private String userName;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setDuke(Bhavs b) {
        bhavs = b;
    }

    public void setScene(Scene s) {
        scene = s;
    }



    public void setUI(UI ui) {
        this.ui = ui;
    }


    public String getNamePrompt() {
        return "Hello! What is your name?";
    }

    public String getPersonalWelcomeMessage(String userName) {
        this.userName = userName;
        return "Hi " + userName + "! You have a cool name.\nWhat can I add to the list?";
    }

    public void askUserName() {
        dialogContainer.getChildren().add(
                DialogBox.getBhavsDialog(getNamePrompt(), dukeImage) // Asking for name
        );
    }



    /** Greet's User */
    // public void greetUser() {
    //     dialogContainer.getChildren().add(
    //             DialogBox.getBhavsDialog(bhavs.getGreeting(), userImage)
    //     );
    //     dialogContainer.getChildren().add(
    //             DialogBox.getBhavsDialog(getPersonalWelcomeMessage(userInput.getText()), userImage)
    //     );
    //
    //     // nave.guiStart();
    // }

    public void greetUser() {
        if (ui != null) {
            dialogContainer.getChildren().add(
                    DialogBox.getBhavsDialog(ui.guiGetWelcomeMessage(), userImage)
            );
        } else {
            System.err.println("⚠️ Error: UI is not initialized in MainWindow!");
        }
    }



    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    // @FXML
    // private void handleUserInput() {
    //     String input = userInput.getText();
    //     String response = bhavs.getResponse(input);
    //     Boolean check_end = check_command(input);
    //     dialogContainer.getChildren().addAll(
    //             DialogBox.getUserDialog(input, userImage),
    //             DialogBox.getBhavsDialog(response, dukeImage)
    //     );
    //     userInput.clear();
    //
    //     if (check_end) {
    //         closeScreen();
    //     }
    // }

    private String userName = null;  // Store user's name

    // @FXML
    // private void handleUserInput() {
    //     String input = userInput.getText().trim();
    //     userInput.clear();
    //
    //     if (input.isEmpty()) {
    //         return;
    //     }
    //
    //     if (userName == null) {
    //         // If the user has not entered their name yet
    //         userName = input;
    //         dialogContainer.getChildren().add(
    //                 DialogBox.getUserDialog(input, userImage) // Show user input
    //         );
    //
    //         // Show the personal welcome message
    //         dialogContainer.getChildren().add(
    //                 DialogBox.getBhavsDialog(ui.getPersonalWelcomeMessage(userName), dukeImage)
    //         );
    //         return;
    //     }
    //
    //     // ✅ Process chatbot commands using UI class
    //     String response = ui.processCommand(input);
    //     dialogContainer.getChildren().addAll(
    //             DialogBox.getUserDialog(input, userImage),
    //             DialogBox.getBhavsDialog(response, dukeImage)
    //     );
    // }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        userInput.clear();

        if (input.isEmpty()) {
            return;
        }

        if (ui == null) {
            System.err.println("⚠️ Error: UI is not initialized in MainWindow!");
            return;
        }

        if (userName == null) {
            // If the user has not entered their name yet
            userName = input;
            dialogContainer.getChildren().add(
                    DialogBox.getUserDialog(input, userImage) // Show user input
            );

            // Show the personal welcome message
            dialogContainer.getChildren().add(
                    DialogBox.getBhavsDialog(ui.getPersonalWelcomeMessage(userName), dukeImage)
            );
            return;
        }

        // ✅ Process chatbot commands using UI class
        String response = ui.processCommand(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBhavsDialog(response, dukeImage)
        );
    }


    private boolean check_command(String input) {
        String temp = input.toLowerCase().trim();
        if(temp == "bye") {
            return true;
        } else {
            return false;
        }
    }

    private void closeScreen() {
        Duration delay = Duration.seconds(3);
        PauseTransition pause = new PauseTransition(delay);

        // ADD CODE

        pause.play();
    }
}
