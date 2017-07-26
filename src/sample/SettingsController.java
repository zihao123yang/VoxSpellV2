package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.dataBase.RevisionData;
import sample.dataBase.SpellingListData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zihao123yang on 14/10/16.
 */
public class SettingsController implements Initializable {

    FileChooser fileChooser = new FileChooser();
    SpellingListData spellingList = SpellingListData.getInstance();
    OverallStatsData _overallStats = OverallStatsData.getInstance();
    RevisionData _revisionData = RevisionData.getInstance();

    @FXML
    Button chooseWordList;

    @FXML
    Button modifyLevels;

    @FXML
    ComboBox<String> selectVoice;

    @FXML
    ToggleButton speed0_5;

    @FXML
    ToggleButton speed0_8;

    @FXML
    ToggleButton speed1_0;

    @FXML
    ToggleButton speed1_5;

    ObservableList<String> voiceList = FXCollections.observableArrayList("voice_kal_diphone", "voice_akl_nz_jdt_diphone");

    ToggleGroup speedButtons = new ToggleGroup();

    @FXML
    ImageView backgroundView;

    Image backgroundImage = new Image("file:background.png");


    @FXML
    ImageView helpView;

    Image questionImage = new Image("file:icons/question.png");


    public void chooseWordListPressed() {

        Music.buttonClickSound();

        File wordList = fileChooser.showOpenDialog(Main.getPrimaryStage());
        if (wordList != null) {
            System.out.println(wordList.getName());
            spellingList.changeWordList(wordList);
            _overallStats.clearStats();
            _revisionData.clearData();

        }
    }



    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Select a word list");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT Files", "*.txt")
        );
    }


    public void mainMenu() throws IOException {

        Music.buttonClickSound();

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuV2.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    public void clearStats() throws IOException {

        Music.buttonClickSound();

        Stage stageNew = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ClearStatisticsScene.fxml"));
        stageNew.setScene(new Scene(root, 600, 400));
        stageNew.show();
    }

    @FXML
    public void voiceChanging() {
        if (selectVoice.getValue().equals("voice_kal_diphone")) {
            Festival.setVoice("voice_kal_diphone");
        } else if (selectVoice.getValue().equals("voice_akl_nz_jdt_diphone")) {
            Festival.setVoice("voice_akl_nz_jdt_diphone");
        }
    }

    public void speed0_5() {

        speed0_8.setDisable(true);
        speed1_0.setDisable(true);
        speed1_5.setDisable(true);
        Festival.setSpeed("1.5");
        Festival.callFestival("please spell the word testing");
        speed0_8.setDisable(false);
        speed1_0.setDisable(false);
        speed1_5.setDisable(false);
    }

    public void speed0_8() {
        speed0_5.setDisable(true);
        speed1_0.setDisable(true);
        speed1_5.setDisable(true);
        Festival.setSpeed("1.2");
        Festival.callFestival("please spell the word testing");
        speed0_5.setDisable(false);
        speed1_0.setDisable(false);
        speed0_5.setDisable(false);
    }

    public void speed1_0() {
        speed0_8.setDisable(true);
        speed0_5.setDisable(true);
        speed1_5.setDisable(true);
        Festival.setSpeed("1.0");
        Festival.callFestival("please spell the word testing");
        speed0_8.setDisable(false);
        speed0_5.setDisable(false);
        speed0_5.setDisable(false);
    }

    public void speed1_5() {
        speed0_8.setDisable(true);
        speed1_0.setDisable(true);
        speed0_5.setDisable(true);
        Festival.setSpeed("0.5");
        Festival.callFestival("please spell the word testing");
        speed0_8.setDisable(false);
        speed1_0.setDisable(false);
        speed0_5.setDisable(false);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backgroundView.setImage(backgroundImage);
        backgroundView.setOpacity(0.55);
        helpView.setImage(questionImage);

        speed0_5.setToggleGroup(speedButtons);
        speed0_8.setToggleGroup(speedButtons);
        speed1_0.setToggleGroup(speedButtons);
        speed1_5.setToggleGroup(speedButtons);

        configureFileChooser(fileChooser);

        selectVoice.setValue(Festival.voice());
        selectVoice.setItems(voiceList);


    }
}
