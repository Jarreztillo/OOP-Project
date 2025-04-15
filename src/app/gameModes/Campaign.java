package app.gameModes;

import app.Combat;
import domain.entities.EnemyCharacter;
import domain.entities.PlayerCharacter;
// Clases locales que usa Campaign.

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
// Java FX.


import java.util.Random;

// Utilidades de Java.

import static domain.entities.EnemyCharacter.collidePlayer;
import static app.main.game.window;
import static domain.entities.PlayerCharacter.collideEnemy;
import static app.Combat.noRandomPosition;

// Variables estaticas importadas.

public class Campaign {

    public static boolean activateRange;
    public static Scene campaignGameScene;
    // Variables públicas estaticas.

    private Group root;
    private Canvas canvas;
    private GraphicsContext graphics;
    private PlayerCharacter player;
    private EnemyCharacter enemy;
    private int actionPoints = 2;
    private Random random = new Random();

    // Variables privadas.



    public void initialize(PlayerCharacter roasterPlayer) {

        root = new Group();
        campaignGameScene = new Scene(root, 1000, 850);
        canvas = new Canvas(1000, 850);
        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();
        window.setScene(campaignGameScene);
        player = new PlayerCharacter(64, 64);
        enemy = new EnemyCharacter();
        player.setImageName(roasterPlayer.getImageName());
        player.setClosestImageName(roasterPlayer.getClosestImageName());
        // Se pone la pantalla y se instancian los jugadores y enemigos.


        if (enemy.isAlive()){
        if (!noRandomPosition){
            randomPosition();
        }
        }else{
        }

        playerMovement(campaignGameScene);


        graphics.fillRect(680, 32, 310, 500);
        // Cuadro de las estadisticas.

        graphics.fillRect(32, 710, 612, 135);
        // Cuadro de los dialogos.

        graphics.fillRect(680, 542, 310, 303);
        // Cuadro de las acciones.

        Font font = new Font("Arial", 20);
        Label actionPoint = new Label("Action Points: "+actionPoints);
        actionPoint.setTranslateX(685);
        actionPoint.setTranslateY(48);
        actionPoint.setTextFill(Color.WHITE);
        actionPoint.setFont(font);
        root.getChildren().add(actionPoint);
        gameLoop(graphics, actionPoint);
        if (enemy.isAlive()){


        enemy.setCharacter(player);
        }
        player.setEnemy(enemy);

        // Colisiones que llevan al combate.



    }




    private void randomPosition() {
        int xPos = random.nextInt(7, 11);
        int yPos = random.nextInt(1, 10);
        /* xPos es de 7 a 11 para que el enemigo solo aparezca
            esas columnas. Y la yPos es para que aparezca en
            los hexagonos del 1 al 10 (esto varia si es par
            o impar).
         */


        if (xPos % 2 == 0) {
            yPos = (yPos * 64) + 32;
        } else {
            yPos = yPos * 64;
        }
        xPos = switch (xPos) {
            case 7 -> 352;
            case 8 -> 400;
            case 9 -> 448;
            case 10 -> 496;
            case 11 -> 544;
            default -> xPos;
        };
        /* Se comprueba la paridad de xPos para ubicarlo
        entre las columnas pares e impares, luego
         */



        enemy.setX(xPos);
        enemy.setY(yPos);

        // Se le pone al enemigo la posicion aleatoria generada.
    }

