package it.polimi.ingsw.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.client.GUI.MessageName;
import it.polimi.ingsw.controller.communication.ClientHandler;
import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.controller.communication.CommandParser;
import it.polimi.ingsw.controller.communication.commands.*;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamemodel.*;

import java.io.IOException;

public class ActionPhase {
    final ClientHandler clientHandler;
    final int playerIndex;

    public ActionPhase(ClientHandler clientHandler, int playerIndex) {
        this.clientHandler = clientHandler;
        this.playerIndex = playerIndex;
    }

    /**
     * This method run all the action phase, so it stops the client if it's not his turn, allows the first player to play,
     * and then when he chose the island, remove the player from orderedList, and allows the other clients to play.Then when
     * all the players have played, refill the clouds, and eventually set false the character9 effect, that is valid only
     * for a single turn.In the end, I simply calculate an int and call the method endGameChecker. If there is a winning condition
     * like emptyBag or emptyHand,the method endGameChecker will set isGameOver as true, and then the loop of turns
     * will be stopped in client Handler run.If there is an immediate winning condition (like towers = 0, or islands in game = 3),server
     * will send the winning player nickname to all clients (for example, i am playing with this thread
     * and i builted all my towers.In the method checkBuildTowers.I am checking an ending condition with endGameChecker()
     * after building a tower: if there is a winner, I enter in the if branch, while other clients
     * that are waiting in the loop, will exit the loop and enters in the if branch under the loop, so they will receive
     * the string of winning player.
     * During the while cycle, the client that is waiting for his turn has to receive ping messages and respondes.If the
     * response is null, there is an exception in the BufferedReader and the server is closed.
     *
     * @throws IOException
     */
    public void runPhase() throws IOException, InterruptedException {
        Game.getInstance().orderedListOfPlayersThatHaveToMove();
        while (Game.getInstance().indexOfplayerThatMoveFirst() != playerIndex && !Game.getInstance().isGameOver()) {
            try {
                String ping = Game.getInstance().buildGameState(MessageName.PING.getValue());
                clientHandler.write(ping);
                String responseToPing = clientHandler.read();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (Game.getInstance().getNumberOfPlayersInGame() != 4 && Game.getInstance().isGameOver()) {
            clientHandler.write(MessageName.ENDGAME.getValue() + Game.getInstance().winningPlayer());
            clientHandler.close();
        } else if (Game.getInstance().getNumberOfPlayersInGame() == 4 && Game.getInstance().isGameOver()) {
            clientHandler.write(Game.getInstance().endGameCheckerTeams());
            clientHandler.close();
        }
        String builder = Game.getInstance().buildGameState(MessageName.STARTTURN.getValue());
        clientHandler.write(builder);
        selectCharacter();
        moveStudents();
        moveMotherNature();
        buildTowerChecker();
        unifyIslandChecker();
        choseCloud();
        if (Game.getInstance().getBoard().getIslandsInGame().get(0).isCharacter9Effect()) {
            for (Island i : Game.getInstance().getBoard().getIslandsInGame()) {
                i.setCharacter9Effect(false);
            }
        }
        if (Game.getInstance().getNumberOfPlayersInGame() != 4) {
            int indexOfWinningPlayer = Game.getInstance().endGameChecker();
        } else if (Game.getInstance().getNumberOfPlayersInGame() == 4) {
            String winningTeam = Game.getInstance().endGameCheckerTeams();
        }
    }

    /**
     * This method is called after moveMotherNature() in runPhase() automatically, and checks if the player can build a tower on the island,
     * then build it automatically, then I check that there is not a winner, and if there is, I close my connection to the client and send a message with
     * winning player's nickname
     *
     * @throws IOException
     */
    public void buildTowerChecker() throws IOException, InterruptedException {
        String request = Game.getInstance().buildGameState(MessageName.BUILDTOWERCHECK.getValue());
        clientHandler.write(request);
        String receivedMessage = clientHandler.read();
        try {
            Game.getInstance().getBoard().getIslandsInGame().get(Game.getInstance().getBoard().getMotherNaturePosition()).buildTower(Game.getInstance().getBoard().getPlayersInGame().get(playerIndex));
            String state = Game.getInstance().buildGameState(MessageName.CONFIRMBUILTEDTOWER.getValue());
            clientHandler.write(state);
        } catch (ProhibitionCardOnIslandException | MotherNatureNotOnIslandException | NotEnoughInfluenceException | AlreadyOwnIslandException | JsonProcessingException e) {
            clientHandler.write(e.getMessage());
        }
        if (Game.getInstance().getNumberOfPlayersInGame() != 4 && Game.getInstance().endGameChecker() != 5) {
            String endGame = Game.getInstance().buildGameState("Game is over. The winner is: " + Game.getInstance().winningPlayer());
            clientHandler.write(endGame);
            clientHandler.close();
        } else if (Game.getInstance().getNumberOfPlayersInGame() == 4 && !Game.getInstance().endGameCheckerTeams().equals("")) {
            String endGame = Game.getInstance().buildGameState(Game.getInstance().endGameCheckerTeams());
            clientHandler.write(endGame);
            clientHandler.close();
        }
    }

    /**
     * This method is called after buildTowerChecker() in runPhase() automatically, and checks if there are islands that could be unified,
     * then unify them automatically;
     *
     * @throws IOException
     */
    public void unifyIslandChecker() throws IOException{
        String request = Game.getInstance().buildGameState(MessageName.UNIFYISLANDCHECK.getValue());
        clientHandler.write(request);
        String casualString = clientHandler.read();
        if (Game.getInstance().getBoard().getMotherNaturePosition() == Game.getInstance().getBoard().getIslandsInGame().size() - 1) {
            Game.getInstance().getBoard().unifyIsland(Game.getInstance().getBoard().getIslandsInGame().size() - 1, 0);
            Game.getInstance().getBoard().unifyIsland(Game.getInstance().getBoard().getIslandsInGame().size() - 1, Game.getInstance().getBoard().getIslandsInGame().size() - 2);
        } else if (Game.getInstance().getBoard().getMotherNaturePosition() == 0) {
            Game.getInstance().getBoard().unifyIsland(0, 1);
            Game.getInstance().getBoard().unifyIsland(0, Game.getInstance().getBoard().getIslandsInGame().size() - 1);
        } else {
            Game.getInstance().getBoard().unifyIsland(Game.getInstance().getBoard().getMotherNaturePosition(), Game.getInstance().getBoard().getMotherNaturePosition() - 1);
            Game.getInstance().getBoard().unifyIsland(Game.getInstance().getBoard().getMotherNaturePosition(), Game.getInstance().getBoard().getMotherNaturePosition() + 1);
        }
        String state = Game.getInstance().buildGameState(MessageName.CONFIRMUNIFYISLANDS.getValue());
        clientHandler.write(state);
        if (Game.getInstance().getNumberOfPlayersInGame() != 4 && Game.getInstance().endGameChecker() != 5) {
            String endGame = Game.getInstance().buildGameState("Game is over. The winner is: " + Game.getInstance().winningPlayer());
            clientHandler.write(endGame);
            clientHandler.close();
        } else if (Game.getInstance().getNumberOfPlayersInGame() == 4 && !Game.getInstance().endGameCheckerTeams().equals("")) {
            String endGame = Game.getInstance().buildGameState(Game.getInstance().endGameCheckerTeams());
            clientHandler.write(endGame);
            clientHandler.close();
        }
    }

    /**
     * This method allows the player to insert the command to chose a cloud wich with he can refill his entrance
     * @throws IOException
     */
    public void choseCloud() throws IOException, InterruptedException {
        boolean correctlychosedCloud = false;
        do {
            String request = Game.getInstance().buildGameState(MessageName.CLOUDREQUEST.getValue());
            clientHandler.write(request);
            String command = clientHandler.read();
            Command parse = null;
            try {
                parse = CommandParser.parse(command, playerIndex);
                if (parse instanceof ChoseCloudCommand) {
                    parse.execute();
                    String state = Game.getInstance().buildGameState(MessageName.CONFIRMCLOUD.getValue());
                    clientHandler.write(state);
                    correctlychosedCloud = true;
                } else {
                    throw new SyntaxErrorException("Command not valid.");
                }
            } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                clientHandler.write(e.getMessage());
            }
        } while (!correctlychosedCloud);
        while (Game.getInstance().getOrderedListOfPlayersThatHaveToMove().size() != 0) {
            String ping = Game.getInstance().buildGameState(MessageName.PING.getValue());
            clientHandler.write(ping);
            String responseToPing = clientHandler.read();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method allows the player to move students in hall or entrance.
     *
     * @throws IOException
     */
    public void moveStudents() throws IOException {
        int numberOfStudentsToMove;
        if (Game.getInstance().getNumberOfPlayersInGame() != 4) {
            numberOfStudentsToMove = Game.getInstance().getBoard().getPlayersInGame().size() + 1;
        } else {
            numberOfStudentsToMove = 3;
        }
        for (int i = 0; i < numberOfStudentsToMove; i++) {
            boolean correctlyMovedStudent = false;
            boolean correctlyInsertedPlace = false;
            String chosedPlace = "";
            do {
                String request = Game.getInstance().buildGameState(MessageName.HALLORISLANDREQUEST.getValue());
                clientHandler.write(request);
                chosedPlace = clientHandler.read();
                if (chosedPlace.equalsIgnoreCase("island")) {
                    String response = Game.getInstance().buildGameState(MessageName.CONFIRMADDISLAND.getValue());
                    clientHandler.write(response);
                    correctlyInsertedPlace = true;
                } else if (chosedPlace.equalsIgnoreCase("hall")) {
                    String response = Game.getInstance().buildGameState(MessageName.CONFIRMADDHALL.getValue());
                    clientHandler.write(response);
                    correctlyInsertedPlace = true;
                } else {
                    try {
                        throw new SyntaxErrorException("Command not valid.");
                    } catch (SyntaxErrorException e) {
                        clientHandler.write(e.getMessage());
                    }
                }
            } while (!correctlyInsertedPlace);
            if (chosedPlace.equalsIgnoreCase("island")) {
                do {
                    String request = Game.getInstance().buildGameState(MessageName.STUDENTONISLANDREQUEST.getValue());
                    clientHandler.write(request);
                    String command = clientHandler.read();
                    Command parse = null;
                    try {
                        parse = CommandParser.parse(command, playerIndex);
                        parse.execute();
                        String state = Game.getInstance().buildGameState(MessageName.CONFIRMSTUDENTISLANDCORRECTLY.getValue());
                        clientHandler.write(state);
                        correctlyMovedStudent = true;
                    } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                        clientHandler.write(e.getMessage());
                    }

                } while (!correctlyMovedStudent);
            }
            if (chosedPlace.equalsIgnoreCase("hall")) {
                do {
                    String request = Game.getInstance().buildGameState(MessageName.STUDENTINHALLREQUEST.getValue());
                    clientHandler.write(request);
                    String command = clientHandler.read();
                    Command parse = null;
                    try {
                        parse = CommandParser.parse(command, playerIndex);
                        Game.getInstance().getBoard().checkRedTeacher();
                        Game.getInstance().getBoard().checkBlueTeacher();
                        Game.getInstance().getBoard().checkPinkTeacher();
                        Game.getInstance().getBoard().checkYellowTeacher();
                        Game.getInstance().getBoard().checkGreenTeacher();
                        parse.execute();
                        String state = Game.getInstance().buildGameState(MessageName.CONFIRMSTUDENTHALLCORRECTLY.getValue());
                        clientHandler.write(state);
                        correctlyMovedStudent = true;
                    } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                        clientHandler.write(e.getMessage());
                    }

                } while (!correctlyMovedStudent);
            }
            if (!clientHandler.isAlreadyActivatedCharacter()) {
                selectCharacter();
            }
        }
    }

    /**
     * This method allows the player to insert the command to move mother nature, then checks if the player can build a tower
     * and eventually if there are islands that can be unified. It also checks if the player can activate an effect, because for
     * example: the game starts, the players says that he wants to play a character, but he has not enought money to play
     * any character. If i don't check if he can effectivly activate an effect, a player would be in an infinite loop,continuing
     * to throws NotEnoughMoneyException
     * @throws IOException
     */
    public void moveMotherNature() throws IOException {
        boolean correctlyMotherNatureMoved = false;
        do {
            String request = Game.getInstance().buildGameState(MessageName.MOVEMOTHERNATUREREQUEST.getValue());
            clientHandler.write(request);
            String command = clientHandler.read();
            Command parse = null;
            try {
                parse = CommandParser.parse(command, playerIndex);
                if (parse instanceof MoveMotherNatureCommand) {
                    parse.execute();
                    String state = Game.getInstance().buildGameState(MessageName.CONFIRMMOTHERNATUREMOVEDCORRECTLY.getValue());
                    clientHandler.write(state);
                    correctlyMotherNatureMoved = true;
                } else {
                    throw new SyntaxErrorException("Command not valid.");
                }
            } catch (CommandNotFoundException | GameException | SyntaxErrorException | InterruptedException e) {
                clientHandler.write(e.getMessage());
            }
        } while (!correctlyMotherNatureMoved);
        if (!clientHandler.isAlreadyActivatedCharacter()) {
            selectCharacter();
        }
    }

    /**
     * This method allows the player to activate character's effect: he has to insert a number, and based on number he inserted
     * the character will be activated.
     * The client has to wait that the changing state of the board is notified to all the clients that are waiting
     * @throws IOException
     */
    public void selectCharacter() throws IOException {
        if (Game.getInstance().getGameVariant() == Variant.EXPERT) {
            boolean correctConfirm = false;
            String confirm = "";
            do {
                String state = Game.getInstance().buildGameState(MessageName.CHARACTERREQUEST.getValue());
                clientHandler.write(state);
                confirm = clientHandler.read();
                if (confirm.equalsIgnoreCase("y")) {
                    String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYCHARACTER.getValue());
                    clientHandler.write(response);
                    correctConfirm = true;
                } else if (confirm.equalsIgnoreCase("n")) {
                    String response = Game.getInstance().buildGameState(MessageName.CONFIRMDONTPLAYCHARACTER.getValue());
                    clientHandler.write(response);
                    return;
                } else {
                    try {
                        throw new SyntaxErrorException("Command not valid.");
                    } catch (SyntaxErrorException e) {
                        clientHandler.write(e.getMessage());
                    }
                }
            } while (!correctConfirm);
            boolean canEffectivlyPlayOneCharacter = false;
            for (Player p : Game.getInstance().getBoard().getPlayersInGame()) {
                if (p.getPlayerIndex() == playerIndex) {
                    if (Game.getInstance().getBoard().getCharacter1().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter1().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter2().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter2().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter3().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter3().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter4().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter4().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter5().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter5().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter6().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter6().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter7().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter7().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter8().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter8().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter9().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter9().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter10().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter10().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter11().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter11().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                    if (Game.getInstance().getBoard().getCharacter12().isInGame() && p.getNumberOfCoins() >= Game.getInstance().getBoard().getCharacter12().getCostOfActivation()) {
                        canEffectivlyPlayOneCharacter = true;
                    }
                }
            }
            if (canEffectivlyPlayOneCharacter) {
                boolean correctCharacterIndex = false;
                do {
                    String reque = Game.getInstance().buildGameState(MessageName.INDEXOFCHARACTERREQUEST.getValue());
                    clientHandler.write(reque);
                    String characterToPlay = clientHandler.read();
                    if (characterToPlay.equals("1")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER1.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER1REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character1Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER1.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                        }
                    } else if (characterToPlay.equals("2")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER2.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER2REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character2Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER2.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                            break;
                        }
                    } else if (characterToPlay.equals("3")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER3.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER3REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character3Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER3.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                            break;
                        }
                    } else if (characterToPlay.equals("4")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER4.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER4REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character4Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER4.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                        }
                    } else if (characterToPlay.equals("5")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER5.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER5REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character5Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER5.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                        }
                    } else if (characterToPlay.equals("6")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER6.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER6REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character6Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER6.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                        }
                    } else if (characterToPlay.equals("7")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER7.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER7REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character7Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER7.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                        }
                    } else if (characterToPlay.equals("8")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER8.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER8REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character8Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER8.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                        }
                    } else if (characterToPlay.equals("9")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER9.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER9REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character9Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER9.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                        }
                    } else if (characterToPlay.equals("10")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER10.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER10REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character10Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER10.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                            break;
                        }
                    } else if (characterToPlay.equals("11")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER11.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER11REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character11Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER11.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                        }
                    } else if (characterToPlay.equals("12")) {
                        String response = Game.getInstance().buildGameState(MessageName.CONFIRMPLAYSELECTEDCHARACTER12.getValue());
                        clientHandler.write(response);
                        String request = Game.getInstance().buildGameState(MessageName.CHARACTER12REQUEST.getValue());
                        clientHandler.write(request);
                        String command = clientHandler.read();
                        Command parse = null;
                        try {
                            parse = CommandParser.parse(command, playerIndex);
                            if (parse instanceof Character12Command) {
                                parse.execute();
                                String state = Game.getInstance().buildGameState(MessageName.CONFIRMCHARACTER12.getValue());
                                clientHandler.write(state);
                                clientHandler.setAlreadyActivatedCharacter(true);
                                correctCharacterIndex = true;
                            } else {
                                throw new SyntaxErrorException("Command not valid.");
                            }
                        } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                            clientHandler.write(e.getMessage());
                            break;
                        }
                    } else {
                        try {
                            throw new SyntaxErrorException("Command not valid.");
                        } catch (SyntaxErrorException e) {
                            clientHandler.write(e.getMessage());
                        }
                    }
                } while (!correctCharacterIndex);

            } else {
                String refuse = Game.getInstance().buildGameState(MessageName.CANTPLAYCHARACTER.getValue());
                clientHandler.write(refuse);
                clientHandler.read();
                String responseToRefuse = Game.getInstance().buildGameState(MessageName.CONFRIMTOGOONCHARACTER.getValue());
                clientHandler.write(responseToRefuse);
                return;
            }
        }
    }
}

