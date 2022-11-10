package it.polimi.ingsw.model.gamemodel;

import it.polimi.ingsw.exception.*;

import java.util.ArrayList;
import java.util.List;

public class Island {
    private int playerIndex = -1;
    private boolean isMotherNaturePresent = false;
    private List<PawnColor> studentsOnIsland = new ArrayList<>();
    private int numberOfBuiltedTowers = 0;
    private TowerColor teamColor = null;
    private boolean hasProhibitionCard = false;
    private boolean character6Effect = false;
    private boolean character9Effect = false;
    private boolean character3Effect = false;
    private PawnColor chosenPawnColorFromCharacter9;

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public List<PawnColor> getStudentsOnIsland() {
        return studentsOnIsland;
    }

    public void setNumberOfBuiltedTowers(int numberOfBuiltedTowers) {this.numberOfBuiltedTowers = numberOfBuiltedTowers;}
    public int getNumberOfBuiltedTowers() {return numberOfBuiltedTowers;}

    public TowerColor getTeamColor() {return teamColor;}
    public void setTeamColor(TowerColor teamColor) {this.teamColor = teamColor;}

    public boolean isMotherNaturePresent() {return isMotherNaturePresent;}
    public void setMotherNaturePresent(boolean motherNaturePresent) {isMotherNaturePresent = motherNaturePresent;}

    public boolean isHasProhibitionCard() {return hasProhibitionCard;}
    public void setHasProhibitionCard(boolean hasProhibitionCard) {this.hasProhibitionCard = hasProhibitionCard;}

    public boolean isCharacter3Effect() {return character3Effect;}
    public void setCharacter3Effect(boolean character3Effect) {
        this.character3Effect = character3Effect;
    }

    public boolean isCharacter6Effect() {return character6Effect;}
    public void setCharacter6Effect(boolean character6Effect) {this.character6Effect = character6Effect;}

    public void setCharacter9Effect(boolean character9Effect) {this.character9Effect = character9Effect;}
    public boolean isCharacter9Effect() {return character9Effect;}

    public void setchosenPawnColorFromCharacter9(PawnColor chosenPawnColorFromCharacter9) {this.chosenPawnColorFromCharacter9 = chosenPawnColorFromCharacter9;}
    public PawnColor getChosenPawnColorFromCharacter9() {return chosenPawnColorFromCharacter9;}

    /**
     * It calculates the influence of the player given in input, based on the students on island, the teacher the player owns
     * and eventually the builted tower, if the given player is the owner of Island. It also
     * calculate influence in a different way, based on which character effect is activated
     * @param p: Player of which I have to calculate influence
     * @return an int that is the calculated influence of the player
     */
    public int checkInfluence(Player p){
        int influence = 0;
        for(PawnColor student : studentsOnIsland){
            if(student == PawnColor.RED && p.getDashboard().isRedTeacherPresent() && (!character9Effect || (PawnColor.RED != chosenPawnColorFromCharacter9))){
                influence++;
            }
            if(student == PawnColor.BLUE && p.getDashboard().isBlueTeacherPresent() && (!character9Effect || (PawnColor.BLUE != chosenPawnColorFromCharacter9))){
                influence++;
            }
            if(student == PawnColor.PINK && p.getDashboard().isPinkTeacherPresent() && (!character9Effect || (PawnColor.PINK != chosenPawnColorFromCharacter9))){
                influence++;
            }
            if(student == PawnColor.YELLOW && p.getDashboard().isYellowTeacherPresent() && (!character9Effect || (PawnColor.YELLOW != chosenPawnColorFromCharacter9))){
                influence++;
            }
            if(student == PawnColor.GREEN && p.getDashboard().isGreenTeacherPresent() && (!character9Effect || (PawnColor.GREEN != chosenPawnColorFromCharacter9))){
                influence++;
            }
        }
        if(p.getPlayerIndex() == playerIndex && !character6Effect && Game.getInstance().getNumberOfPlayersInGame() != 4){
            influence += numberOfBuiltedTowers;
        }
        if(p.isCharacter8Effect()){
            influence += 2;
            p.setCharacter8Effect(false);
        }
        return influence;
    }

