package it.polimi.ingsw.model.gamemodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag = new Bag();

    @Test
    public void testInizializateBag(){
        bag.initializateBag();
        assertEquals(130,bag.getListOfStudentsInBag().size());
    }
}