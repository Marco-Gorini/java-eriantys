package it.polimi.ingsw.controller.communication.commands;

import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.exception.GameException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.PawnColor;
import it.polimi.ingsw.model.gamemodel.Player;

public class MoveStudentOnHallCommand implements Command {
    int playerIndex;
    PawnColor pawnColor;

    public MoveStudentOnHallCommand(PawnColor pawnColor, int playerIndex) {
        this.pawnColor = pawnColor;
        this.playerIndex = playerIndex;
    }

    /**
     * This method allows the player to add students in the hall.
     * The client has to wait that the changing state of the board is notified to all the clients that are waiting
     * @throws GameException: GameException extends Exception, then all the exception of the game extend GameException
     */
    @Override
    public void execute() throws GameException, InterruptedException {
        for (Player p : Game.getInstance().getBoard().getPlayersInGame()) {
            if (p.getPlayerIndex() == playerIndex) {
                p.getDashboard().addStudentInHall(pawnColor);
                Game.getInstance().getBoard().checkRedTeacher();
                Game.getInstance().getBoard().checkBlueTeacher();
                Game.getInstance().getBoard().checkPinkTeacher();
                Game.getInstance().getBoard().checkYellowTeacher();
                Game.getInstance().getBoard().checkGreenTeacher();
            }
        }
    }
}
