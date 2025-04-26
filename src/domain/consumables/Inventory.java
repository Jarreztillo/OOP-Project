package domain.consumables;

import app.gameplayFeatures.Consumables;

import java.util.ArrayList;

public class Inventory {
    private static boolean alreadyCreated = false;
    public static ArrayList<Consumables> createInventory(){
        vitality_potion potion = new vitality_potion();
        ArrayList<Consumables> inventory = new ArrayList<>(){};
        inventory.add(potion);
        alreadyCreated = true;
        return inventory;
    }


    public static boolean isAlreadyCreated() {
        return alreadyCreated;
    }
}
