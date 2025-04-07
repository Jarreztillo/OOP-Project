package game;

import java.util.Random;

import gameMode.Campaign;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class EnemyCharacter {
    private String closestImageName;
    private int x;
    private int y;
    private int attack = 2;
    private int life = 5;
    private boolean alive = true;



    private String imageName;
    private PlayerCharacter character;
    public static boolean collidePlayer;
    Random random = new Random();


    public void draw(GraphicsContext graphics) {
        graphics.drawImage(new Image(imageName), x, y);
    }

    public void move(boolean enemyMove, int playerx, int playery, int enemyx, int enemyy) {
        if (enemyMove) {


            enemyMove = false;
        }

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
        if (character.getX() == x && character.getY() == y) {collidePlayer = true;}
        if (character.getX() == x && character.getY() == y + 64) {collidePlayer = true;}
        if (character.getX() == x && character.getY() == y - 64) {collidePlayer = true;}
        if (character.getX() == x + 48 && character.getY() == y + 32) {collidePlayer = true;}
        if (character.getX() == x - 48 && character.getY() == y + 32) {collidePlayer = true;}
        if (character.getX() == x + 48 && character.getY() == y - 32) {collidePlayer = true;}
        if (character.getX() == x - 48 && character.getY() == y - 32) {collidePlayer = true;}
    }


    public PlayerCharacter getCharacter() {
        return character;
    }

    public void setCharacter(PlayerCharacter character) {
        this.character = character;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public EnemyCharacter(int x, int y, String imageName, String closestImageName) {
        this.y = y;
        this.x = x;
        this.imageName = imageName;
        this.closestImageName = closestImageName;
    }
    public EnemyCharacter(){

    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public String getClosestImageName() {
        return closestImageName;
    }

    public void setClosestImageName(String closestImageName) {
        this.closestImageName = closestImageName;
    }
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}



