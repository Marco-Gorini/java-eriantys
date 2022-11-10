/*
package it.polimi.ingsw.client.GUI.WindowsController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCharacter7 implements Initializable {
    @FXML
    Label titleLabel;
    @FXML
    Paint colore;

    @FXML
    private ChoiceBox<String> cb;

    private String student;

    private String[] island = {"island 1", "island 2", "island 3", "island 4", "island 5", "island 6", "island 7",
            "island 8", "island 9", "island 10", "island 11", "island 12"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        cb.getItems().addAll(island);

    }

    public void choseIsland(MouseEvent event) {
        colore = (((Circle) event.getSource()).getFill());
        if (colore == Color.PINK) {
            student = "p";
            titleLabel.setText("PINK");
        }
        if (colore == Color.DODGERBLUE) {
            student = "b";
            titleLabel.setText("BLUE");
        }
        if (colore == Color.RED) {
            student = "r";
            titleLabel.setText("RED");
        }
        if (colore == Color.YELLOW) {
            student = "y";
            titleLabel.setText("YELLOW");
        }
        if (colore == Color.GREEN) {
            student = "g";
            titleLabel.setText("GREEN");
        }

    }

    public void sendAll(MouseEvent event) {
        String islandIndex = cb.getValue();
        if (student != null && islandIndex != null) {
            titleLabel.setText(student + " " + islandIndex);
        } else if (student == null) {
            titleLabel.setText("please select a student to move");
        } else if (islandIndex == null) {
            titleLabel.setText("please select an Island");
        }
    }
}
*/
