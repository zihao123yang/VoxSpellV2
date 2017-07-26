package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by eli on 22/09/16.
 */
public class ViewStatsController implements Initializable {


    @FXML
    ComboBox<String> chooseLevel;

    ObservableList<String> selectionList = FXCollections.observableArrayList();


    @FXML
    TableView<Word> table;


    @FXML
    TableColumn<Word, String> wordColumn;


    @FXML
    TableColumn<Word, Integer> masteredColumn;


    @FXML
    TableColumn<Word, Integer> faultedColumn;


    @FXML
    TableColumn<Word, Integer> failedColumn;

    @FXML
    ImageView backgroundView;

    Image backgroundImage = new Image("file:background.png");

    @FXML
    ImageView helpView;

    Image questionImage = new Image("file:icons/question.png");


    OverallStatsData _statistics = OverallStatsData.getInstance();


    public void populateTable(int level) {

        ArrayList<Word> levelArrayList = _statistics.statsForLevel(level);

        ObservableList<Word> levelList;
        if (levelArrayList != null) {
            levelList = FXCollections.observableArrayList(levelArrayList);

        } else {
            levelList = FXCollections.observableArrayList();

        }
        wordColumn.setCellValueFactory(
                new PropertyValueFactory<Word, String>("_word")
        );
        masteredColumn.setCellValueFactory(
                new PropertyValueFactory<Word, Integer>("_numMastered")
        );
        faultedColumn.setCellValueFactory(
                new PropertyValueFactory<Word, Integer>("_numFaulted")
        );
        failedColumn.setCellValueFactory(
                new PropertyValueFactory<Word, Integer>("_numFailed")
        );

        table.setItems(levelList);



    }

    public void setUpChoiceBox() {
        int numberOfLevels = SystemStatus.numOfLevels();

        for (int i = 1; i <= numberOfLevels; i++) {
            selectionList.add("LEVEL " + i);
        }

        chooseLevel.setItems(selectionList);
        chooseLevel.setValue("LEVEL 1");
    }

    public void selectingLevel() {
        String[] levelSelected = chooseLevel.getValue().split(" ");
        int level = Integer.parseInt(levelSelected[1]);
        populateTable(level);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backgroundView.setImage(backgroundImage);
        backgroundView.setOpacity(0.55);

        helpView.setImage(questionImage);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setUpChoiceBox();
    }


    @FXML
    public void returnToMainMenu() throws IOException{

        Music.buttonClickSound();
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuV2.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }
}
