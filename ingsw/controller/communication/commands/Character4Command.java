package it.polimi.ingsw.controller.communication.commands;

import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.exception.GameException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;

public class Character4Command implements Command {
    int playerIndex;

    public Character4Command(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     * This method activate Character4 effect
     * The client has to wait that the changing state of the board is notified to all the clients that are waiting
     * @throws GameException: GameException extends Exception, then all the exception of the game extend GameException
     */
    @Override
    public void execute() throws GameException, InterruptedException {
        for (Player p : Game.getInstance().getBoard().getPlayersInGame()) {
            if (p.getPlayerIndex() == playerIndex) {
                Game.getInstance().getBoard().getCharacter4().activateTheEffect(p);
            }
        }
    }
}
