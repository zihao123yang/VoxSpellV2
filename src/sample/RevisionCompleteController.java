package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zihao123yang on 23/09/16.
 */
public class RevisionCompleteController implements Initializable{

    @FXML
    ImageView backgroundView = new ImageView();


    Image backgroundImage = new Image("file:background.png");

    @FXML
    public void returnMainMenu() {

        Music.buttonClickSound();

        try {
            Stage stage = Main.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("MainMenuV2.fxml"));
            stage.setScene(new Scene(root, 1200, 800));
            stage.show();
        } catch (Exception e) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backgroundView.setImage(backgroundImage);
        backgroundView.setOpacity(0.55);
        backgroundView.setPreserveRatio(false);
    }
}
