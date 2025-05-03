package app.gameplayFeatures;

import app.main.AudioPlayer;
import domain.consumables.VitalityPotion;
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
import java.util.Objects;
import java.util.Random;

import static app.gameplayFeatures.Gameplay.*;
import static app.main.Game.*;


public class Combat {
    private static PlayerCharacter[] player;
    private static EnemyCharacter[] enemy;
    private static int selectedCharacter = 0;
    private static int selectedEnemy = 0;
    private static int playerTurn = 5;
    private static GraphicsContext graphics;
    private static AnimationTimer animationForOtherThings;
    private static AnimationTimer combatTimer;
    private static boolean enemyAttack;
    private static boolean selectEnemyToAttack = false;
    private static boolean attackToSelectedEnemy = false;
    private static Random probabilities;
    private static Scene combatScene;
    private static Group root;
    private static ArrayList<Consumables> inventory;
    private static String keyPressed;

    private static Label message;
    private static Label playerLife;
    private static Label playerAttack;
    private static Label playerTurnLabel;

    private static Label enemyLife;
    private static Label enemyAttackL;

    private static Button attack;
    private static Button runAway;
    private static Button passTurn;
    private static Button useConsumable;


    public static boolean noRandomPosition;
    public static boolean dropConsumable;

    public static void initializeCombat() {
        preparingConfigurations();
        preparingWindow();
    }


    private static void preparingConfigurations() {
        Gameplay.stopGameplayTimer();
        player = Gameplay.getPlayer();
        enemy = Gameplay.getEnemy();
        probabilities = new Random();
        inventory = Gameplay.getInventory();
        AudioPlayer.playCombatMusic();
    }


    private static void preparingWindow() {
        root = new Group();
        combatScene = new Scene(root, 832, 850);
        Canvas canvas = new Canvas(832, 850);
        //Configuraciones minimas.

        labelConfigurations();
        // Configuraciones de titulos


        buttonConfigurations();
        // Configuraciones de botones.


        root.getChildren().addAll(canvas,playerLife, playerAttack, enemyLife, enemyAttackL, attack, runAway, passTurn, useConsumable, message, playerTurnLabel);
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
        graphics.drawImage(new Image(enemy[selectedEnemy].getClosestImageName()), 712, 440);
    }

