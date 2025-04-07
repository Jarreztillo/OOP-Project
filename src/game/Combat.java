package game;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


import java.util.Random;

import static game.game.*;
import static game.EnemyCharacter.collidePlayer;
import static game.PlayerCharacter.collideEnemy;
import static gameMode.Campaign.*;


public class Combat {

    private Group root;
    private Group gameOverRoot;
    private Scene combatScene;
    private Scene gameOverScene;
    private static GraphicsContext graphics;
    private GraphicsContext looserGraphics;
    private AnimationTimer animationTimer;
    private AnimationTimer animationforotherthings;
    private boolean enemyAttack;
    private Random probabilities;
    private Scene gameScene;

    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    public void setAnimationTimer(AnimationTimer animationTimer) {
        this.animationTimer = animationTimer;
    }

    public static boolean noRandomPosition;

    public void initializeWindow() {
        animationTimer.stop();
        root = new Group();
        combatScene = new Scene(root, 832, 850);
        Canvas canvas = new Canvas(832, 850);
        //Configuraciones minimas.

        Label playerLife = new Label();
        Label playerAttack = new Label();
        Label enemyLife = new Label();
        Label enemyAttack = new Label();
        Label message = new Label();
        labelConfigurations(playerLife, playerAttack, enemyLife, enemyAttack, message);
        // Configuraciones de titulos

        Button attack = new Button("Atacar.");
        Button passTurn = new Button("Pasar turno.");
        Button runAway = new Button("Huir.");
        buttonConfigurations(attack, passTurn, runAway, playerLife, playerAttack, enemyLife,enemyAttack, message);


        // Configuraciones de ataques.
        root.getChildren().addAll(canvas,playerLife, playerAttack, enemyLife, enemyAttack, attack, message, runAway, passTurn);
        graphics = canvas.getGraphicsContext2D();
        graphics.fillRect(0, 440, 832, 410);
        window.setScene(combatScene);
        drawing();
        animationforotherthings = new AnimationTimer() {
            @Override
            public void handle(long l) {

                lifeChecker();
            }
        };
        animationforotherthings.start();

    }

