package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.exception.StudentsIsNotOnCharacterException;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Character11Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK, 0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void initializateCharacter(){
        Game.getInstance().reset();
        Game.getInstance().setGameVariant(Variant.EXPERT);
        for(int i = 0; i < 4; i++){
            Game.getInstance().getBoard().getBag().getListOfStudentsInBag().add(PawnColor.RED);
        }
        Game.getInstance().getBoard().getCharacter11().initializateCharacter(Game.getInstance().getBoard().getBag());
        assertEquals(4,Game.getInstance().getBoard().getCharacter11().getListOfStudentsOnCharacter().size());
    }

    @Test
    public void testActivateTheEffect() throws NotEnoughMoneyException, CharacterNotInGameException, StudentsIsNotOnCharacterException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        PawnColor pc = PawnColor.RED;
        //Call activateTheEffect without money
        p.setNumberOfCoins(0);
        try{
            Game.getInstance().getBoard().getCharacter11().activateTheEffect(p, pc);
            fail();
        } catch(NotEnoughMoneyException e){
            assertTrue(true);
        }
        assertEquals(0, p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(2);
        try{
            Game.getInstance().getBoard().getCharacter11().activateTheEffect(p, pc);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(2,p.getNumberOfCoins());
        //Initializate listOfStudentsOnCharacter
        for(int i = 0; i < 4; i++){
            Game.getInstance().getBoard().getCharacter11().getListOfStudentsOnCharacter().add(PawnColor.BLUE);
        }
        //Call activateTheEffect, but selected student is not on Character
        Game.getInstance().getBoard().getCharacter11().setInGame(true);
        try{
            Game.getInstance().getBoard().getCharacter11().activateTheEffect(p, pc);
            fail();
        } catch(StudentsIsNotOnCharacterException e){
            assertTrue(true);
        }
        assertEquals(2,p.getNumberOfCoins());
        //Call activateEffect with student on Character, but Bag is over
        Game.getInstance().getBoard().getCharacter11().setInGame(true);
        try{
            Game.getInstance().getBoard().getCharacter11().activateTheEffect(p, pc);
            fail();
        } catch(StudentsIsNotOnCharacterException e){
            assertTrue(true);
        }
        assertEquals(2,p.getNumberOfCoins());
        //Call the effect correctly
        Game.getInstance().getBoard().getBag().getListOfStudentsInBag().add(PawnColor.GREEN);
        Game.getInstance().getBoard().getCharacter11().activateTheEffect(p, PawnColor.BLUE);
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(3,Game.getInstance().getBoard().getCharacter11().getCostOfActivation());
        assertEquals(4,Game.getInstance().getBoard().getCharacter11().getListOfStudentsOnCharacter().size());
    }

}