/*
package it.polimi.ingsw.client.GUI.WindowsController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCharacter3 implements Initializable{

    @FXML
    private ChoiceBox<String> cb;

    @FXML
    private Label titleLabel;


    private String[] island = {"island 1", "island 2", "island 3", "island 4", "island 5", "island 6", "island 7",
            "island 8", "island 9", "island 10", "island 11", "island 12"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cb.getItems().addAll(island);
    }

    public void sendIndex(MouseEvent event) {
        String islandIndex = cb.getValue();
        titleLabel.setText(islandIndex);
    }


}
*/