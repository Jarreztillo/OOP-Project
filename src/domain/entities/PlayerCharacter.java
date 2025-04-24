package domain.entities;

import app.gameModes.Campaign;
import app.gameplayFeatures.Consumables;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

// Temporizador del rango.


public class PlayerCharacter {
    private int x;
    private int y;
    private int attack;
    private int Health;
    private String imageName;
    private String closestImageName;
    private EnemyCharacter enemy;
    private boolean isSelected;
    public static boolean collideEnemy;
    private ArrayList<Consumables> consumables = new ArrayList<>();

    public ArrayList<Consumables> getConsumables() {
        return consumables;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean consumablesIsEmpty(){

        return consumables.isEmpty();
    }

    public Consumables getConsumablesAtIndex(int i) {
        if (i == 0){
            return consumables.getFirst();
        }
        if (i == 1){
            return consumables.get(1);
        }if (i == 2){
            return consumables.get(2);
        }if (i == 3){
            return consumables.get(3);
        }

        return null;
    }

    public void setConsumables(Consumables consumables) {
        this.consumables.add(consumables);
    }




    public void range(GraphicsContext graphics, long time) {
        Image rango = new Image("rangeTerrain.png");
        if (Campaign.activateRange) {
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
    public void collideWithConsumable(ArrayList<Consumables> mapConsumables){
        if (!enemy.isAlive()){
            if (mapConsumables.size() == 1) {
                if (mapConsumables.getFirst().getX() == x && mapConsumables.getFirst().getY() == y){
                    mapConsumables.getFirst().setQuantity(mapConsumables.getFirst().getQuantity() + 3);
                    Campaign.grabConsumable = true;
                    Campaign.drawConsumable = false;
                    enemy.setX(544);
                    enemy.setY(64);
                    enemy.setAlive(true);
                }
            }
            if (mapConsumables.size() == 2) {
                if (mapConsumables.getFirst().getX() == x && mapConsumables.getFirst().getY() == y){
                    mapConsumables.getFirst().setQuantity(mapConsumables.getFirst().getQuantity() + 3);
                    Campaign.grabConsumable = true;
                    Campaign.drawConsumable = false;
                    x = 64;
                    y = 64;
                    enemy.setX(544);
                    enemy.setY(64);
                    enemy.setAlive(true);

                }else if (mapConsumables.get(1).getX() == x && mapConsumables.get(1).getY() == y){

                }
            }
            if (mapConsumables.size() == 3) {
                if (mapConsumables.getFirst().getX() == x && mapConsumables.getFirst().getY() == y){
                    mapConsumables.getFirst().setQuantity(mapConsumables.getFirst().getQuantity() + 3);
                    Campaign.grabConsumable = true;
                    Campaign.drawConsumable = false;
                    x = 64;
                    y = 64;
                    enemy.setX(544);
                    enemy.setY(64);
                    enemy.setAlive(true);

                }else if (mapConsumables.get(1).getX() == x && mapConsumables.get(1).getY() == y){

                }else if (mapConsumables.get(2).getX() == x && mapConsumables.get(2).getY() == y){

                }
            }
            if (mapConsumables.size() == 4) {
                if (mapConsumables.getFirst().getX() == x && mapConsumables.getFirst().getY() == y){
                    mapConsumables.getFirst().setQuantity(mapConsumables.getFirst().getQuantity()+3);
                    Campaign.grabConsumable = true;
                    Campaign.drawConsumable = false;
                    x = 64;
                    y = 64;
                    enemy.setX(544);
                    enemy.setY(64);
                    enemy.setAlive(true);

                }else if (mapConsumables.get(1).getX() == x && mapConsumables.get(1).getY() == y){

                }else if (mapConsumables.get(2).getX() == x && mapConsumables.get(2).getY() == y){

                }else if (mapConsumables.get(3).getX() == x && mapConsumables.get(3).getY() == y){

                }
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
        return Health;
    }

    public void setHealth(int health) {
        this.Health = health;
    }



}

