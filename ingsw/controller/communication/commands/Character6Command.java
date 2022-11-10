package it.polimi.ingsw.controller.communication.commands;

import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.exception.GameException;
import it.polimi.ingsw.exception.IndexOfIslandOutOfBound;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;

public class Character6Command implements Command {
    int playerIndex;
    int islandIndex;

    public Character6Command(int islandIndex,int playerIndex) {
        this.islandIndex = islandIndex;
        this.playerIndex = playerIndex;
    }

    /**
     * This method activate Character6 effect
     * The client has to wait that the changing state of the board is notified to all the clients that are waiting
     * @throws GameException: GameException extends Exception, then all the exception of the game extend GameException
     */
    @Override
    public void execute() throws GameException, InterruptedException {
        for (Player p : Game.getInstance().getBoard().getPlayersInGame()) {
            if (p.getPlayerIndex() == playerIndex) {
                if(islandIndex < Game.getInstance().getBoard().getIslandsInGame().size()){
                    Game.getInstance().getBoard().getCharacter6().activateTheEffect(p, islandIndex);
                }
                else{
                    throw new IndexOfIslandOutOfBound("Index of island is wrong");
                }
            }
        }
    }
}
