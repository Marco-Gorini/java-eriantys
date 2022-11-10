package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Character10Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK, 0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testActivateTheEffect() throws NotEnoughMoneyException, CharacterNotInGameException, StudentsAreNotInHallException, StudentsAreNotInEntranceException, FullRowException, DifferentSizesException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        //Inizializate player's entrance
        p.getDashboard().getEntrance().add(PawnColor.BLUE);
        p.getDashboard().getEntrance().add(PawnColor.BLUE);
        //Inizializate player's hall
        p.getDashboard().getRedRowHall().add(PawnColor.RED);
        p.getDashboard().getYellowRowHall().add(PawnColor.YELLOW);
        //Inizializate player's list of choosenEntrance
        List<PawnColor> entToChange = new ArrayList<>();
        entToChange.add(PawnColor.RED);
        entToChange.add(PawnColor.BLUE);
        entToChange.add(PawnColor.RED);
        //Inizializate player's list of choosenHall
        List<PawnColor> hallToChange = new ArrayList<>();
        hallToChange.add(PawnColor.RED);
        hallToChange.add(PawnColor.GREEN);
        //Call activateTheEffect without money
        p.setNumberOfCoins(0);
        try{
            Game.getInstance().getBoard().getCharacter10().activateTheEffect(p,entToChange,hallToChange);
            fail();
        } catch(NotEnoughMoneyException e){
            assertTrue(true);
        }
        assertEquals(0, p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(1);
        try{
            Game.getInstance().getBoard().getCharacter10().activateTheEffect(p,entToChange,hallToChange);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(1,p.getNumberOfCoins());
        // Call activateTheEffect with money and isOnGame, but the size of 2 list is different
        Game.getInstance().getBoard().getCharacter10().setInGame(true);
        try{
            Game.getInstance().getBoard().getCharacter10().activateTheEffect(p,entToChange,hallToChange);
            fail();
        } catch(DifferentSizesException e){
            assertTrue(true);
        }
        assertEquals(1,p.getNumberOfCoins());
        // Inizializate List with same size, but there are not correct students on the list of entrance
        entToChange.remove(PawnColor.RED);
        try{
            Game.getInstance().getBoard().getCharacter10().activateTheEffect(p,entToChange,hallToChange);
            fail();
        } catch(StudentsAreNotInEntranceException e){
            assertTrue(true);
        }
        assertEquals(1,p.getNumberOfCoins());
        //Correct list in entrance, but wrong in hall
        entToChange.remove(PawnColor.RED);
        hallToChange.remove(PawnColor.RED);
        try{
            Game.getInstance().getBoard().getCharacter10().activateTheEffect(p,entToChange,hallToChange);
            fail();
        } catch(StudentsAreNotInHallException e){
            assertTrue(true);
        }
        //Call the effect correctly
        hallToChange.add(PawnColor.RED);
        hallToChange.remove(PawnColor.GREEN);
        Game.getInstance().getBoard().getCharacter10().activateTheEffect(p,entToChange,hallToChange);
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(2,Game.getInstance().getBoard().getCharacter10().getCostOfActivation());
        assertEquals(2,p.getDashboard().getEntrance().size());
    }

}