package it.polimi.ingsw.controller.communication;


import it.polimi.ingsw.model.gamemodel.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    int port = 30000;
    ServerSocket server;
    Socket connection;
    int numberOfConnectedPlayers = 0;
    List<ClientHandler> clientHandlerList = new ArrayList<>();

    /**
     * This method waits for connection from clients and then create a new Thread to admit client's connection on the
     * same port. If there is already a client connected that have to set the game, or that have to set his nickname,
     * the client will wait until will be his moment.
     * @throws IOException
     */
    public void startGame() throws IOException{
        server = new ServerSocket(port);
        do {
            while(Game.getInstance().getBoard().getPlayersInGame().size() != numberOfConnectedPlayers && numberOfConnectedPlayers != 0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connection = server.accept();
            ClientHandler clientHandler = new ClientHandler(connection,numberOfConnectedPlayers,this);
            clientHandler.start();
            clientHandlerList.add(clientHandler);
            numberOfConnectedPlayers++;
        } while(Game.getInstance().getNumberOfPlayersInGame() == 5 ||
                numberOfConnectedPlayers < Game.getInstance().getNumberOfPlayersInGame());

    }

    public void stop() throws IOException {
        server.close();
    }

    public void notifyDisconnection(){
        try{
            server.close();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
