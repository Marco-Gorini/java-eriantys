package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;
import it.polimi.ingsw.model.gamemodel.TowerColor;
import it.polimi.ingsw.model.gamemodel.Variant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Character8Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK, 0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testActivateTheEffect() throws NotEnoughMoneyException, CharacterNotInGameException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        //Call activateTheEffect without money
        try{
            Game.getInstance().getBoard().getCharacter8().activateTheEffect(p);
            fail();
        } catch(NotEnoughMoneyException e){
            assertTrue(true);
        }
        assertEquals(1,p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(2);
        try{
            Game.getInstance().getBoard().getCharacter8().activateTheEffect(p);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(2,p.getNumberOfCoins());
        //Call the effect correctly
        Game.getInstance().getBoard().getCharacter8().setInGame(true);
        Game.getInstance().getBoard().getCharacter8().activateTheEffect(p);
        assertTrue(p.isCharacter8Effect());
        assertEquals(0,p.getNumberOfCoins());
        assertTrue(p.isCharacter8Effect());
        assertEquals(3,Game.getInstance().getBoard().getCharacter8().getCostOfActivation());
    }

}