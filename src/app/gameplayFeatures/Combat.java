package app.gameplayFeatures;

import domain.consumables.vitality_potion;
import domain.entities.EnemyCharacter;
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

import static app.main.Roaster.Roaster.player;
import static app.main.game.*;
import static domain.entities.EnemyCharacter.collidePlayer;
import static domain.entities.PlayerCharacter.collideEnemy;
import static app.gameModes.Campaign.*;


public class Combat {
    private int selectedCharacter = 0;
    private int playerTurn = 5;
    private EnemyCharacter enemy;
    private static GraphicsContext graphics;
    private AnimationTimer animationTimer;
    private AnimationTimer animationForOtherThings;
    private AnimationTimer combatTimer;
    private boolean enemyAttack;
    private Random probabilities;
    private Scene gameScene;
    private Group root;

    private Label message;
    private Label playerLife;
    private Label playerAttack;


    public static boolean noRandomPosition;
    public static boolean dropConsumable;

    public void initializeWindow() {
        animationTimer.stop();
        root = new Group();
        Scene combatScene = new Scene(root, 832, 850);
        Canvas canvas = new Canvas(832, 850);
        //Configuraciones minimas.

        playerLife = new Label();
        playerAttack = new Label();
        Label enemyLife = new Label();
        Label enemyAttack = new Label();
        message = new Label();
        labelConfigurations (enemyLife, enemyAttack, message);
        // Configuraciones de titulos

        Button attack = new Button("Atacar.");
        Button passTurn = new Button("Pasar turno.");
        Button runAway = new Button("Huir.");
        Button useConsumable = new Button("Usar item.");
        buttonConfigurations(attack, passTurn, runAway, useConsumable, enemyLife,enemyAttack);


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
                drawingPortraits();
                if(playerTurn == 0){
                    enemyTurn();
                }
            }
        };
        animationForOtherThings.start();
        switchCharacters(combatScene);
    }

    private void enemyTurn() {
        boolean enemyAttack = probabilities.nextBoolean();
    }

    private void drawingPortraits() {
        graphics.fillRect(0, 440, 120, 120);
        graphics.drawImage(new Image(player[selectedCharacter].getClosestImageName()), 0, 440);
    }

    private void switchCharacters(Scene combatScene) {

        combatScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode().toString()){
                    case "W":
                        if(selectedCharacter == 4){
                            break;
                        }
                        selectedCharacter++;

                        break;
                    case "S":
                        if (selectedCharacter == 0){
                            break;
                        }
                        selectedCharacter--;
                        break;


                }
            }
        });
    }


    private void drawing() {
        if (player[0].getHealth() != 0) {
            graphics.drawImage(new Image(player[0].getImageName()), 104, 325);
        }
        if(player[1].getHealth() != 0){
            graphics.drawImage(new Image(player[1].getImageName()), 104, 255);

        }
        if(player[2].getHealth() != 0){
            graphics.drawImage(new Image(player[2].getImageName()), 104, 185);
        }
        if(player[3].getHealth() != 0){
            graphics.drawImage(new Image(player[3].getImageName()), 104, 115);
        }
        if(player[4].getHealth() != 0){
            graphics.drawImage(new Image(player[4].getImageName()), 104, 55);
        }

        graphics.drawImage(new Image(enemy.getImageName()), 684, 325);
        graphics.drawImage(new Image(enemy.getClosestImageName()), 712, 440);

        //Dibujando pantalla de pelea.
    }



    private void labelConfigurations(Label enemyLife, Label enemyAttack, Label message) {
        Font stadisticsFont = new Font("ARIAL",20);

        playerLife.setTranslateX(150);
        playerLife.setTranslateY(450);
        playerLife.setText("HP: "+player[0].getHealth());
        playerLife.setTextFill(Color.WHITE);


        playerAttack.setTranslateX(150);
        playerAttack.setTranslateY(480);
        playerAttack.setText("Attack points: "+player[0].getAttack());
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

    private void buttonConfigurations(Button attack, Button passTurn, Button runAway, Button useConsumable, Label enemyLife, Label enemyAttack) {
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
        playerTurn = 0;

    }

    private void playerUseConsumable(Label playerLife){
        if(!(inventory.isEmpty())) {
            System.out.println(inventory.getFirst().getQuantity());
        }
        if (inventory.getFirst().getQuantity()== 0){
            message.setText("¡No tienes consumibles! ¿¡Para que #@|~@ tocas el boton!?");
        }else {
                Button Potion = new Button("Usar poción de vitalidad. ");
                Potion.setTranslateX(250);
                Potion.setTranslateY(550);
                Potion.setOnAction(e -> usePotion(playerLife));
                message.setText("¡Guau, tienes "+ inventory.getFirst().getQuantity()+" pociones de vitalidad!");
                root.getChildren().add(Potion);

        }




    }

    private void lifeChecker(){
        for (int n = 0; n<=player.length-1; n++) {
            if (player[n].getHealth() <= 0) {
            if (player[selectedCharacter] == player[n]){
                if(selectedCharacter == 0){
                    selectedCharacter++;
                    return;
                }
                if(selectedCharacter == 4){
                    selectedCharacter--;
                    return;
                }
                boolean arriba = probabilities.nextBoolean();
                if(arriba){
                    selectedCharacter++;
                }else{
                    selectedCharacter--;
                }
            }
            }
        }


            if (player[4].getHealth() <= 0 && player[3].getHealth() <= 0 &&player[2].getHealth() <= 0 &&player[1].getHealth() <= 0 && player[0].getHealth() <= 0) {
                animationForOtherThings.stop();
                Group gameOverRoot = new Group();
                Scene gameOverScene = new Scene(gameOverRoot, 832, 850);
                Font gameover = new Font(40);
                Canvas looserCanvas = new Canvas(832, 850);
                Label perdiste = new Label("Game over.");
                Label perdiste2 = new Label("Reinicie la partida con R. ");
                gameOverRoot.getChildren().addAll(looserCanvas, perdiste, perdiste2);
                perdiste.setFont(gameover);
                perdiste.setTranslateX(250);
                perdiste.setTranslateY(150);
                perdiste2.setFont(gameover);
                perdiste.setTranslateX(250);
                perdiste.setTranslateY(200);

                window.setScene(gameOverScene);
                comeBack(gameOverScene);
            }



        if (enemy.getHealth() <= 0){
            enemy.setHealth(1);
            enemy.setAlive(false);
            noRandomPosition = true;
            collideEnemy = false;
            collidePlayer = false;
            dropConsumable = true;
            if(dropConsumable) {
                inventory.getFirst().setX(enemy.getX());
                inventory.getFirst().setY(enemy.getY());
                inventory.getFirst().setImage("vitality_potion.png");
                addConsumable = true;
                drawConsumable = true;
            }
            window.setScene(campaignGameScene);
            animationForOtherThings.stop();
            animationTimer.start();
        }

    }

    private void actualizeState() {
        if (grabConsumable && inventory.getFirst().getQuantity() >= 0){
            grabConsumable = false;
        }
        playerLife.setText("HP: "+player[selectedCharacter].getHealth());
        playerAttack.setText("Attack points: "+player[selectedCharacter].getAttack());
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
                        for (int n = player.length-1; n >= 0; n--){
                            player[n].setHealth(5);
                        }
                        break;
                }
            }
        });

    }

    private void usePotion(Label playerLife){
        if (inventory.getFirst().getQuantity() > 0) {
            vitality_potion potion = new vitality_potion();
            player[selectedCharacter].setHealth(player[selectedCharacter].getHealth() + potion.getHealthAdded());
            message.setText("¡Has usado una pocion de vitalidad! ¡Te curas "+potion.getHealthAdded()+" de vida!");
            inventory.getFirst().setQuantity(inventory.getFirst().getQuantity() - 1);
            playerLife.setText("HP: "+player[selectedCharacter].getHealth());
            playerTurn--;

        }else{
            message.setText("¿Crees que vas a usar una pocion de vitalidad que no tienes?");
        }

    }



    private void playerAttack(Label enemyLife, Label playerLife){
        probabilities = new Random();
        enemy.setHealth(enemy.getHealth() - player[selectedCharacter].getAttack());
        System.out.println("Al enemigo le queda: "+enemy.getHealth());
        enemyLife.setText("HP: "+ enemy.getHealth());
        playerTurn--;
    }
    private void enemyAttack(Label playerLife){
            player[selectedCharacter].setHealth(player[selectedCharacter].getHealth() - enemy.getAttack());
            playerLife.setText("HP: "+player[selectedCharacter].getHealth());

    }


    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    public void setAnimationTimer(AnimationTimer animationTimer) {
        this.animationTimer = animationTimer;
    }

    public Combat(EnemyCharacter enemy){
        this.enemy = enemy;

    }





}
