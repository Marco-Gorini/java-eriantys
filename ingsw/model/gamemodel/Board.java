package it.polimi.ingsw.model.gamemodel;

import it.polimi.ingsw.model.characters.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
    private final Bag bag = new Bag();
    private final List<Player> playersInGame = new ArrayList<>();
    private final List<Island> islandsInGame = new ArrayList<>();
    private final HashMap<Integer,List<PawnColor>> clouds = new HashMap<>();
    private int totalNumberOfCoins = 20;
    private int motherNaturePosition;
    private final List<Assistant> playedAssistantThisTurn = new ArrayList<>();
    private final Character1 character1= new Character1();
    private final Character2 character2= new Character2();
    private final Character3 character3 = new Character3();
    private final Character4 character4= new Character4();
    private final Character5 character5= new Character5();
    private final Character6 character6= new Character6();
    private final Character7 character7= new Character7();
    private final Character8 character8= new Character8();
    private final Character9 character9= new Character9();
    private final Character10 character10 = new Character10();
    private final Character11 character11 = new Character11();
    private final Character12 character12 = new Character12();



    public Bag getBag() {return bag;}

    public List<Player> getPlayersInGame() {return playersInGame;}

    public HashMap<Integer, List<PawnColor>> getClouds() {return clouds;}

    public int getTotalNumberOfCoins() {return totalNumberOfCoins;}
    public void setTotalNumberOfCoins(int totalNumberOfCoins) {this.totalNumberOfCoins = totalNumberOfCoins;}

    public List<Island> getIslandsInGame() {return islandsInGame;}

    public int getMotherNaturePosition() {return motherNaturePosition;}
    public void setMotherNaturePosition(int motherNaturePosition) {this.motherNaturePosition = motherNaturePosition;}

    public List<Assistant> getPlayedAssistantThisTurn() {return playedAssistantThisTurn;}

    public Character1 getCharacter1() {return character1;}

    public Character2 getCharacter2() {return character2;}

    public Character3 getCharacter3() {return character3;}

    public Character4 getCharacter4() {return character4;}

    public Character5 getCharacter5() {return character5;}

    public Character6 getCharacter6() {return character6;}

    public Character7 getCharacter7() {return character7;}

    public Character8 getCharacter8() {return character8;}

    public Character9 getCharacter9() {return character9;}

    public Character10 getCharacter10() {return character10;}

    public Character11 getCharacter11() {return character11;}

    public Character12 getCharacter12() {return character12;}


    /**
     * This method unifies two close islands having the same ownerPlayer (and all them attributes)
     * in only the island with minor index, adding on it all the students of the Island with
     * major index, and setting the number of Builted Tower on the correct island, then eliminate
     * the island with major index.
     * @param indexOfIslandToUnify1: index of island 1 in the list "islandInGame" to unify
     * @param indexOfIslandToUnify2: index of island 2 in the list "islandInGame" to unify
     */
    public void unifyIsland(int indexOfIslandToUnify1, int indexOfIslandToUnify2){
        if(indexOfIslandToUnify1 == indexOfIslandToUnify2 + 1 || indexOfIslandToUnify1 == indexOfIslandToUnify2 - 1 || (indexOfIslandToUnify1 == islandsInGame.size() - 1 && indexOfIslandToUnify2 == 0) || (indexOfIslandToUnify1 == 0 && indexOfIslandToUnify2 == islandsInGame.size() - 1 ) ){
            if((islandsInGame.get(indexOfIslandToUnify1).getPlayerIndex() == islandsInGame.get(indexOfIslandToUnify2).getPlayerIndex() && islandsInGame.get(indexOfIslandToUnify1).getPlayerIndex() != -1) || (islandsInGame.get(indexOfIslandToUnify1).getTeamColor() == islandsInGame.get(indexOfIslandToUnify2).getTeamColor() && islandsInGame.get(indexOfIslandToUnify1).getTeamColor() != null)){
                if(indexOfIslandToUnify1 > indexOfIslandToUnify2){
                    for(PawnColor studentsOnIslandToRemove : islandsInGame.get(indexOfIslandToUnify1).getStudentsOnIsland()){
                        islandsInGame.get(indexOfIslandToUnify2).getStudentsOnIsland().add(studentsOnIslandToRemove);
                    }
                    islandsInGame.get(indexOfIslandToUnify2).setNumberOfBuiltedTowers(islandsInGame.get(indexOfIslandToUnify2).getNumberOfBuiltedTowers() + islandsInGame.get(indexOfIslandToUnify1).getNumberOfBuiltedTowers());
                    islandsInGame.remove(indexOfIslandToUnify1);
                    motherNaturePosition = indexOfIslandToUnify2;
                    islandsInGame.get(indexOfIslandToUnify2).setMotherNaturePresent(true);
                }
                else {
                    for(PawnColor studentsOnIslandToRemove : islandsInGame.get(indexOfIslandToUnify2).getStudentsOnIsland()){
                        islandsInGame.get(indexOfIslandToUnify1).getStudentsOnIsland().add(studentsOnIslandToRemove);
                    }
                    islandsInGame.get(indexOfIslandToUnify1).setNumberOfBuiltedTowers(islandsInGame.get(indexOfIslandToUnify2).getNumberOfBuiltedTowers() + islandsInGame.get(indexOfIslandToUnify1).getNumberOfBuiltedTowers());
                    islandsInGame.remove(indexOfIslandToUnify2);
                    motherNaturePosition = indexOfIslandToUnify1;
                    islandsInGame.get(indexOfIslandToUnify1).setMotherNaturePresent(true);
                }
            }
        }
    }


    /**
     * This method will be called every endturn to refill the clouds, drawing students from the bag. It is based on the
     * fact that the clouds are represented with a Map,and in the game there is a number of clouds for each player,
     * then on every cloud are put students based on the number of players in game + 1
     */
    public void putStudentsOnCloud (){
        boolean cloudsAlreadyRefilled = true;
        for(int i = 0; i < clouds.size(); i++){
            if(clouds.get(i).size() == 0){
                cloudsAlreadyRefilled = false;
                break;
            }
        }
        if(!cloudsAlreadyRefilled){
            if(playersInGame.size() != 4){
                for(int i = 0; i < playersInGame.size(); i++){
                    for(int j = 0; j < playersInGame.size() + 1; j++){
                        if(bag.getListOfStudentsInBag().size() > 0){
                            clouds.get(i).add(bag.drawStudent());
                        }
                    }
                }
            }
            else{
                for(int i = 0; i < playersInGame.size(); i++){
                    for(int j = 0; j < 3; j++){
                        if(bag.getListOfStudentsInBag().size() > 0){
                            clouds.get(i).add(bag.drawStudent());
                        }
                    }
                }
            }
        }
    }
    
    /**
     * These methods search the maximum number of students in each player row of associated color, then set true
     * the attribute in players's dashboard for the ownerTeacher , and eventually set false for the precedent player.
     * This comment is valid for every checkteacher, because all the methods are equals.
     */
    public void checkRedTeacher(){
        int majorNumberOfStudentsInHall = 0;
        int potentialPlayerIndex = 5;
        int previousOwnerIndex = 5;
        for(Player p : playersInGame){
            if(p.getDashboard().redTeacherPresent){
                previousOwnerIndex = p.getPlayerIndex();
                potentialPlayerIndex = p.getPlayerIndex();
                majorNumberOfStudentsInHall = p.getDashboard().getRedRowHall().size();
            }
        }
        for(Player player : playersInGame){
            if(player.getDashboard().getRedRowHall().size() > majorNumberOfStudentsInHall){
                majorNumberOfStudentsInHall = player.getDashboard().getRedRowHall().size();
                potentialPlayerIndex = player.getPlayerIndex();
            }
        }
        if(previousOwnerIndex != potentialPlayerIndex && previousOwnerIndex != 5){
            playersInGame.get(potentialPlayerIndex).getDashboard().setRedTeacherPresent(true);
            playersInGame.get(previousOwnerIndex).getDashboard().setRedTeacherPresent(false);
        }
        else {
            if(potentialPlayerIndex != 5){
                playersInGame.get(potentialPlayerIndex).getDashboard().setRedTeacherPresent(true);
            }
        }
    }
    public void checkBlueTeacher(){
        int majorNumberOfStudentsInHall = 0;
        int potentialPlayerIndex = 5;
        int previousOwnerIndex = 5;
        for(Player p : playersInGame){
            if(p.getDashboard().blueTeacherPresent){
                previousOwnerIndex = p.getPlayerIndex();
                potentialPlayerIndex = p.getPlayerIndex();
                majorNumberOfStudentsInHall = p.getDashboard().getBlueRowHall().size();
            }
        }
        for(Player player : playersInGame){
            if(player.getDashboard().getBlueRowHall().size() > majorNumberOfStudentsInHall){
                majorNumberOfStudentsInHall = player.getDashboard().getBlueRowHall().size();
                potentialPlayerIndex = player.getPlayerIndex();
            }
        }
        if(previousOwnerIndex != potentialPlayerIndex && previousOwnerIndex != 5){
            playersInGame.get(potentialPlayerIndex).getDashboard().setBlueTeacherPresent(true);
            playersInGame.get(previousOwnerIndex).getDashboard().setBlueTeacherPresent(false);
        }
        else {
            if(potentialPlayerIndex != 5){
                playersInGame.get(potentialPlayerIndex).getDashboard().setBlueTeacherPresent(true);
            }
        }
    }
    public void checkPinkTeacher(){
        int majorNumberOfStudentsInHall = 0;
        int potentialPlayerIndex = 5;
        int previousOwnerIndex = 5;
        for(Player p : playersInGame){
            if(p.getDashboard().pinkTeacherPresent){
                previousOwnerIndex = p.getPlayerIndex();
                potentialPlayerIndex = p.getPlayerIndex();
                majorNumberOfStudentsInHall = p.getDashboard().getPinkRowHall().size();
            }
        }
        for(Player player : playersInGame){
            if(player.getDashboard().getPinkRowHall().size() > majorNumberOfStudentsInHall){
                majorNumberOfStudentsInHall = player.getDashboard().getPinkRowHall().size();
                potentialPlayerIndex = player.getPlayerIndex();
            }
        }
        if(previousOwnerIndex != potentialPlayerIndex && previousOwnerIndex != 5){
            playersInGame.get(potentialPlayerIndex).getDashboard().setPinkTeacherPresent(true);
            playersInGame.get(previousOwnerIndex).getDashboard().setPinkTeacherPresent(false);
        }
        else {
            if(potentialPlayerIndex != 5){
                playersInGame.get(potentialPlayerIndex).getDashboard().setPinkTeacherPresent(true);
            }
        }
    }
    public void checkYellowTeacher(){
        int majorNumberOfStudentsInHall = 0;
        int potentialPlayerIndex = 5;
        int previousOwnerIndex = 5;
        for(Player p : playersInGame){
            if(p.getDashboard().yellowTeacherPresent){
                previousOwnerIndex = p.getPlayerIndex();
                potentialPlayerIndex = p.getPlayerIndex();
                majorNumberOfStudentsInHall = p.getDashboard().getYellowRowHall().size();
            }
        }
        for(Player player : playersInGame){
            if(player.getDashboard().getYellowRowHall().size() > majorNumberOfStudentsInHall){
                majorNumberOfStudentsInHall = player.getDashboard().getYellowRowHall().size();
                potentialPlayerIndex = player.getPlayerIndex();
            }
        }
        if(previousOwnerIndex != potentialPlayerIndex && previousOwnerIndex != 5){
            playersInGame.get(potentialPlayerIndex).getDashboard().setYellowTeacherPresent(true);
            playersInGame.get(previousOwnerIndex).getDashboard().setYellowTeacherPresent(false);
        }
        else {
            if(potentialPlayerIndex != 5){
                playersInGame.get(potentialPlayerIndex).getDashboard().setYellowTeacherPresent(true);
            }
        }
    }
    public void checkGreenTeacher(){
        int majorNumberOfStudentsInHall = 0;
        int potentialPlayerIndex = 5;
        int previousOwnerIndex = 5;
        for(Player p : playersInGame){
            if(p.getDashboard().greenTeacherPresent){
                previousOwnerIndex = p.getPlayerIndex();
                potentialPlayerIndex = p.getPlayerIndex();
                majorNumberOfStudentsInHall = p.getDashboard().getGreenRowHall().size();
            }
        }
        for(Player player : playersInGame){
            if(player.getDashboard().getGreenRowHall().size() > majorNumberOfStudentsInHall){
                majorNumberOfStudentsInHall = player.getDashboard().getGreenRowHall().size();
                potentialPlayerIndex = player.getPlayerIndex();
            }
        }
        if(previousOwnerIndex != potentialPlayerIndex && previousOwnerIndex != 5){
            playersInGame.get(potentialPlayerIndex).getDashboard().setGreenTeacherPresent(true);
            playersInGame.get(previousOwnerIndex).getDashboard().setGreenTeacherPresent(false);
        }
        else {
            if(potentialPlayerIndex != 5){
                playersInGame.get(potentialPlayerIndex).getDashboard().setGreenTeacherPresent(true);
            }
        }
    }
}

