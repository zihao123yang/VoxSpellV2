package sample;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by zihao123yang on 15/10/16.
 */
public class MainMenuV2Controller implements Initializable {


    @FXML
    Button newQuizButton;

    @FXML
    Button revision;

    @FXML
    Button statistics;

    @FXML
    Button settings;

    @FXML
    Text voxspell;

    @FXML
    Text explanation;

    @FXML
    Pane textPane;

    @FXML
    ImageView backgroundView;

    @FXML
     Image backgroundImage = new Image("file:background.png");

    @FXML
    ImageView helpView;

    Image questionImage = new Image("file:icons/question.png");

    ArrayList<Button> mainMenuButtons = new ArrayList<Button>();

    public void addButtons() {
        mainMenuButtons.addAll(Arrays.asList(newQuizButton, revision, statistics, settings));
    }

    public void colorAdjustSetUp() {

        for (Button button: mainMenuButtons) {

            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.0);

            button.setEffect(colorAdjust);

            button.setOnMouseEntered(e -> {

                Timeline fadeInTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(colorAdjust.brightnessProperty(), colorAdjust.brightnessProperty().getValue(), Interpolator.LINEAR)),
                        new KeyFrame(Duration.seconds(0.25), new KeyValue(colorAdjust.brightnessProperty(), -0.25, Interpolator.LINEAR)
                        ));

                fadeInTimeline.setCycleCount(1);
                fadeInTimeline.setAutoReverse(false);
                fadeInTimeline.play();

                if (button.getText().equals("NEW QUIZ")) {
                    setNewQuizText();
                } else if (button.getText().equals("REVISION QUIZ")) {
                    setRevisionQuizText();
                } else if (button.getText().equals("VIEW STATISTICS")) {
                    setStatisticsText();
                } else {
                    setSettingsText();
                }

                button.setPrefWidth(500);

            });

            button.setOnMouseExited(e -> {

                Timeline fadeOutTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(colorAdjust.brightnessProperty(), colorAdjust.brightnessProperty().getValue(), Interpolator.LINEAR)),
                        new KeyFrame(Duration.seconds(0.25), new KeyValue(colorAdjust.brightnessProperty(), 0, Interpolator.LINEAR)
                        ));
                fadeOutTimeline.setCycleCount(1);
                fadeOutTimeline.setAutoReverse(false);
                fadeOutTimeline.play();

                setVoxspellText();

                button.setPrefWidth(450);

            });
        }

    }



    /**
     * controls the logic for the new spelling quiz pane in the main menu, taking the user to a new scene SelectQuizSettings.fxml
     * @throws IOException
     */
    @FXML
    public void newSpellingQuizClicked() throws IOException {

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("SelectQuizSettings.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    /**
     * controls the logic for the new review quiz pane in the main menu, taking the user to a new scene RevisionSettings.fxml
     * @throws IOException
     */
    public void reviewQuizClicked() throws IOException {
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("RevisionSettings.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();

    }

    /**
     * controls the logic for the view statistics pane in the main menu, taking the user to a new scene StatsScene.fxml
     * @throws IOException
     */
    public void viewStatisticsClicked() throws IOException {
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("StatisticsScene.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();

    }

    /**
     * controls the logic for the clear statistics pane in the main menu, taking the user to a new scene ClearStatisticsScene.fxml
     * @throws IOException
     */
    public void SettingsClicked() throws IOException {

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    public void buttonClickedSound() {
        Music.buttonClickSound();
    }

    public void setNewQuizText() {
        explanation.setVisible(true);
        voxspell.setVisible(false);
        voxspell.setManaged(false);
        explanation.setText("THIS IS THE NEW QUIZ OPTION.");
    }

    public void setRevisionQuizText() {
        explanation.setVisible(true);
        voxspell.setVisible(false);
        voxspell.setManaged(false);
        explanation.setText("this is revision quiz");
    }

    public void setStatisticsText() {
        explanation.setVisible(true);
        voxspell.setVisible(false);
        voxspell.setManaged(false);
        explanation.setText("this is statistics");
    }

    public void setSettingsText() {
        explanation.setVisible(true);
        voxspell.setVisible(false);
        voxspell.setManaged(false);
        explanation.setText("this is settings");
    }

    public void setVoxspellText() {
        voxspell.setVisible(true);
        voxspell.setManaged(true);

        explanation.setVisible(false);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        helpView.setImage(questionImage);
        addButtons();
        colorAdjustSetUp();

        //backgroundView.setPreserveRatio(false);
        backgroundView.setImage(backgroundImage);


    }
}
