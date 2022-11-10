package it.polimi.ingsw.client.GUI;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.model.gamemodel.Dashboard;
import it.polimi.ingsw.model.gamemodel.GameState;
import it.polimi.ingsw.model.gamemodel.TowerColor;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket connection;
    int port = 30000;
    PrintWriter out;
    BufferedReader in;
    private final ObjectMapper objectMapper;
    boolean gui = false;
    GameState gameState;
    MessageHandler messageHandler = new MessageHandler(this);
    @FXML
    private Stage stage;
    Scanner scanner = new Scanner(System.in);

    public Client() {
        this.objectMapper = new ObjectMapper();
    }

    public PrintWriter getOut() {return out;}

    public BufferedReader getIn() {return in;}

    public boolean isGui() {return gui;}
    public void setGui(boolean gui) {this.gui = gui;}

    public GameState getGameState() {return gameState;}

    /*@Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/white.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        guiReader();
    }
     */

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.startClient(args);
        } catch (IOException e) {
            if(e.getMessage().equalsIgnoreCase("connection reset")){
                System.out.println("A client is disconnected or the server goes down. The game is over.");
                System.exit(1);
            }
            else{
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }

    /**
     * This method is based n the fact that the client has to receive a request from server, have to send the response
     * in a well written string (based and what the server asks him) and then receive a response from server
     * @throws IOException
     */
    public void startClient(String[] args) throws IOException {
        boolean correctGraphic = false;
        while(!correctGraphic){
            System.out.println(MessageName.CLIREQUEST.getValue());
            String responseToRequest = scanner.nextLine();
            if(responseToRequest.equals("graphic GUI")){
                System.out.print("Ok, let's use the gui.\n");
                correctGraphic = true;
                gui = true;
            }
            else if(responseToRequest.equals("graphic CLI")){
                System.out.print("Ok, let's use the cli.\n");
                correctGraphic = true;
            }
            else{
                System.out.println("Command not found.");
            }
        }
        boolean correctIP = false;
        while(!correctIP){
            System.out.println("Insert the IP of the server: ");
            String insertedIP = scanner.nextLine();
            try{
                connection = new Socket(insertedIP,port);
                correctIP = true;
            } catch(IOException e){
                System.out.println("The IP is wrong");
            }
        }
        out = new PrintWriter(connection.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        System.out.println(MessageName.STARTINGMESSAGE.getValue());
        while(true){
            if(gui){
               //launchGui(args);
            }
            String request = in.readLine();
            if(request == null){
                throw new IOException("A client is disconnected. The game is over.");
            }
            try{
                gameState = objectMapper.readValue(request,GameState.class);
                messageHandler.messageParser(gameState);
            } catch (Exception e){
                System.out.println(request);
            }
        }
    }

    public void guiReader() throws IOException {
        String request = in.readLine();
        try{
            gameState = objectMapper.readValue(request,GameState.class);
            messageHandler.messageParser(gameState);
        } catch (Exception e){
            System.out.println(request);
        }
    }

    public void stop () throws IOException {
        in.close();
        out.close();
        connection.close();
    }

    /**
     * This method write a String to allow the player to play from CLI, with all data he needs to play the game
     * @param gameState object sent from server
     * @return a String ready to be printed for the message handler
     */
    public String printState(GameState gameState){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < gameState.getIslandsInGame().size(); i++){
            if(gameState.getIslandsInGame().get(i).isMotherNaturePresent()){
                builder.append("Island ").append(i).append(": ").append("(^) ").append(gameState.getIslandsInGame().get(i).getStudentsOnIsland());
            }
            else{
                builder.append("Island ").append(i).append(": ").append("    ").append(gameState.getIslandsInGame().get(i).getStudentsOnIsland());
            }
            if(gameState.getIslandsInGame().get(i).getPlayerIndex() != - 1){
                for(int j = 0; j < gameState.getIslandsInGame().get(i).getNumberOfBuiltedTowers(); j++){
                    builder.append(" |^|");
                }
                for(Dashboard d : gameState.getDashboards()){
                    if(gameState.getIslandsInGame().get(i).getPlayerIndex() == d.getPlayerIndex()){
                        builder.append(" owner: ").append(d.getPlayerNickname());
                    }
                }
            }
            if(gameState.getIslandsInGame().get(i).getTeamColor() != null){
                for(int j = 0; j < gameState.getIslandsInGame().get(i).getNumberOfBuiltedTowers(); j++){
                    builder.append(" |^|");
                }
                if(gameState.getIslandsInGame().get(i).getTeamColor() == TowerColor.BLACK){
                    builder.append(" owner: black team");
                }
                else{
                    builder.append(" owner: white team");
                }
            }
            if(gameState.getIslandsInGame().get(i).isHasProhibitionCard()){
                builder.append(" (-)");
            }
            builder.append("\n");
        }
        builder.append("\n");
        for(int i = 0; i < gameState.getClouds().size(); i++){
            builder.append("Cloud ").append(i).append(": ").append(gameState.getClouds().get(i)).append("\n");
        }
        builder.append("\n");
        if(gameState.isExpert()){
            builder.append("Total number of coins in game are: ").append(gameState.getTotalNumberOfCoins()).append("\n");
        }
        builder.append("\n");
        builder.append("\n");
        for(int i = 0; i < gameState.getDashboards().size(); i++){
            builder.append("Player ").append(gameState.getDashboards().get(i).getPlayerNickname()).append(": \n");
            if(gameState.isExpert()){
                builder.append("\tPlayer ").append(gameState.getDashboards().get(i).getPlayerNickname()).append(" has ").append(gameState.getPlayersCoins().get(i)).append(" coins").append("\n");
            }
            builder.append("\t").append(gameState.getDashboards().get(i).getPlayerNickname()).append("'s hand is (represented in turn heavyness):").append("\n");
            for(int j = 0; j < gameState.getPlayersHand().get(i).size(); j++){
                builder.append("\t\tIndex ").append(j).append(": ").append(gameState.getPlayersHand().get(i).get(j)).append(" ");
            }
            builder.append("\n");
            builder.append("\tEntrance: ").append(gameState.getDashboards().get(i).getEntrance()).append("\n");
            builder.append("\tRed Row: ").append(gameState.getDashboards().get(i).getRedRowHall()).append("\n");
            builder.append("\tBlue Row: ").append(gameState.getDashboards().get(i).getBlueRowHall()).append("\n");
            builder.append("\tPink Row: ").append(gameState.getDashboards().get(i).getPinkRowHall()).append("\n");
            builder.append("\tYellow Row: ").append(gameState.getDashboards().get(i).getYellowRowHall()).append("\n");
            builder.append("\tGreen Row: ").append(gameState.getDashboards().get(i).getGreenRowHall()).append("\n");
            builder.append("\tRed Teacher: ").append(gameState.getDashboards().get(i).isRedTeacherPresent()).append("  ");
            builder.append("\tBlue Teacher: ").append(gameState.getDashboards().get(i).isBlueTeacherPresent()).append("  ");
            builder.append("\tPink Teacher: ").append(gameState.getDashboards().get(i).isPinkTeacherPresent()).append("  ");
            builder.append("\tYellow Teacher: ").append(gameState.getDashboards().get(i).isYellowTeacherPresent()).append("  ");
            builder.append("\tGreen Teacher: ").append(gameState.getDashboards().get(i).isGreenTeacherPresent()).append("  ");
            builder.append("\n\n");
        }
        builder.append("\n");
        builder.append("Mother Nature (^) is on island ").append(gameState.getMotherNaturePosition()).append("\n");
        builder.append("Symbols:").append("\n");
        builder.append("\t|^| -> Tower").append("\n");
        builder.append("\t(-) -> Prohibition Card").append("\n");
        if(gameState.isCharacter1IsInGame()){
            builder.append("Character 1: ").append(gameState.getStudentsOnCharacter1()).append("\n");
        }
        if(gameState.isCharacter7IsInGame()){
            builder.append("Character 7: ").append(gameState.getStudentsOnCharacter7()).append("\n");
        }
        if(gameState.isCharacter11IsInGame()){
            builder.append("Character 11: ").append(gameState.getStudentsOnCharacter11()).append("\n");
        }
        builder.append("\n");
        builder.append(gameState.getMessage());
        return builder.toString();
    }

//    public void launchGui(String[] args){
//        Application.launch(Client.class,args);
//    }

}