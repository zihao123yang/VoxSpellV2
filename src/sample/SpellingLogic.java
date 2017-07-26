package sample;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.dataBase.*;

import java.util.ArrayList;

/**
 * Created by zihao123yang on 7/10/16.
 */
public class SpellingLogic {


    private QuizDataBase quizDatabase;
    private CurrentQuizStats currentStats;
    private OverallStatsData overallStats;
    private ArrayList<String> quizList;

    private TextField _userInput;
    private Button _submit;
    private  Task _festivalTask;
    private boolean _inputFlag;
    private boolean _repeatFlag;
    private int _position;
    private int _numWords;
    private Word _word;


    public SpellingLogic(CurrentQuizStats currentStats) {
        this.currentStats = currentStats;
        overallStats = OverallStatsData.getInstance();
    }



    public void setUpQuiz (TextField inputField, Button button) {

        _submit = button;
        _userInput = inputField;
        _inputFlag = false;
        _repeatFlag = false;


        if (SystemStatus.quizType() == "new") {

            quizDatabase = SpellingListData.getInstance();

        } else if (SystemStatus.quizType() == "revision") {
            quizDatabase = RevisionData.getInstance();
        }

        quizList = quizDatabase.makeQuizList();

        for (int i = 0; i < quizList.size(); i++) {

            System.out.println("quizList:");
            System.out.print(quizList.get(i));
            System.out.println();
        }

        _numWords = quizList.size();
        currentStats.setNumWords(_numWords);

    }


    public void spellingQuiz(String input) {

        if (_inputFlag == false) {

            _word = new Word(quizList.get(_position), SystemStatus.currentlevel());
            startFestivalTask("Please spell the word " + quizList.get(_position));

            _inputFlag = true;
            return;


        }

        // first time getting user input - either mastered or second attempt
        if (_repeatFlag == false) {

            _word = new Word(quizList.get(_position), SystemStatus.currentlevel());
            if (quizList.get(_position).toLowerCase().trim().equals(input.toLowerCase().trim())) {

            } else {

                startFestivalTask("Incorrect! Please try again. " + quizList.get(_position));

                _repeatFlag = true;
                return;
            }

        }

        // second attempt of user spelling the word - either faulted or failed
        if (_repeatFlag == true) {

            _word = new Word(quizList.get(_position), SystemStatus.currentlevel());
            if (quizList.get(_position).toLowerCase().trim().equals(input.toLowerCase().trim())) {

            } else {

                quizDatabase.addToDataBase(_word.name());

            }
        }

        _repeatFlag = false;
        _position++;


        if (_position < _numWords ) {
            _word = new Word(quizList.get(_position), SystemStatus.currentlevel());
            startFestivalTask("Please spell the word " + quizList.get(_position));

            return;
        } else {

            overallStats.addScore(currentStats.currentScore());
            overallStats.addHighScore(currentStats.currentScore());
            if (Music.isMute()) {
                Music.unmuteBackground();
            }

            if (SystemStatus.quizType().equals("revision")) {
                revisionComplete();

            }else if(SystemStatus.quizType().equals("new")) {

                System.out.println("entered here. " + SystemStatus.numOfLevels());

                if(SystemStatus.currentlevel() <= SystemStatus.numOfLevels()) {
                    if (currentStats.levelPassed()) {
                        System.out.println("level Complete");
                        levelComplete();
                    } else {
                        System.out.println("level failed");
                        levelFailed();
                    }
                }

            }
        }

    }

    public boolean isSpellingCorrect(String input) {

        if (quizList.get(_position).toLowerCase().trim().equals(input.toLowerCase().trim())) {
            return true;
        } else {
            return false;
        }
    }


    public Word currentWord() {
        return _word;
    }

    public int whichIteration() {
        if (_repeatFlag) {
            return 2;
        } else {
            return 1;
        }
    }

    public void startFestivalTask(String sayThis) {

        _userInput.setText("LOADING...");
        _userInput.setDisable(true);
        _submit.setDisable(true);

        _festivalTask = new Task() {
            @Override
            protected Object call() throws Exception {
                Festival.callFestival(sayThis);
                return null;
            }
        };

        _festivalTask.setOnSucceeded(event -> {
            _userInput.setDisable(false);
            _userInput.clear();
            _userInput.requestFocus();
            _submit.setDisable(false);
        });

        new Thread(_festivalTask).start();
    }




    public void levelComplete() {

        try {
            Stage stage = Main.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("gui/LevelComplete.fxml"));
            stage.setScene(new Scene(root, 1200, 800));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void levelFailed() {

        try {
            Stage stage = Main.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("gui/LevelFailed1.fxml"));
            stage.setScene(new Scene(root, 1200, 800));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void revisionComplete() {
        try {
            Stage stage = Main.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("gui/RevisionComplete.fxml"));
            stage.setScene(new Scene(root, 1200, 800));
            stage.show();
        } catch (Exception e) {

        }
    }





}
