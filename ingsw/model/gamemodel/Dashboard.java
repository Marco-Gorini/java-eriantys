package it.polimi.ingsw.model.gamemodel;

import it.polimi.ingsw.exception.CloudAlreadyChoosenException;
import it.polimi.ingsw.exception.FullRowException;
import it.polimi.ingsw.exception.StudentsAreNotInEntranceException;
import it.polimi.ingsw.exception.WrongCloudIndexException;

import java.util.*;

public class Dashboard {
    private int playerIndex;
    private String playerNickname;
    private int numberOfTowers = 0;
    private List<PawnColor> redRowHall = new ArrayList<>();
    private List<PawnColor> blueRowHall = new ArrayList<>();
    private List<PawnColor> pinkRowHall = new ArrayList<>();
    private List<PawnColor> yellowRowHall = new ArrayList<>();
    private List<PawnColor> greenRowHall = new ArrayList<>();
    private List<PawnColor> entrance = new ArrayList<>();
    boolean redTeacherPresent = false;
    boolean blueTeacherPresent = false;
    boolean pinkTeacherPresent = false;
    boolean yellowTeacherPresent = false;
    boolean greenTeacherPresent = false;

    public Dashboard(int playerIndex, String playerNickname) {
        this.playerIndex = playerIndex;
        this.playerNickname = playerNickname;
    }

