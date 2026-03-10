package gameplayFeatures;

import main.Maps;
import fastFeatures.AudioPlayer;
import generalClasses.PlayerCharacter;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static fastFeatures.LabelManager.createLabel;
import static gameplayFeatures.TerrainEffects.*;


public class TileMap {
    private static final int initialColPos = 64;
    private static final int finalColPos = 544;
    private static final int initialEvenRowPos = 64;
    private static final int finalEvenRowPos = 640;
    private static final int initialOddRowPos = 32;
    private static final int finalOddRowPos = 672;
    private static final int nextCol = 48;
    private static final int nextRow = 64;
    private static final int initialPos = 64;
    private static final int realocate = 20;
    private static final int realocateCol = 8;
    private static final int realocateRow = 8;


    private static GraphicsContext graphics = Gameplay.getGraphics();
    private static PlayerCharacter[] player;
    private static boolean returnToNormal = false;
    private static int[][] matrix;
    private static int[][] upperThingsMatrix;
    private static String[] images = {
            "/DAO/images/terrains/borderlessGrass.png",
            "/DAO/images/terrains/borderGrass2.png",
            "/DAO/images/terrains/borderGrass1.png",
            "/DAO/images/terrains/borderSand1.png",
            "/DAO/images/terrains/borderSand2.png",
            "/DAO/images/terrains/sand.png",
            "/DAO/images/terrains/borderWater1.png",
            "/DAO/images/terrains/borderWater2.png",
            "/DAO/images/terrains/water.png",
            "/DAO/images/terrains/hoyo_trampa.png"};
    private static String[] upperThingsImages = {
            "/DAO/images/terrains/nothing.png",
            "/DAO/images/terrains/ruins.png",
            "/DAO/images/terrains/tree.png",
            "/DAO/images/terrains/mountains.png"};


    public static void drawCampaignMap(GraphicsContext graphics, int mission) {
        matrix = Maps.getFirstMap();
        upperThingsMatrix = Maps.getFirstMapUpperThingsMatrix();
        switch (mission){
            case 1:
                matrix = Maps.getSecondMap();
                upperThingsMatrix = Maps.getSecondMapUpperThingsMatrix();
                break;
            case 2:
                matrix = Maps.getThirdMap();
                upperThingsMatrix = Maps.getThirdMapUpperThingsMatrix();
                break;
            case 3:
                matrix = Maps.getLastMap();
                upperThingsMatrix = Maps.getLastMapUpperThingsMatrix();
                break;
        }
        int colCounter = 0;

        for (int col = initialPos; col <= finalColPos; col += nextCol) {
            int terrain;
            int upper;
            int rowCounter = 0;
            if (colCounter % 2 == 0) {
                for (int evenRow = initialEvenRowPos; evenRow <= finalEvenRowPos; evenRow += nextRow) {
                    terrain = matrix[colCounter][rowCounter];
                    upper = upperThingsMatrix[colCounter][rowCounter];
                    graphics.drawImage(new Image(images[terrain]), col, evenRow);
                    checkTerrains(terrain, upper, col, evenRow, colCounter, rowCounter);
                    if (upper == 3) {
                        evenRow = evenRow - realocate;
                        returnToNormal = true;
                    }
                    if (returnToNormal) {
                        evenRow = evenRow + realocate;
                        returnToNormal = false;
                    }
                    graphics.drawImage(new Image(upperThingsImages[upper]), col + realocateCol, evenRow + realocateRow);
                    rowCounter++;
                }
                // Impar.
                // Par.
            } else {
                for (int oddRow = initialOddRowPos; oddRow < finalOddRowPos; oddRow += nextRow) {
                    terrain = matrix[colCounter][rowCounter];
                    upper = upperThingsMatrix[colCounter][rowCounter];
                    graphics.drawImage(new Image(images[terrain]), col, oddRow);
                    checkTerrains(terrain, upper, col, oddRow, colCounter, rowCounter);
                    if (upper == 3) {
                        oddRow = oddRow + realocate;
                        returnToNormal = true;
                    }
                    if (returnToNormal) {
                        oddRow = oddRow - realocate;
                        returnToNormal = false;
                    }
                    graphics.drawImage(new Image(upperThingsImages[upper]), col + realocateCol, oddRow + realocateRow);
                    rowCounter++;
                }
            }
            colCounter++;
            AudioPlayer.playTileMap();}
    }




    private static void checkTerrains(int actualTerrain, int upperActualTerrain, int x, int y, int col, int fil) {
        if (actualTerrain >= 0 && actualTerrain <=2 && player[0].getX() == x && player[0].getY() == y){
            graphics.drawImage(new Image(images[0]), 725, 350);

        }
        if (upperActualTerrain == 2 && player[0].getX() == x && player[0].getY() == y){
            graphics.drawImage(new Image(upperThingsImages[upperActualTerrain]), 803, 350);
        }

        if (actualTerrain >= 3 && actualTerrain <=5 && player[0].getX() == x && player[0].getY() == y){
            graphics.drawImage(new Image(images[actualTerrain]), 725, 350);
        }


        if(actualTerrain >= 6 && actualTerrain <=8){

           if (player[0].getX() == x && player[0].getY() == y){
               player[0].setX(Gameplay.getPreviusX());
               player[0].setY(Gameplay.getPreviusY());
               Gameplay.setActionPoints(Gameplay.getActionPoints()+1);
           }
        }
           if(upperActualTerrain == 3 && player[0].getX() == x && player[0].getY() == y){
               graphics.drawImage(new Image(upperThingsImages[upperActualTerrain]), 803, 350);

           }
        if (upperActualTerrain == 1 && player[0].getX() == x && player[0].getY() == y){
            graphics.drawImage(new Image(upperThingsImages[upperActualTerrain]), 803, 350);
        }

        applyTerrainEffects(player,actualTerrain,x,y);




      }

      public static void setPlayer(PlayerCharacter[] player){
        TileMap.player = player;}
    }



