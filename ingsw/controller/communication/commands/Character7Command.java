package it.polimi.ingsw.controller.communication.commands;

import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.PawnColor;
import it.polimi.ingsw.model.gamemodel.Player;
import java.util.List;

public class Character7Command implements Command {
    int playerIndex;
    List<PawnColor> studentsFromCharacter;
    List<PawnColor> studentsFromEntrance;

    public Character7Command(List<PawnColor> studentsFromCharacter, List<PawnColor> studentsFromEntrance, int playerIndex) {
        this.studentsFromCharacter = studentsFromCharacter;
        this.studentsFromEntrance = studentsFromEntrance;
        this.playerIndex = playerIndex;
    }

    /**
     * This method activate Character7 effect
     * The client has to wait that the changing state of the board is notified to all the clients that are waiting
     * @throws GameException: GameException extends Exception, then all the exception of the game extend GameException
     */
    @Override
    public void execute() throws GameException, InterruptedException {
        for (Player p : Game.getInstance().getBoard().getPlayersInGame()) {
            if (p.getPlayerIndex() == playerIndex) {
                Game.getInstance().getBoard().getCharacter7().activateTheEffect(p, studentsFromEntrance, studentsFromCharacter);
            }
        }
    }
}
