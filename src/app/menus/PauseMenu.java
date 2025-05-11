package app.menus;

import app.main.AudioPlayer;
import app.main.Game;
import app.main.Roaster.Roaster;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PauseMenu {

    private static VBox pauseMenu;
    private static Button resume=new Button("Resume");
    private static Button exit = new Button("Exit");

    public static void initPauseMenu() {

        resume.getStyleClass().add("menu-button");
        exit.getStyleClass().add("menu-button");

        pauseMenu = new VBox(20);
        pauseMenu.setAlignment(Pos.CENTER);
        pauseMenu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");
        pauseMenu.setPrefSize(1000, 850);
        pauseMenu.setVisible(false);
        pauseMenu.setMouseTransparent(true);


        resume.setOnAction(_ ->{
            AudioPlayer.playButtonSound();
            hide();
        });



        exit.setOnAction(_ ->{
            AudioPlayer.playButtonSound();
            Game.window.close();
        });

        pauseMenu.getChildren().addAll(resume, exit);
    }

    public static VBox getPauseMenu() {
        return pauseMenu;
    }

    public static void managePauseMenu() {
        boolean visible = pauseMenu.isVisible();
        pauseMenu.setVisible(!visible);
        pauseMenu.setMouseTransparent(visible);
    }

    public static void hide() {
        pauseMenu.setVisible(false);
        pauseMenu.setMouseTransparent(true);
    }
}
