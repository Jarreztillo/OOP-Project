package app.main;

import app.menus.*;

public class Initializer {

    public static void InitAllMethods(){
        AudioPlayer.initMediaPlayer();
        AudioPlayer.initAudioClips();
        MainMenu.initMainMenu();
        GameModeMenu.initGameModeMenu();
        OptionsMenu.initOptionsMenu();
        SlidersSoundsMenu.initSlidersForVolumen();
        AudioPlayer.playMainMenu();


    }
}
