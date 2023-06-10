/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contral;

import java.io.File;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 *
 * @author HP
 */
public class AudioBackground extends Thread {

    public static AudioBackground instance;
    MediaPlayer clickyS1;
    Media m1;
    double Volume = 0;
    double VolumePicker = 0;
    boolean mute = false;

    public static AudioBackground getInstance() throws IOException {
        if (instance == null) {
            instance = new AudioBackground();
        }
        return instance;
    }

    private AudioBackground() {
    }

    @Override
    public void run() {
        clickyS1 = new MediaPlayer(m1);
        if(mute){
            clickyS1.setMute(true);
        }else{
        clickyS1.setVolume(Volume + VolumePicker);
        }
        clickyS1.play();
        clickyS1.setOnEndOfMedia((new Runnable() {
            @Override
            public void run() {
                clickyS1.seek(Duration.ZERO);
                clickyS1.play();

            }
        }));
    }

    public void Volume(double v) {
        Volume = v;
    }

    public void addVolume(double v) {
        mute = false;
        VolumePicker = v;
        clickyS1.setVolume(Volume + VolumePicker);
        clickyS1.play();
    }

    public void mute() {
        mute = true;
        clickyS1.pause();
    }

    public void chooseSong(int i) {

        if (clickyS1 != null && clickyS1.getStatus().equals(Status.PLAYING)) {
            clickyS1.pause();
        }

        if (i == 0) {
            m1 = new Media(new File(getClass().getResource("/audio/FoodBackground.wav").getPath()).toURI().toString());

        } else if (i == 1) {
            m1 = new Media(new File(getClass().getResource("/audio/HorroFoodBackground.wav").getPath()).toURI().toString());

        } else if (i == 2) {
            m1 = new Media(new File(getClass().getResource("/audio/Mathbackground.wav").getPath()).toURI().toString());

        } else if (i == 3) {
            m1 = new Media(new File(getClass().getResource("/audio/HorroMathBackground.wav").getPath()).toURI().toString());

        } else {
            m1 = new Media(new File(getClass().getResource("/audio/Plante.wav").getPath()).toURI().toString());
        }
    }
}