    private static void switchCharacters(Scene combatScene) {

        combatScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode().toString());
                switch (event.getCode().toString()){
                    case "W":
                        keyPressed = "W";
                        if (!selectEnemyToAttack) {
                            if (selectedCharacter == 4) {
                                break;
                            }
                            selectedCharacter++;
                        }else{
                            if(selectedEnemy == enemy.length-1){
                                break;
                            }
                            selectedEnemy++;
                        }
                        break;
                    case "S":
                        keyPressed = "S";
                        if (!selectEnemyToAttack) {
                        if (selectedCharacter == 0) {
                            break;
                        }
                        selectedCharacter--;
                    }else{
                            if(selectedEnemy == 0){
                                break;
                            }
                            selectedEnemy--;
                    }
                        break;
                    case "SPACE":
                        if(selectEnemyToAttack){
                            System.out.println("Llegaste aca.");
                            attackToSelectedEnemy = true;
                            keyPressed = event.getCode().toString();

                        }
                        break;


                }
            }
        });
    }


    private static void drawing() {
        graphics.drawImage(new Image("combatBackground.png"), 0, 0);
        graphics.drawImage(new Image("combatSquare.png"), 0, 440);

        int positionY = 325;

        for (int i = 0; i< player.length; i++, positionY-= 50){
            if (player[i].getHealth() > 0) {
                graphics.drawImage(new Image(player[i].getImageName()), 104, positionY);

            }
        }
        positionY = 325;
        for (int i = 0; i< enemy.length; i++, positionY-=50){
            if (enemy[i].getHealth() >= 0) {
                graphics.drawImage(new Image(enemy[i].getImageName()), 684, positionY);
            }
        }
        positionY = 325;
        if(selectEnemyToAttack){
            if(selectedEnemy >= 0){
                positionY = positionY-(selectedEnemy*50);
            }
            graphics.drawImage(new Image("selectEnemy.png"), 684,positionY);
        }

        //Dibujando pantalla de pelea.
    }



    private static void labelConfigurations() {
        playerLife = new Label();
        playerAttack = new Label();
        playerTurnLabel = new Label();
        enemyLife = new Label();
        enemyAttackL = new Label();
        message = new Label();
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
        enemyLife.setText("HP: "+enemy[0].getHealth());
        enemyLife.setTextFill(Color.WHITE);

        enemyAttackL.setTranslateX(542);
        enemyAttackL.setTranslateY(480);
        enemyAttackL.setText("Attack points: "+enemy[0].getAttack());
        enemyAttackL.setTextFill(Color.WHITE);

        //Label del enemigo

        message.setTranslateX(20);
        message.setTranslateY(750);
        message.setText("¡Te has encontrado a tu mayor enemigo!");
        message.setTextFill(Color.WHITE);

        enemyLife.setFont(stadisticsFont);
        enemyAttackL.setFont(stadisticsFont);
        message.setFont(stadisticsFont);


    }

    private static void buttonConfigurations() {
        attack = new Button("Atacar.");
        passTurn = new Button("Pasar turno.");
        runAway = new Button("Huir.");
        useConsumable = new Button("Usar item.");

        attack.setTranslateX(150);
        attack.setTranslateY(550);
        attack.setFocusTraversable(false);
            attack.setOnAction(e -> playerAttack());


        passTurn.setTranslateX(150);
        passTurn.setTranslateY(600);
        passTurn.setFocusTraversable(false);
        passTurn.setOnAction(e -> playerPassTurn(playerLife));

        useConsumable.setTranslateX(150);
        useConsumable.setTranslateY(650);
        useConsumable.setFocusTraversable(false);
        useConsumable.setOnAction(e -> playerUseConsumable(playerLife));

        runAway.setTranslateX(150);
        runAway.setTranslateY(700);
        runAway.setFocusTraversable(false);
        runAway.setOnAction(e -> playerRunAway(playerLife));

}

    private static void playerRunAway(Label playerLife) {
        boolean runAway = probabilities.nextBoolean();
        if (runAway){
            noRandomPosition = true;
            window.setScene(getGameplayScene());
            AudioPlayer.stopIfPlaying("combatMusic");
            Gameplay.stopGameplayTimer();
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

            if(player[selectedCharacter].getHealth() <= 0){
               goToAliveCharacter();
            }
            if(enemy[selectedEnemy].getHealth() <= 0){
                goToAliveEnemy();
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


            for (int i = 0; i <enemy.length; i++){
                if(enemy[i].getHealth() <= 0){
                    enemy[i].setAlive(false);
                }
            }


        if (areAllDead()){
            AudioPlayer.stopIfPlaying("combatMusic");
            selectedEnemy = 0;
            for (int i = 0; i<enemy.length; i++){
                enemy[i].setHealth(10);
            }
            noRandomPosition = true;
            dropConsumable = probabilities.nextBoolean();
            if(dropConsumable) {
                inventory.getFirst().setX(enemy[0].getX());
                inventory.getFirst().setY(enemy[0].getY());
                inventory.getFirst().setImage("vitality_potion.png");
                Gameplay.setAddConsumable(true);
                Gameplay.setDrawConsumable(true);
            }
            combatScene.setRoot(new Group());
            animationForOtherThings.stop();
            Gameplay.startGameplayTimer();
            window.setScene(Gameplay.getGameplayScene());
        }

    }


    private static void actualizeState() {
        if (Gameplay.isGrabConsumable() && inventory.getFirst().getQuantity() >= 0){
            Gameplay.setGrabConsumable(false);
        }
        playerLife.setText("HP: "+player[selectedCharacter].getHealth());
        playerAttack.setText("Attack points: "+player[selectedCharacter].getAttack());
        playerTurnLabel.setText("Acciones por turno: "+playerTurn);

        enemyLife.setText("HP: "+enemy[selectedEnemy].getHealth());
        enemyAttackL.setText("Attack points: "+enemy[selectedEnemy].getAttack());


        enemyLife.setText("HP: "+enemy[selectedEnemy].getHealth());

        if(attackToSelectedEnemy){
            if (Objects.equals(keyPressed, "SPACE")) {
                enemy[selectedEnemy].setHealth(enemy[selectedEnemy].getHealth() - player[selectedCharacter].getAttack());
                message.setText("¡Ay, Dios! ¡" + player[selectedCharacter].getCharacterName() + "ha atacado al enemigo numero " + selectedEnemy + "!");
                selectEnemyToAttack = false;
                attackToSelectedEnemy = false;
                attack.setDisable(false);
                keyPressed = "";
            }
        }
    }

    private static void comeBack(Scene gameOverScene) {

        gameOverScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode().toString()){
                    case "R":
                        noRandomPosition = true;
                        window.setScene(Gameplay.getGameplayScene());
                        animationForOtherThings.stop();
                        Gameplay.startGameplayTimer();
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

    private static void playerAttack(){
        boolean playerAttack = probabilities.nextBoolean();
        if(playerAttack) {
            selectEnemyToAttack = true;
            attack.setDisable(true);
            System.out.println("Llegaste. "+selectedEnemy);
            }else{
            message.setText("¡"+player[selectedCharacter].getCharacterName()+" ha fallado el ataque!");
        }
        playerTurn--;
    }
    private static void enemyAttack() {
        for (int i = 0; i < enemy.length; i++) {

            boolean enemyAttack = probabilities.nextBoolean();
            int damagedPlayer = probabilities.nextInt(0, 5);
            if (enemyAttack) {
                player[damagedPlayer].setHealth(player[damagedPlayer].getHealth() - enemy[i].getAttack());
                message.setText("¡El enemigo numero "+i+" ha atacado a " + player[damagedPlayer].getCharacterName() + " y le ha hecho " + enemy[0].getAttack() + " de daño!");
            } else {
                message.setText("¡El enemigo numero "+i+" intento atacar a " + player[damagedPlayer].getCharacterName() + ", pero se resbalo y no pudo!");

            }
            System.out.println("El enemigo: "+i+" a: "+player[damagedPlayer].getCharacterName());
        }
    }

    private static void enemyTurn() {
        enemyAttack();
        playerTurn = 5;
    }

    public Combat(){

    }

    private static boolean areAllDead(){
        boolean allDead = false;
        int contador = enemy.length-1;


        for (int i = 0; i < enemy.length; i++){
            if (!enemy[i].isAlive()){
                contador--;
            }
        }

        if(contador < 0){
            allDead = true;
        }
        return allDead;
    }


    private static void goToAliveCharacter() {
        System.out.println("Me activo. ");
        int[] auxiliar = new int[player.length];
        for (int i = 0; i<player.length; i++) {
            if (player[i].getHealth() > 0) {
                auxiliar[i] = i+1;
            }
        }
        System.out.println("Se activa el auxiliar, que tiene: "+auxiliar);
        for(int i = 0; i<player.length; i++){
            if (selectedCharacter == i && auxiliar[i] == 0){
                if (keyPressed == "S"){
                    for (int a = 0; a<player.length; a++){
                        if(auxiliar[a] != 0){
                            selectedCharacter = auxiliar[a]-1;
                            return;
                        }
                }}else if (keyPressed == "W"){
                        for (int a = player.length-1; a>=0; a--){
                            if(auxiliar[a] != 0){
                                selectedCharacter = auxiliar[a]-1;
                                return;
                            }
                    }

                }

                }
            }
        }

    private static void goToAliveEnemy() {
        System.out.println("Me activo. ");
        int[] auxiliar = new int[enemy.length];
        for (int i = 0; i < enemy.length; i++) {
            if (enemy[i].getHealth() > 0) {
                auxiliar[i] = i + 1;
            }
        }
        System.out.println("Se activa el auxiliar, que tiene: " + auxiliar);
        for (int i = 0; i < enemy.length; i++) {
            if (selectedEnemy == i && auxiliar[i] == 0) {
                if (keyPressed == "S") {
                    for (int a = 0; a < enemy.length; a++) {
                        if (auxiliar[a] != 0) {
                            selectedEnemy = auxiliar[a] - 1;
                            return;
                        }
                    }
                } else if (keyPressed == "W") {
                    for (int a = enemy.length - 1; a >= 0; a--) {
                        if (auxiliar[a] != 0) {
                            selectedEnemy = auxiliar[a] - 1;
                            return;
                        }
                    }

                }

            }
        }
    }
    }





