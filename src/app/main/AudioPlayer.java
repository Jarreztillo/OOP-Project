package app.main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class AudioPlayer {
    private static final Map<String, MediaPlayer> sounds = new HashMap<>();
    private static double volume = 0.4;
    private static Map<String, MediaPlayer> activeSounds = new HashMap<>();

    public static void init() {

        sounds.put("selectedCharacter", new MediaPlayer(new Media(Paths.get("src/DAO/audios/sound_effects/Personajeseleccionao.mp3").toUri().toString())));
        sounds.put("mainMenu", new MediaPlayer(new Media(Paths.get("src/DAO/audios/music/mainMenu.mp3").toUri().toString())));
        sounds.put("buttonSound", new MediaPlayer(new Media(Paths.get("src/DAO/audios/sound_effects/buttonMenu.mp3").toUri().toString())));
        sounds.put("combatMusic", new MediaPlayer(new Media(Paths.get("src/DAO/audios/music/combatMusic.mp3").toUri().toString())));
        sounds.put("roasterButton", new MediaPlayer(new Media(Paths.get("src/DAO/audios/sound_effects/roasterButtonActivated.mp3").toUri().toString())));

        for (MediaPlayer player : sounds.values()) {
            player.setVolume(volume);
        }
    }

    public static void playInit(){

    }

    public static void playSelectedCharacter() {
        stopIfPlaying("selectedCharacter");
        MediaPlayer player = sounds.get("selectedCharacter");
        if (player != null) {
            player.setVolume(volume);
            player.play();
            activeSounds.put("selectedCharacter", player);
        }
    }

    public static void playMainMenu() {
        stopIfPlaying("mainMenu");
        MediaPlayer player = sounds.get("mainMenu");
        if (player != null) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.setVolume(volume);
            player.play();
            activeSounds.put("mainMenu", player);
        }
    }


    public static void stopMainMenu() {
        MediaPlayer player = activeSounds.get("mainMenu");
        if (player != null) {
            player.stop();
            activeSounds.remove("mainMenu");
        }
    }

    public static void playButtonSound() {
        stopIfPlaying("buttonSound");
        MediaPlayer player = sounds.get("buttonSound");
        if (player != null) {
            player.setVolume(volume);
            player.play();
            activeSounds.put("buttonSound", player);
        }
    }

    public static void playRoasterButtonSound() {
        stopIfPlaying("roasterButton");
        MediaPlayer player = sounds.get("roasterButton");
        if (player != null) {
            player.setVolume(volume);
            player.play();
            activeSounds.put("roasterButton", player);

        }
    }


    public static void playCombatMusic() {
        stopIfPlaying("combatMusic");
        MediaPlayer player = sounds.get("combatMusic");
        if (player != null) {
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.setVolume(volume);
            player.play();
            activeSounds.put("combatMusic", player);
        }
    }

    public static void stopIfPlaying(String soundKey) {
        MediaPlayer player = activeSounds.get(soundKey);
        if (player != null) {
            player.stop();
            activeSounds.remove(soundKey);
        }
    }

    public static void setVolumeForAllSounds(double newVolume) {
        volume = newVolume;
        System.out.println("Nuevo volumen: " + volume);


        for (MediaPlayer player : activeSounds.values()) {
            player.setVolume(volume);
        }
    }

}
