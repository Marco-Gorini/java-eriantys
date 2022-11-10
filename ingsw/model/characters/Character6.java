package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;

public class Character6 {
    private int costOfActivation = 3;
    private boolean isInGame = false;

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    /**
     * This method set true the attribute on chosen Island, wich with influence's point given by towers are not assigned
     * @param p: player that is activating the effect
     * @param indexOfchosenIsland: chosen index of island chosen from client
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     */
    public void activateTheEffect(Player p, int indexOfchosenIsland) throws NotEnoughMoneyException, CharacterNotInGameException {
        if(p.getNumberOfCoins() >= costOfActivation && isInGame){
            Game.getInstance().getBoard().getIslandsInGame().get(indexOfchosenIsland).setCharacter6Effect(true);
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
