/*
package it.polimi.ingsw.client.GUI.WindowsController;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerPlayCharacter {
    String command = "n";
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label titleLabel;


    public void continueToPlay(MouseEvent event){
        titleLabel.setText(command); //sarebbe poi l'out.write
    }

    public void showCharacter(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("Character.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
 */