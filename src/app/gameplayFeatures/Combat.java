package app.gameplayFeatures;

import domain.consumables.vitality_potion;
import domain.entities.EnemyCharacter;
import domain.entities.PlayerCharacter;
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


import java.util.ArrayList;
import java.util.Random;

import static app.main.game.*;
import static domain.entities.EnemyCharacter.collidePlayer;
import static domain.entities.PlayerCharacter.collideEnemy;
import static app.gameModes.Campaign.*;


public class Combat {

    private PlayerCharacter player;
    private EnemyCharacter enemy;
    private static GraphicsContext graphics;
    private AnimationTimer animationTimer;
    private AnimationTimer animationForOtherThings;
    private boolean enemyAttack;
    private Random probabilities;
    private Scene gameScene;
    private Group root;
    private vitality_potion potion = new vitality_potion();

    private Label message;

    public static boolean noRandomPosition;
    public static boolean dropConsumable;

    public void initializeWindow() {
        animationTimer.stop();
        root = new Group();
        Scene combatScene = new Scene(root, 832, 850);
        Canvas canvas = new Canvas(832, 850);
        //Configuraciones minimas.

        Label playerLife = new Label();
        Label playerAttack = new Label();
        Label enemyLife = new Label();
        Label enemyAttack = new Label();
        message = new Label();
        labelConfigurations(playerLife, playerAttack, enemyLife, enemyAttack, message);
        // Configuraciones de titulos

        Button attack = new Button("Atacar.");
        Button passTurn = new Button("Pasar turno.");
        Button runAway = new Button("Huir.");
        Button useConsumable = new Button("Usar item.");
        buttonConfigurations(attack, passTurn, runAway, useConsumable, playerLife, playerAttack, enemyLife,enemyAttack);


        // Configuraciones de ataques.
        root.getChildren().addAll(canvas,playerLife, playerAttack, enemyLife, enemyAttack, attack, runAway, passTurn, useConsumable, message);
        graphics = canvas.getGraphicsContext2D();
        graphics.fillRect(0, 440, 832, 410);
        window.setScene(combatScene);
        drawing();
        animationForOtherThings = new AnimationTimer() {
            @Override
            public void handle(long l) {

                lifeChecker();
                actualizeState();
            }
        };
        animationForOtherThings.start();

    }




    private void drawing() {
        graphics.drawImage(new Image(player.getImageName()), 104, 325);
        graphics.drawImage(new Image(player.getClosestImageName()), 0, 440);
        graphics.drawImage(new Image(enemy.getImageName()), 684, 325);
        graphics.drawImage(new Image(enemy.getClosestImageName()), 712, 440);

        //Dibujando pantalla de pelea.
    }



    private void labelConfigurations(Label playerLife, Label playerAttack, Label enemyLife, Label enemyAttack, Label message) {
        Font stadisticsFont = new Font("ARIAL",20);

        playerLife.setTranslateX(150);
        playerLife.setTranslateY(450);
        playerLife.setText("HP: "+player.getHealth());
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
        enemyLife.setText("HP: "+enemy.getHealth());
        enemyLife.setTextFill(Color.WHITE);

        enemyAttack.setTranslateX(542);
        enemyAttack.setTranslateY(480);
        enemyAttack.setText("Attack points: "+enemy.getAttack());
        enemyAttack.setTextFill(Color.WHITE);

        //Label del enemigo

        message.setTranslateX(20);
        message.setTranslateY(750);
        message.setText("¡Te has encontrado a tu mayor enemigo!");
        message.setTextFill(Color.WHITE);

        enemyLife.setFont(stadisticsFont);
        enemyAttack.setFont(stadisticsFont);
        message.setFont(stadisticsFont);


    }

    private void buttonConfigurations(Button attack, Button passTurn, Button runAway, Button useConsumable, Label playerLife, Label playerAttack, Label enemyLife, Label enemyAttack) {
        attack.setTranslateX(150);
        attack.setTranslateY(550);
        attack.setOnAction(e -> playerAttack(enemyLife, playerLife));

        passTurn.setTranslateX(150);
        passTurn.setTranslateY(600);
        passTurn.setOnAction(e -> playerPassTurn(playerLife));

        useConsumable.setTranslateX(150);
        useConsumable.setTranslateY(650);
        useConsumable.setOnAction(e -> playerUseConsumable(playerLife));

        runAway.setTranslateX(150);
        runAway.setTranslateY(700);
        runAway.setOnAction(e -> playerRunAway(playerLife));

}

