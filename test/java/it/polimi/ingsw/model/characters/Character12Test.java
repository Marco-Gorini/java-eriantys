package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Character12Test {
    Player p = new Player("MarcoGorini", TowerColor.BLACK, 0);

    @BeforeAll
    public static void reset(){
        Game.getInstance().reset();
    }

    @Test
    public void testActivateTheEffect() throws NotEnoughMoneyException, CharacterNotInGameException {
        Game.getInstance().setGameVariant(Variant.EXPERT);
        //Call activateTheEffect without money
        PawnColor pc = PawnColor.RED;
        try{
            Game.getInstance().getBoard().getCharacter12().activateTheEffect(p, pc);
        } catch(NotEnoughMoneyException e){
            assertTrue(true);
        }
        assertEquals(1, p.getNumberOfCoins());
        // Call activateTheEffect with money but isOnGame is false
        p.setNumberOfCoins(3);
        try{
            Game.getInstance().getBoard().getCharacter12().activateTheEffect(p, pc);
        } catch(CharacterNotInGameException e){
            assertTrue(true);
        }
        assertEquals(3,p.getNumberOfCoins());
        // Call activateTheEffect correctly, but with only 1 student in RedHall
        //Inizializate listOfPlayers and Bag
        Game.getInstance().getBoard().getCharacter12().setInGame(true);
        Game.getInstance().getBoard().getPlayersInGame().add(p);
        Game.getInstance().getBoard().getPlayersInGame().add(new Player("AndreaFerrazzano",TowerColor.WHITE,1));
        for(Player pl : Game.getInstance().getBoard().getPlayersInGame()){
            pl.getDashboard().getRedRowHall().add(PawnColor.RED);
        }
        //Calling correctly the method
        Game.getInstance().getBoard().getCharacter12().activateTheEffect(p,pc);
        assertEquals(2,Game.getInstance().getBoard().getBag().getListOfStudentsInBag().size());
        for(Player pl : Game.getInstance().getBoard().getPlayersInGame()){
            assertEquals(0,pl.getDashboard().getRedRowHall().size());
        }
        assertEquals(0,p.getNumberOfCoins());
        assertEquals(4,Game.getInstance().getBoard().getCharacter12().getCostOfActivation());
    }
}