package it.polimi.ingsw.controller.communication;

import it.polimi.ingsw.exception.*;

public interface Command {
    void execute() throws GameException, IndexOfIslandOutOfBound, InterruptedException;
}
