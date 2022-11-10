package it.polimi.ingsw.model.gamemodel;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.exception.CloudAlreadyChoosenException;
import it.polimi.ingsw.exception.FullRowException;
import it.polimi.ingsw.exception.StudentsAreNotInEntranceException;
import it.polimi.ingsw.exception.WrongCloudIndexException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DashboardTest {
    Player p = new Player("luciano", TowerColor.BLACK, 0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testAddStudentInHall() throws StudentsAreNotInEntranceException, FullRowException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p);
        //Testing the first if
        p.getDashboard().getEntrance().add(PawnColor.BLUE);
        try{
            p.getDashboard().addStudentInHall(PawnColor.GREEN);
            fail();
        } catch(StudentsAreNotInEntranceException e){
            assertTrue(true);
        }
        assertEquals(0,p.getDashboard().getGreenRowHall().size());
        //Testing full row
        p.getDashboard().getEntrance().add(PawnColor.GREEN);
        for(int i = 0; i < 10; i++){
            p.getDashboard().getBlueRowHall().add(PawnColor.BLUE);
        }
        try{
            p.getDashboard().addStudentInHall(PawnColor.BLUE);
            fail();
        } catch(FullRowException e){
            assertTrue(true);
        }
        assertEquals(10,p.getDashboard().getBlueRowHall().size());
        //Testing normal method without assign coin
        p.getDashboard().addStudentInHall(PawnColor.GREEN);
        assertEquals(1,p.getDashboard().getGreenRowHall().size());
        assertEquals(1,p.getDashboard().getEntrance().size());
        p.getDashboard().getEntrance().add(PawnColor.YELLOW);
        p.getDashboard().addStudentInHall(PawnColor.YELLOW);
        assertEquals(1,p.getDashboard().getYellowRowHall().size());
        assertEquals(1,p.getDashboard().getEntrance().size());
        p.getDashboard().getEntrance().add(PawnColor.PINK);
        p.getDashboard().addStudentInHall(PawnColor.PINK);
        assertEquals(1,p.getDashboard().getPinkRowHall().size());
        assertEquals(1,p.getDashboard().getEntrance().size());
        //Testing normal method with assign coin
        p.getDashboard().getEntrance().add(PawnColor.RED);
        p.getDashboard().getEntrance().add(PawnColor.RED);
        p.getDashboard().getEntrance().add(PawnColor.RED);
        p.getDashboard().addStudentInHall(PawnColor.RED);
        p.getDashboard().addStudentInHall(PawnColor.RED);
        p.getDashboard().addStudentInHall(PawnColor.RED);
        assertEquals(1,p.getDashboard().getEntrance().size());
        assertEquals(3,p.getDashboard().getRedRowHall().size());
        assertEquals(2,p.getNumberOfCoins());
    }


    @Test
    public void testAssignCoinToOwnerPlayer(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p);
        //Testing the first if
        Game.getInstance().getBoard().setTotalNumberOfCoins(0);
        p.getDashboard().assignCoinToOwnerPlayer(Game.getInstance().getBoard());
        assertEquals(1,p.getNumberOfCoins());
        //Testing the method correctly;
        Game.getInstance().getBoard().setTotalNumberOfCoins(20);
        p.getDashboard().assignCoinToOwnerPlayer(Game.getInstance().getBoard());
        assertEquals(2,p.getNumberOfCoins());
        assertEquals(19,Game.getInstance().getBoard().getTotalNumberOfCoins());
    }

    @Test
    public void testAddStudentsInEntrance() throws WrongCloudIndexException, CloudAlreadyChoosenException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getClouds().put(0, new ArrayList<>());
        Game.getInstance().getBoard().getPlayersInGame().add(p);
        Bag b = new Bag();
        b.initializateBag();
        //Putting students on cloud 0, and then testing the first if
        for(int i = 0; i < 3; i++){
            Game.getInstance().getBoard().getClouds().get(0).add(b.drawStudent());
        }
        try{
            p.getDashboard().addStudentsInEntrance(-1);
            fail();
        } catch(WrongCloudIndexException e){
            assertTrue(true);
        }
        assertEquals(0,p.getDashboard().getEntrance().size());
        //Testing the second if
        try{
            p.getDashboard().addStudentsInEntrance(1);
            fail();
        } catch(WrongCloudIndexException e){
            assertTrue(true);
        }
        assertEquals(0,p.getDashboard().getEntrance().size());
        //Testing the third if, cloud was already choosen
        Game.getInstance().getBoard().getClouds().put(1, new ArrayList<>());
        Game.getInstance().getBoard().getPlayersInGame().add(p);
        Game.getInstance().getBoard().getPlayersInGame().add(new Player("g", TowerColor.GREY,1));
        try{
            p.getDashboard().addStudentsInEntrance(1);
            fail();
        } catch(CloudAlreadyChoosenException e){
            assertTrue(true);
        }
        //Testing the method correctly
        p.getDashboard().addStudentsInEntrance(0);
        assertEquals(3,p.getDashboard().getEntrance().size());
        assertEquals(0,Game.getInstance().getBoard().getClouds().get(0).size());
    }
}