package it.polimi.ingsw.model.characters;
import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.exception.StudentsIsNotOnCharacterException;
import it.polimi.ingsw.model.gamemodel.*;

import java.util.List;
import java.util.ArrayList;

public class Character1 {
    private int costOfActivation = 1;
    private boolean isInGame = false;
    private List<PawnColor> listofStudentsOnCharacter = new ArrayList<>();

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    public List<PawnColor> getListofStudentsOnCharacter() {return listofStudentsOnCharacter;}

    /**
     * This method put 4 students on the character at the start of game (if the character is initialized).This method
     * does not check the bag dimension because at the start of game the bag is full
     * @param gameBag: I have to draw students from Bag that is in board
     */
    public void initializateCharacter(Bag gameBag){
        for(int i = 0; i < 4; i++){
            listofStudentsOnCharacter.add(gameBag.drawStudent());
        }
    }

    /**
     * This method simply move a students from character to choosen island from client, then draw a student to refill
     * the character, then increments cost of activation
     * @param student: chosen student from client
     * @param indexOfChosenIsland: chosen index of island choosen from client
     * @param p: player that is activating the effect
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     * @throws StudentsIsNotOnCharacterException: passed student is not on the character
     */
    public void activateTheEffect(PawnColor student, int indexOfChosenIsland, Player p) throws NotEnoughMoneyException, CharacterNotInGameException, StudentsIsNotOnCharacterException {
        if(p.getNumberOfCoins() >= costOfActivation && isInGame){
            boolean isOnCard = false;
            for(PawnColor s : listofStudentsOnCharacter){
                if(s == student){
                    isOnCard = true;
                    break;
                }
            }
            if(isOnCard && Game.getInstance().getBoard().getBag().getListOfStudentsInBag().size() > 0){
                Game.getInstance().getBoard().getIslandsInGame().get(indexOfChosenIsland).getStudentsOnIsland().add(student);
                listofStudentsOnCharacter.remove(student);
                listofStudentsOnCharacter.add(Game.getInstance().getBoard().getBag().drawStudent());
                p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
                costOfActivation ++;
            }
            else{
                throw new StudentsIsNotOnCharacterException("Student is not on Character");
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
