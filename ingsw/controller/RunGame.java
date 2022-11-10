package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.communication.GameServer;

import java.io.IOException;

public class RunGame {

    public static void main(String[] args) throws IOException {
        GameServer gameServer = new GameServer();
        gameServer.startGame();
        System.in.read();
        gameServer.stop();
    }
}
