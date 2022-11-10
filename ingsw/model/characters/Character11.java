package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.exception.StudentsIsNotOnCharacterException;
import it.polimi.ingsw.model.gamemodel.Bag;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.PawnColor;
import it.polimi.ingsw.model.gamemodel.Player;

import java.util.List;
import java.util.ArrayList;

public class Character11 {
    private int costOfActivation = 2;
    private List<PawnColor> listOfStudentsOnCharacter= new ArrayList<>();
    private boolean isInGame = false;

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public List<PawnColor> getListOfStudentsOnCharacter() {return listOfStudentsOnCharacter;}

    public int getCostOfActivation() {return costOfActivation;}

    /**
     * This method put 4 students on the character at the start of game (if it's initialized). This method doesn not check
     * Bag dimension because at the start of game the Bag is full
     * @param gameBag: I have to draw students from Bag that is in board
     */
    public void initializateCharacter(Bag gameBag){
        for(int i = 0; i < 4; i++){
            listOfStudentsOnCharacter.add(gameBag.drawStudent());
        }
    }

    /**
     * This method put a chosen student from player to his hall, then draw a student from bag
     * @param p: player that is activating the effect
     * @param chosenStudent: chosen student from client
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     * @throws StudentsIsNotOnCharacterException: passed student is not on the character
     */
    public void activateTheEffect(Player p, PawnColor chosenStudent) throws NotEnoughMoneyException, CharacterNotInGameException, StudentsIsNotOnCharacterException {
        if(p.getNumberOfCoins() >= costOfActivation  && isInGame){
            boolean Can = false;
            for(PawnColor s : listOfStudentsOnCharacter){
                if(s == chosenStudent){
                    Can = true;
                    break;
                }
            }
            if(Can && Game.getInstance().getBoard().getBag().getListOfStudentsInBag().size() > 0){
                listOfStudentsOnCharacter.add(Game.getInstance().getBoard().getBag().drawStudent());
                listOfStudentsOnCharacter.remove(chosenStudent);
                if(chosenStudent == PawnColor.RED){
                    p.getDashboard().getRedRowHall().add(chosenStudent);
                }
                if(chosenStudent == PawnColor.BLUE){
                    p.getDashboard().getBlueRowHall().add(chosenStudent);
                }
                if(chosenStudent == PawnColor.PINK){
                    p.getDashboard().getPinkRowHall().add(chosenStudent);
                }
                if(chosenStudent == PawnColor.YELLOW){
                    p.getDashboard().getYellowRowHall().add(chosenStudent);
                }
                if(chosenStudent == PawnColor.GREEN){
                    p.getDashboard().getGreenRowHall().add(chosenStudent);
                }
                p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
                costOfActivation++;
            }
            else {
                throw new StudentsIsNotOnCharacterException("chosen students are not on character");
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
