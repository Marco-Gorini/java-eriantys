package it.polimi.ingsw.model.gamemodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariantTest {

    @Test
    public void testFromValue(){
        try{
            Variant.fromValue("hi");
            fail();
        } catch(IllegalArgumentException e){
            assertTrue(true);
        }
        assertEquals(Variant.EXPERT,Variant.fromValue("expert"));
    }
}