    /**
     * It adds the chosen student from the player on the island, adding it to studentsOnIsland (attribute of Island)
     * @param p: player that is adding a student
     * @param studentToAdd: pawnColor of the student to add on island.
     * @throws StudentsAreNotInEntranceException: it checks if the student the player wants to add is in the player's
     * entrance
     */
    public void addStudentOnIsland(Player p, PawnColor studentToAdd) throws StudentsAreNotInEntranceException {
        boolean canAdd = false;
        for(PawnColor s : p.getDashboard().getEntrance()){
            if(s == studentToAdd){
                canAdd = true;
                break;
            }
        }
        if(canAdd){
            studentsOnIsland.add(studentToAdd);
            p.getDashboard().getEntrance().remove(studentToAdd);
        }
        else{
            throw new StudentsAreNotInEntranceException("The student you want to add is not in your entrance");
        }
    }

    /**
     * This method build a tower on the island, incrementing the numberOfBuiltedTowers(attribute of island) and setting
     * the owner player of the island. This method works even if there is an island that was unified.It also checks every
     * character that can influence the building of tower.This method is implemented to do the right calculation of influence if
     * the game is composed by 4 players.
     * @param p: Player that have to build a tower
     * @throws ProhibitionCardOnIslandException it checks if island has a prohibition card
     * @throws MotherNatureNotOnIslandException it checks that mother nature is on the island
     * @throws NotEnoughInfluenceException it checks that the player has enough influence to build a tower
     */
    public void buildTower(Player p) throws ProhibitionCardOnIslandException, MotherNatureNotOnIslandException, NotEnoughInfluenceException, AlreadyOwnIslandException {
        if((p.getPlayerIndex() != playerIndex && Game.getInstance().getNumberOfPlayersInGame() != 4) || (p.getAssociatedTowerColor() != teamColor && Game.getInstance().getNumberOfPlayersInGame() == 4)){
            if(!hasProhibitionCard && (isMotherNaturePresent || character3Effect) ){
                if(Game.getInstance().getNumberOfPlayersInGame() == 3 || Game.getInstance().getNumberOfPlayersInGame() == 2){
                    if(playerIndex == -1){
                        if(character3Effect){
                            character3Effect = false;
                        }
                        int maxInfluence = checkInfluence(p);
                        boolean canBuild = true;
                        for(Player player : Game.getInstance().getBoard().getPlayersInGame()){
                            if(player.getPlayerIndex() != p.getPlayerIndex()){
                                if(checkInfluence(player) > maxInfluence || maxInfluence == 0){
                                    canBuild = false;
                                }
                            }
                        }
                        if(canBuild){
                            numberOfBuiltedTowers += 1;
                            p.getDashboard().setNumberOfTowers(p.getDashboard().getNumberOfTowers() - 1);
                            playerIndex = p.getPlayerIndex();
                        }
                        else{
                            throw new NotEnoughInfluenceException("Your influence is not enough to build tower on this island");
                        }
                    }
                    else{
                        if(character3Effect){
                            character3Effect = false;
                        }
                        int maxInfluence = 0;
                        for(Player pl : Game.getInstance().getBoard().getPlayersInGame()){
                            if(pl.getPlayerIndex() == playerIndex){
                                maxInfluence = checkInfluence(pl);
                                break;
                            }
                        }
                        boolean canBuild = true;
                        if(checkInfluence(p) <= maxInfluence){
                            canBuild = false;
                        }
                        if(canBuild){
                            for(Player pl : Game.getInstance().getBoard().getPlayersInGame()){
                                if(playerIndex == pl.getPlayerIndex()){
                                    pl.getDashboard().setNumberOfTowers(pl.getDashboard().getNumberOfTowers() + numberOfBuiltedTowers);
                                }
                            }
                            numberOfBuiltedTowers += 1;
                            p.getDashboard().setNumberOfTowers(p.getDashboard().getNumberOfTowers() - numberOfBuiltedTowers);
                            playerIndex = p.getPlayerIndex();
                        }
                        else{
                            throw new NotEnoughInfluenceException("Your influence is not enough to build tower on this island");
                        }
                    }
                }
                else if(Game.getInstance().getNumberOfPlayersInGame() == 4){
                    if(teamColor == null){
                        if(character3Effect){
                            character3Effect = false;
                        }
                        int influenceOfPlayerTeam = 0;
                        int influenceOfOpponentsTeam = 0;
                        for(Player pl : Game.getInstance().getBoard().getPlayersInGame()){
                            if(pl.getAssociatedTowerColor() == p.getAssociatedTowerColor()){
                                influenceOfPlayerTeam += checkInfluence(pl);
                            }
                        }
                        for(Player pl : Game.getInstance().getBoard().getPlayersInGame()){
                            if(pl.getAssociatedTowerColor() != p.getAssociatedTowerColor()){
                                influenceOfOpponentsTeam += checkInfluence(pl);
                            }
                        }
                        if(influenceOfOpponentsTeam > influenceOfPlayerTeam || influenceOfPlayerTeam == 0){
                            throw  new NotEnoughInfluenceException("Your influence is not enough to build tower on this island");
                        }
                        else{
                            numberOfBuiltedTowers++;
                            if(p.getAssociatedTowerColor() == TowerColor.BLACK){
                                Game.getInstance().setTowersOfBlackTeam(Game.getInstance().getTowersOfBlackTeam() - 1);
                                teamColor = TowerColor.BLACK;
                            }
                            else{
                                Game.getInstance().setTowersOfWhiteTeam(Game.getInstance().getTowersOfWhiteTeam() - 1);
                                teamColor = TowerColor.WHITE;
                            }
                        }
                    }
                    else{
                        if(character3Effect){
                            character3Effect = false;
                        }
                        int influenceOfPlayerTeam = 0;
                        int influenceOfOpponentsTeam = 0;
                        for(Player pl : Game.getInstance().getBoard().getPlayersInGame()){
                            if(pl.getAssociatedTowerColor() == p.getAssociatedTowerColor()){
                                influenceOfPlayerTeam += checkInfluence(pl);
                            }
                        }
                        for(Player pl : Game.getInstance().getBoard().getPlayersInGame()){
                            if(pl.getAssociatedTowerColor() != p.getAssociatedTowerColor()){
                                influenceOfOpponentsTeam += checkInfluence(pl);
                            }
                        }
                        influenceOfOpponentsTeam += numberOfBuiltedTowers;
                        if(influenceOfOpponentsTeam >= influenceOfPlayerTeam || influenceOfPlayerTeam == 0){
                            throw  new NotEnoughInfluenceException("Your influence is not enough to build tower on this island");
                        }
                        else{
                            if(p.getAssociatedTowerColor() == TowerColor.BLACK){
                                Game.getInstance().setTowersOfWhiteTeam(Game.getInstance().getTowersOfWhiteTeam() + numberOfBuiltedTowers);
                                numberOfBuiltedTowers++;
                                Game.getInstance().setTowersOfBlackTeam(Game.getInstance().getTowersOfBlackTeam() - numberOfBuiltedTowers);
                                teamColor = TowerColor.BLACK;
                            }
                            else{
                                Game.getInstance().setTowersOfBlackTeam(Game.getInstance().getTowersOfBlackTeam() + numberOfBuiltedTowers);
                                numberOfBuiltedTowers++;
                                Game.getInstance().setTowersOfWhiteTeam(Game.getInstance().getTowersOfWhiteTeam() - numberOfBuiltedTowers);
                                teamColor = TowerColor.WHITE;
                            }
                        }
                    }
                }
            }
            else{
                if(hasProhibitionCard){
                    hasProhibitionCard = false;
                    Game.getInstance().getBoard().getCharacter5().setNumberOfPrhoibitionCard(Game.getInstance().getBoard().getCharacter5().getNumberOfPrhoibitionCard() + 1);
                    throw new ProhibitionCardOnIslandException("Island has a prohibition card.");
                }
                else{
                    throw new MotherNatureNotOnIslandException("MotherNature is not on this island.");
                }

            }
        }
        else{
            throw new AlreadyOwnIslandException("You are already the owner of this island");
        }
    }
}
