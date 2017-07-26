package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.dataBase.CurrentQuizStats;
import sample.dataBase.RevisionData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zihao123yang on 19/09/16.
 */
public class SpellingQuizController implements Initializable {



    private CurrentQuizStats _quizStats;
    private OverallStatsData _overallStats;

    private SpellingLogic _spellingLogic;

    private RevisionData _revisionDataBase = RevisionData.getInstance();

    private Image _muteImage = new Image("file:icons/mute.png");
    private Image _unmuteImage = new Image("file:icons/unmute.png");
    private Image _questionImage = new Image("file:icons/question.png");

    @FXML
    private Text _levelAccuracyText;

    @FXML
    private Text _testAccuracyText;

    @FXML
    private Text _score;

    @FXML
    private Text _highscore;

    @FXML
    private Text _levelText;

    @FXML
    private ComboBox selectVoice;


    @FXML
    private TextField _inputField;

    @FXML
    private Button _submitButton;

    @FXML
    private Button _mute;

    ObservableList<String> voiceList = FXCollections.observableArrayList("voice_kal_diphone", "voice_akl_nz_jdt_diphone");

    @FXML
    HBox progressBox;

    @FXML
    ImageView backgroundView;

    @FXML
    ImageView helpView;

    Image backgroundImage = new Image("file:background.png");


    Image blankStarImage = new Image("file:icons/starBlack.png");

    Image completeStarImage = new Image("file:icons/completeStar.png");
    Image redStarImage = new Image("file:icons/starRed.png");

    ArrayList<ImageView> stars = new ArrayList<ImageView>();



    @FXML
    public void textFieldClicked() {
        if (_inputField.getText().equals("PLEASE SPELL WORD HERE")) {
            _inputField.clear();
        }
    }

    @FXML
    public void notTextField() {
        _inputField.setText("PLEASE SPELL WORD HERE");
    }


    @FXML
    public void submitButtonPressed() {

       String userInput = _inputField.getText();
        _inputField.clear();

        inputChange(userInput);
        int iteration = _spellingLogic.whichIteration();

        if(iteration == 1) {        // mastered, ...

            if (_spellingLogic.isSpellingCorrect(userInput)) {

                _quizStats.increaseMastered();
                _overallStats.addMasteredWord(_spellingLogic.currentWord());
                _quizStats.add1000Score();

                stars.get(_quizStats.getWordsTest() - 1).setImage(completeStarImage);

                if (SystemStatus.quizType() == "revision") {
                    _revisionDataBase.removeFromDataBase(_spellingLogic.currentWord().name());
                }

                if (SystemStatus.quizType() == "new") {
                    _score.setText("SCORE: " + _quizStats.currentScore());
                }
                _testAccuracyText.setText("TEST ACCURACY: " +  _quizStats.calculateAccurracy() + "%");
                _levelAccuracyText.setText("LEVEL ACCURACY: " + _overallStats.calculateLevelAccuracy(SystemStatus.currentlevel()) + "%");
                //-------------------------------------------------------------------
            } else {

            }

        } else if (iteration == 2) {    //faulted, failed

            if (_spellingLogic.isSpellingCorrect(userInput)) {              //faulted
                _quizStats.increaseFaulted();
                _overallStats.addFaultedWord(_spellingLogic.currentWord());
                _quizStats.add500();

                ImageView star = stars.get(_quizStats.getWordsTest() - 1);
                star.setFitWidth(50);
                star.setFitHeight(50);
                star.setImage(completeStarImage);

                if (SystemStatus.quizType() == "revision") {
                    _revisionDataBase.removeFromDataBase(_spellingLogic.currentWord().name());
                }

                if (SystemStatus.quizType() == "new") {
                    _score.setText("SCORE: " + _quizStats.currentScore());
                }
                _testAccuracyText.setText("TEST ACCURACY: " +  _quizStats.calculateAccurracy() + "%");
                _levelAccuracyText.setText("LEVEL ACCURACY: " + _overallStats.calculateLevelAccuracy(SystemStatus.currentlevel()) + "%");

            } else {                                                        //failed

                _quizStats.increaseFailed();
                _overallStats.addFailedWord(_spellingLogic.currentWord());
                _revisionDataBase.addToDataBase(_spellingLogic.currentWord().name());

                ImageView star = stars.get(_quizStats.getWordsTest() - 1);
                star.setFitWidth(50);
                star.setFitHeight(50);
                star.setImage(redStarImage);

            }

            if (SystemStatus.quizType() == "new") {
                _score.setText("SCORE: " + _quizStats.currentScore());
            }
            _testAccuracyText.setText("TEST ACCURACY: " +  _quizStats.calculateAccurracy() + "%");
            _levelAccuracyText.setText("LEVEL ACCURACY: " + _overallStats.calculateLevelAccuracy(SystemStatus.currentlevel()) + "%");
        }

        _spellingLogic.spellingQuiz(userInput);

    }

