package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.IndexOfIslandOutOfBound;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.exception.StudentsIsNotOnCharacterException;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Character1Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK,0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testInitializateCharacter(){
        Board board = new Board();
        Game.getInstance().setGameVariant(Variant.EXPERT);
        //Testing the condition in wich Character1 is inizializated after setting of Bag, so student on it are 4
        board.getBag().getListOfStudentsInBag().add(PawnColor.RED);
        board.getBag().getListOfStudentsInBag().add(PawnColor.RED);
        board.getBag().getListOfStudentsInBag().add(PawnColor.BLUE);
        board.getBag().getListOfStudentsInBag().add(PawnColor.BLUE);
        board.getCharacter1().initializateCharacter(board.getBag());
        assertEquals(4,board.getCharacter1().getListofStudentsOnCharacter().size());
    }

    @Test
    public void testActivateTheEffect() throws CharacterNotInGameException, StudentsIsNotOnCharacterException, NotEnoughMoneyException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Board board = Game.getInstance().getBoard();
        board.getIslandsInGame().add(new Island());
        board.getCharacter1().setInGame(true);
        p.setNumberOfCoins(0);
        //Calling activateEffect without coins, so the student will not be add on Island
        try {
            board.getCharacter1().activateTheEffect(PawnColor.RED,0,p);
            fail();
        } catch (NotEnoughMoneyException e) {
            assertTrue(true);
        }
        assertEquals(0,board.getIslandsInGame().get(0).getStudentsOnIsland().size());
        //Calling activateEffect with coins, but isInGame attribute is false
        board.getCharacter1().setInGame(false);
        p.setNumberOfCoins(1);
        try{
            board.getCharacter1().activateTheEffect(PawnColor.RED,0,p);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(0,board.getIslandsInGame().get(0).getStudentsOnIsland().size());
        //Calling activateEffect with coin and isOnGame, but student is not on Character and bag size() is not <= 0
        board.getCharacter1().setInGame(true);
        board.getBag().getListOfStudentsInBag().add(PawnColor.GREEN);
        try{
            board.getCharacter1().activateTheEffect(PawnColor.RED,0,p);
            fail();
        } catch(StudentsIsNotOnCharacterException e){
            assertTrue(true);
        }
        assertEquals(0,board.getIslandsInGame().get(0).getStudentsOnIsland().size());
        //Calling activateEffect with coin and isOnGame, student is  on Character and bag size() is not <= 0
        board.getCharacter1().getListofStudentsOnCharacter().add(PawnColor.GREEN);
        board.getCharacter1().activateTheEffect(PawnColor.GREEN,0,p);
        assertEquals(1,board.getIslandsInGame().get(0).getStudentsOnIsland().size());
        assertEquals(1,board.getCharacter1().getListofStudentsOnCharacter().size());
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(2,board.getCharacter1().getCostOfActivation());
        assertEquals(0,board.getBag().getListOfStudentsInBag().size());
        //Calling activateEffect with coin and isOnGame, student is  on Character and bag size() is == 0
        p.setNumberOfCoins(2);
        try{
            board.getBag().getListOfStudentsInBag().remove(0);
            fail();
        } catch(IndexOutOfBoundsException e){
            assertTrue(true);
        }
        try{
            board.getCharacter1().activateTheEffect(PawnColor.GREEN,0,p);
        } catch(StudentsIsNotOnCharacterException e){
            assertTrue(true);
        }
        assertEquals(2,p.getNumberOfCoins());
    }
}