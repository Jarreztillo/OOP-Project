package app.gameplayFeatures;

import app.main.AudioPlayer;
import domain.consumables.VitalityPotion;
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

import static app.gameplayFeatures.Gameplay.*;
import static app.main.Roaster.Roaster.player;
import static app.main.Game.*;


public class Combat {
    private static int selectedCharacter = 0;
    private static int playerTurn = 5;
    private static GraphicsContext graphics;
    private static AnimationTimer animationForOtherThings;
    private static AnimationTimer combatTimer;
    private static boolean enemyAttack;
    private static Random probabilities;
    private static Scene combatScene;
    private static Group root;

    private static Label message;
    private static Label playerLife;
    private static Label playerAttack;
    private static Label playerTurnLabel;


    public static boolean noRandomPosition;
    public static boolean dropConsumable;

    public static void initializeWindow() {
        gameplayTimer.stop();
        AudioPlayer.playCombatMusic();
        root = new Group();
        combatScene = new Scene(root, 832, 850);
        Canvas canvas = new Canvas(832, 850);
        //Configuraciones minimas.

        playerLife = new Label();
        playerAttack = new Label();
        playerTurnLabel = new Label();
        Label enemyLife = new Label();
        Label enemyAttack = new Label();
        message = new Label();
        labelConfigurations (enemyLife, enemyAttack);
        // Configuraciones de titulos

        Button attack = new Button("Atacar.");
        Button passTurn = new Button("Pasar turno.");
        Button runAway = new Button("Huir.");
        Button useConsumable = new Button("Usar item.");
        buttonConfigurations(attack, passTurn, runAway, useConsumable, enemyLife, enemyAttack);


        // Configuraciones de ataques.
        root.getChildren().addAll(canvas,playerLife, playerAttack, enemyLife, enemyAttack, attack, runAway, passTurn, useConsumable, message, playerTurnLabel);
        graphics = canvas.getGraphicsContext2D();
        window.setScene(combatScene);
        animationForOtherThings = new AnimationTimer() {
            @Override
            public void handle(long l) {
                lifeChecker();
                actualizeState();
                drawing();
                drawingPortraits();
                if(playerTurn == 0){
                    enemyTurn();
                }

            }
        };
        animationForOtherThings.start();
        switchCharacters(combatScene);
    }

    private static void drawingPortraits() {
        graphics.drawImage(new Image(player[selectedCharacter].getClosestImageName()), 0, 440);
    }

