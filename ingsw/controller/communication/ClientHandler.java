package it.polimi.ingsw.controller.communication;


import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.client.GUI.MessageName;
import it.polimi.ingsw.controller.communication.commands.GameSetUpCommand;
import it.polimi.ingsw.controller.communication.commands.NicknameCommand;
import it.polimi.ingsw.controller.ActionPhase;
import it.polimi.ingsw.controller.PlanningPhase;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.listeners.Observer;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Variant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread implements Observer {

    PrintWriter out;
    BufferedReader in;
    final Socket connection;
    int playerIndex;
    boolean alreadyActivatedCharacter;
    boolean gui = false;
    GameServer gameServer;

    public ClientHandler(Socket connection, int playerIndex,GameServer gs) {
        this.connection = connection;
        this.playerIndex = playerIndex;
        this.gameServer = gs;
    }


    public String read() throws IOException {
        String s = in.readLine();
        if(s == null){
            throw new IOException("A client is disconnected");
        }
        return s;
    }

    public void write(String s) {
        out.println(s);
    }

    public BufferedReader getIn() {
        return in;
    }

    public boolean isAlreadyActivatedCharacter() {
        return alreadyActivatedCharacter;
    }

    public void setAlreadyActivatedCharacter(boolean alreadyActivatedCharacter) {
        this.alreadyActivatedCharacter = alreadyActivatedCharacter;
    }

    /**
     * This method contains the Game structure, then enters in a while until the game is over. This method implements
     * the game structure from other methods and from other class, and it's composed by an ActionPhase and a PlanningPhase
     */
    @Override
    public void run() {
        Game.getInstance().addObserver(this);
        try {
            out = new PrintWriter(connection.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (playerIndex == 0) {
                gameSetup();
            }
            nicknameSet();
            while (!Game.getInstance().isInitialized()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stringToAnnounceStartGame();
            while (!Game.getInstance().isGameOver()) {
                alreadyActivatedCharacter = false;
                new PlanningPhase(this, playerIndex).runPhase();
                new ActionPhase(this, playerIndex).runPhase();
            }
            if (Game.getInstance().getNumberOfPlayersInGame() != 4) {
                String endGame = Game.getInstance().buildGameState("Game is over. The winner is " + Game.getInstance().winningPlayer());
                write(endGame);
                close();
            } else {
                String endGame = Game.getInstance().buildGameState(Game.getInstance().endGameCheckerTeams());
                write(endGame);
                close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            gameServer.notifyDisconnection();
            try {
                close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void close() throws IOException {
        in.close();
        out.close();
        connection.close();
    }

    /**
     * This method allows the first client to setup the game, checking the commands with the command parser
     *
     * @throws IOException
     */
    private void gameSetup() throws IOException {
        while (true) {
            String request = Game.getInstance().buildGameState(MessageName.SETUPREQUEST.getValue());
            write(request);
            String command = read();
            try {
                Command parse = CommandParser.parse(command, playerIndex);
                if (parse instanceof GameSetUpCommand) {
                    parse.execute();
                    String response = "";
                    if (Game.getInstance().getNumberOfPlayersInGame() == 4) {
                        response = MessageName.CONFIRMGAMESETUPTEAMS.getValue();
                    } else {
                        response = MessageName.CONFIRMGAMESETUP.getValue();
                    }
                    String resp = Game.getInstance().buildGameState(response);
                    write(resp);
                    return;
                } else {
                    throw new SyntaxErrorException("Command not valid.");
                }
            } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                write(e.getMessage());
            }
        }
    }


    /**
     * This method allows all the clients to set their nickname, checking the commands with the command parser
     *
     * @throws IOException
     */
    private void nicknameSet() throws IOException {
        while (true) {
            if (!gui) {
                String message = "";
                if (Game.getInstance().getNumberOfPlayersInGame() == 4) {
                    if (playerIndex == 0 || playerIndex == 1) {
                        message = MessageName.NICKNAMETEAMSBLACK.getValue();
                    } else if (playerIndex == 2 || playerIndex == 3) {
                        message = MessageName.NICKNAMETEAMSWHITE.getValue();
                    }
                } else {
                    message = MessageName.NICKNAMENOTEAM.getValue();
                }
                String state = Game.getInstance().buildGameState(message);
                write(state);
                String command = read();
                try {
                    Command parse = CommandParser.parse(command, playerIndex);
                    if (parse instanceof NicknameCommand) {
                        parse.execute();
                        return;
                    } else {
                        throw new SyntaxErrorException("Command not valid.");
                    }
                } catch (CommandNotFoundException | SyntaxErrorException | GameException | InterruptedException e) {
                    write(e.getMessage());
                }
            }
        }
    }

    /**
     * This method is to explains the player which character are casually initialized if GameVariant is expert, else
     * a single string that announce the game's start
     */
    public void stringToAnnounceStartGame() throws JsonProcessingException {
        if (Game.getInstance().getGameVariant() == Variant.EXPERT) {
            String characters = "";
            if (Game.getInstance().getBoard().getCharacter1().isInGame()) {
                characters = characters + "1 ";
            }
            if (Game.getInstance().getBoard().getCharacter2().isInGame()) {
                characters = characters + "2 ";
            }
            if (Game.getInstance().getBoard().getCharacter3().isInGame()) {
                characters = characters + "3 ";
            }
            if (Game.getInstance().getBoard().getCharacter4().isInGame()) {
                characters = characters + "4 ";
            }
            if (Game.getInstance().getBoard().getCharacter5().isInGame()) {
                characters = characters + "5 ";
            }
            if (Game.getInstance().getBoard().getCharacter6().isInGame()) {
                characters = characters + "6 ";
            }
            if (Game.getInstance().getBoard().getCharacter7().isInGame()) {
                characters = characters + "7 ";
            }
            if (Game.getInstance().getBoard().getCharacter8().isInGame()) {
                characters = characters + "8 ";
            }
            if (Game.getInstance().getBoard().getCharacter9().isInGame()) {
                characters = characters + "9 ";
            }
            if (Game.getInstance().getBoard().getCharacter10().isInGame()) {
                characters = characters + "10 ";
            }
            if (Game.getInstance().getBoard().getCharacter11().isInGame()) {
                characters = characters + "11 ";
            }
            if (Game.getInstance().getBoard().getCharacter12().isInGame()) {
                characters = characters + "12 ";
            }
            String state = Game.getInstance().buildGameState(MessageName.CONFIRMSTARTGAMEEXPERT.getValue() + characters + ".Game" +
                    " will continue when the casual starting player will play an assistant.");
            write(state);
        } else {
            String state = Game.getInstance().buildGameState(MessageName.CONFIRMSTARTGAME.getValue());
            write(state);
        }
    }

    @Override
    public void onInitialized() {
        System.out.println(playerIndex + " :game initialized");
    }
}
