package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.dataBase.RevisionData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A javaFX controller class for the ClearStatisticsScene.fxml
 */
public class ClearStatisticsScene implements Initializable{

    OverallStatsData _overallStats = OverallStatsData.getInstance();
    RevisionData _revisionData = RevisionData.getInstance();


    // FXML gui component fields
    @FXML
    Label yesLabel;

    @FXML
    Label noLabel;

    @FXML
    Button mainMenuButton;

    @FXML
    Button yesButton;

    @FXML
    Button noButton;


    /**
     * called on startup of the ClearStatisticsScene.fxml, accessed via the main menu.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //certain buttons should only be visible after the user makes a decision
        yesLabel.setVisible(false);
        noLabel.setVisible(false);
        mainMenuButton.setVisible(false);
        mainMenuButton.disarm();
    }


    /**
     * clears the statistics when the user presses yes and gives the option to return to the main menu
     */
    public void yesPressed(){

        Music.buttonClickSound();
        yesLabel.setVisible(true);

        _overallStats.clearStats();
        _revisionData.clearData();

        yesButton.setVisible(false);
        noButton.setVisible(false);

        mainMenuButton.arm();
        mainMenuButton.setVisible(true);



    }


    /**
     * does not clear any statistics, offerst the option to return to the main menu
     */
    public void noPressed(){

        Music.buttonClickSound();

        noLabel.setVisible(true);


        yesButton.setVisible(false);
        noButton.setVisible(false);

        mainMenuButton.arm();
        mainMenuButton.setVisible(true);

    }


    /**
     * controls the return to the main menu after the user makes a decision on whether or not they should clear statistics
     * @throws IOException
     */
    public void mainMenuPressed() throws IOException{
        Stage stage = (Stage) yesLabel.getScene().getWindow();
        stage.close();
    }

}
