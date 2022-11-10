package it.polimi.ingsw.controller.communication.commands;

import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.exception.GameException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;

public class PlayAssistantCommand implements Command {
    int indexOfAssistantToPlay;
    int indexOfPlayer;

    public PlayAssistantCommand(int indexOfPlayer, int indexOfAssistantToPlay) {
        this.indexOfPlayer = indexOfPlayer;
        this.indexOfAssistantToPlay = indexOfAssistantToPlay;
    }

    /**
     * This method allows the client to play assistant: if it's the first turn, ordered list is initializated randomly,
     * in other cases is always calculated in Planning Phase
     * @throws GameException: GameException extends Exception, then all the exception of the game extend GameException
     */
    @Override
    public void execute() throws GameException {
        for (Player p : Game.getInstance().getBoard().getPlayersInGame()) {
            if (p.getPlayerIndex() == indexOfPlayer) {
                p.playAssistant(indexOfAssistantToPlay);
                if (Game.getInstance().isFirstTurn()) {
                    Game.getInstance().getRandomOrderedListOfPlayersThatHaveToPlayAssistant().remove(0);
                    if (Game.getInstance().getRandomOrderedListOfPlayersThatHaveToPlayAssistant().size() == 0) {
                        Game.getInstance().getBoard().getPlayedAssistantThisTurn().clear();
                    }
                } else {
                    Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().remove(0);
                    if (Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().size() == 0) {
                        Game.getInstance().getBoard().getPlayedAssistantThisTurn().clear();
                    }
                }
            }
        }
    }
}
