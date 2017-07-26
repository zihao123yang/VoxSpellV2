package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by eli on 21/09/16.
 * Controls the logic for the scene after the 'New Quiz button' or 'Review  Quiz button' is selected
 */
public class SelectQuizSettingsController implements Initializable {

    ArrayList<ToggleButton> myDynamicButtons = new ArrayList<ToggleButton>();

    ToggleGroup levelButtons = new ToggleGroup();

    int _levelUnlocked;

    Text levelText = new Text("LEVEL: ");


    @FXML
    HBox buttonBox;

    @FXML
    Button continueButton;

    @FXML
    Text moreLevels;

    @FXML
    ComboBox<Integer> moreLevelsChoice;

    @FXML
    private ComboBox voiceChoiceBox;

    ObservableList<String> voiceList = FXCollections.observableArrayList("voice_kal_diphone", "voice_akl_nz_jdt_diphone");


    @FXML
    ImageView backgroundView;

    @FXML
    Image backgroundImage = new Image("file:background.png");

    @FXML
    ImageView helpView;

    Image questionImage = new Image("file:icons/question.png");


    @FXML
    public void continueToQuiz() throws IOException {

        Music.buttonClickSound();

        SystemStatus.setUnlockedlevel(_levelUnlocked);
        SystemStatus.setQuizType("new");

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
        if (voiceChoiceBox.getValue().equals("voice_kal_diphone")) {
            Festival.setVoice("voice_kal_diphone");
        } else if (voiceChoiceBox.getValue().equals("voice_akl_nz_jdt_diphone")) {
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
                if ((SystemStatus.Unlockedlevel() == 0) || (i <= SystemStatus.Unlockedlevel())) {
                    extraLevels.add(i);
                }

            }

            moreLevelsChoice.setItems(extraLevels);
        }
    }

    public void chooseExtraLevel() {

        if (moreLevelsChoice.getValue() != null) {
            SystemStatus.setLevel(moreLevelsChoice.getValue());
            _levelUnlocked = moreLevelsChoice.getValue() + 1;

            levelButtons.selectToggle(null);
            continueButton.setDisable(false);
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backgroundView.setImage(backgroundImage);
        backgroundView.setOpacity(0.55);

        helpView.setImage(questionImage);


        setUpMoreLevelsChoice();



        voiceChoiceBox.setValue(Festival.voice());
        voiceChoiceBox.setItems(voiceList);

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
                    _levelUnlocked = finalI + 1;
                    moreLevelsChoice.setValue(null);

                    continueButton.setDisable(false);
                }
            });
            myDynamicButtons.add(levelButton);
            buttonBox.getChildren().add(levelButton);
        }


        //for dynamic buttons, depending on spelling list
        if (SystemStatus.Unlockedlevel() != 0) {
            for (int i = 0; i < levelsInButtons; i++) {
                if (i + 1 > SystemStatus.Unlockedlevel()) {
                    myDynamicButtons.get(i).setDisable(true);
                }
            }
        }


    }
}

