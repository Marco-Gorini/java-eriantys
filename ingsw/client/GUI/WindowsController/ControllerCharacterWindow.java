/*package it.polimi.ingsw.client.GUI.WindowsController;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCharacterWindow implements Initializable {
    @FXML
    Label titleLabel;

    String command1;
    String command2 = "character";
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    ImageView firstCharacter;
    @FXML
    Image Personaggio1;
    @FXML
    Image Personaggio2;
    @FXML
    Image Personaggio3;
    @FXML
    ImageView secondCharacter;
    @FXML
    ImageView thirdCharacter;

    int index1 = 7; // mi devono passare l'indice
    int index2 = 10;
    int index3 = 3;
    int index = 0;
    String character;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        Image Personaggio1 = new Image(getClass().getResourceAsStream("Character/" + index1 + ".jpg"));
        firstCharacter.setImage(Personaggio1);

        Image Personaggio2 = new Image(getClass().getResourceAsStream("Character/" + index2 + ".jpg"));
        secondCharacter.setImage(Personaggio2);

        Image Personaggio3 = new Image(getClass().getResourceAsStream("Character/" + index3 + ".jpg"));
        thirdCharacter.setImage(Personaggio3);

    }

    public void setFirstCharacter (MouseEvent event) {

        firstCharacter.setOpacity(0);
        index = index1;
    }

    public void setSecondCharacter (MouseEvent event){
        secondCharacter.setOpacity(0);
        index = index2;
    }

    public void setThirdCharacter (MouseEvent event)  {
        thirdCharacter.setOpacity(0);
        index = index3;
    }

    public void sendButton(MouseEvent event) throws IOException{
        if(index == 0){
            titleLabel.setText("Please select a character");
        }else {
            command1 = "y";
            command2 = command2 + " " + index;
            titleLabel.setText(command2);
            if(index == index1){
                Parent root = FXMLLoader.load(Main.class.getResource("character" + index1 + ".fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            if(index == index2){
                Parent root = FXMLLoader.load(Main.class.getResource("character" + index2 + ".fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            if (index == index3){
                Parent root = FXMLLoader.load(Main.class.getResource("character" + index3 + ".fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void backButton (MouseEvent event){
        command1 = "n";
        titleLabel.setText("command1");
    }




}

*/