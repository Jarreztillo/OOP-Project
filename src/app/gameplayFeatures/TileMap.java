package app.gameplayFeatures;

import app.gameplayFeatures.maps.Maps;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileMap {
    private static boolean returnToNormal = false;
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
                    if(contadorFila % 2 == 0) {
                        for (int j = 64; j < 704; j += 64) {
                            int n = matrix[contadorFila][contadorColumna];
                            graphics.drawImage(new Image(images[n]), pos, j);
                            contadorColumna++;
                        }
                    }else {
                        for (int j = 32; j <= 608; j += 64) {
                            int n = matrix[contadorFila][contadorColumna];
                            graphics.drawImage(new Image(images[n]), pos, j);
                            contadorColumna++;
                        }
                    }
                    contadorFila++;

                }

            }





    public static void drawCampaingMap(GraphicsContext graphics) {
        matrix = Maps.getFirstMap();
        upperThingsMatrix = Maps.getFirstMapUpperThingsMatrix();
        int contadorFila = 0;


        for (int pos = 64; pos <= 544; pos += 48) {
            int contadorColumna = 0;
            if(contadorFila % 2 == 0) {
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
