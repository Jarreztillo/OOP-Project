package app.main;

import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

import static app.main.game.audio;


public class audioPlayer {

    private static double globalVolumen= 0.5;
    public static void playInit(){

    }

    public static void playSelectedCharacter(){
        audio = new AudioClip((Paths.get("src/DAO/audios/sound_effects/Personajeseleccionao.mp3").toUri().toString()));
        audio.setVolume(0.4);
        audio.play();

    }

    public static void playMainMenu(){
        audio = new AudioClip((Paths.get("src/DAO/audios/music/mainMenu.mp3").toUri().toString()));
        audio.setVolume(0.4);
        audio.setCycleCount(AudioClip.INDEFINITE);
        audio.play();
    }

    public static void stopMainMenu(){
        audio = new AudioClip((Paths.get("src/DAO/audios/music/mainMenu.mp3").toUri().toString()));
        audio.stop();
    }

    public static void playButtonSound(){
        audio = new AudioClip((Paths.get("src/DAO/audios/sound_effects/buttonMenu.mp3").toUri().toString()));
        audio.setVolume(0.4);
        audio.play();
    }

    public static void playCombatMusic(){
        audio = new AudioClip("combatMusic.mp3");
        audio.setVolume(0.4);
        audio.play();
    }

    public static void setVolume(double volumen) {
        globalVolumen=volumen;
    }

}