    private void playerMovement(Scene scene) {

        /* Por cada tecla presionada, el codigo evaluara
        los puntos de accion y la posicion del jugador
        y determinara si se mueve o no se mueve.
        */


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode().toString()) {
                    case "A":
                        if (actionPoints != 0){

                            actionPoints--;

                        }else{
                            break;
                        }
                        if (actionPoints >= 0) {
                            if (player.getY() == 640) {
                                actionPoints++;
                                break;
                            }
                            if (player.getX() == 64) {
                                actionPoints++;
                                break;
                            }

                            player.setX(player.getX()-48);
                            player.setY(player.getY()+32);


                            break;
                        }
                    case "D":
                        if (actionPoints != 0){

                        actionPoints--;

                        }else{
                            break;
                        }

                        if (actionPoints >= 0) {
                            if (player.getY() == 640) {
                                actionPoints++;
                                break;
                            }
                            if (player.getX() == 544) {
                                actionPoints++;
                                break;
                            }
                            player.setX(player.getX()+48);
                            player.setY(player.getY()+32);
                        }
                        break;

                    case "W":
                        if (actionPoints != 0){

                            actionPoints--;

                        }else{
                            break;
                        }
                        if (actionPoints >= 0) {
                            if (player.getY() == 32) {
                                actionPoints++;
                                break;
                            } else if (player.getY() == 64) {
                                actionPoints++;
                                break;
                            }

                            player.setY(player.getY()-64);
                            }
                        break;

                    case "S":
                        if (actionPoints != 0){

                            actionPoints--;

                        }else{
                            break;
                        }
                        if (actionPoints >= 0) {
                            if (player.getY() == 640) {
                                actionPoints++;
                                break;
                            }
                            if (player.getY() == 608) {
                                actionPoints++;
                                break;
                            }
                            player.setY(player.getY()+64);
                        }
                        break;

                    case "Q":
                        if (actionPoints != 0){

                        actionPoints--;

                    }else{
                        break;
                    }
                        if (actionPoints >= 0) {
                            if (player.getY() == 32) {
                                actionPoints++;
                                break;
                            }
                            if (player.getX() == 64) {
                                actionPoints++;
                                break;
                            }

                            player.setX(player.getX()-48);
                            player.setY(player.getY()-32);
                        }
                        break;
                    case "E":
                        if (actionPoints != 0){

                        actionPoints--;

                    }else{
                        break;
                    }
                        if (actionPoints >= 0) {
                            if (player.getY() == 32) {
                                actionPoints++;
                                break;
                            }
                            if (player.getX() == 544) {
                                actionPoints++;
                                break;
                            }

                            player.setX(player.getX()+48);
                            player.setY(player.getY()-32);

                        }

                        break;
                    case "R":
                        actionPoints = 2;
                        System.out.println("xDistanceEvP: "+(player.getX()-enemy.getX()));
                        System.out.println("yDistanceEvP: "+(player.getY()-enemy.getY()));
                        for (int i = 1; i<= 2; i++){

                        enemy.move(player.getX(), player.getY(), enemy.getX(), enemy.getY());

                        }

                        break;
                    case "T":
                        activateRange = !activateRange;

                        break;

                }
            }
        });
    }



    private void gameLoop(GraphicsContext graphics, Label actionPoint) {
        long initialTime = System.nanoTime();
        Combat combat = new Combat(player, enemy);
        AnimationTimer animationTimer = new AnimationTimer() {

            //Este metodo es el que maneja los frames por segundo, que son 60.
            @Override
            public void handle(long actualTime) {
                long time = (actualTime - initialTime) / 1000000000;
                if (time == 60){
                    time = 0;
                }
                actionPoint.setText("Action Points: "+actionPoints);
                /* Aqui se calcula el tiempo, que luego se usara
                para que parpadee el rango.
                */

                draw(time, graphics);
                actualizeState(combat);

            }
        };
        animationTimer.start();

        combat.setAnimationTimer(animationTimer);
    }


    private void actualizeState(Combat combat) {
        if (enemy.isAlive()){
            enemy.collideRange();
        }


        player.collideRange();

        /* Llama a los metodos que comprueban las
         colisiones en cada enemigo y personaje.
        */

        if (collidePlayer && collideEnemy) {
            combat.setGameScene(campaignGameScene);
            combat.initializeWindow();
            player.setX(64);
            player.setY(64);
        }
        if (collidePlayer) {
            combat.setGameScene(campaignGameScene);
            combat.initializeWindow();
            player.setX(64);
            player.setY(64);
        }
        if (collideEnemy) {
            combat.setGameScene(campaignGameScene);
            combat.initializeWindow();
            player.setX(64);
            player.setY(64);
        }



    }


    private void draw(long time, GraphicsContext graphics) {
        //Salvenos Dios por toda esta cantidad de codigo.

        for (int i = 1; i <= 11; i++) {
            Image hex = new Image("normalTerrain.png");
            if (i % 2 == 0) {
                for (int pos = 112; pos <= 496; pos+= 96) {
                    for (int j = 32; j < 640; j += 64) {
                        graphics.drawImage(hex, pos, j);
                    }
                }

            } else {

                for (int pos = 64; pos <= 544; pos+=96) {
                    for (int j = 64; j < 704; j += 64) {
                        graphics.drawImage(hex, pos, j);
                    }
                }

            }


        }
        //Dibujo de las columnas de hexagonos.

        player.range(graphics, time);
        if(enemy.isAlive()) {
            enemy.range(graphics, time);
            enemy.draw(graphics);
            rangeCollition(time);
        }
        player.draw(graphics);
        graphics.drawImage(new Image("narrador1.png"), 40,710);

    }

    private void rangeCollition(long time){
        int xDistanceEvP = enemy.getX() - player.getX();
        int yDistanceEvP = enemy.getY() - player.getY();
        if (activateRange){
        if (time % 2== 0){
        Image rangoSobrepuesto = new Image("overRangeTerrain.png");
        if (xDistanceEvP == 96 && yDistanceEvP == 0){
            graphics.drawImage(rangoSobrepuesto, player.getX()+48, player.getY()+32);
            graphics.drawImage(rangoSobrepuesto, player.getX()+48, player.getY()-32);
        }
            if (xDistanceEvP == -96 && yDistanceEvP == 0){
                graphics.drawImage(rangoSobrepuesto, player.getX()-48, player.getY()+32);
                graphics.drawImage(rangoSobrepuesto, player.getX()-48, player.getY()-32);
            }
            if (xDistanceEvP == 96 && yDistanceEvP == 64){
                graphics.drawImage(rangoSobrepuesto, player.getX()+48, player.getY()+32);
            }
            if (xDistanceEvP == 96 && yDistanceEvP == -64){
                graphics.drawImage(rangoSobrepuesto, player.getX()+48, player.getY()-32);
            }
            if (xDistanceEvP == -96 && yDistanceEvP == 64){
                graphics.drawImage(rangoSobrepuesto, player.getX()-48, player.getY()+32);
            }
            if (xDistanceEvP == -96 && yDistanceEvP == -64){
                graphics.drawImage(rangoSobrepuesto, player.getX()-48, player.getY()-32);
            }
            // Los tres primeros ifs son a la izquierda y los tres ultimos a la derecha.


            if (xDistanceEvP == 0 && yDistanceEvP == 128){
                graphics.drawImage(rangoSobrepuesto, player.getX(), player.getY()+64);

            }
            if (xDistanceEvP == 48 && yDistanceEvP == 96){
                graphics.drawImage(rangoSobrepuesto, player.getX(), player.getY()+64);
                graphics.drawImage(rangoSobrepuesto, player.getX()+48, player.getY()+32);
            }
            if (xDistanceEvP == -48 && yDistanceEvP == 96){
                graphics.drawImage(rangoSobrepuesto, player.getX(), player.getY()+64);
                graphics.drawImage(rangoSobrepuesto, player.getX()-48, player.getY()+32);
            }
            if (xDistanceEvP == 0 && yDistanceEvP == -128){
                graphics.drawImage(rangoSobrepuesto, player.getX(), player.getY()-64);

            }
            if (xDistanceEvP == 48 && yDistanceEvP == -96){
                graphics.drawImage(rangoSobrepuesto, player.getX(), player.getY()-64);
                graphics.drawImage(rangoSobrepuesto, player.getX()+48, player.getY()-32);
            }
            if (xDistanceEvP == -48 && yDistanceEvP == -96){
                graphics.drawImage(rangoSobrepuesto, player.getX(), player.getY()-64);
                graphics.drawImage(rangoSobrepuesto, player.getX()-48, player.getY()-32);
            }




            }
        }




    }




}
