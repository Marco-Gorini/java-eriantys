package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.AlreadyProhibitionCardException;
import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NoMoreProhibitionCardException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Character5Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK, 0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testActivateTheEffect() throws NoMoreProhibitionCardException, NotEnoughMoneyException, CharacterNotInGameException, AlreadyProhibitionCardException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        //Call activateTheEffect without money
        try{
            Game.getInstance().getBoard().getCharacter5().activateTheEffect(p,0);
            fail();
        } catch(NotEnoughMoneyException e){
            assertTrue(true);
        }
        assertEquals(1,p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(2);
        try{
            Game.getInstance().getBoard().getCharacter5().activateTheEffect(p,0);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(2,p.getNumberOfCoins());
        // Call activateTheEffect with money, with isInGame, but without prhoibition card
        Game.getInstance().getBoard().getCharacter5().setInGame(true);
        Game.getInstance().getBoard().getCharacter5().setNumberOfPrhoibitionCard(0);
        try{
            Game.getInstance().getBoard().getCharacter5().activateTheEffect(p,0);
            fail();
        } catch(NoMoreProhibitionCardException e){
            assertTrue(true);
        }
        assertEquals(2,p.getNumberOfCoins());
        // Call activateTheEffect with money, with isInGame,with prhoibition card on character, but on
        //Island there is a prhoibition card
        Game.getInstance().getBoard().getCharacter5().setNumberOfPrhoibitionCard(4);
        Game.getInstance().getBoard().getIslandsInGame().get(0).setHasProhibitionCard(true);
        try{
            Game.getInstance().getBoard().getCharacter5().activateTheEffect(p,0);
            fail();
        } catch(AlreadyProhibitionCardException e){
            assertTrue(true);
        }
        assertEquals(2,p.getNumberOfCoins());
        //Call the effect correctly
        Game.getInstance().getBoard().getIslandsInGame().get(0).setHasProhibitionCard(false);
        Game.getInstance().getBoard().getCharacter5().activateTheEffect(p,0);
        assertTrue(Game.getInstance().getBoard().getIslandsInGame().get(0).isHasProhibitionCard());
        assertEquals(3,Game.getInstance().getBoard().getCharacter5().getNumberOfPrhoibitionCard());
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(3,Game.getInstance().getBoard().getCharacter5().getCostOfActivation());
        assertTrue(Game.getInstance().getBoard().getIslandsInGame().get(0).isHasProhibitionCard());
    }
}