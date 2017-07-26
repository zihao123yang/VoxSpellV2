package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * is a javaFX controller class for the sample.fxml scene, where the sample.fxml scene represents the quiz's main menu.
 */
public class Controller implements Initializable {




    /**
     * controls the logic for the new spelling quiz pane in the main menu, taking the user to a new scene SelectQuizSettings.fxml
     * @throws IOException
     */
    @FXML
    public void newSpellingQuizClicked() throws IOException {

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("SelectQuizSettings.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * controls the logic for the new review quiz pane in the main menu, taking the user to a new scene RevisionSettings.fxml
     * @throws IOException
     */
    public void reviewQuizClicked() throws IOException {
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("RevisionSettings.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }

    /**
     * controls the logic for the view statistics pane in the main menu, taking the user to a new scene StatsScene.fxml
     * @throws IOException
     */
    public void viewStatisticsClicked() throws IOException {
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("StatisticsScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
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


    /**
     * is called when the program (VOXSPELL) is started, printing the saved files from the database
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
