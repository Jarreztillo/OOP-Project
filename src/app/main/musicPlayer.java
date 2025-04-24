package app.main;

import javafx.scene.media.AudioClip;
import static app.main.game.audio;

import java.nio.file.Paths;


public class musicPlayer {
    public static void playInit(){
        audio = new AudioClip("rasputin.mp3");
        audio.setVolume(0.7);
        audio.play();
    }

    public static void playCombatMusic(){
        audio = new AudioClip("combatMusic.mp3");
        audio.setVolume(0.7);
        audio.play();
    }

    public static void stopMusic(){
        audio.stop();
    }

}
