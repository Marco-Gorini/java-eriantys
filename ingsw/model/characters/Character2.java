package it.polimi.ingsw.model.characters;
import it.polimi.ingsw.exception.Character2Exception;
import it.polimi.ingsw.exception.CharacterNotInGameException;
import it.polimi.ingsw.exception.NotEnoughMoneyException;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;

public class Character2 {
    private int costOfActivation = 2;
    private boolean isInGame = false;

    public void setInGame(boolean inGame) {isInGame = inGame;}
    public boolean isInGame() {return isInGame;}

    public int getCostOfActivation() {return costOfActivation;}

    /**
     * This method checks if there is a player that owns a teacher,then checks if the player that is activating
     * character's effect can steal the teacher to another player
     * @param p: player that is activating the effect
     * @throws NotEnoughMoneyException: not enough money to activate the effect
     * @throws CharacterNotInGameException: character is not activated in this game
     * @throws Character2Exception: if no one owns a single teacher, it has no sense to activate this effect
     */
    public void activateTheEffect(Player p) throws NotEnoughMoneyException, CharacterNotInGameException, Character2Exception {
        if(p.getNumberOfCoins() >= costOfActivation && isInGame){
            boolean canActivate = false;
            for(Player player : Game.getInstance().getBoard().getPlayersInGame()){
                if(player.getDashboard().isRedTeacherPresent()|| player.getDashboard().isBlueTeacherPresent() || player.getDashboard().isPinkTeacherPresent() || player.getDashboard().isGreenTeacherPresent() || player.getDashboard().isYellowTeacherPresent()){
                    canActivate = true;
                    break;
                }
            }
            if(canActivate){
                for(Player player : Game.getInstance().getBoard().getPlayersInGame()){
                    if(p != player && ((p.getDashboard().getRedRowHall().size() == player.getDashboard().getRedRowHall().size()) && player.getDashboard().isRedTeacherPresent())) {
                        player.getDashboard().setRedTeacherPresent(false);
                        p.getDashboard().setRedTeacherPresent(true);
                        p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
                        costOfActivation ++;
                        break;
                    }
                    if(p != player && ((p.getDashboard().getBlueRowHall().size() == player.getDashboard().getBlueRowHall().size()) && player.getDashboard().isBlueTeacherPresent())) {
                        player.getDashboard().setBlueTeacherPresent(false);
                        p.getDashboard().setBlueTeacherPresent(true);
                        p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
                        costOfActivation ++;
                        break;
                    }
                    if(p != player && ((p.getDashboard().getPinkRowHall().size() == player.getDashboard().getPinkRowHall().size()) && player.getDashboard().isPinkTeacherPresent())) {
                        player.getDashboard().setPinkTeacherPresent(false);
                        p.getDashboard().setPinkTeacherPresent(true);
                        p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
                        costOfActivation ++;
                        break;
                    }
                    if(p != player && ((p.getDashboard().getYellowRowHall().size() == player.getDashboard().getYellowRowHall().size()) && player.getDashboard().isYellowTeacherPresent())) {
                        player.getDashboard().setYellowTeacherPresent(false);
                        p.getDashboard().setYellowTeacherPresent(true);
                        p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
                        costOfActivation ++;
                        break;
                    }
                    if(p != player && ((p.getDashboard().getGreenRowHall().size() == player.getDashboard().getGreenRowHall().size()) && player.getDashboard().isGreenTeacherPresent())) {
                        player.getDashboard().setGreenTeacherPresent(false);
                        p.getDashboard().setGreenTeacherPresent(true);
                        p.setNumberOfCoins(p.getNumberOfCoins() - costOfActivation);
                        costOfActivation ++;
                        break;
                    }
                }
            }
            else{
                throw new Character2Exception("No player owns a Teacher, activating the effect has no sense");
            }
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
