package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamemodel.Bag;
import it.polimi.ingsw.model.gamemodel.PawnColor;
import it.polimi.ingsw.model.gamemodel.Player;

import java.util.List;
import java.util.ArrayList;

public class Character7 {
    private int costOfActivation = 1;
    private List<PawnColor> listOfStudentsOnCharacter = new ArrayList<>();
    private boolean isInGame = false;

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    public List<PawnColor> getListOfStudentsOnCharacter() {return listOfStudentsOnCharacter;}

    /**
     * This method put 6 students on character at the start of game (if it's initialized).This not check if bag is empty
     * because at the start of game bag is full.
     * @param gameBag: I have to draw students from Bag that is in board
     */
    public void initializateCharacter(Bag gameBag){
        for(int i = 0; i < 6; i++){
            listOfStudentsOnCharacter.add(gameBag.drawStudent());
        }
    }

    /**
     * This method exchange chosen students from character to entrance
     * @param p: player that is activating the effect
     * @param chosenStudentsFromEntrance: students the player wants change from entrance
     * @param chosenStudentsFromCharacter: students the player wants to take from character
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     * @throws DifferentSizesException: this checks that player has not inserted different number of students from
     * character and entrance
     * @throws StudentsIsNotOnCharacterException: students are not all on character
     * @throws StudentsAreNotInEntranceException: students are not all in player's entrance
     */
    public void activateTheEffect(Player p, List<PawnColor> chosenStudentsFromEntrance, List<PawnColor> chosenStudentsFromCharacter) throws NotEnoughMoneyException, CharacterNotInGameException, DifferentSizesException, StudentsIsNotOnCharacterException, StudentsAreNotInEntranceException {
        if(p.getNumberOfCoins() >= costOfActivation && isInGame){
            if(chosenStudentsFromCharacter.size() <= 3 && chosenStudentsFromCharacter.size() == chosenStudentsFromEntrance.size()){
                List<PawnColor> copyStudentsInEntrance = new ArrayList<>();
                List<PawnColor> copyStudentsInCharacter = new ArrayList<>();
                int counter1 = 0;
                int counter2 = 0;
                copyStudentsInEntrance.addAll(p.getDashboard().getEntrance());
                copyStudentsInCharacter.addAll(listOfStudentsOnCharacter);
                for(PawnColor s : chosenStudentsFromEntrance){
                    for(PawnColor student : copyStudentsInEntrance){
                        if(s == student){
                            counter1++;
                            copyStudentsInEntrance.remove(student);
                            break;
                        }
                    }
                }
                for(PawnColor s : chosenStudentsFromCharacter){
                    for(PawnColor student : copyStudentsInCharacter){
                        if(s == student){
                            counter2++;
                            copyStudentsInCharacter.remove(student);
                            break;
                        }
                    }
                }
                if(counter1 == chosenStudentsFromEntrance.size() && counter2 == chosenStudentsFromCharacter.size()){
                    for(PawnColor s : chosenStudentsFromEntrance){
                        listOfStudentsOnCharacter.add(s);
                        p.getDashboard().getEntrance().remove(s);
                    }
                    for(PawnColor s : chosenStudentsFromCharacter){
                        p.getDashboard().getEntrance().add(s);
                        listOfStudentsOnCharacter.remove(s);
                    }
                    p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
                    costOfActivation++;
                }
                else{
                    if(counter1 != chosenStudentsFromEntrance.size()){
                        throw new StudentsAreNotInEntranceException("chosen students are not in the entrance");
                    }
                    if(counter2 != chosenStudentsFromCharacter.size()){
                        throw new StudentsIsNotOnCharacterException("chosen students are not on the Character.");
                    }
                }
            }
            else{
                throw new DifferentSizesException("Number of students to change is too much, or number of students you want to exchange it's not the same for Character and Entrance");
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
