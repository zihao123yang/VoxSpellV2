package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by zihao123yang on 21/10/16.
 */
public class MainMenuPromptController {

    @FXML
    Button noButton;

    public void yesPressed() throws IOException {

        Music.buttonClickSound();

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuV2.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();

        Stage stageThis = (Stage) noButton.getScene().getWindow();
        stageThis.close();

    }

    public void noPressed() {

        Music.buttonClickSound();

        Stage stage = (Stage) noButton.getScene().getWindow();
        stage.close();

    }
}

