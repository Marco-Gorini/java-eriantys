package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Character4Test {

    Player p = new Player("MarcoGorini", TowerColor.BLACK,0);
    Assistant a = new Assistant(2,4);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testActivateTheEffect() throws NotEnoughMoneyException, CharacterNotInGameException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        //Call activateTheEffect without money
        p.setNumberOfCoins(0);
        try{
            Game.getInstance().getBoard().getCharacter4().activateTheEffect(p);
            fail();
        } catch(NotEnoughMoneyException e){
            assertTrue(true);
        }
        assertEquals(0,p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(1);
        try{
            Game.getInstance().getBoard().getCharacter4().activateTheEffect(p);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(1,p.getNumberOfCoins());
        // Correctly activate effect
        Game.getInstance().getBoard().getCharacter4().setInGame(true);
        p.setPlayedAssistantThisTurn(a);
        Game.getInstance().getBoard().getCharacter4().activateTheEffect(p);
        assertEquals(6,p.getPlayedAssistantThisTurn().getMotherNatureMovements());
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(2,Game.getInstance().getBoard().getCharacter4().getCostOfActivation());
    }

}