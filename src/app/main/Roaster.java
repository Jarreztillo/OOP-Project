package app.main;

import app.gameModes.Campaign;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import static app.main.game.window;

import domain.entities.PlayerCharacter;

public class Roaster {
    private static Group root;
    private static Canvas canvas;
    private static GraphicsContext graphics;
    private static Scene roasterScene;
    private static AnimationTimer animationTimer;
     /* Objeto campaña para instanciar el modo campaña. Cuando aprenda
            a programar mejor las clases, no hare uso de un objeto. Tambien
            se iguala el objeto publico estatico para poder usar la instancia
            en todas las clases.
         */

    private static int x = 32;
    private static int y = 32;

    public static void initialize(){
        root = new Group();
        roasterScene = new Scene(root, 1000, 850);
        canvas = new Canvas(1000, 850);
        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();
        window.setScene(roasterScene);
        PlayerCharacter player = new PlayerCharacter();
        moveSelectionSquare(roasterScene, player);
        drawAndActualizePosition();

    }

    private static void moveSelectionSquare(Scene roasterScene, PlayerCharacter player) {
        roasterScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode().toString()){
                    case "W":
                        if (y == 32){
                            break;
                        }
                        y -= 120;
                        break;
                    case "S":
                        if(y == 392 && x == 392){
                            y+=120;
                            break;
                        }
                        if(y == 392 && x == 512){
                            y+=120;
                            break;
                        }
                        if(y == 512 && x == 512){
                            break;
                        }
                        if(y == 512 && x == 392){
                            break;
                        }


                        if (y == 392){
                            break;
                        }
                        y += 120;
                        break;
                    case "D":
                        if(y == 512 && x == 512){
                            break;
                        }
                        if (x == 512){
                            break;
                        }
                        x+= 120;
                        break;

                    case "A":
                        if(y == 512 && x == 392){
                            break;
                        }
                        if (x == 32){
                            break;
                        }
                        x-= 120;
                        break;
                    case "SPACE":
                        if (x == 32 && y == 32){
                            player.setImageName("monigote.png");
                            player.setClosestImageName("decerca.png");
                            Campaign.initialize(player);
                            animationTimer.stop();
                        }
                        if (x== 32 && y == 152){
                            player.setImageName("draven.png");
                            //32x48
                            player.setClosestImageName("closerDraven.png");
                            Campaign.initialize(player);
                            animationTimer.stop();
                        }
                        if (x== 32 && y == 272){
                            player.setImageName("berserker.png");
                            //32x48
                            player.setClosestImageName("closerBerserker.png");
                            Campaign.initialize(player);
                            animationTimer.stop();
                        }
                        break;

                }
            }
        });


    }

        private static void drawAndActualizePosition(){
            animationTimer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    for (int i = 0; i <= 4; i++){
                        int pos = 32+(120 * i);
                        for (int j = 32; j < 512; j+= 120){
                            graphics.fillRect(pos, j, 120, 120);
                        }
                    }
                    graphics.fillRect(392, 512, 120,120);
                    graphics.fillRect(512, 512, 120,120);

                    graphics.drawImage(new Image("decerca.png"), 32,32);
                    graphics.drawImage(new Image("closerDraven.png"), 32, 152);
                    graphics.drawImage(new Image("closerBerserker.png"), 32, 272);
                    //60 x 90.

                    Image cuadraito = new Image("selectSquare.png");
                    graphics.drawImage(cuadraito, x, y);
                }
            };
            animationTimer.start();
        }

    }



