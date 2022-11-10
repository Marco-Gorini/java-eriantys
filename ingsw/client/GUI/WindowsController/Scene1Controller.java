package it.polimi.ingsw.client.GUI.WindowsController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene1Controller {
        @FXML
        private Stage stage;
        private Scene scene;
        private Parent root;

        public void login(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("black.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
}