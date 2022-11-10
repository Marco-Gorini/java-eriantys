package it.polimi.ingsw.controller.communication.commands;

import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.exception.GameException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;

public class MoveMotherNatureCommand implements Command {
    int playerIndex;
    int numberOfMovements;

    public MoveMotherNatureCommand(int numberOfMovements, int playerIndex) {
        this.numberOfMovements = numberOfMovements;
        this.playerIndex = playerIndex;
    }

    /**
     * This method allows the player to move mother nature.
     * The client has to wait that the changing state of the board is notified to all the clients that are waiting
     * @throws GameException: GameException extends Exception, then all the exception of the game extend GameException
     */
    @Override
    public void execute() throws GameException, InterruptedException {
        for (Player p : Game.getInstance().getBoard().getPlayersInGame()) {
            if (p.getPlayerIndex() == playerIndex) {
                p.moveMotherNature(numberOfMovements);
            }
        }
    }
}
