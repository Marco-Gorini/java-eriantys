package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Character7Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK, 0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testInitializateCharacter(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        //Inizializate Bag to test correctly
        for(int i = 0; i < 3; i++){
            Game.getInstance().getBoard().getBag().getListOfStudentsInBag().add(PawnColor.RED);
        }
        for(int i = 0; i < 3; i++){
            Game.getInstance().getBoard().getBag().getListOfStudentsInBag().add(PawnColor.BLUE);
        }
        Game.getInstance().getBoard().getCharacter7().initializateCharacter(Game.getInstance().getBoard().getBag());
        assertEquals(6,Game.getInstance().getBoard().getCharacter7().getListOfStudentsOnCharacter().size());
    }

    @Test
    public void testActivateTheEffect() throws NotEnoughMoneyException, CharacterNotInGameException, StudentsIsNotOnCharacterException, StudentsAreNotInEntranceException, DifferentSizesException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        List<PawnColor> pawnColorChoosenFromEntrance = new ArrayList<>();
        List<PawnColor> pawnColorChoosenFromCharacter = new ArrayList<>();
        //Initializate Character
        for(int i = 0; i < 3; i++){
            Game.getInstance().getBoard().getCharacter7().getListOfStudentsOnCharacter().add(PawnColor.RED);
        }
        for(int i = 0; i < 3; i++){
            Game.getInstance().getBoard().getCharacter7().getListOfStudentsOnCharacter().add(PawnColor.BLUE);
        }
        //Inizializate Player's entrance
        p.getDashboard().getEntrance().add(PawnColor.BLUE);
        p.getDashboard().getEntrance().add(PawnColor.BLUE);
        p.getDashboard().getEntrance().add(PawnColor.GREEN);
        //Inizializate ListOfStudent the player want to exchange in entrance
        pawnColorChoosenFromEntrance.add(PawnColor.BLUE);
        pawnColorChoosenFromEntrance.add(PawnColor.BLUE);
        //Inizializate ListOfStudent the player want to exchange in Character
        pawnColorChoosenFromCharacter.add(PawnColor.RED);
        pawnColorChoosenFromCharacter.add(PawnColor.BLUE);
        pawnColorChoosenFromCharacter.add(PawnColor.GREEN);
        pawnColorChoosenFromCharacter.add(PawnColor.YELLOW);
        //Call activateTheEffect without money
        p.setNumberOfCoins(0);
        try{
            Game.getInstance().getBoard().getCharacter7().activateTheEffect(p,pawnColorChoosenFromEntrance,pawnColorChoosenFromCharacter);
            fail();
        } catch(NotEnoughMoneyException e){
            assertTrue(true);
        }
        assertEquals(0,p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(1);
        try{
            Game.getInstance().getBoard().getCharacter7().activateTheEffect(p,pawnColorChoosenFromEntrance,pawnColorChoosenFromCharacter);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(1,p.getNumberOfCoins());
        // Call activateTheEffect with money and isOnGame, but the size of 2 list is different
        Game.getInstance().getBoard().getCharacter7().setInGame(true);
        try{
            Game.getInstance().getBoard().getCharacter7().activateTheEffect(p,pawnColorChoosenFromEntrance,pawnColorChoosenFromCharacter);
            fail();
        } catch(DifferentSizesException e){
            assertTrue(true);
        }        assertEquals(1,p.getNumberOfCoins());
        // Iniziaizate List with same size, but there are not correct students on the lists
        pawnColorChoosenFromEntrance.add(PawnColor.BLUE);
        pawnColorChoosenFromCharacter.remove(PawnColor.YELLOW);
        try{
            Game.getInstance().getBoard().getCharacter7().activateTheEffect(p,pawnColorChoosenFromEntrance,pawnColorChoosenFromCharacter);
            fail();
        } catch(StudentsIsNotOnCharacterException | StudentsAreNotInEntranceException e){
            assertTrue(true);
        }        assertEquals(1,p.getNumberOfCoins());
        //Call the effect correctly
        pawnColorChoosenFromCharacter.remove(PawnColor.GREEN);
        pawnColorChoosenFromCharacter.remove(PawnColor.RED);
        pawnColorChoosenFromCharacter.add(PawnColor.BLUE);
        pawnColorChoosenFromEntrance.remove(PawnColor.BLUE);
        Game.getInstance().getBoard().getCharacter7().activateTheEffect(p,pawnColorChoosenFromEntrance,pawnColorChoosenFromCharacter);
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(2,Game.getInstance().getBoard().getCharacter7().getCostOfActivation());
    }
}