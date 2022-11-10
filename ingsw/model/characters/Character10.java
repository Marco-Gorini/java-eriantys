package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamemodel.PawnColor;
import it.polimi.ingsw.model.gamemodel.Player;

import java.util.ArrayList;
import java.util.List;

public class Character10 {
    private int costOfActivation = 1;
    private boolean isInGame = false;

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    /**
     * This method exchange chosen students from hall and entrance of player that activated this effect
     * @param p: player that is activating the effect
     * @param chosenStudentsFromEntrance: students the player wants change from entrance
     * @param chosenStudentsFromHall: students the player wants change from hall
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     * @throws StudentsAreNotInEntranceException: students are not all in player's entrance
     * @throws StudentsAreNotInHallException: students are not all in player's hall
     * @throws FullRowException: addstudentInHall exception
     * @throws DifferentSizesException:this checks that player has not inserted different number of students from
     * character and entrance
     */
    public void activateTheEffect(Player p, List<PawnColor> chosenStudentsFromEntrance, List<PawnColor> chosenStudentsFromHall) throws NotEnoughMoneyException, CharacterNotInGameException, StudentsAreNotInEntranceException, StudentsAreNotInHallException, FullRowException, DifferentSizesException {
        if(p.getNumberOfCoins() >= costOfActivation && isInGame){
            if (chosenStudentsFromEntrance.size() == chosenStudentsFromHall.size() && chosenStudentsFromEntrance.size() <= 2) {
                List<PawnColor> copyStudentsInEntrance = new ArrayList<>();
                int checkRedRowDimension = p.getDashboard().getRedRowHall().size();
                int checkBlueRowDimension = p.getDashboard().getBlueRowHall().size();
                int checkPinkRowDimension = p.getDashboard().getPinkRowHall().size();
                int checkYellowRowDimension = p.getDashboard().getYellowRowHall().size();
                int checkGreenRowDimension = p.getDashboard().getGreenRowHall().size();
                int counter1 = 0;
                int counter2 = 0;

                for (PawnColor s : p.getDashboard().getEntrance()) {
                    copyStudentsInEntrance.add(s);
                }
                for (PawnColor s : chosenStudentsFromEntrance) {
                    for (PawnColor student : copyStudentsInEntrance) {
                        if (s == student) {
                            counter1++;
                            copyStudentsInEntrance.remove(student);
                            break;
                        }
                    }
                }
                for (PawnColor s : chosenStudentsFromHall) {
                    if (s == PawnColor.RED && checkRedRowDimension > 0) {
                        counter2++;
                        checkRedRowDimension--;
                    }
                    if (s == PawnColor.BLUE && checkBlueRowDimension > 0) {
                        counter2++;
                        checkBlueRowDimension--;
                    }
                    if (s == PawnColor.PINK && checkPinkRowDimension > 0) {
                        counter2++;
                        checkPinkRowDimension--;
                    }
                    if (s == PawnColor.YELLOW && checkYellowRowDimension > 0) {
                        counter2++;
                        checkYellowRowDimension--;
                    }
                    if (s == PawnColor.GREEN && checkGreenRowDimension > 0) {
                        counter2++;
                        checkGreenRowDimension--;
                    }
                }
                if (counter1 == chosenStudentsFromEntrance.size() && counter2 == chosenStudentsFromHall.size()) {
                    for (PawnColor s : chosenStudentsFromEntrance) {
                        p.getDashboard().addStudentInHall(s);
                    }
                    for (PawnColor s : chosenStudentsFromHall) {
                        if (s == PawnColor.RED) {
                            p.getDashboard().getRedRowHall().remove(s);
                            p.getDashboard().getEntrance().add(s);
                        }
                        if (s == PawnColor.BLUE) {
                            p.getDashboard().getBlueRowHall().remove(s);
                            p.getDashboard().getEntrance().add(s);
                        }
                        if (s == PawnColor.PINK) {
                            p.getDashboard().getPinkRowHall().remove(s);
                            p.getDashboard().getEntrance().add(s);
                        }
                        if (s == PawnColor.YELLOW) {
                            p.getDashboard().getYellowRowHall().remove(s);
                            p.getDashboard().getEntrance().add(s);
                        }
                        if (s == PawnColor.GREEN) {
                            p.getDashboard().getGreenRowHall().remove(s);
                            p.getDashboard().getEntrance().add(s);
                        }
                    }
                    p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
                    costOfActivation++;
                }
                else{
                    if(counter1 != chosenStudentsFromEntrance.size()){
                        throw new StudentsAreNotInEntranceException("chosen students are not in the entrance");
                    }
                    if(counter2 != chosenStudentsFromHall.size()){
                        throw  new StudentsAreNotInHallException("chosen students are not in the hall");
                    }
                }
            }
            else{
                throw new DifferentSizesException("There is not the same number of student to exchange from hall and entrance");
            }
        }
        else{
            if(p.getNumberOfCoins() < costOfActivation){
                throw new NotEnoughMoneyException("You have not enough money to play this Character.");
            }
            else{
                throw new CharacterNotInGameException("Selected Character is not in game");
            }
        }
    }
}
