/*
package it.polimi.ingsw.client.GUI.WindowsController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCharacter5 implements Initializable {

    @FXML
    ImageView pc1;

    @FXML
    ImageView pc2;

    @FXML
    ImageView pc3;

    @FXML
    ImageView pc4;

    String image;

    @FXML
    Label titleLabel;

    boolean selected = false;

    @FXML
    private ChoiceBox<String> cb2;


    private String[] island = {"island 1", "island 2", "island 3", "island 4", "island 5", "island 6", "island 7",
            "island 8", "island 9", "island 10", "island 11", "island 12"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cb2.getItems().addAll(island);
    }

    public void selectPC (MouseEvent event) {
        image = (((ImageView) event.getSource()).getId());
        titleLabel.setText(image);
        if (selected == false) {
            if (image.equals("pc1")) {
                pc1.setOpacity(0);
                selected = true;
            }
            if (image.equals("pc2")) {
                pc2.setOpacity(0);
                selected = true;
            }
            if (image.equals("pc3")) {
                pc3.setOpacity(0);
                selected = true;
            }
            if (image.equals("pc4")) {
                pc4.setOpacity(0);
                selected = true;
            }
        }else{
            titleLabel.setText("you have already used your prohibition card, please select an island");
        }
    }

    public void usePC(MouseEvent event) {
        String islandIndex2 = cb2.getValue();
        titleLabel.setText(islandIndex2);
        selected = false;
    }


}
*/