    public Dashboard() {
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public List<PawnColor> getRedRowHall() {return redRowHall;}

    public List<PawnColor> getBlueRowHall() {return blueRowHall;}

    public List<PawnColor> getPinkRowHall() {return pinkRowHall;}

    public List<PawnColor> getYellowRowHall() {return yellowRowHall;}

    public List<PawnColor> getGreenRowHall() {return greenRowHall;}

    public int getNumberOfTowers() {
        return numberOfTowers;
    }
    public void setNumberOfTowers(int numberOfTowers) {this.numberOfTowers = numberOfTowers;}

    public List<PawnColor> getEntrance() {
        return entrance;
    }

    public boolean isRedTeacherPresent() {
        return redTeacherPresent;
    }
    public void setRedTeacherPresent(boolean redTeacherPresent) {
        this.redTeacherPresent = redTeacherPresent;
    }

    public boolean isBlueTeacherPresent() {
        return blueTeacherPresent;
    }
    public void setBlueTeacherPresent(boolean blueTeacherPresent) {this.blueTeacherPresent = blueTeacherPresent;}

    public boolean isPinkTeacherPresent() {
        return pinkTeacherPresent;
    }
    public void setPinkTeacherPresent(boolean pinkTeacherPresent) {this.pinkTeacherPresent = pinkTeacherPresent;}

    public boolean isYellowTeacherPresent() {
        return yellowTeacherPresent;
    }
    public void setYellowTeacherPresent(boolean yellowTeacherPresent) {this.yellowTeacherPresent = yellowTeacherPresent;}

    public boolean isGreenTeacherPresent() {
        return greenTeacherPresent;
    }
    public void setGreenTeacherPresent(boolean greenTeacherPresent) {this.greenTeacherPresent = greenTeacherPresent;}


    /**
     * This method add the passed student in the right row of the dashboard, and eventually assign coin.Then
     * it remove the added student from the entrance
     * @param student: student to add in the dashboard
     * @throws FullRowException: when a row is full, the player can't add a student in the hall
     * @throws StudentsAreNotInEntranceException: when the player select a student that is not in the entrance
     */
    public void addStudentInHall(PawnColor student) throws FullRowException, StudentsAreNotInEntranceException {
        boolean canAdd = false;
        boolean fullRow = false;
        for(PawnColor s : entrance){
            if(s == student){
                canAdd = true;
                break;
            }
        }
        if(student == PawnColor.RED && canAdd){
            if(redRowHall.size() < 10){
                redRowHall.add(student);
            }
            else{
                fullRow = true;
            }
            if((redRowHall.size() == 3 || redRowHall.size() == 6 || redRowHall.size() == 9) && Game.getInstance().getGameVariant() == Variant.EXPERT){
                assignCoinToOwnerPlayer(Game.getInstance().getBoard());
            }
        }
        if(student == PawnColor.BLUE && canAdd){
            if(blueRowHall.size() < 10){
                blueRowHall.add(student);
            }
            else{
                fullRow = true;
            }
            if((blueRowHall.size() == 3 || blueRowHall.size() == 6 || blueRowHall.size() == 9) && Game.getInstance().getGameVariant() == Variant.EXPERT){
                assignCoinToOwnerPlayer(Game.getInstance().getBoard());
            }
        }
        if(student == PawnColor.PINK && canAdd){
            if(pinkRowHall.size() < 10){
                pinkRowHall.add(student);
            }
            else{
                fullRow = true;
            }
            if((pinkRowHall.size() == 3 || pinkRowHall.size() == 6 || pinkRowHall.size() == 9) && Game.getInstance().getGameVariant() == Variant.EXPERT){
                assignCoinToOwnerPlayer(Game.getInstance().getBoard());
            }
        }
        if(student == PawnColor.YELLOW && canAdd){
            if(yellowRowHall.size() < 10){
                yellowRowHall.add(student);
            }
            else{
                fullRow = true;
            }
            if((yellowRowHall.size() == 3 || yellowRowHall.size() == 6 || yellowRowHall.size() == 9) && Game.getInstance().getGameVariant() == Variant.EXPERT){
                assignCoinToOwnerPlayer(Game.getInstance().getBoard());
            }
        }
        if(student == PawnColor.GREEN && canAdd){
            if(greenRowHall.size() < 10){
                greenRowHall.add(student);
            }
            else{
                fullRow = true;
            }
            if((greenRowHall.size() == 3 || greenRowHall.size() == 6 || greenRowHall.size() == 9) && Game.getInstance().getGameVariant() == Variant.EXPERT){
                assignCoinToOwnerPlayer(Game.getInstance().getBoard());
            }
        }
        if(canAdd && !fullRow){
            entrance.remove(student);
        }
        else{
            if(!canAdd){
                throw new StudentsAreNotInEntranceException("Selected student is not in the entrance");
            }
            else{
                throw new FullRowException("The row is full");
            }
        }
    }

    /**
     * This method add students in the entrance, based on the choosen island, then clear the choosen island
     * @param indexOfChoosenCloud: index of chosen cloud wich with refil the entrance
     * @throws WrongCloudIndexException: index of cloud out of bound or < 0
     * @throws CloudAlreadyChoosenException: another player selected this cloud before
     */
    public void addStudentsInEntrance(int indexOfChoosenCloud) throws WrongCloudIndexException, CloudAlreadyChoosenException {
        if (indexOfChoosenCloud >= 0) {
            if (indexOfChoosenCloud < Game.getInstance().getBoard().getPlayersInGame().size()) {
                if(!Game.getInstance().getBoard().getClouds().get(indexOfChoosenCloud).isEmpty()){
                    entrance.addAll(Game.getInstance().getBoard().getClouds().get(indexOfChoosenCloud));
                    Game.getInstance().getBoard().getClouds().get(indexOfChoosenCloud).clear();
                }
                else{
                    throw new CloudAlreadyChoosenException("Cloud was already choosen.");
                }
            } else {
                throw new WrongCloudIndexException("Index of cloud is wrong");
            }
        } else {
            throw new WrongCloudIndexException("Index of cloud is wrong");
        }
    }

    /**
     * Increments the player's coin and decrements the total number of coins in board.
     * @param gameBoard: it's to have access to the total number of coins and decrementate it.
     */

    public void assignCoinToOwnerPlayer(Board gameBoard){
        if(gameBoard.getTotalNumberOfCoins() > 0){
            for(Player p : gameBoard.getPlayersInGame()){
                if(p.getPlayerIndex() == playerIndex){
                    p.setNumberOfCoins(p.getNumberOfCoins() + 1);
                }
            }
            gameBoard.setTotalNumberOfCoins(gameBoard.getTotalNumberOfCoins() - 1);
        }
    }
}