    private static void switchCharacters(Scene combatScene) {

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


    private static void drawing() {
        graphics.drawImage(new Image("combatBackground.png"), 0, 0);
        graphics.drawImage(new Image("combatSquare.png"), 0, 440);
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



    private static void labelConfigurations(Label enemyLife, Label enemyAttack) {
        Font stadisticsFont = new Font("ARIAL",20);

        playerLife.setTranslateX(150);
        playerLife.setTranslateY(450);
        playerLife.setText("HP: "+player[0].getHealth());
        playerLife.setTextFill(Color.WHITE);


        playerAttack.setTranslateX(150);
        playerAttack.setTranslateY(480);
        playerAttack.setText("Attack points: "+player[0].getAttack());
        playerAttack.setTextFill(Color.WHITE);

        playerTurnLabel.setTranslateX(20);
        playerTurnLabel.setTranslateY(780);
        playerTurnLabel.setText("Acciones por turno: "+playerTurn);
        playerTurnLabel.setTextFill(Color.WHITE);

        playerLife.setFont(stadisticsFont);
        playerAttack.setFont(stadisticsFont);
        playerTurnLabel.setFont(stadisticsFont);
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

    private static void buttonConfigurations(Button attack, Button passTurn, Button runAway, Button useConsumable, Label enemyLife, Label enemyAttack) {
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

    private static void playerRunAway(Label playerLife) {
        probabilities = new Random();
        boolean runAway = probabilities.nextBoolean();
        if (runAway){
            noRandomPosition = true;
            window.setScene(gameplayScene);
            AudioPlayer.stopIfPlaying("combatMusic");
            gameplayTimer.start();
        }else {
            enemyAttack = probabilities.nextBoolean();
            playerTurn--;
            if(enemyAttack){
                enemyAttack();
                message.setText("¡El enemigo te metio un trampie y no te dejo huir! ¡-1 de vida!");
            }else{
                message.setText("¡No escapaste y el enemigo no te daño porque se tropezo con un charco!");
            }
        }
    }

    private static void playerPassTurn(Label playerLife) {
        probabilities = new Random();
        enemyAttack = probabilities.nextBoolean();
        playerTurn = 0;
    }

    private static void playerUseConsumable(Label playerLife){

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

    private static void lifeChecker(){
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
                AudioPlayer.stopIfPlaying("combatMusic");
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

                combatScene.setRoot(new Group());
                animationForOtherThings.stop();
                window.setScene(gameOverScene);
                comeBack(gameOverScene);
            }



        if (enemy.getHealth() <= 0){
            AudioPlayer.stopIfPlaying("combatMusic");
            enemy.setHealth(10);
            enemy.setAlive(false);
            noRandomPosition = true;
            dropConsumable = probabilities.nextBoolean();
            if(dropConsumable) {
                inventory.getFirst().setX(enemy.getX());
                inventory.getFirst().setY(enemy.getY());
                inventory.getFirst().setImage("vitality_potion.png");
                addConsumable = true;
                drawConsumable = true;
            }
            combatScene.setRoot(new Group());
            animationForOtherThings.stop();
            gameplayTimer.start();
            window.setScene(gameplayScene);
        }

    }

    private static void actualizeState() {
        if (grabConsumable && inventory.getFirst().getQuantity() >= 0){
            grabConsumable = false;
        }
        playerLife.setText("HP: "+player[selectedCharacter].getHealth());
        playerAttack.setText("Attack points: "+player[selectedCharacter].getAttack());
        playerTurnLabel.setText("Acciones por turno: "+playerTurn);
    }

    private static void comeBack(Scene gameOverScene) {

        gameOverScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode().toString()){
                    case "R":
                        noRandomPosition = true;
                        window.setScene(gameplayScene);
                        animationForOtherThings.stop();
                        gameplayTimer.start();
                        for (int n = player.length-1; n >= 0; n--){
                            player[n].setHealth(5);
                        }
                        break;
                }
            }
        });

    }

    private static void usePotion(Label playerLife){
        if (inventory.getFirst().getQuantity() > 0) {
            VitalityPotion potion = new VitalityPotion();
            player[selectedCharacter].setHealth(player[selectedCharacter].getHealth() + potion.getHealthAdded());
            message.setText("¡Has usado una pocion de vitalidad! ¡Te curas "+potion.getHealthAdded()+" de vida!");
            inventory.getFirst().setQuantity(inventory.getFirst().getQuantity() - 1);
            playerLife.setText("HP: "+player[selectedCharacter].getHealth());
            playerTurn--;

        }else{
            message.setText("¿Crees que vas a usar una poción de vitalidad que no tienes?");
        }

    }

    private static void playerAttack(Label enemyLife, Label playerLife){
        probabilities = new Random();
        boolean playerAttack = probabilities.nextBoolean();
        if(playerAttack) {
            enemy.setHealth(enemy.getHealth() - player[selectedCharacter].getAttack());
            enemyLife.setText("HP: " + enemy.getHealth());
            message.setText("¡Ay Dios, "+player[selectedCharacter].getCharacterName()+" le ha hecho "+player[selectedCharacter].getAttack()+" de daño!");
        }else{
            message.setText("¡"+player[selectedCharacter].getCharacterName()+" ha fallado el ataque!");
        }
        playerTurn--;
    }
    private static void enemyAttack(){
        boolean enemyAttack = probabilities.nextBoolean();
        int damagedPlayer = probabilities.nextInt(0, 5);
        System.out.println(damagedPlayer);
        if (enemyAttack){
            player[damagedPlayer].setHealth(player[damagedPlayer].getHealth() - enemy.getAttack());
            message.setText("¡El enemigo ha atacado a "+player[damagedPlayer].getCharacterName()+" y le ha hecho "+enemy.getAttack()+" de daño!");
        }else{
            message.setText("¡El enemigo intento atacar a "+player[damagedPlayer].getCharacterName()+", pero se resbalo y no pudo!");

        }
        }

    private static void enemyTurn() {
        enemyAttack();
        playerTurn = 5;
    }

    public Combat(){

    }





}
