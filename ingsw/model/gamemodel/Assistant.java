package it.polimi.ingsw.model.gamemodel;

public class Assistant {
    private int turnHeavyness;
    private int motherNatureMovements;

    /**
     * This constructor is for setting all the values of the assistants at the beginning, in particular in the method inizializateGame()
     * in the Game class.
     * @param turnHeavyness: it's for calculate who play first, and who move first.
     * @param motherNatureMovements: it's for the maximum mother nature movement, that is written on the card
     */
    public Assistant(int turnHeavyness,int motherNatureMovements) {
        this.motherNatureMovements = motherNatureMovements;
        this.turnHeavyness = turnHeavyness;
    }

    public int getTurnHeavyness() {return turnHeavyness;}
    public void setTurnHeavyness(int turnHeavyness) {this.turnHeavyness = turnHeavyness;}

    public int getMotherNatureMovements() {return motherNatureMovements;}
    public void setMotherNatureMovements(int motherNatureMovements) {this.motherNatureMovements = motherNatureMovements;}
}

