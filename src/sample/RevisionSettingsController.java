package sample;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.dataBase.RevisionData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zihao123yang on 22/09/16.
 */
public class RevisionSettingsController implements Initializable {

    ArrayList<ToggleButton> myDynamicButtons = new ArrayList<ToggleButton>();

    RevisionData _revisionQUiz = RevisionData.getInstance();



    @FXML
    HBox buttonBox;

    // putting the level buttons in a toggle group so that only one of the level buttons can be selected at a time
    ToggleGroup levelButtons = new ToggleGroup();

    @FXML
    Button continueButton;

    @FXML
    private ComboBox selectVoice;


    ObservableList<String> voiceList = FXCollections.observableArrayList("voice_kal_diphone", "voice_akl_nz_jdt_diphone");

    Text levelText = new Text("LEVEL: ");

    @FXML
    Text moreLevels;

    @FXML
    ComboBox<Integer> moreLevelsChoice;

    @FXML
    ImageView helpView;

    Image questionImage = new Image("file:icons/question.png");

    @FXML
    ImageView backgroundView;

    Image backgroundImage = new Image("file:background.png");

    @FXML
    public void continueToQuiz() throws IOException {

        Music.buttonClickSound();

        SystemStatus.setQuizType("revision");

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("SpellingQuizV2.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();


    }

    @FXML
    public void returnToMainMenu() throws IOException {

        Music.buttonClickSound();

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuV2.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    @FXML
    public void voiceChanging() {
        if (selectVoice.getValue().equals("voice_kal_diphone")) {
            Festival.setVoice("voice_kal_diphone");
        } else if (selectVoice.getValue().equals("voice_akl_nz_jdt_diphone")) {
            Festival.setVoice("voice_akl_nz_jdt_diphone");
        }
    }

    public void setUpMoreLevelsChoice() {
        if (SystemStatus.numOfLevels() <= 11) {
            moreLevels.setVisible(false);
            moreLevelsChoice.setVisible(false);
        } else {
            ObservableList<Integer> extraLevels = FXCollections.observableArrayList();


            for (int i = 12; i <= SystemStatus.numOfLevels(); i++) {
                if (_revisionQUiz.checkForWords(i + 1)) {
                    extraLevels.add(i);
                }
            }

            moreLevelsChoice.setItems(extraLevels);
        }
    }

    public void chooseExtraLevel() {

        if (moreLevelsChoice.getValue() != null) {
            SystemStatus.setLevel(moreLevelsChoice.getValue());

            levelButtons.selectToggle(null);

            continueButton.setDisable(false);
            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setUpMoreLevelsChoice();

        backgroundView.setImage(backgroundImage);
        backgroundView.setOpacity(0.55);


        helpView.setImage(questionImage);

        selectVoice.setValue(Festival.voice());
        selectVoice.setItems(voiceList);


        continueButton.setDisable(true);

        buttonBox.setSpacing(40);

        levelText.setFont(Font.font(25));
        buttonBox.getChildren().add(levelText);

        int levelsInButtons = 0;
        if (SystemStatus.numOfLevels() <= 11) {
            levelsInButtons = SystemStatus.numOfLevels();
        } else {
            levelsInButtons = 11;
        }


        //adding the level buttons dynamically depending on the number of levels there are in the quiz
        for (int i = 0; i < levelsInButtons; i++) {
            String level = Integer.toString(i + 1);
            ToggleButton levelButton = new ToggleButton(level);
            levelButton.setPrefWidth(50);
            levelButton.setPrefHeight(40);
            levelButton.setStyle("-fx-base: #ffe066; -fx-background-radius: 10");
            levelButton.setToggleGroup(levelButtons);
            int finalI = i;
            levelButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    SystemStatus.setLevel(finalI + 1);

                    continueButton.setDisable(false);
                    moreLevelsChoice.setValue(null);

                }
            });
            //add button to arraylist of buttons
            myDynamicButtons.add(levelButton);
            //add button to HBox
            buttonBox.getChildren().add(levelButton);
        }

        for (int i = 0; i < levelsInButtons ; i++) {

            if (!_revisionQUiz.checkForWords(i + 1)) {
                myDynamicButtons.get(i).setDisable(true);
            } else {
                myDynamicButtons.get(i).setDisable(false);
            }
        }





    }
}
