package it.polimi.ingsw.controller.communication.commands;

import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.exception.GameException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;

public class ChoseCloudCommand implements Command {
    int playerIndex;
    int cloudIndex;

    public ChoseCloudCommand(int cloudIndex, int playerIndex) {
        this.cloudIndex = cloudIndex;
        this.playerIndex = playerIndex;
    }

    /**
     * This method refill students in entrance, based on index of cloud the player chose.
     * The client has to wait that the changing state of the board is notified to all the clients that are waiting
     * @throws GameException: GameException extends Exception, then all the exception of the game extend GameException
     */
    @Override
    public void execute() throws GameException, InterruptedException {
        for (Player p : Game.getInstance().getBoard().getPlayersInGame()) {
            if (p.getPlayerIndex() == playerIndex) {
                p.getDashboard().addStudentsInEntrance(cloudIndex);
            }
        }
        Game.getInstance().getOrderedListOfPlayersThatHaveToMove().remove(0);
        if(Game.getInstance().getOrderedListOfPlayersThatHaveToMove().size() == 0){
            Game.getInstance().getBoard().putStudentsOnCloud();
        }
    }
}
