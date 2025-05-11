package app.main;

import app.menus.*;
import javafx.scene.layout.StackPane;

public class Initializer {

    public static void InitAllMethods(){
        PauseMenu.initPauseMenu();
        AudioPlayer.initMediaPlayer();
        AudioPlayer.initAudioClips();
        MainMenu.initMainMenu();
        GameModeMenu.initGameModeMenu();
        OptionsMenu.initOptionsMenu();
        SlidersSoundsMenu.initSlidersForVolumen();
        AudioPlayer.playMainMenu();
        SlidersBrightnessMenu.initBrightnessSlider();

    }
}
