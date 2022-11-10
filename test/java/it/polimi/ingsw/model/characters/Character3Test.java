package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.NotActiveException;

import static org.junit.jupiter.api.Assertions.*;

class Character3Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK,0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testActivateTheEffect() throws MotherNatureNotOnIslandException, NotEnoughInfluenceException, CharacterNotInGameException, ProhibitionCardOnIslandException, AlreadyOwnIslandException, NotEnoughMoneyException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        Game.getInstance().setNumberOfPlayersInGame(2);
        Game.getInstance().getBoard().getPlayersInGame().add(p);
        Game.getInstance().getBoard().getPlayersInGame().add(new Player("a",TowerColor.WHITE,1));
        //Call activateTheEffect without money
        try{
            Game.getInstance().getBoard().getCharacter3().activateTheEffect(p,0);
            fail();
        } catch(NotEnoughMoneyException e){
            assertTrue(true);
        }
        assertEquals(1,p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(3);
        try{
            Game.getInstance().getBoard().getCharacter3().activateTheEffect(p,0);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(3,p.getNumberOfCoins());
        // Call activateTheEffect with money and isOnGame, but he can't build a tower because the player has not
        // enough money
        Game.getInstance().getBoard().getCharacter3().setInGame(true);
        try{
            Game.getInstance().getBoard().getCharacter3().activateTheEffect(p,0);
            fail();
        } catch(NotEnoughInfluenceException e){
            assertTrue(true);
        }
        assertEquals(0,Game.getInstance().getBoard().getIslandsInGame().get(0).getNumberOfBuiltedTowers());
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(4,Game.getInstance().getBoard().getCharacter3().getCostOfActivation());
        assertFalse(Game.getInstance().getBoard().getIslandsInGame().get(0).isCharacter3Effect());
        // Call activateTheEffect with money and isOnGame, and the player can build a tower
        p.setNumberOfCoins(4);
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.RED);
        p.getDashboard().setRedTeacherPresent(true);
        Game.getInstance().getBoard().getCharacter3().activateTheEffect(p,0);
        assertEquals(1,Game.getInstance().getBoard().getIslandsInGame().get(0).getNumberOfBuiltedTowers());
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(5,Game.getInstance().getBoard().getCharacter3().getCostOfActivation());
        assertFalse(Game.getInstance().getBoard().getIslandsInGame().get(0).isCharacter3Effect());
    }

}