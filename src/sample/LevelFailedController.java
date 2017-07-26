package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * javaFX controller class for the scene LevelFailed.fxml
 */
public class LevelFailedController implements Initializable {


    @FXML
    Text scoreText;

    @FXML
    ImageView highScoreView;

    @FXML
    Text congratsText;

    @FXML
    ImageView backgroundView;

    Image backgroundImage = new Image("file:background.png");

    Image congratsImage = new Image("file:icons/highscore.png");

    OverallStatsData overstats = OverallStatsData.getInstance();

    /**
     * controls the logic for the retry level button, when pressed the scene switches to SpellingQuiz.fxml
     * @throws IOException
     */
    @FXML
    public void retryLevel() throws IOException {

        Music.buttonClickSound();

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("SpellingQuizV2.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    /**
     * controls the logic for the return to main menu button, when pressed the scene switches to sample.fxml (our main menu)
     * @throws IOException
     */
    public void returnToMainMenu() throws IOException {

        Music.buttonClickSound();

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuV2.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }


    /**
     * called when the scene is opened, makes sure to disable the next level button if on level 10
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backgroundView.setImage(backgroundImage);
        backgroundView.setOpacity(0.55);
        backgroundView.toBack();

        if (SystemStatus.isNewHighScore()) {
            highScoreView.setImage(congratsImage);
            highScoreView.setFitHeight(200);
            highScoreView.setFitWidth(150);
            congratsText.setVisible(true);
            //highScoreView.setVisible(true);
            scoreText.setText("SCORE: " + overstats.getScore());
        } else {
            congratsText.setVisible(false);
            //highScoreView.setVisible(false);
            scoreText.setText("SCORE: " + overstats.getScore());
        }

        SystemStatus.resetHighScoreFlag();
    }
}
