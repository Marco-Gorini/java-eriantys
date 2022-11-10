package it.polimi.ingsw.controller.communication.commands;

import it.polimi.ingsw.controller.communication.Command;
import it.polimi.ingsw.model.gamemodel.Game;
import it.polimi.ingsw.model.gamemodel.Player;
import it.polimi.ingsw.model.gamemodel.TowerColor;

public class NicknameCommand implements Command {
    int playerIndex;
    String nickname;

    public NicknameCommand(String nickname, int playerIndex) {
        this.nickname = nickname;
        this.playerIndex = playerIndex;
    }

    /**
     * This method set all atributes of player and add a new player on listOfPlayersInGame in Board
     */
    @Override
    public void execute() {
        TowerColor playerTowerColor;
        if(Game.getInstance().getNumberOfPlayersInGame() == 2 || Game.getInstance().getNumberOfPlayersInGame() == 3){
            switch(playerIndex){

                case 1 :
                    playerTowerColor = TowerColor.WHITE;
                    break;
                case 2 :
                    playerTowerColor = TowerColor.GREY;
                    break;
                case 0:
                default:
                    playerTowerColor = TowerColor.BLACK;
                    break;
            }
            Player player = new Player(nickname, playerTowerColor ,playerIndex);
            Game.getInstance().getBoard().getPlayersInGame().add(player);
            if(Game.getInstance().getNumberOfPlayersInGame() == 2){
                player.getDashboard().setNumberOfTowers(8);
            }
            if(Game.getInstance().getNumberOfPlayersInGame() == 3){
                player.getDashboard().setNumberOfTowers(6);
            }
            if(Game.getInstance().getNumberOfPlayersInGame() == Game.getInstance().getBoard().getPlayersInGame().size()){
                Game.getInstance().inizializateGame();
            }
        }
        else{
            switch(playerIndex){
                case 0:
                default:
                    playerTowerColor = TowerColor.BLACK;
                    break;
                case 1:
                    playerTowerColor = TowerColor.BLACK;
                    break;
                case 2:
                    playerTowerColor = TowerColor.WHITE;
                    break;
                case 3:
                    playerTowerColor = TowerColor.WHITE;
                    break;
            }
            Player player = new Player(nickname, playerTowerColor, playerIndex);
            Game.getInstance().getBoard().getPlayersInGame().add(player);
            if(Game.getInstance().getNumberOfPlayersInGame() == Game.getInstance().getBoard().getPlayersInGame().size()){
                Game.getInstance().inizializateGame();
            }
        }
    }
}
