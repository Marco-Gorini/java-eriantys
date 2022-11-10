package it.polimi.ingsw.model.gamemodel;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    
    Player p1 = new Player("Marco",TowerColor.BLACK,0);
    Player p2 = new Player("Andrea",TowerColor.WHITE,1);
    Player p3 = new Player("Luciano",TowerColor.GREY,2);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testInizializateGame1(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        //Testing all the things have to do inizializateGame if players are 2
        Game.getInstance().inizializateGame();
        for(Player p : Game.getInstance().getBoard().getPlayersInGame()){
            assertEquals(10,p.getHand().size());
        }
        assertEquals(12,Game.getInstance().getBoard().getIslandsInGame().size());
        assertTrue(Game.getInstance().getBoard().getIslandsInGame().get(Game.getInstance().getBoard().getMotherNaturePosition()).isMotherNaturePresent());
        for(int i = 0; i < 12; i++){
            if (!Game.getInstance().getBoard().getIslandsInGame().get(i).isMotherNaturePresent() && (i != Game.getInstance().getBoard().getMotherNaturePosition() + 6) && (i != Game.getInstance().getBoard().getMotherNaturePosition() - 6)){
                assertEquals(1,Game.getInstance().getBoard().getIslandsInGame().get(i).getStudentsOnIsland().size());
            }
        }
        for(Player p : Game.getInstance().getBoard().getPlayersInGame()){
            assertEquals(7,p.getDashboard().getEntrance().size());
        }
        for(int i = 0; i < Game.getInstance().getBoard().getPlayersInGame().size(); i++){
           assertEquals(3,Game.getInstance().getBoard().getClouds().get(i).size());
        }
    }

    @Test
    public void testInizializateGame2(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().getPlayersInGame().add(p3);
        Game.getInstance().inizializateGame();
        //Testing all the things have to do inizializateGame id players are 3
        for(Player p : Game.getInstance().getBoard().getPlayersInGame()){
            assertEquals(9,p.getDashboard().getEntrance().size());
        }
        for(int i = 0; i < Game.getInstance().getBoard().getPlayersInGame().size(); i++){
            assertEquals(4,Game.getInstance().getBoard().getClouds().get(i).size());
        }
    }

    @Test
    public void testEndGameCheckerCondition1(){
        //When Islands are 3, the game finishes and win who has the minimum of towers
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        p1.getDashboard().setNumberOfTowers(5);
        p2.getDashboard().setNumberOfTowers(3);
        for(int i = 0; i < 3; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        assertEquals(1,Game.getInstance().endGameChecker());
        //If players have the same number of Towers, wins who has the major number of teachers
        p1.getDashboard().setNumberOfTowers(3);
        p1.getDashboard().setRedTeacherPresent(true);
        assertEquals(0,Game.getInstance().endGameChecker());
    }

    @Test
    public void testEndGameCheckerCondition2(){
        //When hand of players is finished, the game finishes and win who has the minimum of towers
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        p1.getDashboard().setNumberOfTowers(5);
        p2.getDashboard().setNumberOfTowers(3);
        for(int i = 0; i < 4; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        assertEquals(1,Game.getInstance().endGameChecker());
        //Hand is over, but also number of towers is draw, calculate number of teachers
        p1.getDashboard().setNumberOfTowers(3);
        p2.getDashboard().setRedTeacherPresent(true);
        assertEquals(1,Game.getInstance().endGameChecker());
        //trying the same, but with 3 players, third player has 5 towers but more teachers, so p2 has to win
        p3.getDashboard().setNumberOfTowers(5);
        p3.getDashboard().setBlueTeacherPresent(true);
        p3.getDashboard().setGreenTeacherPresent(true);
        assertEquals(1,Game.getInstance().endGameChecker());
    }
    @Test
    public void testEndGameCheckerCondition3(){
        //When bag is finished, the game finishes and win who has the minimum of towers
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        p1.getDashboard().setNumberOfTowers(5);
        p2.getDashboard().setNumberOfTowers(3);
        for(int i = 0; i < 4; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        p1.getHand().add(new Assistant(1,1));
        p2.getHand().add(new Assistant(1,1));
        assertEquals(1,Game.getInstance().endGameChecker());
        //Draw number of towers, counting teachers
        p1.getDashboard().setNumberOfTowers(3);
        p2.getDashboard().setRedTeacherPresent(true);
        assertEquals(1,Game.getInstance().endGameChecker());
    }

    @Test
    public void testEndGameCheckerCondition4(){
        //When a player has 0 towers, he wins
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        p1.getDashboard().setNumberOfTowers(0);
        p2.getDashboard().setNumberOfTowers(3);
        for(int i = 0; i < 4; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        p1.getHand().add(new Assistant(1,1));
        p2.getHand().add(new Assistant(1,1));
        assertEquals(0,Game.getInstance().endGameChecker());
    }

    @Test
    public void testWinningPlayer(){
        //p1 has 0 towers, p1 wins
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        p1.getDashboard().setNumberOfTowers(0);
        p2.getDashboard().setNumberOfTowers(3);
        for(int i = 0; i < 4; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        p1.getHand().add(new Assistant(1,1));
        p2.getHand().add(new Assistant(1,1));
        assertEquals("Marco",Game.getInstance().winningPlayer());
    }

    @Test
    public void testOrderedListOfPlayersThatHaveToPlayAssistant(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        //Inizializate and check who play Assistant first
        p1.setPlayedAssistantThisTurn(new Assistant(1,3));
        p2.setPlayedAssistantThisTurn(new Assistant(2,3));
        p3.setPlayedAssistantThisTurn(new Assistant(3,3));
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().getPlayersInGame().add(p3);
        Game.getInstance().orderedListOfPlayersThatHaveToPlayAssistant();
        assertEquals(3,Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().size());
        assertEquals(1,Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().get(1).getPlayerIndex());
        assertEquals(2,Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().get(2).getPlayerIndex());
        assertEquals(0,Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().get(0).getPlayerIndex());
        //Trying to calculate it with draw assistant's turn's heavyness
        p1.setPlayedAssistantThisTurn(new Assistant(1,3));
        p2.setPlayedAssistantThisTurn(new Assistant(2,3));
        p3.setPlayedAssistantThisTurn(new Assistant(2,3));
        Game.getInstance().orderedListOfPlayersThatHaveToPlayAssistant();
        assertEquals(3,Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().size());
        assertEquals(0,Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().get(0).getPlayerIndex());
        assertEquals(1,Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().get(1).getPlayerIndex());
        assertEquals(2,Game.getInstance().getOrderedListOfPlayersThatHaveToPlayAssistant().get(2).getPlayerIndex());
    }


    @Test
    public void testOrderedListOfPlayersThatHaveToMove(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        //Inizializate and check who Move Mother Nature first
        p1.setPlayedAssistantThisTurn(new Assistant(1,3));
        p2.setPlayedAssistantThisTurn(new Assistant(2,3));
        p3.setPlayedAssistantThisTurn(new Assistant(3,3));
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().getPlayersInGame().add(p3);
        Game.getInstance().orderedListOfPlayersThatHaveToMove();
        assertEquals(3,Game.getInstance().getOrderedListOfPlayersThatHaveToMove().size());
        assertEquals(1,Game.getInstance().getOrderedListOfPlayersThatHaveToMove().get(1).getPlayerIndex());
        assertEquals(2,Game.getInstance().getOrderedListOfPlayersThatHaveToMove().get(2).getPlayerIndex());
        assertEquals(0,Game.getInstance().getOrderedListOfPlayersThatHaveToMove().get(0).getPlayerIndex());
    }

    @Test
    public void testRandomChooseOfCharacter(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getBag().initializateBag();
        Game.getInstance().randomChooseOfCharacter();
        int counter = 0;
        if(Game.getInstance().getBoard().getCharacter1().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter2().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter3().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter4().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter5().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter6().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter7().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter8().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter9().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter10().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter11().isInGame()){
            counter++;
        }
        if(Game.getInstance().getBoard().getCharacter12().isInGame()){
            counter++;
        }
        assertEquals(3,counter);
    }

    @Test
    public void testGetGameVariant(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        assertEquals(Variant.EXPERT,Game.getInstance().getGameVariant());
    }

    @Test
    public void testRandomOrderedListOfPlayersThatHaveToPlayAssistant(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().getPlayersInGame().add(p3);
        Game.getInstance().inizializateGame();
        assertEquals(3,Game.getInstance().getRandomOrderedListOfPlayersThatHaveToPlayAssistant().size());
    }

    @Test
    public void testEndGameCheckerTeamsCondition1(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().getBoard().getPlayersInGame().add(new Player("Gianni",TowerColor.BLACK,2));
        Game.getInstance().getBoard().getPlayersInGame().add(new Player("Luciani",TowerColor.WHITE,3));
        //Testing white team winning with 3 islands in game
        for(int i = 0; i < 3; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        Game.getInstance().setTowersOfWhiteTeam(5);
        Game.getInstance().setTowersOfBlackTeam(6);
        assertEquals("Game over.The winner is:  White Team.",Game.getInstance().endGameCheckerTeams());
        //Testing black team winning with 3 islands in game
        Game.getInstance().setTowersOfBlackTeam(3);
        assertEquals("Game over.The winner is:  Black Team.",Game.getInstance().endGameCheckerTeams());
        //Testing with draw number of Towers, but with major number of teachers for white team
        Game.getInstance().setTowersOfWhiteTeam(3);
        Game.getInstance().getBoard().getPlayersInGame().get(3).getDashboard().setRedTeacherPresent(true);
        assertEquals("Game over.The winner is:  White Team.",Game.getInstance().endGameCheckerTeams());
        //Testing with draw number of Towers, but with mahor number of teachers for black team
        Game.getInstance().getBoard().getPlayersInGame().get(2).getDashboard().setGreenTeacherPresent(true);
        p1.getDashboard().setBlueTeacherPresent(true);
        assertEquals("Game over.The winner is:  Black Team.",Game.getInstance().endGameCheckerTeams());
    }

    @Test
    public void testEndGameCheckerTeamsCondition2(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        Game.getInstance().setTowersOfWhiteTeam(0);
        for(int i = 0; i < 4; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        //Testing white team winning with 0 towers
        assertEquals("Game over.The winner is:  White Team.",Game.getInstance().endGameCheckerTeams());
        //Testing black team winning with 0 towers
        Game.getInstance().setTowersOfWhiteTeam(3);
        Game.getInstance().setTowersOfBlackTeam(0);
        assertEquals("Game over.The winner is:  Black Team.",Game.getInstance().endGameCheckerTeams());
    }

    @Test
    public void testEndGameCheckerTeamsCondition3(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        for(int i = 0; i < 4; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        Game.getInstance().setTowersOfWhiteTeam(6);
        Game.getInstance().setTowersOfBlackTeam(5);
        //Testing with 0 cards in hand
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        assertEquals("Game over.The winner is:  Black Team.",Game.getInstance().endGameCheckerTeams());
    }

    @Test
    public void testEndGameCheckerTeamsCondition4(){
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        for(int i = 0; i < 4; i++){
            Game.getInstance().getBoard().getIslandsInGame().add(new Island());
        }
        //Testing with empty bag
        Game.getInstance().setTowersOfWhiteTeam(6);
        Game.getInstance().setTowersOfBlackTeam(5);
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        p1.getHand().add(new Assistant(1,1));
        p2.getHand().add(new Assistant(1,1));
        assertEquals("Game over.The winner is:  Black Team.",Game.getInstance().endGameCheckerTeams());
    }

    @Test
    public void testBuildGameState() throws JsonProcessingException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        Game.getInstance().reset();
        //Testing all the instances of BuildGameState
        Game.getInstance().getBoard().getPlayersInGame().add(p1);
        Game.getInstance().getBoard().getPlayersInGame().add(p2);
        Game.getInstance().inizializateGame();
        //Initializating Character1,7,11
        Game.getInstance().getBoard().getCharacter1().setInGame(true);
        Game.getInstance().getBoard().getCharacter7().setInGame(true);
        Game.getInstance().getBoard().getCharacter11().setInGame(true);
        Game.getInstance().getBoard().getCharacter1().initializateCharacter(Game.getInstance().getBoard().getBag());
        Game.getInstance().getBoard().getCharacter7().initializateCharacter(Game.getInstance().getBoard().getBag());
        Game.getInstance().getBoard().getCharacter11().initializateCharacter(Game.getInstance().getBoard().getBag());
        Game.getInstance().buildGameState("Hi");
        assertEquals(2,Game.getInstance().getState().getClouds().size());
        assertEquals(12,Game.getInstance().getState().getIslandsInGame().size());
        assertEquals(2,Game.getInstance().getState().getDashboards().size());
        assertEquals(p1.getDashboard().getRedRowHall().size(),Game.getInstance().getState().getDashboards().get(0).getRedRowHall().size());
        assertEquals(p1.getDashboard().getBlueRowHall().size(),Game.getInstance().getState().getDashboards().get(0).getBlueRowHall().size());
        assertEquals(p1.getDashboard().getYellowRowHall().size(),Game.getInstance().getState().getDashboards().get(0).getYellowRowHall().size());
        assertEquals(p1.getDashboard().getPinkRowHall().size(),Game.getInstance().getState().getDashboards().get(0).getPinkRowHall().size());
        assertEquals(p1.getDashboard().getGreenRowHall().size(),Game.getInstance().getState().getDashboards().get(0).getGreenRowHall().size());
        assertEquals(p1.getNumberOfCoins(),Game.getInstance().getState().getPlayersCoins().get(0));
        assertEquals(p2.getNumberOfCoins(),Game.getInstance().getState().getPlayersCoins().get(1));
        assertEquals(Game.getInstance().getBoard().getTotalNumberOfCoins(),Game.getInstance().getState().getTotalNumberOfCoins());
        assertEquals(Game.getInstance().getBoard().getMotherNaturePosition(),Game.getInstance().getState().getMotherNaturePosition());
        assertEquals(Game.getInstance().getBoard().getCharacter1().getListofStudentsOnCharacter(),Game.getInstance().getState().getStudentsOnCharacter1());
        assertEquals(Game.getInstance().getBoard().getCharacter7().getListOfStudentsOnCharacter(),Game.getInstance().getState().getStudentsOnCharacter7());
        assertEquals(Game.getInstance().getBoard().getCharacter11().getListOfStudentsOnCharacter(),Game.getInstance().getState().getStudentsOnCharacter11());
        assertTrue(Game.getInstance().getState().isExpert());
        for(int i = 0; i < p1.getHand().size(); i++){
            assertEquals(p1.getHand().get(i).getTurnHeavyness(),Game.getInstance().getState().getPlayersHand().get(0).get(i));
        }
        assertEquals("Hi",Game.getInstance().getState().getMessage());
    }
}