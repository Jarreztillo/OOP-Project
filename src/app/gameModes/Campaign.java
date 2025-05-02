package app.gameModes;

import app.gameplayFeatures.Combat;
import app.gameplayFeatures.Consumables;
import app.gameplayFeatures.Gameplay;
import app.gameplayFeatures.TileMap;
import domain.consumables.Inventory;
import domain.entities.EnemyCharacter;
// Clases locales que usa Campaign.

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
// Java FX.

import java.util.ArrayList;
import java.util.Random;

// Utilidades de Java.

import static app.gameplayFeatures.Combat.dropConsumable;
import static app.main.Roaster.Roaster.player;
import static domain.entities.EnemyCharacter.collidePlayer;
import static app.main.Game.window;
import static domain.entities.PlayerCharacter.collideEnemy;
import static app.gameplayFeatures.Combat.noRandomPosition;

// Variables estaticas importadas.

public class Campaign {


    // Variables públicas estaticas.

    private static Group root;
    private static Canvas canvas;
    private static GraphicsContext graphics;
    private static EnemyCharacter enemy;
    private static int actionPoints = 2;
    private static Random random = new Random();

    // Variables privadas.



    public static void initialize() {
        Gameplay.initializeGameplay();

    }
}
