package bhavs;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            Logger.getLogger(DialogBox.class.getName()).log(Level.SEVERE, "Error loading DialogBox.fxml", e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates a user dialog box.
     * @param text The text to be displayed.
     * @param img The image of the user.
     * @return A new instance of DialogBox representing the user's dialog.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a Bhavs-style dialog box (flipped).
     * @param text The text to be displayed.
     * @param img The image of the speaker.
     * @return A new instance of DialogBox representing the Bhavs dialog.
     */
    public static DialogBox getBhavsDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }

    /**
     * Creates a Bhavs-style dialog box with a command-based style.
     * @param text The text to be displayed.
     * @param img The image of the speaker.
     * @param command The command type for styling.
     * @return A new instance of DialogBox representing the Bhavs dialog with styling.
     */
    public static DialogBox getBhavsDialog(String text, Image img, CommandType command) {
        var db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(command);
        return db;
    }

    /**
     * Enum for different command types used in the dialog box.
     */
    public enum CommandType {
        TASK, MARK, DELETE
    }

    /**
     * Changes the style of the dialog box based on the command type.
     * @param command The command type to determine the style.
     */
    private void changeDialogStyle(CommandType command) {
        switch (command) {
            case TASK -> dialog.getStyleClass().add("add-label");
            case MARK -> dialog.getStyleClass().add("marked-label");
            case DELETE -> dialog.getStyleClass().add("delete-label");
            default -> dialog.getStyleClass().add("");
        }
    }
}