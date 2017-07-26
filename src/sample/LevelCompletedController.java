package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * javaFX controller for the LevelComplete.fxml scene
 */
public class LevelCompletedController implements Initializable{

    @FXML
    Button nextLevel;

    @FXML
    Text scoreText;

    @FXML
    ImageView highScoreView;

    @FXML
    Text congratsText;


    ImageView backgroundView = new ImageView();

    @FXML
    Image backgroundImage = new Image("file:background.png");

    @FXML
    BorderPane back;

    Image congratsImage = new Image("file:icons/highscore.png");

    OverallStatsData overstats = OverallStatsData.getInstance();

    /**
     * method called when the return to main menu button is pressed, changing the scene to sample.fxml
     * @throws IOException
     */
    @FXML
    public void returnToMainMenu() throws IOException {

        Music.buttonClickSound();

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuV2.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }


    /**
     * method called when the retry level button is pressed, changing the scene to SpellingQuiz.fxml
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
     * method called when the next level button is pressed, updating the level and changing the scene to SpellingQuiz.fxml
     * @throws IOException
     */
    @FXML
    public void nextLevel() throws IOException {

        Music.buttonClickSound();

        SystemStatus.goNextLevel();
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("SpellingQuizV2.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    /**
     * method called when the play video button is pressed, changing the scene to SpellingQuiz.fxml
     * @throws IOException
     */
    @FXML
    public void playVideo() throws IOException {

        Music.buttonClickSound();
        Music.muteBackground();

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("VideoScene.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    /**
     * called after the quiz is completed and checks if the next level is accessible
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        backgroundView.setImage(backgroundImage);
        backgroundView.setOpacity(0.55);
        backgroundView.setFitHeight(800);
        backgroundView.setFitWidth(1200);
        backgroundView.toBack();
        backgroundView.setEffect(new InnerShadow());
        back.getChildren().add(backgroundView);

        if (SystemStatus.currentlevel() >= SystemStatus.numOfLevels()) {
            nextLevel.setDisable(true);
        }

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

        SystemStatus.nextLevelUnlocked();
    }
}
