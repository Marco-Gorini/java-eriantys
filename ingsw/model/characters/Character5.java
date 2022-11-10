package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.AlreadyProhibitionCardException;
import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NoMoreProhibitionCardException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;

public class Character5 {
    private int costOfActivation = 2;
    private int numberOfPrhoibitionCard = 4;
    private boolean isInGame = false;

    public void setNumberOfPrhoibitionCard(int numberOfPhoibitionCard) {this.numberOfPrhoibitionCard = numberOfPhoibitionCard;}
    public int getNumberOfPrhoibitionCard() {return numberOfPrhoibitionCard;}

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    /**
     * This method puts a prohibition card on the chosen island from client, then decrementate starting number of prohibition
     * cards on this character
     * @param p: player that is activating the effect
     * @param indexOfchosenIsland: chosen index of island chosen from client
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     * @throws NoMoreProhibitionCardException: there are not prohibition card on the character
     * @throws AlreadyProhibitionCardException: there is already a prohibition card on the chosen island
     */
    public void activateTheEffect(Player p, int indexOfchosenIsland) throws NotEnoughMoneyException, CharacterNotInGameException, NoMoreProhibitionCardException, AlreadyProhibitionCardException {
        if(p.getNumberOfCoins() >= costOfActivation && numberOfPrhoibitionCard > 0 && isInGame){
            if(!Game.getInstance().getBoard().getIslandsInGame().get(indexOfchosenIsland).isHasProhibitionCard()){
                Game.getInstance().getBoard().getIslandsInGame().get(indexOfchosenIsland).setHasProhibitionCard(true);
                numberOfPrhoibitionCard --;
                p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
                costOfActivation ++;
            }
            else{
                throw new AlreadyProhibitionCardException("Island already have a prohibition card.");
            }
        }
        else{
            if(p.getNumberOfCoins() < costOfActivation){
                throw new NotEnoughMoneyException("You have not enough money to play this Character.");
            }
            if(p.getNumberOfCoins() >=costOfActivation &&!isInGame) {
                throw new CharacterNotInGameException("Selected Character is not in game");
            }
            if(numberOfPrhoibitionCard <= 0){
                throw new NoMoreProhibitionCardException("Character has no more prohibition card");
            }
        }
    }
}
