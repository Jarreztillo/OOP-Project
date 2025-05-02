package app.gameplayFeatures;

import domain.consumables.Inventory;
import domain.entities.EnemyCharacter;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;

import static app.gameplayFeatures.Combat.dropConsumable;
import static app.gameplayFeatures.Combat.noRandomPosition;
import static app.main.Game.window;
import static app.main.Roaster.Roaster.player;
import static domain.entities.EnemyCharacter.collidePlayer;
import static domain.entities.PlayerCharacter.collideEnemy;

public class Gameplay {

    public static boolean grabConsumable;
    public static boolean drawConsumable;
    public static boolean addConsumable;
    public static boolean activateRange;
    public static AnimationTimer gameplayTimer;
    public static ArrayList<Consumables> inventory;
    public static EnemyCharacter enemy;
    public static long time;

    public static boolean cantMoveUp;
    public static boolean cantMoveDown;
    public static boolean cantMoveLeftUp;
    public static boolean cantMoveLeftDown;
    public static boolean cantMoveRightUp;
    public static boolean cantMoveRightDown;

    public static int previusX;
    public static int previusY;


    public static Scene gameplayScene;


    private static Group root;
    private static Canvas canvas;
    public static GraphicsContext graphics;
    public static int actionPoints = 2;
    private static Random random = new Random();


    private static Label actionPoint;
    // Labels y botones.

    public static void initializeGameplay() {
        reviewMission();
        root = new Group();
        gameplayScene = new Scene(root, 1000, 850);
        canvas = new Canvas(1000, 850);
        root.getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();
        window.setScene(gameplayScene);
        enemy = new EnemyCharacter();

        if (!(Inventory.isAlreadyCreated())) {
            inventory = Inventory.createInventory();

        }


        if (enemy.isAlive()) {
            if (!noRandomPosition) {
                randomPosition();
            }
        }

        playerMovement();

        graphics.drawImage(new Image("background1.png"), 0, 0);
        // Fondo.

        graphics.drawImage(new Image("gameplaySquare1.png"), 680, 32);
        // Cuadro de las estadisticas.

        graphics.drawImage(new Image("gameplaySquare2.png"), 32, 710);
        // Cuadro de los dialogos.

        graphics.drawImage(new Image("gameplaySquare3.png"), 680, 542);
        // Cuadro de las acciones.

        Font font = new Font("Arial", 20);
        actionPoint = new Label("Action Points: " + actionPoints);
        actionPoint.setTranslateX(705);
        actionPoint.setTranslateY(48);
        actionPoint.setTextFill(Color.WHITE);
        actionPoint.setFont(font);
        root.getChildren().add(actionPoint);
        gameLoop();
        if (enemy.isAlive()) {
            enemy.setCharacter(player[0]);
        }
        player[0].setEnemy(enemy);
    }


    private static void gameLoop() {
        long initialTime = System.nanoTime();
        gameplayTimer = new AnimationTimer() {

            //Este metodo es el que maneja los frames por segundo, que son 60.
            @Override
            public void handle(long actualTime) {
                time = (actualTime - initialTime) / 1000000000;
                if (time == 60) {
                    time = 0;
                }
                actionPoint.setText("Action Points: " + actionPoints);
                /* Aqui se calcula el tiempo, que luego se usara
                para que parpadee el rango.
                */
                draw(time);

                actualizeState();

            }
        };
        gameplayTimer.start();
    }

