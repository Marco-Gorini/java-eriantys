package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Character6Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK, 0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testActivateTheEffect() throws NotEnoughMoneyException, CharacterNotInGameException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        //Call activateTheEffect without money
        try{
            Game.getInstance().getBoard().getCharacter6().activateTheEffect(p,0);
            fail();
        } catch(NotEnoughMoneyException e){
            assertTrue(true);
        }
        assertEquals(1,p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(3);
        try{
            Game.getInstance().getBoard().getCharacter6().activateTheEffect(p,0);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(3,p.getNumberOfCoins());
        //Call the effect correctly
        Game.getInstance().getBoard().getCharacter6().setInGame(true);
        Game.getInstance().getBoard().getCharacter6().activateTheEffect(p,0);
        assertTrue(Game.getInstance().getBoard().getIslandsInGame().get(0).isCharacter6Effect());
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(4,Game.getInstance().getBoard().getCharacter6().getCostOfActivation());
        assertTrue(Game.getInstance().getBoard().getIslandsInGame().get(0).isCharacter6Effect());
    }

}