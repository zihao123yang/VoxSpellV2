package sample;

import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

import static javafx.scene.media.MediaPlayer.INDEFINITE;

/**
 * Created by zihao123yang on 22/10/16.
 */
public class Music {

    private static MediaPlayer _backGroundMusic;

    public static void playBackGroundMusic() {


                String path = new File("background1.mp3").getAbsolutePath();
                Media media = new Media(new File(path).toURI().toString());
                _backGroundMusic = new MediaPlayer(media);
                _backGroundMusic.setVolume(0.2);
                _backGroundMusic.setOnEndOfMedia(new Runnable() {
                    public void run() {
                        _backGroundMusic.seek(Duration.ZERO);
                    }
                });

                _backGroundMusic.play();

    }

    public static void muteBackground() {
        _backGroundMusic.setMute(true);
    }

    public static void unmuteBackground() {
        _backGroundMusic.setMute(false);
    }

    public static boolean isMute() {
        return _backGroundMusic.isMute();
    }


    public static void buttonClickSound() {

        String path = new File("button.mp3").getAbsolutePath();
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mp = new MediaPlayer(media);
        mp.setVolume(0.6);
        mp.play();
    }

}