    private static void randomPosition() {
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

    private static void playerMovement() {
        /* Por cada tecla presionada, el codigo evaluara
        los puntos de accion y la posicion del jugador
        y determinara si se mueve o no se mueve.
        */

        gameplayScene.setOnKeyPressed(event -> {
            switch (event.getCode().toString()) {
                case "A":
                    // Tecla A mueve el jugador hacia la izquierda-abajo (X-48, Y+32)
                   previusX = player[0].getX();
                   previusY = player[0].getY();
                        if (actionPoints != 0) {
                            actionPoints--;
                        } else {
                            break;
                        }
                        if (actionPoints >= 0) {
                            if (player[0].getY() == 640) {
                                actionPoints++;
                                break;
                            }
                            if (player[0].getX() == 64) {
                                actionPoints++;
                                break;
                            }
                            player[0].setX(player[0].getX() - 48);
                            player[0].setY(player[0].getY() + 32);

                        }
                    break;
                case "D":
                    // Tecla D mueve el jugador hacia la derecha-abajo (X+48, Y+32)
                    previusX = player[0].getX();
                    previusY = player[0].getY();
                        if (actionPoints != 0) {
                            actionPoints--;
                        } else {
                            break;
                        }
                        if (actionPoints >= 0) {
                            if (player[0].getY() == 640) {
                                actionPoints++;
                                break;
                            }
                            if (player[0].getX() == 544) {
                                actionPoints++;
                                break;
                            }
                            player[0].setX(player[0].getX() + 48);
                            player[0].setY(player[0].getY() + 32);
                        }

                    break;
                case "W":
                    // Tecla W mueve el jugador hacia arriba (Y-64)
                    previusX = player[0].getX();
                    previusY = player[0].getY();
                        if (actionPoints != 0) {
                            actionPoints--;
                        } else {
                            break;
                        }
                        if (actionPoints >= 0) {
                            if (player[0].getY() == 32) {
                                actionPoints++;
                                break;
                            }
                            if (player[0].getY() == 64) {
                                actionPoints++;
                                break;
                            }
                            player[0].setY(player[0].getY() - 64);
                        }

                    break;
                case "S":
                    // Tecla S mueve el jugador hacia abajo (Y+64)
                    previusX = player[0].getX();
                    previusY = player[0].getY();
                        if (actionPoints != 0) {
                            actionPoints--;
                        } else {
                            break;
                        }
                        if (actionPoints >= 0) {
                            if (player[0].getY() == 640) {
                                actionPoints++;
                                break;
                            }
                            if (player[0].getY() == 608) {
                                actionPoints++;
                                break;
                            }
                            player[0].setY(player[0].getY() + 64);
                        }

                    break;
                case "Q":
                    // Tecla Q mueve el jugador hacia la izquierda-arriba (X-48, Y-32)
                    previusX = player[0].getX();
                    previusY = player[0].getY();
                        if (actionPoints != 0) {
                            actionPoints--;
                        } else {
                            break;
                        }
                        if (actionPoints >= 0) {
                            if (player[0].getY() == 32) {
                                actionPoints++;
                                break;
                            }
                            if (player[0].getX() == 64) {
                                actionPoints++;
                                break;
                            }
                            player[0].setX(player[0].getX() - 48);
                            player[0].setY(player[0].getY() - 32);
                        }
                    break;
                case "E":
                    // Tecla E mueve el jugador hacia la derecha-arriba (X+48, Y-32)
                    previusX = player[0].getX();
                    previusY = player[0].getY();
                        if (actionPoints != 0) {
                            actionPoints--;
                        } else {
                            break;
                        }

                        if (actionPoints >= 0) {
                            if (player[0].getY() == 32) {
                                actionPoints++;
                                break;
                            }
                            if (player[0].getX() == 544) {
                                actionPoints++;
                                break;
                            }
                            player[0].setX(player[0].getX() + 48);
                            player[0].setY(player[0].getY() - 32);
                        }

                    break;
                case "R":
                    actionPoints = 2;
                    enemy.move(player[0].getX(), player[0].getY(), enemy.getX(), enemy.getY());
                    break;
                case "T":
                    activateRange = !activateRange;
                    break;

            }
        });


    }

    private static void draw(long time) {
        //Salvenos Dios por toda esta cantidad de codigo.

        TileMap.drawCampaignMap(graphics);
        //Dibujo de las columnas de hexagonos.

        player[0].range(graphics, time);
        if (enemy.isAlive()) {
            enemy.range(graphics, time);
            enemy.draw(graphics);
            rangeCollition(time);
        }
        graphics.drawImage(new Image(player[0].getImageName()), player[0].getX(), player[0].getY());
        //graphics.drawImage(new Image("narrador1.png"), 40,710);

        if (dropConsumable) {
            Consumables consumible = inventory.getFirst();
            if (drawConsumable) {
                graphics.drawImage(new Image(consumible.getImage()), consumible.getX(), consumible.getY());
            }
        }


    }

    private static void rangeCollition(long time) {
        int xDistanceEvP = enemy.getX() - player[0].getX();
        int yDistanceEvP = enemy.getY() - player[0].getY();
        if (activateRange) {
            if (time % 2 == 0) {
                Image rangoSobrepuesto = new Image("overRangeTerrain.png");
                if (xDistanceEvP == 96 && yDistanceEvP == 0) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() + 48, player[0].getY() + 32);
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() + 48, player[0].getY() - 32);
                }
                if (xDistanceEvP == -96 && yDistanceEvP == 0) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() - 48, player[0].getY() + 32);
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() - 48, player[0].getY() - 32);
                }
                if (xDistanceEvP == 96 && yDistanceEvP == 64) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() + 48, player[0].getY() + 32);
                }
                if (xDistanceEvP == 96 && yDistanceEvP == -64) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() + 48, player[0].getY() - 32);
                }
                if (xDistanceEvP == -96 && yDistanceEvP == 64) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() - 48, player[0].getY() + 32);
                }
                if (xDistanceEvP == -96 && yDistanceEvP == -64) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() - 48, player[0].getY() - 32);
                }
                // Los tres primeros ifs son a la izquierda y los tres ultimos a la derecha.


                if (xDistanceEvP == 0 && yDistanceEvP == 128) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX(), player[0].getY() + 64);

                }
                if (xDistanceEvP == 48 && yDistanceEvP == 96) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX(), player[0].getY() + 64);
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() + 48, player[0].getY() + 32);
                }
                if (xDistanceEvP == -48 && yDistanceEvP == 96) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX(), player[0].getY() + 64);
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() - 48, player[0].getY() + 32);
                }
                if (xDistanceEvP == 0 && yDistanceEvP == -128) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX(), player[0].getY() - 64);

                }
                if (xDistanceEvP == 48 && yDistanceEvP == -96) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX(), player[0].getY() - 64);
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() + 48, player[0].getY() - 32);
                }
                if (xDistanceEvP == -48 && yDistanceEvP == -96) {
                    graphics.drawImage(rangoSobrepuesto, player[0].getX(), player[0].getY() - 64);
                    graphics.drawImage(rangoSobrepuesto, player[0].getX() - 48, player[0].getY() - 32);
                }
            }
        }
    }

    private static void actualizeState() {
        if (enemy.isAlive()) {
            enemy.collideRange();
        }
        if (!inventory.isEmpty()) {
            player[0].collideWithConsumable(inventory);
        }
        player[0].collideRange();

        /* Llama a los metodos que comprueban las
         colisiones en cada enemigo y personaje.
        */

        if (collidePlayer && collideEnemy) {
            Combat.initializeWindow();
            collidePlayer = false;
            collideEnemy = false;
            player[0].setX(64);
            player[0].setY(64);
        }
        if (collidePlayer) {
            Combat.initializeWindow();
            collidePlayer = false;
            collideEnemy = false;
            player[0].setX(64);
            player[0].setY(64);
        }
        if (collideEnemy) {
            Combat.initializeWindow();
            collidePlayer = false;
            collideEnemy = false;
            player[0].setX(64);
            player[0].setY(64);
        }




    }

    private static void reviewMission() {
    }

}