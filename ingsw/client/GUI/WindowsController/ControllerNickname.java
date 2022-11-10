/*package it.polimi.ingsw.client.GUI.WindowsController;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerNickname {
    @FXML
    TextField nicknameField;
    @FXML
    Button button;
    @FXML
    private Label titleLabel;

    String command = "name";
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void sendNickname(ActionEvent event) throws IOException {
        command = command + " " + nicknameField.getText();
        titleLabel.setText(command);
        Parent root = FXMLLoader.load(Main.class.getResource("waitingScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
*/