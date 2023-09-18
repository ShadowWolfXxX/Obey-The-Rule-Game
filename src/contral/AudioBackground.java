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
    MediaPlayer playerSong;
    double Volume = 0;
    double VolumePicker = 0;
    boolean mute = false;
    String songName = "no";
    
    //singleton
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
        if (mute) {
            playerSong.setMute(true);
        } else {
            playerSong.setVolume(Volume + VolumePicker);
        }
        playerSong.play();
        //loop the song
        playerSong.setOnEndOfMedia((new Runnable() {
            @Override
            public void run() {
                playerSong.seek(Duration.ZERO);
                playerSong.play();
            }
        }));
    }

    //choose song and Volume then run the thread
    public void makeSong(double vol, String songNum) {
        if(!(songName.equals(songNum))){
        songName = songNum;     
        Volume = vol;
        playerSong = new MediaPlayer(chooseSong(songNum));
        this.run();
        }
    }

    //for option screen to increase o decrease Volume
    public void addVolume(double v) {
        mute = false;
        VolumePicker = v;
        playerSong.setVolume(Volume + VolumePicker);
        playerSong.play();
    }

    
    //for option screen to mute all song
    public void mute() {
        mute = true;
        playerSong.pause();
    }

    private Media chooseSong(String i) {
        Media song;

        if (playerSong != null && playerSong.getStatus().equals(Status.PLAYING)) {
            playerSong.pause();
        }
            
        song = new Media(new File(getClass().getResource("/audio/"+i+".wav").getPath()).toURI().toString());

        return song;
    }
}
