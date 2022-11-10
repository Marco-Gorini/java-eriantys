/*
package it.polimi.ingsw.client.GUI.WindowsController;

import com.sun.tools.javac.Main;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ControllerLoginWindow{
    @FXML
    private Label titleLabel;

    @FXML
    private Button playersbutton2;
    @FXML
    private Button playersbutton3;
    @FXML
    private Button playersbutton4;
    @FXML
    private Button normalbutton;
    @FXML
    private Button expertbutton;

    private String players;
    private String variant;
    private String command = "setup";

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    public void set2players(ActionEvent event) {

        Button x = (Button) event.getSource();

        if (x.getId().equals(playersbutton2.getId())) {
            titleLabel.setText("due");
            players = "2";
        }
        if (x.getId().equals(playersbutton3.getId())) {
            titleLabel.setText("tre");
            players = "3";
        }
        if (x.getId().equals(playersbutton4.getId())) {
            titleLabel.setText("quattro");
            players = "4";
        }
    }

    @FXML
    public void setVariant(ActionEvent event) {
        Button x = (Button) event.getSource();

        if (x.getId().equals(normalbutton.getId())) {
            titleLabel.setText("normal");
            variant = "normal";
        }

        if (x.getId().equals(expertbutton.getId())) {
            titleLabel.setText("expert");
            variant = "expert";
        }

    }

    @FXML
    public void sendAll(ActionEvent event) throws IOException {
        command = command + " " + players + " " + variant;
        //out.write ecc
        titleLabel.setText(command);
        Parent root = FXMLLoader.load(Main.class.getResource("waitingScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        FadeTransition trans = new FadeTransition(Duration.seconds(4));
        trans.setFromValue(1.0);
        trans.setToValue(.20);
        trans.play();


    }

    public void goForward(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("nicknameWindow.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
*/



