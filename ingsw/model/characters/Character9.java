package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Island;
import it.polimi.ingsw.model.gamemodel.PawnColor;
import it.polimi.ingsw.model.gamemodel.Player;

public class Character9 {
    int costOfActivation = 3;
    private boolean isInGame = false;

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    /**
     * This method set a chosen color from player that activates it on all islands in game, so that the chosen color
     * is not considerated in checkInfluence method in Island. Then, I will set false the attribute at the end of current
     * turn
     * @param p: player that is activating the effect
     * @param chosenColor: chosen color to not consider the influence
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     */
    public void activateTheEffect(Player p, PawnColor chosenColor) throws NotEnoughMoneyException, CharacterNotInGameException {
        if(p.getNumberOfCoins() >= costOfActivation && isInGame) {
            for(Island i : Game.getInstance().getBoard().getIslandsInGame()){
                i.setCharacter9Effect(true);
                i.setchosenPawnColorFromCharacter9(chosenColor);
            }
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
