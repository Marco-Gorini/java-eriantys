/*package it.polimi.ingsw.client.GUI.WindowsController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerAssistant implements Initializable {
    @FXML
    ImageView assistant1;
    @FXML
    ImageView assistant2;
    @FXML
    ImageView assistant3;
    @FXML
    ImageView assistant4;
    @FXML
    ImageView assistant5;
    @FXML
    ImageView assistant6;
    @FXML
    ImageView assistant7;
    @FXML
    ImageView assistant8;
    @FXML
    ImageView assistant9;
    @FXML
    ImageView assistant10;
    @FXML
    Label titleLabel;

    ArrayList<Integer> a = new ArrayList<>();
    Image Personaggio1 = new Image(getClass().getResourceAsStream("Assistenti/Assistente(" + 1 + ").png"));
    Image Personaggio2 = new Image(getClass().getResourceAsStream("Assistenti/Assistente(" + 2 + ").png"));
    Image Personaggio3 = new Image(getClass().getResourceAsStream("Assistenti/Assistente(" + 3 + ").png"));
    Image Personaggio4 = new Image(getClass().getResourceAsStream("Assistenti/Assistente(" + 4 + ").png"));
    Image Personaggio5 = new Image(getClass().getResourceAsStream("Assistenti/Assistente(" + 5 + ").png"));
    Image Personaggio6 = new Image(getClass().getResourceAsStream("Assistenti/Assistente(" + 6 + ").png"));
    Image Personaggio7 = new Image(getClass().getResourceAsStream("Assistenti/Assistente(" + 7 + ").png"));
    Image Personaggio8 = new Image(getClass().getResourceAsStream("Assistenti/Assistente(" + 8 + ").png"));
    Image Personaggio9 = new Image(getClass().getResourceAsStream("Assistenti/Assistente(" + 9 + ").png"));
    Image Personaggio10 = new Image(getClass().getResourceAsStream("Assistenti/Assistente(" + 10 + ").png"));

    @Override
    public void initialize(URL url, ResourceBundle rb){
        a.add(1);
        a.add(3);
        a.add(5);
        a.add(6);
        a.add(7);
        for(Integer i : a){
            if(i==1){
                assistant1.setImage(Personaggio1);
            }
            if(i==2){
                assistant2.setImage(Personaggio2);
            }
            if(i==3){
                assistant3.setImage(Personaggio3);
            }
            if(i==4){
                assistant4.setImage(Personaggio4);
            }
            if(i==5){
                assistant5.setImage(Personaggio5);
            }
            if(i==6){
                assistant6.setImage(Personaggio6);
            }
            if(i==7){
                assistant7.setImage(Personaggio7);
            }
            if(i==8){
                assistant8.setImage(Personaggio8);
            }
            if(i==9){
                assistant9.setImage(Personaggio9);
            }
            if(i==10){
                assistant10.setImage(Personaggio10);
            }

        }

    }

    public void removeAssistant(MouseEvent event){
        String image = (((ImageView) event.getSource()).getId());
        if (image.equals("assistant1")){
            assistant1.setImage(null);
            a.remove(0);
            titleLabel.setText(image);
        }
        if (image.equals("assistant2")){
            assistant2.setImage(null);
            a.remove(1);
            titleLabel.setText(image);
        }
        if (image.equals("assistant3")){
            assistant3.setImage(null);
            a.remove(2);
            titleLabel.setText(image);
        }
        if (image.equals("assistant4")){
            assistant4.setImage(null);
            a.remove(3);
            titleLabel.setText(image);
        }
        if (image.equals("assistant5")){
            assistant5.setImage(null);
            a.remove(4);
            titleLabel.setText(image);
        }
        if (image.equals("assistant6")){
            assistant6.setImage(null);
            a.remove(5);
            titleLabel.setText(image);
        }
        if (image.equals("assistant7")){
            assistant7.setImage(null);
            a.remove(6);
            titleLabel.setText(image);
        }if (image.equals("assistant8")){
            assistant8.setImage(null);
            a.remove(7);
            titleLabel.setText(image);
        }
        if (image.equals("assistant9")){
            assistant9.setImage(null);
            a.remove(8);
            titleLabel.setText(image);
        }
        if (image.equals("assistant10")){
            assistant10.setImage(null);
            a.remove(9);
            titleLabel.setText(image);
        }


    }
}
*/