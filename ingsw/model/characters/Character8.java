package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.Player;

public class Character8 {
    private int costOfActivation = 2;
    private boolean isInGame = false;

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    /**
     * This method set true an attribute of player that activated it, then will consider it in checkInfluence method
     * on Island
     * @param p: player that is activating the effect
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     */
    public void activateTheEffect(Player p) throws NotEnoughMoneyException, CharacterNotInGameException {
        if(p.getNumberOfCoins() >= costOfActivation && isInGame){
            p.setCharacter8Effect(true);
            p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
            costOfActivation ++;
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
