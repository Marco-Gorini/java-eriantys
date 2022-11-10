package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.Player;

public class Character4 {
    private int costOfActivation = 1;
    private boolean isInGame = false;

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    /**
     * This method increments of 2 the number of movements a player can make, based on played assistant this turn
     * @param p: player that is activating the effect
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     */
    public void activateTheEffect(Player p) throws NotEnoughMoneyException, CharacterNotInGameException {
        if(p.getNumberOfCoins() >= costOfActivation && isInGame){
            p.getPlayedAssistantThisTurn().setMotherNatureMovements(p.getPlayedAssistantThisTurn().getMotherNatureMovements() + 2);
            p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
            costOfActivation ++ ;
        }
        else{
            if(p.getNumberOfCoins() < costOfActivation){
                throw new NotEnoughMoneyException("You have not enough money to play this Character.");
            }
            else{
                throw new CharacterNotInGameException("Selected Character is not in game");
            }
        }
    }
}
