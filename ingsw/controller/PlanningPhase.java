package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.GUI.MessageName;
import it.polimi.ingsw.controller.communication.ClientHandler;
import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.controller.communication.CommandParser;
import it.polimi.ingsw.controller.communication.commands.PlayAssistantCommand;
import it.polimi.ingsw.exception.CommandNotFoundException;
import it.polimi.ingsw.exception.GameException;
import it.polimi.ingsw.exception.SyntaxErrorException;
import it.polimi.ingsw.model.gamemodel.Game;

import java.io.IOException;

public class PlanningPhase {
    final ClientHandler clientHandler;
    final int playerIndex;


    public PlanningPhase(ClientHandler clientHandler, int playerIndex) {
        this.clientHandler = clientHandler;
        this.playerIndex = playerIndex;
    }

    /**
     * This method takes the command from player to play an assistant, and if there is an error in the command, it sends
     * again the string with instructions. Then, if the command is correct, this method try to execute the command.It stops the client
     * if it's not his turn, and every player that correctly play an assistant, is removed from the orderedListOfPlayersThat
     * HaveToPlayAssistant. If the client is waiting because it's not his turn, ping messages are sent to eventually
     * check an exception in the BufferedReader and to stop the game.
     * @throws IOException
     */
    public void runPhase() throws IOException {
        if (Game.getInstance().isFirstTurn()) {
            while (Game.getInstance().getRandomOrderedListOfPlayersThatHaveToPlayAssistant().get(0).getPlayerIndex() != playerIndex){
                try{
                    String ping = Game.getInstance().buildGameState(MessageName.PING.getValue());
                    clientHandler.write(ping);
                    String responseToPing = clientHandler.read();
                    String confirmToClient = Game.getInstance().buildGameState(MessageName.CONFIRMPING.getValue());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boolean correctPlayAssistant = false;
            do {
                String request = Game.getInstance().buildGameState(MessageName.PLAYASSISTANTREQUEST.getValue());
                clientHandler.write(request);
                String command = clientHandler.read();
                Command parse = null;
                try {
                    parse = CommandParser.parse(command, playerIndex);
                    if (parse instanceof PlayAssistantCommand) {
                        parse.execute();
                        String state = Game.getInstance().buildGameState(MessageName.CONFIRMASSISTANTPLAYED.getValue());
                        clientHandler.write(state);
                        correctPlayAssistant = true;
                    } else {
                        throw new SyntaxErrorException("Command not valid.");
                    }
                } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                    clientHandler.write(e.getMessage());
                }
            } while (!correctPlayAssistant);
            while (Game.getInstance().getRandomOrderedListOfPlayersThatHaveToPlayAssistant().size() != 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Game.getInstance().setFirstTurn(false);
        }
        else {
            Game.getInstance().orderedListOfPlayersThatHaveToPlayAssistant();
            while (Game.getInstance().indexOfPlayerThatPlayAssistantFirst() != playerIndex) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boolean correctPlayAssistant = false;
            do {
                String request = Game.getInstance().buildGameState(MessageName.PLAYASSISTANTREQUEST.getValue());
                clientHandler.write(request);
                String command = clientHandler.read();
                Command parse = null;
                try {
                    parse = CommandParser.parse(command,playerIndex);
                    if (parse instanceof PlayAssistantCommand) {
                        parse.execute();
                        String state = Game.getInstance().buildGameState(MessageName.CONFIRMASSISTANTPLAYED.getValue());
                        clientHandler.write(state);
                        correctPlayAssistant = true;

                    } else {
                        throw new SyntaxErrorException("Command not valid.");
                    }
                } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                    clientHandler.write(e.getMessage());
                }
            } while (!correctPlayAssistant);
            while (Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().size() != 0) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
