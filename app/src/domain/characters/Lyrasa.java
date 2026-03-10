package characters;

import generalClasses.PlayerCharacter;

public class Lyrasa extends PlayerCharacter {
    private int health = 10;
    private int attack = 5;
    private boolean havesMana = false;
    private String characterName = "Lyrasa";
    private String imageName = "/DAO/images/inGameplayCharacters/lyrasa.png";
    private String closestImageName = "/DAO/images/frontCharacters/closerLyrasa.png";

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
