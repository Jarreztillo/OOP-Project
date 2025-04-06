package game;

import gameMode.Campaign;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Timer;
import java.util.TimerTask;
// Temporizador del rango.


public class PlayerCharacter {
    private int x;
    private int y;
    private int attack = 1;
    private int life =  5;

    private String imageName;


    private String closestImageName;
    private boolean activatedRange;
    private EnemyCharacter enemy;

    public static boolean collideEnemy;




    public void draw(GraphicsContext graphics) {
        graphics.drawImage(new Image(imageName), x, y);
    }
    //Se ejecuta por cada iteracion del gameLoop.



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

    public int getAttack() {return attack;}

    public void setAttack(int attack) {this.attack = attack;}

    public PlayerCharacter(String imageName) {this.imageName = imageName;}

    public EnemyCharacter getEnemy() {return enemy;}

    public void setEnemy(EnemyCharacter enemy) {this.enemy = enemy;}

    public PlayerCharacter(int x, int y, String imageName, String closestImageName) {
        this.x = x;
        this.y = y;
        this.imageName = imageName;
        this.closestImageName = closestImageName;
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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}