    private void playerRunAway(Label playerLife) {
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

    private void playerPassTurn(Label playerLife) {
        probabilities = new Random();
        enemyAttack = probabilities.nextBoolean();
        if(enemyAttack){
            enemyAttack(playerLife);
            message.setText("¡El enemigo aprovecho tu descanso para golpearte!");
        }else{
            message.setText("¡El enemigo recogió tremendo boniato y no te golpeo pese a haberte quedado quieto!");
        }

    }

    private void playerUseConsumable(Label playerLife){
        if (player.consumablesIsEmpty()){
            message.setText("¡No tienes consumibles! ¿¡Para que pinga tocas el boton!?");
        }else {
            potion = (vitality_potion) player.getConsumablesAtIndex(0);
            if (player.getConsumablesAtIndex(0) == potion) {
                Button Potion = new Button("Usar poción de vitalidad. ");
                Potion.setTranslateX(250);
                Potion.setTranslateY(550);
                Potion.setOnAction(e -> usePotion(potion, playerLife));
                message.setText("¡Guau, tienes "+potion.getQuantity()+" pociones de vitalidad!");
                root.getChildren().add(Potion);
            }
        }




    }

    private void lifeChecker(){

        if (player.getHealth() <= 0){
            animationForOtherThings.stop();
            Group gameOverRoot = new Group();
            Scene gameOverScene = new Scene(gameOverRoot, 832, 850);
            Font gameover = new Font(40);
            Canvas looserCanvas = new Canvas(832, 850);
            Label perdiste = new Label("Game over.");
            Label perdiste2 = new Label("Reinicie la partida con R. ");
            GraphicsContext looserGraphics = looserCanvas.getGraphicsContext2D();
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

        if (enemy.getHealth() <= 0){
            enemy.setHealth(1);
            enemy.setAlive(false);
            noRandomPosition = true;
            collideEnemy = false;
            collidePlayer = false;
            dropConsumable = probabilities.nextBoolean();
            if(dropConsumable) {
                potion.setX(enemy.getX());
                potion.setY(enemy.getY());
                potion.setImage("vitality_potion.png");
                if (mapConsumables.isEmpty()){
                mapConsumables.add(potion);
                addConsumable = true;
                drawConsumable = true;
                }

            }
            window.setScene(campaignGameScene);
            animationForOtherThings.stop();
            animationTimer.start();
        }

    }

    private void actualizeState() {
        if (grabConsumable && potion.getQuantity() >= 0){
            System.out.println("Hay "+ mapConsumables.getFirst().getQuantity()+" pociones añadidas. ");
            System.out.println("Tienes "+ potion.getQuantity()+" pociones. ");
            potion.setQuantity(mapConsumables.getFirst().getQuantity());
            grabConsumable = false;
        }
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
                        animationForOtherThings.stop();
                        animationTimer.start();
                        player.setHealth(5);
                        System.out.println(gameScene);
                        break;
                }
            }
        });

    }

    private void usePotion(vitality_potion potion, Label playerLife){
        if (potion.getQuantity() > 0) {

            player.setHealth(player.getHealth() + potion.getHealthAdded());
            message.setText("¡Has usado una pocion de vitalidad! ¡Te curas "+potion.getHealthAdded()+" de vida!");
            potion.setQuantity(potion.getQuantity() - 1);
            playerLife.setText("HP: "+player.getHealth());

        }else{
            message.setText("¿Crees que vas a usar una pocion de vitalidad que no tienes?");
        }

    }



    private void playerAttack(Label enemyLife, Label playerLife){
        probabilities = new Random();
        enemy.setHealth(enemy.getHealth() - player.getAttack());
        System.out.println("Al enemigo le queda: "+enemy.getHealth());
        enemyLife.setText("HP: "+ enemy.getHealth());
        enemyAttack = probabilities.nextBoolean();
        if(enemyAttack){
            enemyAttack(playerLife);
            message.setText("¡El enemigo te golpeo en el estomago!");
        }else{
            message.setText("¡El enemigo se ha resbalado y has evitado su ataque!");
        }
    }
    private void enemyAttack(Label playerLife){
            player.setHealth(player.getHealth() - enemy.getAttack());
            playerLife.setText("HP: "+player.getHealth());

    }


    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    public void setAnimationTimer(AnimationTimer animationTimer) {
        this.animationTimer = animationTimer;
    }

    public Combat(PlayerCharacter player, EnemyCharacter enemy){
        this.player = player;
        this.enemy = enemy;

    }





}
