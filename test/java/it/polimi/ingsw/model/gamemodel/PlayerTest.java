package it.polimi.ingsw.model.gamemodel;

import it.polimi.ingsw.exception.AssistantAlreadyBeenPlayedException;
import it.polimi.ingsw.exception.WrongAssistantIndexEcxeption;
import it.polimi.ingsw.exception.WrongMotherNatureMovement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player p = new Player("MarcoGorini", TowerColor.BLACK,0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }
    
    @Test
    public void testMoveMotherNature() throws WrongMotherNatureMovement {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        //Inizializate mother nature present on island 0 and assistant played this turn in player
        Assistant a = new Assistant(1,4);
        p.setPlayedAssistantThisTurn(a);
        for(int i = 0; i < 12; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        Game.getInstance().getBoard().getIslandsInGame().get(0).setMotherNaturePresent(true);
        Game.getInstance().getBoard().getPlayersInGame().add(p);
        //Call the method with wrong index (0)
        try{
            p.moveMotherNature(0);
            fail();
        } catch(WrongMotherNatureMovement e){
            assertTrue(true);
        }
        assertTrue(Game.getInstance().getBoard().getIslandsInGame().get(0).isMotherNaturePresent());
        //Call the method with wrong index (5)
        try{
            p.moveMotherNature(0);
            fail();
        } catch(WrongMotherNatureMovement e){
            assertTrue(true);
        }
        assertTrue(Game.getInstance().getBoard().getIslandsInGame().get(0).isMotherNaturePresent());
        //Call the method with correct index
        p.moveMotherNature(4);
        assertTrue(Game.getInstance().getBoard().getIslandsInGame().get(4).isMotherNaturePresent());
        assertFalse(Game.getInstance().getBoard().getIslandsInGame().get(0).isMotherNaturePresent());
        assertEquals(0,Game.getInstance().getBoard().getIslandsInGame().get(4).getNumberOfBuiltedTowers());
        //Call the method is MotherNature is on island11 and i want to move it of 1
        Game.getInstance().getBoard().getIslandsInGame().get(4).setMotherNaturePresent(false);
        Game.getInstance().getBoard().getIslandsInGame().get(11).setMotherNaturePresent(true);
        Game.getInstance().getBoard().setMotherNaturePosition(11);
        p.moveMotherNature(1);
        assertTrue(Game.getInstance().getBoard().getIslandsInGame().get(0).isMotherNaturePresent());
        assertFalse(Game.getInstance().getBoard().getIslandsInGame().get(11).isMotherNaturePresent());
        assertEquals(0,Game.getInstance().getBoard().getIslandsInGame().get(0).getNumberOfBuiltedTowers());
    }

    @Test
    public void testPlayAssistant() throws AssistantAlreadyBeenPlayedException, WrongAssistantIndexEcxeption {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Assistant a = new Assistant(1,1);
        Assistant a1 = new Assistant(2,2);
        //Calling the method with hand size = 0
        p.playAssistant(1);
        assertEquals(0,Game.getInstance().getBoard().getPlayedAssistantThisTurn().size());
        //Calling the method with wrong index
        p.getHand().add(a);
        try{
            p.playAssistant(11);
            fail();
        } catch(WrongAssistantIndexEcxeption e){
            assertTrue(true);
        }
        assertEquals(0,Game.getInstance().getBoard().getPlayedAssistantThisTurn().size());
        //Calling the method with correct index, but assistant was already played this turn and player can player another assistant
        Game.getInstance().getBoard().getPlayedAssistantThisTurn().add(a);
        p.getHand().add(a1);
        try{
            p.playAssistant(0);
            fail();
        } catch(AssistantAlreadyBeenPlayedException e){
            assertTrue(true);
        }
        assertEquals(1,Game.getInstance().getBoard().getPlayedAssistantThisTurn().size());
        assertEquals(2,p.getHand().size());
        //Calling the method with correct index,  assistant was already played this turn but player has only 1 card
        p.getHand().remove(a1);
        p.playAssistant(0);
        assertEquals(2,Game.getInstance().getBoard().getPlayedAssistantThisTurn().size());
        assertEquals(0,p.getHand().size());


    }

}