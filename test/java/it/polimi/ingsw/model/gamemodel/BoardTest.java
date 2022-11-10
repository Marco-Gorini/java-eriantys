package it.polimi.ingsw.model.gamemodel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Player p1 = new Player("Marco",TowerColor.BLACK,0);
    Player p2 = new Player("Andrea",TowerColor.WHITE,1);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testUnifyIsland(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        //Inizializate islandsInGame and call unifyIsland with wrong indexes
        for(int i = 0; i < 12; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        Game.getInstance().getBoard().unifyIsland(0,6);
        assertEquals(12,Game.getInstance().getBoard().getIslandsInGame().size());
        //Call the method with right indexes 0-1, but wrong owners
        Game.getInstance().getBoard().getIslandsInGame().get(0).setPlayerIndex(p1.getPlayerIndex());
        Game.getInstance().getBoard().getIslandsInGame().get(1).setPlayerIndex(p2.getPlayerIndex());
        Game.getInstance().getBoard().unifyIsland(0,1);
        assertEquals(12,Game.getInstance().getBoard().getIslandsInGame().size());
        //Inizializate listOfStudentsOnIsland for each Island to test
        Game.getInstance().getBoard().getIslandsInGame().get(1).setPlayerIndex(p1.getPlayerIndex());
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.RED);
        Game.getInstance().getBoard().getIslandsInGame().get(1).getStudentsOnIsland().add(PawnColor.RED);

        //Call the method with right indexes 0-1 and right owners.
        Game.getInstance().getBoard().unifyIsland(0,1);
        assertEquals(11,Game.getInstance().getBoard().getIslandsInGame().size());
        assertEquals(2,Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().size());
        //Call the method with right indexes 0-10 and right owners.
        Game.getInstance().getBoard().getIslandsInGame().get(10).setPlayerIndex(p1.getPlayerIndex());
        Game.getInstance().getBoard().getIslandsInGame().get(10).getStudentsOnIsland().add(PawnColor.RED);
        Game.getInstance().getBoard().unifyIsland(10,0);
        assertEquals(10,Game.getInstance().getBoard().getIslandsInGame().size());
        assertEquals(3,Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().size());
        //Call the method with 4 islands in game, and then call the endgame condition
        for(int i = 0; i < 6; i++){
            Game.getInstance().getBoard().getIslandsInGame().remove(0);
        }
        Game.getInstance().getBoard().getIslandsInGame().get(1).setPlayerIndex(p1.getPlayerIndex());
        Game.getInstance().getBoard().getIslandsInGame().get(0).setPlayerIndex(p1.getPlayerIndex());
        Game.getInstance().getBoard().unifyIsland(1,0);
        assertEquals(3,Game.getInstance().getBoard().getIslandsInGame().size());
    }

    @Test
    public void testPutStudentsOnCloud(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        //inizializate gameListOfPlayers,bag and clouds, then call and i expect first if of method
        for(int i = 0; i < 6; i++){
            Game.getInstance().getBoard().getBag().getListOfStudentsInBag().add(PawnColor.RED);
        }
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().getClouds().put(0,new ArrayList<>());
        Game.getInstance().getBoard().getClouds().put(1,new ArrayList<>());
        Game.getInstance().getBoard().putStudentsOnCloud();
        assertEquals(3,Game.getInstance().getBoard().getClouds().get(0).size());
        assertEquals(3,Game.getInstance().getBoard().getClouds().get(1).size());
        assertEquals(0,Game.getInstance().getBoard().getBag().getListOfStudentsInBag().size());
        //remove 1 student from bag and then i enter in the else branch
        Game.getInstance().getBoard().putStudentsOnCloud();
        assertEquals(3,Game.getInstance().getBoard().getClouds().get(0).size());
        assertEquals(3,Game.getInstance().getBoard().getClouds().get(1).size());
    }

    @Test
    public void testCheckRedTeacher(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        //When all players has 0 students in RowHall, all players have isRedTeacherPresent = false
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().checkRedTeacher();
        for(Player p : Game.getInstance().getBoard().getPlayersInGame()){
            assertFalse(p.getDashboard().isRedTeacherPresent());
        }
        //When p1 has more red students, p1 has isRedTeacherPresent = true
        p1.getDashboard().getRedRowHall().add(PawnColor.RED);
        Game.getInstance().getBoard().checkRedTeacher();
        assertTrue(p1.getDashboard().isRedTeacherPresent());
        assertFalse(p2.getDashboard().isRedTeacherPresent());
        //When p2 has more red students and p1 had red teacher,p1 isRedTeacherPresent = false and p2
        //isRedTeacherPresent = true
        p2.getDashboard().getRedRowHall().add(PawnColor.RED);
        p2.getDashboard().getRedRowHall().add(PawnColor.RED);
        Game.getInstance().getBoard().checkRedTeacher();
        assertTrue(p2.getDashboard().isRedTeacherPresent());
        assertFalse(p1.getDashboard().isRedTeacherPresent());
        //If number of red students in each hall is the same,redteacher stay with the precedent owner player
        p1.getDashboard().getRedRowHall().add(PawnColor.RED);
        Game.getInstance().getBoard().checkRedTeacher();
        assertTrue(p2.getDashboard().isRedTeacherPresent());
        assertFalse(p1.getDashboard().isRedTeacherPresent());
    }

    @Test
    public void testCheckBlueTeacher(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        //When all players has 0 students in RowHall, all players have isBlueTeacherPresent = false
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().checkBlueTeacher();
        for(Player p : Game.getInstance().getBoard().getPlayersInGame()){
            assertFalse(p.getDashboard().isBlueTeacherPresent());
        }
        //When p1 has more blue students, p1 has isBlueTeacherPresent = true
        p1.getDashboard().getBlueRowHall().add(PawnColor.BLUE);
        Game.getInstance().getBoard().checkBlueTeacher();
        assertTrue(p1.getDashboard().isBlueTeacherPresent());
        assertFalse(p2.getDashboard().isBlueTeacherPresent());
        //When p2 has more blue students and p1 had blue teacher,p1 isBlueTeacherPresent = false and p2
        //isBlueTeacherPresent = true
        p2.getDashboard().getBlueRowHall().add(PawnColor.BLUE);
        p2.getDashboard().getBlueRowHall().add(PawnColor.BLUE);
        Game.getInstance().getBoard().checkBlueTeacher();
        assertTrue(p2.getDashboard().isBlueTeacherPresent());
        assertFalse(p1.getDashboard().isBlueTeacherPresent());
        //If number of blue students in each hall is the same,blueteacher stay with the precedent owner player
        p1.getDashboard().getBlueRowHall().add(PawnColor.BLUE);
        Game.getInstance().getBoard().checkBlueTeacher();
        assertTrue(p2.getDashboard().isBlueTeacherPresent());
        assertFalse(p1.getDashboard().isBlueTeacherPresent());
    }

    @Test
    public void testCheckYellowTeacher(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        //When all players has 0 students in RowHall, all players have isYellowTeacherPresent = false
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().checkYellowTeacher();
        for(Player p : Game.getInstance().getBoard().getPlayersInGame()){
            assertFalse(p.getDashboard().isYellowTeacherPresent());
        }
        //When p1 has more yellow students, p1 has isYellowTeacherPresent = true
        p1.getDashboard().getYellowRowHall().add(PawnColor.YELLOW);
        Game.getInstance().getBoard().checkYellowTeacher();
        assertTrue(p1.getDashboard().isYellowTeacherPresent());
        assertFalse(p2.getDashboard().isYellowTeacherPresent());
        //When p2 has more yellow students and p1 had yellow teacher,p1 isYellowTeacherPresent = false and p2
        //isYellowTeacherPresent = true
        p2.getDashboard().getYellowRowHall().add(PawnColor.YELLOW);
        p2.getDashboard().getYellowRowHall().add(PawnColor.YELLOW);
        Game.getInstance().getBoard().checkYellowTeacher();
        assertTrue(p2.getDashboard().isYellowTeacherPresent());
        assertFalse(p1.getDashboard().isYellowTeacherPresent());
        //If number of yellow students in each hall is the same,yellowteacher stay with the precedent owner player
        p1.getDashboard().getYellowRowHall().add(PawnColor.YELLOW);
        Game.getInstance().getBoard().checkYellowTeacher();
        assertTrue(p2.getDashboard().isYellowTeacherPresent());
        assertFalse(p1.getDashboard().isYellowTeacherPresent());
    }

    @Test
    public void testCheckPinkTeacher(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        //When all players has 0 students in RowHall, all players have isPinkTeacherPresent = false
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().checkPinkTeacher();
        for(Player p : Game.getInstance().getBoard().getPlayersInGame()){
            assertFalse(p.getDashboard().isPinkTeacherPresent());
        }
        //When p1 has more pink students, p1 has isPinkTeacherPresent = true
        p1.getDashboard().getPinkRowHall().add(PawnColor.PINK);
        Game.getInstance().getBoard().checkPinkTeacher();
        assertTrue(p1.getDashboard().isPinkTeacherPresent());
        assertFalse(p2.getDashboard().isPinkTeacherPresent());
        //When p2 has more pink students and p1 had pink teacher,p1 isPinkTeacherPresent = false and p2
        //isPinkTeacherPresent = true
        p2.getDashboard().getPinkRowHall().add(PawnColor.PINK);
        p2.getDashboard().getPinkRowHall().add(PawnColor.PINK);
        Game.getInstance().getBoard().checkPinkTeacher();
        assertTrue(p2.getDashboard().isPinkTeacherPresent());
        assertFalse(p1.getDashboard().isPinkTeacherPresent());
        //If number of pink students in each hall is the same,pinkteacher stay with the precedent owner player
        p1.getDashboard().getPinkRowHall().add(PawnColor.PINK);
        Game.getInstance().getBoard().checkPinkTeacher();
        assertTrue(p2.getDashboard().isPinkTeacherPresent());
        assertFalse(p1.getDashboard().isPinkTeacherPresent());
    }

    @Test
    public void testCheckGreenTeacher(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        //When all players has 0 students in RowHall, all players have isGreenTeacherPresent = false
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().checkGreenTeacher();
        for(Player p : Game.getInstance().getBoard().getPlayersInGame()){
            assertFalse(p.getDashboard().isGreenTeacherPresent());
        }
        //When p1 has more green students, p1 has isGreenTeacherPresent = true
        p1.getDashboard().getGreenRowHall().add(PawnColor.GREEN);
        Game.getInstance().getBoard().checkGreenTeacher();
        assertTrue(p1.getDashboard().isGreenTeacherPresent());
        assertFalse(p2.getDashboard().isGreenTeacherPresent());
        //When p2 has more green students and p1 had green teacher,p1 isGreenTeacherPresent = false and p2
        //isGreenTeacherPresent = true
        p2.getDashboard().getGreenRowHall().add(PawnColor.GREEN);
        p2.getDashboard().getGreenRowHall().add(PawnColor.GREEN);
        Game.getInstance().getBoard().checkGreenTeacher();
        assertTrue(p2.getDashboard().isGreenTeacherPresent());
        assertFalse(p1.getDashboard().isGreenTeacherPresent());
        //If number of green students in each hall is the same,greenteacher stay with the precedent owner player
        p1.getDashboard().getGreenRowHall().add(PawnColor.GREEN);
        Game.getInstance().getBoard().checkGreenTeacher();
        assertTrue(p2.getDashboard().isGreenTeacherPresent());
        assertFalse(p1.getDashboard().isGreenTeacherPresent());
    }

}