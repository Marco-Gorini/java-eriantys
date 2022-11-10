package it.polimi.ingsw.model.gamemodel;

import java.util.HashMap;
import java.util.List;

public class GameState {
    /**
     * This class contains all the attributes that have to be sent to the client to print CLI and GUI
     **/
    private List<Dashboard> dashboards;
    private List<Island> islandsInGame;
    private HashMap<Integer, List<PawnColor>> clouds;
    private int totalNumberOfCoins;
    private int motherNaturePosition;
    private List<Integer> playersCoins;
    private List<Integer> playedAssistantThisTurn;
    private boolean character1IsInGame = false;
    private boolean character7IsInGame = false;
    private boolean character11IsInGame = false;
    private boolean expert = false;
    private List<PawnColor> studentsOnCharacter1;
    private List<PawnColor> studentsOnCharacter7;
    private List<PawnColor> studentsOnCharacter11;
    private HashMap<Integer,List<Integer>> playersHand;
    private String message;

    public List<Integer> getPlayedAssistantThisTurn() {return playedAssistantThisTurn;}
    public void setPlayedAssistantThisTurn(List<Integer> playedAssistantThisTurn) {this.playedAssistantThisTurn = playedAssistantThisTurn;}

    public HashMap<Integer, List<Integer>> getPlayersHand() {return playersHand;}
    public void setPlayersHand(HashMap<Integer, List<Integer>> playersHand) {this.playersHand = playersHand;}

    public List<PawnColor> getStudentsOnCharacter1() {return studentsOnCharacter1;}
    public void setStudentsOnCharacter1(List<PawnColor> studentsOnCharacter1) {this.studentsOnCharacter1 = studentsOnCharacter1;}

    public List<PawnColor> getStudentsOnCharacter7() {return studentsOnCharacter7;}
    public void setStudentsOnCharacter7(List<PawnColor> studentsOnCharacter7) {this.studentsOnCharacter7 = studentsOnCharacter7;}

    public List<PawnColor> getStudentsOnCharacter11() {return studentsOnCharacter11;}
    public void setStudentsOnCharacter11(List<PawnColor> studentsOnCharacter11) {this.studentsOnCharacter11 = studentsOnCharacter11;}

    public boolean isCharacter1IsInGame() {
        return character1IsInGame;
    }
    public void setCharacter1IsInGame(boolean character1IsInGame) {
        this.character1IsInGame = character1IsInGame;
    }

    public boolean isCharacter7IsInGame() {
        return character7IsInGame;
    }
    public void setCharacter7IsInGame(boolean character7IsInGame) {
        this.character7IsInGame = character7IsInGame;
    }

    public boolean isCharacter11IsInGame() {
        return character11IsInGame;
    }
    public void setCharacter11IsInGame(boolean character11IsInGame) {
        this.character11IsInGame = character11IsInGame;
    }

    public boolean isExpert() {
        return expert;
    }
    public void setExpert(boolean expert) {
        this.expert = expert;
    }

    public List<Integer> getPlayersCoins() {
        return playersCoins;
    }
    public void setPlayersCoins(List<Integer> playersCoins) {
        this.playersCoins = playersCoins;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<Dashboard> getDashboards() {return dashboards;}
    public void setDashboards(List<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }

    public List<Island> getIslandsInGame() {
        return islandsInGame;
    }
    public void setIslandsInGame(List<Island> islandsInGame) {
        this.islandsInGame = islandsInGame;
    }

    public HashMap<Integer, List<PawnColor>> getClouds() {
        return clouds;
    }
    public void setClouds(HashMap<Integer, List<PawnColor>> clouds) {
        this.clouds = clouds;
    }

    public int getTotalNumberOfCoins() {
        return totalNumberOfCoins;
    }
    public void setTotalNumberOfCoins(int totalNumberOfCoins) {
        this.totalNumberOfCoins = totalNumberOfCoins;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }
    public void setMotherNaturePosition(int motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }
}
