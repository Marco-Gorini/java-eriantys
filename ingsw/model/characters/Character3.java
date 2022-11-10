package it.polimi.ingsw.model.characters;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;

public class Character3 {
    private int costOfActivation = 3;
    private boolean isInGame = false;

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    /**
     * This method build a tower on a chosen island, paying attention that there is not a prohibition card and the
     * player has enough influence (these parts are checked from buildTower method of Island)
     * @param p: player that is activating the effect
     * @param indexOfchosenIsland: chosen index of island chosen from client
     * @throws CharacterNotInGameException: character is not activated in this game
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws ProhibitionCardOnIslandException: buildTower exception
     * @throws MotherNatureNotOnIslandException: buildTower exception
     * @throws NotEnoughInfluenceException: buildTower exception
     */
    public void activateTheEffect (Player p, int indexOfchosenIsland) throws CharacterNotInGameException, NotEnoughMoneyException, ProhibitionCardOnIslandException, MotherNatureNotOnIslandException, NotEnoughInfluenceException, AlreadyOwnIslandException {
        if (p.getNumberOfCoins() >= costOfActivation && isInGame){
            Game.getInstance().getBoard().getIslandsInGame().get(indexOfchosenIsland).setCharacter3Effect(true);
            p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
            costOfActivation++;
            Game.getInstance().getBoard().getIslandsInGame().get(indexOfchosenIsland).buildTower(p);
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
