package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zihao123yang on 22/09/16.
 */
public class VideoSceneController implements Initializable {


    @FXML
    private MediaView mediaView;

    @FXML
    private Button _mute;

    @FXML
    private Button _play;

    @FXML
    private Button _stop;

    private MediaPlayer mediaPlayer;

    private Media media;


    private Image _muteImage = new Image("file:icons/mute.png");
    private Image _unmuteImage = new Image("file:icons/unmute.png");
    private Image _playImage = new Image("file:icons/play.png");
    private Image _pauseImage = new Image("file:icons/pause.png");
    private Image _stopImage = new Image("file:icons/stop.png");


    public void playButtonPressed(ActionEvent event) {
        ImageView image = null;
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            image = new ImageView(_playImage);
            image.setFitHeight(40);
            image.setFitWidth(40);
            _play.setGraphic(image);
            mediaPlayer.pause();
        } else {
            image = new ImageView(_pauseImage);
            image.setFitHeight(40);
            image.setFitWidth(40);
            _play.setGraphic(image);
            mediaPlayer.play();
        }
    }

    public void stopButtonPressed(ActionEvent event) throws IOException {
        Music.buttonClickSound();
        Music.unmuteBackground();
        mediaPlayer.stop();

        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("LevelComplete.fxml"));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }

    public void muteButtonPressed(ActionEvent event) {

        ImageView image = null;
        if (!mediaPlayer.isMute()) {
            image = new ImageView(_unmuteImage);
            image.setFitHeight(40);
            image.setFitWidth(40);
            _mute.setGraphic(image);
            mediaPlayer.setMute(true);
        } else {
            image = new ImageView(_muteImage);
            image.setFitHeight(40);
            image.setFitWidth(40);
            _mute.setGraphic(image);
            mediaPlayer.setMute(false);
        }
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String path = new File("big_buck_bunny_1_minute.mp4").getAbsolutePath();

        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));


        ImageView muteView = new ImageView(_muteImage);
        muteView.setFitHeight(40);
        muteView.setFitWidth(40);
        _mute.setGraphic(muteView);

        ImageView pauseView = new ImageView(_pauseImage);
        pauseView.setFitHeight(40);
        pauseView.setFitWidth(40);
        _play.setGraphic(pauseView);

        ImageView stopView = new ImageView(_stopImage);
        stopView.setFitHeight(40);
        stopView.setFitWidth(40);
        _stop.setGraphic(stopView);


    }
}