    @FXML
    public void mainMenuPressed() throws IOException {

        Music.buttonClickSound();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuPrompt.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    @FXML
    public void repeatWordPressed() {

        Music.buttonClickSound();
        Festival.callFestival(_spellingLogic.currentWord().name());
    }

    @FXML
    public void voiceChanging() {
        if (selectVoice.getValue().equals("voice_kal_diphone")) {
            Festival.setVoice("voice_kal_diphone");
        } else if (selectVoice.getValue().equals("voice_akl_nz_jdt_diphone")) {
            Festival.setVoice("voice_akl_nz_jdt_diphone");
        }
    }

    public void mute() {
        ImageView image = null;
        if (Music.isMute()) {
            image = new ImageView(_muteImage);
            image.setFitHeight(40);
            image.setFitWidth(40);
            _mute.setGraphic(image);
            Music.unmuteBackground();
        } else {
            image = new ImageView(_unmuteImage);
            image.setFitHeight(40);
            image.setFitWidth(40);
            _mute.setGraphic(image);
            Music.muteBackground();
        }

    }

    public void setupMute() {
        ImageView image = null;
        if (Music.isMute()) {
            image = new ImageView(_unmuteImage);
            image.setFitHeight(40);
            image.setFitWidth(40);
            _mute.setGraphic(image);
        } else {
            image = new ImageView(_muteImage);
            image.setFitHeight(40);
            image.setFitWidth(40);
            _mute.setGraphic(image);
        }
    }

    public void textFieldGlow(String input) {

        if (_spellingLogic.isSpellingCorrect(input)) {
            int depth = 70;
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.GREEN);
            borderGlow.setWidth(depth);
            borderGlow.setHeight(depth);

            _inputField.setEffect(borderGlow);
        } else {
            int depth = 70;
            DropShadow borderGlow = new DropShadow();
            //borderGlow.setOffsetY(0f);
            //borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.RED);
            borderGlow.setWidth(depth);
            borderGlow.setHeight(depth);

            _inputField.setEffect(borderGlow);

        }
    }

    public void inputChange(String input) {

        textFieldGlow(input);

        Task sleeper = new Task() {

            @Override
            protected Void call() throws Exception {
                try {

                    Thread.sleep(300);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                _inputField.setEffect(null);
            }
        });
        new Thread(sleeper).start();

    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        backgroundView.setImage(backgroundImage);
        backgroundView.setOpacity(0.55);
        backgroundView.setPreserveRatio(false);

        helpView.setImage(_questionImage);

        setupMute();

        _quizStats = new CurrentQuizStats();
        _overallStats = OverallStatsData.getInstance();
        _spellingLogic = new SpellingLogic(_quizStats);


        if (SystemStatus.quizType().equals("revision")) {
            _score.setText("");
            _highscore.setText("");
        } else {
            _highscore.setText("HIGHSCORE: " + _overallStats.getHighScore());
        }



        selectVoice.setValue(Festival.voice());
        selectVoice.setItems(voiceList);

        _spellingLogic.setUpQuiz(_inputField, _submitButton);
        _spellingLogic.spellingQuiz("");


        _levelText.setText("LEVEL " +  SystemStatus.currentlevel());
        _testAccuracyText.setText("TEST ACCURACY: 100.0%");
        _levelAccuracyText.setText("LEVEL ACCURACY: " + _overallStats.calculateLevelAccuracy(SystemStatus.currentlevel()) + "%");


        progressBox.setSpacing(40);

        for (int i = 0; i < _quizStats.getNumWords(); i++) {

            ImageView star = new ImageView(blankStarImage);
            star.setFitWidth(70);
            star.setFitHeight(70);

            stars.add(star);
            progressBox.getChildren().add(star);
        }

    }


}
