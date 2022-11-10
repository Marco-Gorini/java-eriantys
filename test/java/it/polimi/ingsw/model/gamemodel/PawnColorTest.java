package it.polimi.ingsw.model.gamemodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnColorTest {

    @Test
    public void testFromValue(){
        try{
            PawnColor.fromValue("ciao") ;
            fail();
        } catch(IllegalArgumentException e){
            assertTrue(true);
        }
        assertEquals(PawnColor.RED,PawnColor.fromValue("r"));
    }

}