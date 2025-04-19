package domain.consumables;

import app.gameplayFeatures.Consumables;

public class vitality_potion extends Consumables {
    private String id = "01";
    private int healthAdded = 3;

    public int getHealthAdded() {
        return healthAdded;
    }

    public void setHealthAdded(int healthAdded) {
        this.healthAdded = healthAdded;
    }
    public vitality_potion() {
    }
}
