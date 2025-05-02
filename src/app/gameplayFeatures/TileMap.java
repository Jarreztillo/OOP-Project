package app.gameplayFeatures;

import app.gameplayFeatures.maps.Maps;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static app.gameplayFeatures.Gameplay.*;
import static app.main.Roaster.Roaster.player;

public class TileMap {
    private static int contador = 0;
    private static int knownPlayerPosition;
    private static boolean returnToNormal = false;
    private static boolean[][] boleanos = new boolean[11][11];
    private static int[][] matrix;
    private static int[][] upperThingsMatrix;
    private static String[] images = {
            "borderlessgraso.png",
            "borderGrasso1.png",
            "borderGrasso2.png",
            "borderSando1.png",
            "borderSando2.png",
            "sando.png",
            "borderWater1.png",
            "borderWater2.png",
            "water.png"};
    private static String[] upperThingsImages = {
            "nothing.png",
            "ruinsa.png",
            "tree.png",
            "mountains.png"};


    public static void drawTutorialMap(GraphicsContext graphics) {
        matrix = Maps.getTutorialMap();
        int contadorFila = 0;

        for (int pos = 64; pos <= 544; pos += 48) {
            int contadorColumna = 0;
            if (contadorFila % 2 == 0) {
                for (int j = 64; j < 704; j += 64) {
                    int n = matrix[contadorFila][contadorColumna];
                    graphics.drawImage(new Image(images[n]), pos, j);
                    contadorColumna++;
                }
            } else {
                for (int j = 32; j <= 608; j += 64) {
                    int n = matrix[contadorFila][contadorColumna];
                    graphics.drawImage(new Image(images[n]), pos, j);
                    contadorColumna++;
                }
            }
            contadorFila++;

        }

    }


    public static void drawCampaignMap(GraphicsContext graphics) {
        matrix = Maps.getFirstMap();
        upperThingsMatrix = Maps.getFirstMapUpperThingsMatrix();
        int contadorColumna = 0;

        for (int pos = 64; pos <= 544; pos += 48) {
            int contadorFila = 0;
            if (contadorColumna % 2 == 0) {
                for (int j = 64; j <= 640; j += 64) {
                    int n = matrix[contadorColumna][contadorFila];
                    int a = upperThingsMatrix[contadorColumna][contadorFila];
                    graphics.drawImage(new Image(images[n]), pos, j);
                    checkTerrains(n, a, pos, j, contadorColumna, contadorFila);
                    if (a == 3) {
                        j = j - 20;
                        returnToNormal = true;
                    }
                    if (returnToNormal) {
                        j = j + 20;
                        returnToNormal = false;
                    }
                    graphics.drawImage(new Image(upperThingsImages[a]), pos + 8, j + 8);
                    contadorFila++;

                }
                // Impar.
                // Par.
            } else {
                for (int j = 32; j <= 608; j += 64) {
                    int n = matrix[contadorColumna][contadorFila];
                    int a = upperThingsMatrix[contadorColumna][contadorFila];
                    graphics.drawImage(new Image(images[n]), pos, j);
                    checkTerrains(n, a, pos, j, contadorColumna, contadorFila);
                    if (a == 3) {
                        j = j + 20;
                        returnToNormal = true;
                    }
                    if (returnToNormal) {
                        j = j - 20;
                        returnToNormal = false;
                    }
                    graphics.drawImage(new Image(upperThingsImages[a]), pos + 8, j + 8);
                    contadorFila++;
                }
            }
            contadorColumna++;
        }
    }

    private static void checkTerrains(int actualTerrain, int upperActualTerrain, int x, int y, int col, int fil) {
        if(actualTerrain >= 6 && actualTerrain <=8){

           if (player[0].getX() == x && player[0].getY() == y){
               player[0].setX(previusX);
               player[0].setY(previusY);
               actionPoints++;
           }

        }

    }
}

