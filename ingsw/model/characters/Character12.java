package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.PawnColor;
import it.polimi.ingsw.model.gamemodel.Player;

public class Character12 {
    private int costOfActivation = 3;
    private boolean isInGame = false;

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    /**
     * This method remove 3 students of a chosen color from client that activated this effect from all player's hall
     * @param p: player that is activating the effect
     * @param chosenColor: chosen color from client
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     */
    public void activateTheEffect(Player p, PawnColor chosenColor) throws NotEnoughMoneyException, CharacterNotInGameException {
        if(p.getNumberOfCoins() >= costOfActivation  && isInGame){
            for(Player player : Game.getInstance().getBoard().getPlayersInGame()){
                for(int i = 0; i < 3; i++){
                    if(chosenColor == PawnColor.RED && player.getDashboard().getRedRowHall().size() > 0){
                        player.getDashboard().getRedRowHall().remove(chosenColor);
                        Game.getInstance().getBoard().getBag().getListOfStudentsInBag().add(chosenColor);
                    }
                    if(chosenColor == PawnColor.BLUE && player.getDashboard().getBlueRowHall().size() > 0){
                        player.getDashboard().getBlueRowHall().remove(chosenColor);
                        Game.getInstance().getBoard().getBag().getListOfStudentsInBag().add(chosenColor);
                    }
                    if(chosenColor == PawnColor.PINK && player.getDashboard().getPinkRowHall().size() > 0){
                        player.getDashboard().getPinkRowHall().remove(chosenColor);
                        Game.getInstance().getBoard().getBag().getListOfStudentsInBag().add(chosenColor);
                    }
                    if(chosenColor == PawnColor.YELLOW && player.getDashboard().getYellowRowHall().size() > 0){
                        player.getDashboard().getYellowRowHall().remove(chosenColor);
                        Game.getInstance().getBoard().getBag().getListOfStudentsInBag().add(chosenColor);
                    }
                    if(chosenColor == PawnColor.GREEN && player.getDashboard().getGreenRowHall().size() > 0){
                        player.getDashboard().getGreenRowHall().remove(chosenColor);
                        Game.getInstance().getBoard().getBag().getListOfStudentsInBag().add(chosenColor);
                    }
                }
            }
            p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
            costOfActivation++;
        }
        else{
            if(p.getNumberOfCoins() < costOfActivation){
                throw new NotEnoughMoneyException("You have not enough money to play this Character.");
            }
            else{
                throw new CharacterNotInGameException("Selected Character is not in game");
            }
        }
    }
}
