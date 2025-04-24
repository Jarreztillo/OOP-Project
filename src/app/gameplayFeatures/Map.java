package app.gameplayFeatures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class Map {

    private static String[] images = {"grass.png", "forest.png", "ruins.png", "mountain.png", "sand.png", "water.png"};
    private static int[][] oddmatrix = {       
            {0, 0, 0, 0, 0, 0, 0, 4, 5, 5},   
            {1, 1, 0, 0, 0, 0, 0, 4, 5, 5},
            {1, 1, 0, 0, 0, 0, 0, 4, 5, 5},   
            {1, 1, 0, 0, 0, 0, 0, 4, 5, 5},
            {1, 1, 0, 0, 0, 0, 0, 4, 5, 5},   
            {3, 0, 0, 0, 0, 0, 0, 4, 5, 5}};  
    private static int[][] evenmatrix = {      
            {1, 1, 0, 0, 0, 0, 0, 4, 5, 5},
            {1, 1, 0, 0, 0, 0, 0, 4, 5, 5},
            {1, 1, 0, 0, 0, 2, 0, 4, 5, 5},
            {1, 1, 0, 0, 0, 0, 0, 4, 5, 5},   
            {1, 1, 0, 0, 0, 0, 0, 4, 5, 5},   
            {1, 1, 0, 0, 0, 0, 0, 4, 5, 5}};  

    public static void drawNormalMap(GraphicsContext graphics) {

        for (int i = 1; i <= 11; i++) {
            Image hex = new Image("grass.png");
            if (i % 2 == 0) {
                for (int pos = 112; pos <= 496; pos += 96) {

                    for (int j = 32; j < 640; j += 64) {
                        graphics.drawImage(hex, pos, j);
                    }
                }

            } else {

                for (int pos = 64; pos <= 544; pos += 96) {
                    for (int j = 64; j < 704; j += 64) {
                        graphics.drawImage(hex, pos, j);
                    }
                }

            }


        }
    }

    public static void drawCampaingMap(GraphicsContext graphics) {
        int contadorColumna = 0;
        int contadorFila = 0;

        for (int pos = 64; pos <= 544; pos += 96) {
            for (int j = 64; j <= 640; j += 64) {
                int n = oddmatrix[contadorFila][contadorColumna];
                graphics.drawImage(new Image(images[n]), pos, j);
                contadorColumna++;
            }
            contadorColumna = 0;
            contadorFila++;
        }
        contadorFila = 0;
        for (int pos = 112; pos <= 496; pos += 96) {
            for (int j = 32; j <= 608; j += 64) {
                int n = evenmatrix[contadorFila][contadorColumna];
                graphics.drawImage(new Image(images[n]), pos, j);
                contadorColumna++;
            }
            contadorColumna = 0;
            contadorFila++;
        }


    }





}