    private void comeBack(Scene gameOverScene) {

        gameOverScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode().toString()){
                    case "R":
                        noRandomPosition = true;
                        collideEnemy = false;
                        collidePlayer = false;
                        window.setScene(gameScene);
                        animationforotherthings.stop();
                        animationTimer.start();
                        player.setLife(5);
                        System.out.println(gameScene);
                        break;
                }
            }
        });

    }

    private void buttonConfigurations(Button attack, Button passTurn, Button runAway, Label playerLife, Label playerAttack, Label enemyLife, Label enemyAttack, Label message) {
        attack.setTranslateX(150);
        attack.setTranslateY(550);
        attack.setOnAction(e -> playerAttack(enemyLife, playerLife, message));

        passTurn.setTranslateX(150);
        passTurn.setTranslateY(600);
        passTurn.setOnAction(e -> playerPassTurn(message,playerLife));

        runAway.setTranslateX(150);
        runAway.setTranslateY(650);
        runAway.setOnAction(e -> playerRunAway(message, playerLife));

}

    private void playerRunAway(Label message, Label playerLife) {
        probabilities = new Random();
        boolean runAway = probabilities.nextBoolean();
        if (runAway){
            noRandomPosition = true;
            collideEnemy = false;
            collidePlayer = false;
            window.setScene(campaignGameScene);
            animationTimer.start();
        }else {
            enemyAttack = probabilities.nextBoolean();
            if(enemyAttack){
                enemyAttack(playerLife);
                message.setText("¡El enemigo te metio un trampie y no te dejo huir! ¡-1 de vida!");
            }else{
                message.setText("¡No escapaste y el enemigo no te daño porque se tropezo con un charco!");
            }
        }
    }

    private void playerPassTurn(Label message, Label playerLife) {
        probabilities = new Random();
        enemyAttack = probabilities.nextBoolean();
        if(enemyAttack){
            enemyAttack(playerLife);
            message.setText("¡El enemigo aprovecho tu descanso para golpearte!");
        }else{
            message.setText("¡El enemigo recogio tremendo boniato y no te golpeo pese a haberte quedado quieto!");
        }

    }

    private void labelConfigurations(Label playerLife, Label playerAttack, Label enemyLife, Label enemyAttack, Label message) {
        Font stadisticsFont = new Font("ARIAL",20);

        playerLife.setTranslateX(150);
        playerLife.setTranslateY(450);
        playerLife.setText("HP: "+player.getLife());
        playerLife.setTextFill(Color.WHITE);


        playerAttack.setTranslateX(150);
        playerAttack.setTranslateY(480);
        playerAttack.setText("Attack points: "+player.getAttack());
        playerAttack.setTextFill(Color.WHITE);

        playerLife.setFont(stadisticsFont);
        playerAttack.setFont(stadisticsFont);
        //Label del jugador.


        enemyLife.setTranslateX(635);
        enemyLife.setTranslateY(450);
        enemyLife.setText("HP: "+enemy.getLife());
        enemyLife.setTextFill(Color.WHITE);

        enemyAttack.setTranslateX(542);
        enemyAttack.setTranslateY(480);
        enemyAttack.setText("Attack points: "+enemy.getAttack());
        enemyAttack.setTextFill(Color.WHITE);

        message.setTranslateX(20);
        message.setTranslateY(700);
        message.setText("¡Te has encontrado a tu mayor enemigo!");
        message.setTextFill(Color.WHITE);

        enemyLife.setFont(stadisticsFont);
        enemyAttack.setFont(stadisticsFont);
        message.setFont(stadisticsFont);

        //Label del enemigo



    }

    private void lifeChecker(){

        if (player.getLife() <= 0){
            animationforotherthings.stop();
            gameOverRoot = new Group();
            gameOverScene = new Scene(gameOverRoot, 832, 850);
            Font gameover = new Font(40);
            Canvas looserCanvas = new Canvas(832, 850);
            Label perdiste = new Label("Game over.");
            Label perdiste2 = new Label("Reinicie la partida con R. ");
            looserGraphics = looserCanvas.getGraphicsContext2D();
            gameOverRoot.getChildren().addAll(looserCanvas, perdiste, perdiste2);
            perdiste.setFont(gameover);
            perdiste.setTranslateX(250);
            perdiste.setTranslateY(150);
            perdiste2.setFont(gameover);
            perdiste.setTranslateX(250);
            perdiste.setTranslateY(200);

            looserGraphics.drawImage(new Image("fondo.jpg"), 116, 325);
            window.setScene(gameOverScene);
            comeBack(gameOverScene);
        }

        if (enemy.getLife() <= 0){
            enemy.setAlive(false);
            noRandomPosition = true;
            collideEnemy = false;
            collidePlayer = false;
            window.setScene(campaignGameScene);
            animationforotherthings.stop();
            animationTimer.start();
        }

    }

    private void drawing() {
        graphics.drawImage(new Image(player.getImageName()), 104, 325);
        graphics.drawImage(new Image(player.getClosestImageName()), 0, 440);
        graphics.drawImage(new Image(enemy.getImageName()), 684, 325);
        graphics.drawImage(new Image(enemy.getClosestImageName()), 712, 440);

        //Dibujando pantalla de pelea.
    }



    private void playerAttack(Label enemyLife, Label playerLife, Label message){
        probabilities = new Random();
        enemy.setLife(enemy.getLife() - player.getAttack());
        System.out.println("Al enemigo le queda: "+enemy.getLife());
        enemyLife.setText("HP: "+ enemy.getLife());
        enemyAttack = probabilities.nextBoolean();
        if(enemyAttack){
            enemyAttack(playerLife);
            message.setText("¡El enemigo te golpeo en el estomago!");
        }else{
            message.setText("¡El enemigo se ha resbalado y has evitado su ataque!");
        }
    }
    private void enemyAttack(Label playerLife){
            player.setLife(player.getLife() - enemy.getAttack());
            playerLife.setText("HP: "+player.getLife());

    }




}
