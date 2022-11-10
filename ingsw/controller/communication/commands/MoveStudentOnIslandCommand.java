package it.polimi.ingsw.controller.communication.commands;

import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.exception.GameException;
import it.polimi.ingsw.exception.IndexOfIslandOutOfBound;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.PawnColor;
import it.polimi.ingsw.model.gamemodel.Player;

public class MoveStudentOnIslandCommand implements Command {
    int playerIndex;
    int islandIndex;
    PawnColor pawnColor;

    public MoveStudentOnIslandCommand(PawnColor pawnColor, int islandIndex, int playerIndex) {
        this.pawnColor = pawnColor;
        this.islandIndex = islandIndex;
        this.playerIndex = playerIndex;
    }

    /**
     * This method allows the player to put chosen student on chosen island.
     * The client has to wait that the changing state of the board is notified to all the clients that are waiting.
     * @throws GameException: GameException extends Exception, then all the exception of the game extend GameException
     */
    @Override
    public void execute() throws GameException, InterruptedException {
        for (Player p : Game.getInstance().getBoard().getPlayersInGame()) {
            if (p.getPlayerIndex() == playerIndex) {
                if(islandIndex < Game.getInstance().getBoard().getIslandsInGame().size()){
                    Game.getInstance().getBoard().getIslandsInGame().get(islandIndex).addStudentOnIsland(p, pawnColor);
                }
                else{
                    throw new IndexOfIslandOutOfBound("Index of island is wrong");
                }
            }
        }
    }
}
