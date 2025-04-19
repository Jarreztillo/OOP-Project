package app.cutscenes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


import java.nio.file.Paths;

import static app.main.game.window;

public class yosbelFirstApparition {
    private static GraphicsContext graphics;
    private static Scene yosbelTalkingScene;
    private static Group root;
    private static Canvas canvas;


    public static void initialize(){

        root = new Group();
        yosbelTalkingScene = new Scene(root, 1000, 850);
        canvas = new Canvas(1000, 850);
        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();
        window.setScene(yosbelTalkingScene);
        draw();
        labelConfigurations(root);
        audioConfiguration();

    }

    private static void audioConfiguration() {
        AudioClip audio = new AudioClip(Paths.get("src/DAO/audios/music/capareza.mp3").toUri().toString());
        audio.play();
        audio.setVolume(0.7);
    }


    private static void draw(){
        graphics.fillRect(0, 0, 1000, 850);
        Image yosbelito = new Image("narrador.png");
        graphics.drawImage(yosbelito, 150, -100);

    }


    private static void labelConfigurations(Group root) {
        Label message = new Label("¡Hola! Soy Ioxver. Cogiste 2 en tu prueba y ahora reviviste en un mundo de fantasia. ");
        Font font = new Font ("Times_new_roman", 25);

        message.setFont(font);
        message.setTextFill(Color.WHITE);
        message.setTranslateX(10);
        message.setTranslateY(700);

        root.getChildren().add(message);


    }

}
