package characters;

import generalClasses.PlayerCharacter;

public class Cintya extends PlayerCharacter {
    private int health = 9999;
    private int attack = 9999;
    private boolean havesMana = false;
    private String characterName = "Cintya";
    private String imageName = "/DAO/images/inGameplayCharacters/cintya.png";
    private String closestImageName = "/DAO/images/frontCharacters/closerCintya.png";

    public String getClosestImageName() {
        return closestImageName;
    }

    public String getImageName() {
        return imageName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public boolean havesMana() {
        return havesMana;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void setAttack(int attack){
        this.attack=attack;
    }
}
