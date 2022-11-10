package it.polimi.ingsw.controller.communication.commands;

import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Variant;

public class GameSetUpCommand implements Command {
    int numberOfChoosenPlayer;
    Variant variant;

    public GameSetUpCommand(int numberOfChoosenPlayer, Variant variant) {
        this.numberOfChoosenPlayer = numberOfChoosenPlayer;
        this.variant = variant;
    }

    /**
     * This method set the game attributes at the start of the game
     */
    @Override
    public void execute() {
        Game.getInstance().setGameVariant(variant);
        Game.getInstance().setNumberOfPlayersInGame(numberOfChoosenPlayer);
    }
}
