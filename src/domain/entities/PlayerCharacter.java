package domain.entities;

import app.gameplayFeatures.Consumables;
import app.gameplayFeatures.Gameplay;
import app.main.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

// Temporizador del rango.


public class PlayerCharacter {
    private int x;
    private int y;
    private int attack;
    private int health;
    private String characterName;
    private String imageName;
    private String closestImageName;
    private EnemyCharacter enemy;
    private boolean isSelected;
    public static boolean collideEnemy;





    public void range(GraphicsContext graphics, long time) {
        Image rango = new Image("rangeTerrain.png");
        if (Gameplay.activateRange) {
            boolean actived = false;
            boolean corner = false;
            if (time % 2 == 0) {

                if (x == 64 && y == 640) {

                    graphics.drawImage(rango, x, y - 64);
                    graphics.drawImage(rango, x + 48, y - 32);
                    actived = true;
                    corner = true;
                } else if (x == 64 && y == 64) {
                    graphics.drawImage(rango, x, y + 64);
                    graphics.drawImage(rango, x + 48, y + 32);
                    graphics.drawImage(rango, x + 48, y - 32);
                    actived = true;
                    corner = true;
                }
                if (x == 544 && y == 640) {
                    graphics.drawImage(rango, x, y - 64);
                    graphics.drawImage(rango, x - 48, y - 32);
                    actived = true;
                    corner = true;
                } else if (x == 544 && y == 64) {
                    graphics.drawImage(rango, x, y + 64);
                    graphics.drawImage(rango, x - 48, y + 32);
                    graphics.drawImage(rango, x - 48, y - 32);
                    corner = true;
                    actived = true;
                }

                if (!corner) {
                    if (x == 64) {
                        graphics.drawImage(rango, x, y + 64);
                        graphics.drawImage(rango, x, y - 64);
                        graphics.drawImage(rango, x + 48, y + 32);
                        graphics.drawImage(rango, x + 48, y - 32);

                        actived = true;
                    } else if (x == 544) {
                        graphics.drawImage(rango, x, y + 64);
                        graphics.drawImage(rango, x, y - 64);
                        graphics.drawImage(rango, x - 48, y + 32);
                        graphics.drawImage(rango, x - 48, y - 32);
                        actived = true;
                    }

                    for (int i = 1; i <= 11; i++) {

                        if (i % 2 == 0) {
                            for (int pos = 112; pos <= 496; pos += 96) {
                                if (y == 32 && x == pos) {
                                    graphics.drawImage(rango, x, y + 64);
                                    graphics.drawImage(rango, x + 48, y + 32);
                                    graphics.drawImage(rango, x - 48, y + 32);
                                    actived = true;
                                }
                                if (y == 608 && x == pos) {
                                    graphics.drawImage(rango, x, y - 64);
                                    graphics.drawImage(rango, x + 48, y - 32);
                                    graphics.drawImage(rango, x - 48, y - 32);
                                    graphics.drawImage(rango, x + 48, y + 32);
                                    graphics.drawImage(rango, x - 48, y + 32);
                                    actived = true;
                                }
                            }

                        } else {
                            for (int pos = 64; pos <= 544; pos += 96) {
                                if (y == 64 && x == pos) {
                                    graphics.drawImage(rango, x, y + 64);
                                    graphics.drawImage(rango, x + 48, y + 32);
                                    graphics.drawImage(rango, x - 48, y + 32);
                                    graphics.drawImage(rango, x - 48, y - 32);
                                    graphics.drawImage(rango, x + 48, y - 32);
                                    actived = true;
                                }
                                if (y == 640 && x == pos) {
                                    graphics.drawImage(rango, x, y - 64);
                                    graphics.drawImage(rango, x + 48, y - 32);
                                    graphics.drawImage(rango, x - 48, y - 32);
                                    actived = true;
                                }
                            }
                        }
                    }

                    if (!actived) {

                        graphics.drawImage(rango, x, y + 64);
                        graphics.drawImage(rango, x, y - 64);
                        graphics.drawImage(rango, x + 48, y - 32);
                        graphics.drawImage(rango, x - 48, y - 32);
                        graphics.drawImage(rango, x + 48, y + 32);
                        graphics.drawImage(rango, x - 48, y + 32);
                    }
                }

            }

        }

    }

    public void collideRange() {
        if (enemy.isAlive()){
        if (enemy.getX() == x && enemy.getY() == y) {collideEnemy = true;}
        if (enemy.getX() == x && enemy.getY() == y + 64) {collideEnemy = true;}
        if (enemy.getX() == x && enemy.getY() == y - 64) {collideEnemy = true;}
        if (enemy.getX() == x + 48 && enemy.getY() == y + 32) {collideEnemy = true;}
        if (enemy.getX() == x - 48 && enemy.getY() == y + 32) {collideEnemy = true;}
        if (enemy.getX() == x + 48 && enemy.getY() == y - 32) {collideEnemy = true;}
        if (enemy.getX() == x - 48 && enemy.getY() == y - 32) {collideEnemy = true;}

        }
    }
    public void collideWithConsumable(ArrayList<Consumables> inventory){
        if (!enemy.isAlive()){
            if (inventory.size() == 1){
                if (inventory.getFirst().getX() == x && inventory.getFirst().getY() == y) {
                    inventory.getFirst().setQuantity(inventory.getFirst().getQuantity() + 3);
                    Gameplay.grabConsumable = true;
                    Gameplay.drawConsumable = false;
                    x = 64;
                    y = 64;
                    enemy.setX(544);
                    enemy.setY(64);
                    enemy.setAlive(true);
                }
                }else if (inventory.get(1).getX() == x && inventory.get(1).getY() == y){

                }else if (inventory.get(2).getX() == x && inventory.get(2).getY() == y){

                }else if (inventory.get(3).getX() == x && inventory.get(3).getY() == y){

                }
            }
    }





    public int getAttack() {return attack;}

    public void setAttack(int attack) {this.attack = attack;}

    public PlayerCharacter(String imageName) {this.imageName = imageName;}

    public EnemyCharacter getEnemy() {return enemy;}

    public void setEnemy(EnemyCharacter enemy) {this.enemy = enemy;}

    public PlayerCharacter(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public PlayerCharacter(){

    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getX() {return x;}

    public void setX(int x) {this.x = x;}

    public int getY() {return y;}

    public void setY(int y) {this.y = y;}

    public String getImageName() {return imageName;}

    public void setImageName(String imageName) {this.imageName = imageName;}


    public String getClosestImageName() {
        return closestImageName;
    }

    public void setClosestImageName(String closestImageName) {
        this.closestImageName = closestImageName;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }



}

