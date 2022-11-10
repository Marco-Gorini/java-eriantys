package it.polimi.ingsw.model.gamemodel;

import it.polimi.ingsw.exception.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
    Player p = new Player("luciano", TowerColor.BLACK, 0);

    @BeforeAll
    public static void reset() {
        Game.getInstance().reset();
    }


    @Test
    public void testAddStudentOnIsland() throws StudentsAreNotInEntranceException {
        Game.getInstance().reset();
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        //Adding student on entrance, then I want to add it on Island.Size() of studentsOnIsland has to be 1
        p.getDashboard().getEntrance().add(PawnColor.BLUE);
        Game.getInstance().getBoard().getIslandsInGame().get(0).addStudentOnIsland(p, PawnColor.BLUE);
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().size());
        //Adding a student that is not in the entrance.Size() of studentsOnIsland has to be 1
        try {
            Game.getInstance().getBoard().getIslandsInGame().get(0).addStudentOnIsland(p, PawnColor.GREEN);
            fail();
        } catch (StudentsAreNotInEntranceException e) {
            assertTrue(true);
        }
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().size());
    }

    @Test
    public void testCheckInfluence() {
        Game.getInstance().reset();
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        //* BlueTeacher present, add blue student on island and verify that Influence is 1.
        p.getDashboard().blueTeacherPresent = true;
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.BLUE);
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(0).checkInfluence(p));
        //* Blue Teacher present,Green Teacher present, verify that the influence is 1.
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.GREEN);
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(0).checkInfluence(p));
        //* Build tower, set Owner Player, and verify that influence is incremented at 2.
        Game.getInstance().getBoard().getIslandsInGame().get(0).setNumberOfBuiltedTowers(1);
        Game.getInstance().getBoard().getIslandsInGame().get(0).setPlayerIndex(p.getPlayerIndex());
        assertEquals(2, Game.getInstance().getBoard().getIslandsInGame().get(0).checkInfluence(p));
        //* Set character6 effect to true, and verify that the influence given by numberOfBuiltedTower is not considerated.
        Game.getInstance().getBoard().getIslandsInGame().get(0).setCharacter6Effect(true);
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(0).checkInfluence(p));
        //* Set character9 effect to true and set choosenPawnColorFromCharacter9 == PawnColor.BLUE.Then influence has to be 0
        Game.getInstance().getBoard().getIslandsInGame().get(0).setCharacter9Effect(true);
        Game.getInstance().getBoard().getIslandsInGame().get(0).setchosenPawnColorFromCharacter9(PawnColor.BLUE);
        assertEquals(0, Game.getInstance().getBoard().getIslandsInGame().get(0).checkInfluence(p));
        //° Set choosenPawnColorFromCharacter9 == PawnCOlor.GREEN. The influence has to be 1
        Game.getInstance().getBoard().getIslandsInGame().get(0).setchosenPawnColorFromCharacter9(PawnColor.GREEN);
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(0).checkInfluence(p));
        //*Setting character 8 in player, means that to the influence on the island has to be added 2
        p.setCharacter8Effect(true);
        assertEquals(3, Game.getInstance().getBoard().getIslandsInGame().get(0).checkInfluence(p));
    }

    @Test
    public void testBuildTower() throws MotherNatureNotOnIslandException, NotEnoughInfluenceException, ProhibitionCardOnIslandException, AlreadyOwnIslandException {
        Game.getInstance().reset();
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        //*Setting proibithion card true, this means that Player can't build tower and after that the prhoibithionCard is added on the character
        //* and that prhoibition card is set to false after that
        Game.getInstance().getBoard().getIslandsInGame().get(0).setHasProhibitionCard(true);
        Game.getInstance().getBoard().getIslandsInGame().get(0).setMotherNaturePresent(true);
        try {
            Game.getInstance().getBoard().getIslandsInGame().get(0).buildTower(p);
            fail();
        } catch (ProhibitionCardOnIslandException e) {
            assertTrue(true);
        }
        assertEquals(0, Game.getInstance().getBoard().getIslandsInGame().get(0).getNumberOfBuiltedTowers());
        assertEquals(5, Game.getInstance().getBoard().getCharacter5().getNumberOfPrhoibitionCard());
        assertFalse(Game.getInstance().getBoard().getIslandsInGame().get(0).isHasProhibitionCard());
        //*Building Tower normally after set the influence to 1 adding a Blue Student
        p.getDashboard().setBlueTeacherPresent(true);
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.BLUE);
        Game.getInstance().setNumberOfPlayersInGame(2);
        Game.getInstance().getBoard().getIslandsInGame().get(0).buildTower(p);
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(0).getNumberOfBuiltedTowers());
        //Try to build a tower on the same island
        try {
            Game.getInstance().getBoard().getIslandsInGame().get(0).buildTower(p);
            fail();
        } catch (AlreadyOwnIslandException e) {
            assertTrue(true);
        }
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(0).getNumberOfBuiltedTowers());
        //Adding a new Island in game, then set MotherNature on that Island as false, then trhowing MotherNatureexception
        Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        try {
            Game.getInstance().getBoard().getIslandsInGame().get(1).buildTower(p);
            fail();
        } catch (MotherNatureNotOnIslandException e) {
            assertTrue(true);
        }
        //*Setting mother nature false but activating characater 3, i can build the same, and then reset character3 effect false
        Game.getInstance().getBoard().getIslandsInGame().get(1).setCharacter3Effect(true);
        Game.getInstance().getBoard().getIslandsInGame().get(1).buildTower(p);
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(1).getNumberOfBuiltedTowers());
        assertFalse(Game.getInstance().getBoard().getIslandsInGame().get(1).isCharacter3Effect());
        //Testing the building of tower if there is already a owner
        Player p1 = new Player("Andrea", TowerColor.WHITE, 1);
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        Game.getInstance().getBoard().getIslandsInGame().get(2).getStudentsOnIsland().add(PawnColor.GREEN);
        p1.getDashboard().setGreenTeacherPresent(true);
        Game.getInstance().getBoard().getIslandsInGame().get(2).setMotherNaturePresent(true);
        Game.getInstance().getBoard().getIslandsInGame().get(2).buildTower(p1);
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(2).getPlayerIndex());
        Game.getInstance().getBoard().getIslandsInGame().get(2).getStudentsOnIsland().add(PawnColor.BLUE);
        Game.getInstance().getBoard().getIslandsInGame().get(2).getStudentsOnIsland().add(PawnColor.BLUE);
        Game.getInstance().getBoard().getIslandsInGame().get(2).getStudentsOnIsland().add(PawnColor.BLUE);
        Game.getInstance().getBoard().getIslandsInGame().get(2).buildTower(p);
        assertEquals(0, Game.getInstance().getBoard().getIslandsInGame().get(2).getPlayerIndex());
        assertEquals(2, Game.getInstance().getBoard().getIslandsInGame().get(2).getNumberOfBuiltedTowers());
        //Testing for number of players = 4
        Game.getInstance().setNumberOfPlayersInGame(4);
        Game.getInstance().getBoard().getPlayersInGame().add(p);
        Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        Game.getInstance().getBoard().getIslandsInGame().get(3).setMotherNaturePresent(true);
        Game.getInstance().getBoard().getIslandsInGame().get(3).getStudentsOnIsland().add(PawnColor.BLUE);
        assertEquals(2, Game.getInstance().getBoard().getPlayersInGame().size());
        Game.getInstance().getBoard().getIslandsInGame().get(3).buildTower(p);
        assertEquals(1, Game.getInstance().getBoard().getIslandsInGame().get(3).getNumberOfBuiltedTowers());
        //Testing with already a ownerTeam
        Game.getInstance().getBoard().getIslandsInGame().get(3).getStudentsOnIsland().add(PawnColor.GREEN);
        Game.getInstance().getBoard().getIslandsInGame().get(3).getStudentsOnIsland().add(PawnColor.GREEN);
        Game.getInstance().getBoard().getIslandsInGame().get(3).getStudentsOnIsland().add(PawnColor.GREEN);
        Game.getInstance().getBoard().getIslandsInGame().get(3).buildTower(p1);
        assertEquals(TowerColor.WHITE, Game.getInstance().getBoard().getIslandsInGame().get(3).getTeamColor());
        assertEquals(2, Game.getInstance().getBoard().getIslandsInGame().get(3).getNumberOfBuiltedTowers());
        //Testing Black team
        for (int i = 0; i < 5; i++) {
            Game.getInstance().getBoard().getIslandsInGame().get(3).getStudentsOnIsland().add(PawnColor.BLUE);
        }
        Game.getInstance().getBoard().getIslandsInGame().get(3).buildTower(p);
        assertEquals(TowerColor.BLACK, Game.getInstance().getBoard().getIslandsInGame().get(3).getTeamColor());
        assertEquals(3, Game.getInstance().getBoard().getIslandsInGame().get(3).getNumberOfBuiltedTowers());
    }

    @Test
    public void testBuildTower2() throws MotherNatureNotOnIslandException, NotEnoughInfluenceException, ProhibitionCardOnIslandException, AlreadyOwnIslandException {
        Game.getInstance().reset();
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().getBoard().getPlayersInGame().add(p);
        p.getDashboard().setNumberOfTowers(8);
        Player pl = new Player("luciano",TowerColor.WHITE,1);
        Game.getInstance().getBoard().getPlayersInGame().add(pl);
        Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        p.getDashboard().setRedTeacherPresent(true);
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.RED);
        assertEquals(1,Game.getInstance().getBoard().getIslandsInGame().get(0).checkInfluence(p));
        Game.getInstance().getBoard().getIslandsInGame().get(0).setMotherNaturePresent(true);
        Game.getInstance().setNumberOfPlayersInGame(2);
        Game.getInstance().getBoard().getIslandsInGame().get(0).buildTower(p);
        assertEquals(1,Game.getInstance().getBoard().getIslandsInGame().get(0).getNumberOfBuiltedTowers());
        assertEquals(0,Game.getInstance().getBoard().getIslandsInGame().get(0).getPlayerIndex());
        assertEquals(7,p.getDashboard().getNumberOfTowers());
        //Testing other conditions
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.RED);
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.RED);
        assertEquals(4,Game.getInstance().getBoard().getIslandsInGame().get(0).checkInfluence(p));
        pl.getDashboard().setGreenTeacherPresent(true);
        pl.getDashboard().setYellowTeacherPresent(true);
        pl.getDashboard().setPinkTeacherPresent(true);
        pl.getDashboard().setBlueTeacherPresent(true);
        pl.getDashboard().setNumberOfTowers(8);
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.BLUE);
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.YELLOW);
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.GREEN);
        assertEquals(3,Game.getInstance().getBoard().getIslandsInGame().get(0).checkInfluence(pl));
        try {
            Game.getInstance().getBoard().getIslandsInGame().get(0).buildTower(pl);
            fail();
        } catch (NotEnoughInfluenceException e) {
            assertTrue(true);
        }
        //Other condition
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.GREEN);
        Game.getInstance().getBoard().getIslandsInGame().get(0).getStudentsOnIsland().add(PawnColor.GREEN);
        Game.getInstance().getBoard().getIslandsInGame().get(0).buildTower(pl);
        assertEquals(2,Game.getInstance().getBoard().getIslandsInGame().get(0).getNumberOfBuiltedTowers());
        assertEquals(8,p.getDashboard().getNumberOfTowers());
        assertEquals(6,pl.getDashboard().getNumberOfTowers());
    }
}
