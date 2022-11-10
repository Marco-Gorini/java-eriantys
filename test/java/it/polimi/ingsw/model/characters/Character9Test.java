package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Character9Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK, 0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testActivateTheEffect() throws NotEnoughMoneyException, CharacterNotInGameException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        PawnColor pc = PawnColor.RED;
        //InitializateIslandsInGame
        for(int i = 0; i < 12; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island()) ;
        }
        //Call activateTheEffect without money
        p.setNumberOfCoins(0);
        try{
            Game.getInstance().getBoard().getCharacter9().activateTheEffect(p, pc);
            fail();
        } catch(NotEnoughMoneyException e){
            assertTrue(true);
        }
        assertEquals(0, p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(3);
        try{
            Game.getInstance().getBoard().getCharacter9().activateTheEffect(p, pc);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(3,p.getNumberOfCoins());
        //Call the effect correctly
        Game.getInstance().getBoard().getCharacter9().setInGame(true);
        Game.getInstance().getBoard().getCharacter9().activateTheEffect(p,pc);
        for(Island i : Game.getInstance().getBoard().getIslandsInGame()){
            assertTrue(i.isCharacter9Effect());
            assertEquals(pc,i.getChosenPawnColorFromCharacter9());
        }
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(4,Game.getInstance().getBoard().getCharacter9().getCostOfActivation());
    }
}