package app.gameplayFeatures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class Map {
    private static boolean returnToNormal = false;
    private static String[] images = {
            "borderlessgraso.png",
            "borderGrasso1.png",
            "borderGrasso2.png",
            "forest.png",
            "ruins.png",
            "mountain.png",
            "borderSando1.png",
            "borderSando2.png",
            "sando.png",
            "borderWater1.png",
            "borderWater2.png",
            "water.png"};
    private static String[] upperThingsImages = {"nothing.png", "ruinsa.png", "tree.png", "mountains.png"};
    private static int[][] matrix = {
            {1, 1, 1, 1, 1, 1, 1, 6, 9, 9},
            {0, 0, 0, 0, 0, 0, 0, 8, 11, 11},
            {0, 0, 0, 0, 0, 0, 0, 8, 11, 11},
            {0, 0, 0, 0, 0, 0, 0, 8, 11, 11},
            {0, 0, 0, 0, 0, 0, 0, 8, 11, 11},
            {0, 0, 0, 0, 0, 0, 0, 8, 11, 11},
            {0, 0, 0, 0, 0, 0, 0, 8, 11, 11},
            {0, 0, 0, 0, 0, 0, 0, 8, 11, 11},
            {0, 0, 0, 0, 0, 0, 0, 8, 11, 11},
            {0, 0, 0, 0, 0, 0, 0, 8, 11, 11},
            {2, 2, 2, 2, 2, 2, 2, 7, 10, 10}};
    private static int[][] upperThingsMatrix = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 1, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    public static void drawNormalMap(GraphicsContext graphics) {
        Image hex = new Image("grass.png");


                for (int pos = 64; pos <= 544; pos += 32) {
                    if(pos%64==0) {
                        for (int j = 64; j < 704; j += 64) {
                            graphics.drawImage(hex, pos, j);
                        }
                    }else {
                        for (int j = 32; j <= 608; j += 64) {
                        graphics.drawImage(hex, pos, j);
                        }
                    }

                }

            }





    public static void drawCampaingMap(GraphicsContext graphics) {
        int contadorColumna = 0;
        int contadorFila = 0;


        for (int pos = 64; pos <= 544; pos += 48) {
            contadorColumna = 0;
            if(contadorFila == 0 || contadorFila % 2 == 0) {
                for (int j = 64; j <= 640; j += 64) {
                    int n = matrix[contadorFila][contadorColumna];
                    int a = upperThingsMatrix[contadorFila][contadorColumna];
                    graphics.drawImage(new Image(images[n]), pos, j);
                    if (a == 3){
                        j= j-20;
                        returnToNormal = true;
                    }
                    if (returnToNormal){
                        j = j+20;
                        returnToNormal = false;
                    }
                    graphics.drawImage(new Image(upperThingsImages[a]), pos+8, j+8);

                    contadorColumna++;
                }
            }else {
                for (int j = 32; j <= 608; j += 64) {
                    int n = matrix[contadorFila][contadorColumna];
                    int a = upperThingsMatrix[contadorFila][contadorColumna];
                    graphics.drawImage(new Image(images[n]), pos, j);
                    if (a == 3){
                        j= j+20;
                        returnToNormal = true;
                    }
                    if (returnToNormal){
                        j = j-20;
                        returnToNormal = false;
                    }
                    graphics.drawImage(new Image(upperThingsImages[a]), pos+8, j+8);
                    contadorColumna++;
                }
            }
            contadorFila++;
        }

    }
}
