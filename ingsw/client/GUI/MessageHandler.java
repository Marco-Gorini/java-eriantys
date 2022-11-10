package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.GUI.WindowsController.Scene1Controller;
import it.polimi.ingsw.model.gamemodel.GameState;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Scanner;

public class MessageHandler {
    Scanner scanner = new Scanner(System.in);
    Client client;

    public MessageHandler(Client client) {
        this.client = client;
    }

    /**
     * This method processes every message that receive from server, so if the message is a response from server, it simply
     * print it, else asks the client an input to send to the server.It implements MVC.
     * @param gameState
     */
    public void messageParser(GameState gameState) throws IOException {
        String command = gameState.getMessage();
        if(!client.isGui()){
            if(command.equals(MessageName.PING.getValue())){
                client.getOut().println("Yes");
            }
            if(command.equals(MessageName.SETUPREQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.NICKNAMENOTEAM.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
                System.out.println("Ok,waiting for other players.");
            }
            if(command.equals(MessageName.NICKNAMETEAMSBLACK.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
                System.out.println("Ok,waiting for other players.");
            }
            if(command.equals(MessageName.NICKNAMETEAMSWHITE.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
                System.out.println("Ok,waiting for other players.");
            }
            if(command.equals(MessageName.PLAYASSISTANTREQUEST.getValue())){
                System.out.println(client.printState(gameState));
                StringBuilder builder = new StringBuilder();
                builder.append("\n");
                builder.append("Played assistant this turn (represented in turn's heavyness): ");
                builder.append("\n");
                for(Integer i: client.getGameState().getPlayedAssistantThisTurn()){
                    builder.append(i).append(" ");
                }
                builder.append("\n");
                for(int i = 0; i < client.getGameState().getDashboards().size(); i++){
                    builder.append("").append(gameState.getDashboards().get(i).getPlayerNickname()).append("'s hand is (represented in turn heavyness):").append("\n");
                    for(int j = 0; j < gameState.getPlayersHand().get(i).size(); j++){
                        builder.append("\tIndex ").append(j).append(": ").append(gameState.getPlayersHand().get(i).get(j)).append(" ");
                    }
                    builder.append("\n");
                }
                System.out.println(builder.toString());
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTERREQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.INDEXOFCHARACTERREQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER1REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER2REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER3REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER4REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER5REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER6REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER7REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER8REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER9REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER10REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER11REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CHARACTER12REQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CANTPLAYCHARACTER.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.MOVEMOTHERNATUREREQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.HALLORISLANDREQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.STUDENTONISLANDREQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.STUDENTINHALLREQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CLOUDREQUEST.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.UNIFYISLANDCHECK.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.BUILDTOWERCHECK.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.startsWith(MessageName.ENDGAME.getValue(),26)){
                System.out.println(client.printState(gameState));
                try {
                    client.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(command.equals(MessageName.GAMESTATE.getValue())){
                System.out.println(command);
                String responseToRequest = scanner.nextLine();
                client.getOut().println(responseToRequest);
            }
            if(command.equals(MessageName.CONFIRMGAMESETUPTEAMS.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMGAMESETUP.getValue())){
                System.out.println(command);

            }
            if(command.startsWith(MessageName.CONFIRMSTARTGAMEEXPERT.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMSTARTGAME.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMASSISTANTPLAYED.getValue())){
                System.out.println(client.printState(gameState));

            }
            if(command.equals(MessageName.CONFIRMSTATE.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMBUILTEDTOWER.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMUNIFYISLANDS.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMCLOUD.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMADDISLAND.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMADDHALL.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMSTUDENTISLANDCORRECTLY.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMSTUDENTHALLCORRECTLY.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMMOTHERNATUREMOVEDCORRECTLY.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYCHARACTER.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMDONTPLAYCHARACTER.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER1.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER1.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER2.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER2.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER3.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER3.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER4.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER4.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER5.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER5.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER6.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER6.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER7.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER7.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER8.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER8.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER9.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER9.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER10.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER10.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER11.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER11.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.CONFIRMPLAYSELECTEDCHARACTER12.getValue())){
                System.out.println(command);
            }
            if(command.equals(MessageName.CONFIRMCHARACTER12.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.startsWith(MessageName.ADJOURNEDGAMESTATE.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.equals(MessageName.STARTTURN.getValue())){
                System.out.println(client.printState(gameState));
            }
            if(command.startsWith("Game is over. The winner is: ")){
                System.out.println(command);
                client.stop();
            }
        }
    }
}
