package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.Character2Exception;
import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Character2Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK,0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testActivateTheEffect() throws NotEnoughMoneyException, CharacterNotInGameException, Character2Exception {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        //Call activateTheEffect without money
        p.setNumberOfCoins(1);
        try{
            Game.getInstance().getBoard().getCharacter2().activateTheEffect(p);
            fail();
        } catch(NotEnoughMoneyException | CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(1, p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(2);
        try{
            Game.getInstance().getBoard().getCharacter2().activateTheEffect(p);
            fail();
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(2, p.getNumberOfCoins());
        // Call activateTheEffect with money and isOnGame, but canActivate is false
        Game.getInstance().getBoard().getCharacter2().setInGame(true);
        try{
            Game.getInstance().getBoard().getCharacter2().activateTheEffect(p);
            fail();
        } catch(Character2Exception e){
            assertTrue(true);
        }
        assertEquals(2, p.getNumberOfCoins());
        // Call activateTheEffect with money,isOnGame,canActivate, but p == player in canActivate condition
        Game.getInstance().getBoard().getPlayersInGame().add(p);
        p.getDashboard().setRedTeacherPresent(true);
        Game.getInstance().getBoard().getCharacter2().activateTheEffect(p);
        assertEquals(2, p.getNumberOfCoins());
        // Call activateTheEffect with money,isOnGame,canActivate, p != player in canActivate condition, but redRowHall
        // of p.size() != redRowHall of player.size()
        Game.getInstance().getBoard().getPlayersInGame().add(new Player("AndreaFerrazzano",TowerColor.WHITE,0));
        p.getDashboard().setRedTeacherPresent(false);
        p.getDashboard().getRedRowHall().add(PawnColor.RED);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().getRedRowHall().add(PawnColor.RED);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().getRedRowHall().add(PawnColor.RED);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().setRedTeacherPresent(true);
        Game.getInstance().getBoard().getCharacter2().activateTheEffect(p);
        assertEquals(2, p.getNumberOfCoins());
        // Call activateTheEffect with money,isOnGame,canActivate, p != player in canActivate condition,redRowHall
        // of p.size() == redRowHall of player.size() but player has not RedTeacher
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().setRedTeacherPresent(false);
        p.getDashboard().getRedRowHall().add(PawnColor.RED);
        assertEquals(2,p.getNumberOfCoins());
        // Call activateTheEffect with money,isOnGame,canActivate, p != player in canActivate condition,redRowHall
        // of p.size() == redRowHall of player.size() and player owns RedTeacher
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().setRedTeacherPresent(true);
        Game.getInstance().getBoard().getCharacter2().activateTheEffect(p);
        assertEquals(0,p.getNumberOfCoins());
        assertFalse(Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().isRedTeacherPresent());
        assertTrue(p.getDashboard().isRedTeacherPresent());
        assertEquals(3,Game.getInstance().getBoard().getCharacter2().getCostOfActivation());
        // Call activateTheEffect with money,isOnGame,canActivate, p != player in canActivate condition,blueRowHall
        // of p.size() == blueRowHall of player.size() and player owns BlueTeacher
        p.setNumberOfCoins(3);
        Game.getInstance().getBoard().getPlayersInGame().get(0).getDashboard().getBlueRowHall().add(PawnColor.BLUE);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().getBlueRowHall().add(PawnColor.BLUE);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().setBlueTeacherPresent(true);
        Game.getInstance().getBoard().getCharacter2().activateTheEffect(p);
        assertEquals(0,p.getNumberOfCoins());
        assertFalse(Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().isBlueTeacherPresent());
        assertTrue(p.getDashboard().isBlueTeacherPresent());
        assertEquals(4,Game.getInstance().getBoard().getCharacter2().getCostOfActivation());
        // Call activateTheEffect with money,isOnGame,canActivate, p != player in canActivate condition,greenRowHall
        // of p.size() == greenRowHall of player.size() and player owns GreenTeacher
        p.setNumberOfCoins(4);
        Game.getInstance().getBoard().getPlayersInGame().get(0).getDashboard().getGreenRowHall().add(PawnColor.GREEN);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().getGreenRowHall().add(PawnColor.GREEN);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().setGreenTeacherPresent(true);
        Game.getInstance().getBoard().getCharacter2().activateTheEffect(p);
        assertEquals(0,p.getNumberOfCoins());
        assertFalse(Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().isGreenTeacherPresent());
        assertTrue(p.getDashboard().isGreenTeacherPresent());
        assertEquals(5,Game.getInstance().getBoard().getCharacter2().getCostOfActivation());
        // Call activateTheEffect with money,isOnGame,canActivate, p != player in canActivate condition,pinkRowHall
        // of p.size() == pinkRowHall of player.size() and player owns PinkTeacher
        p.setNumberOfCoins(5);
        Game.getInstance().getBoard().getPlayersInGame().get(0).getDashboard().getPinkRowHall().add(PawnColor.PINK);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().getPinkRowHall().add(PawnColor.PINK);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().setPinkTeacherPresent(true);
        Game.getInstance().getBoard().getCharacter2().activateTheEffect(p);
        assertEquals(0,p.getNumberOfCoins());
        assertFalse(Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().isPinkTeacherPresent());
        assertTrue(p.getDashboard().isPinkTeacherPresent());
        assertEquals(6,Game.getInstance().getBoard().getCharacter2().getCostOfActivation());
        // Call activateTheEffect with money,isOnGame,canActivate, p != player in canActivate condition,pinkRowHall
        // of p.size() == pinkRowHall of player.size() and player owns PinkTeacher
        p.setNumberOfCoins(6);
        Game.getInstance().getBoard().getPlayersInGame().get(0).getDashboard().getYellowRowHall().add(PawnColor.YELLOW);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().getYellowRowHall().add(PawnColor.YELLOW);
        Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().setYellowTeacherPresent(true);
        Game.getInstance().getBoard().getCharacter2().activateTheEffect(p);
        assertEquals(0,p.getNumberOfCoins());
        assertFalse(Game.getInstance().getBoard().getPlayersInGame().get(1).getDashboard().isYellowTeacherPresent());
        assertTrue(p.getDashboard().isYellowTeacherPresent());
        assertEquals(7,Game.getInstance().getBoard().getCharacter2().getCostOfActivation());
    }
}