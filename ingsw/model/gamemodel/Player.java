package it.polimi.ingsw.model.gamemodel;

import it.polimi.ingsw.exception.*;
import java.util.*;

public class Player {
    private final String nickname;
    private final TowerColor associatedTowerColor;
    private Dashboard dashboard;
    private final int playerIndex;
    private int numberOfCoins = 1;
    private List<Assistant> hand = new ArrayList<>();
    private Assistant playedAssistantThisTurn;
    private boolean character8Effect = false;


    @Override
    public boolean equals(Object obj) {
        return playerIndex == ((Player)obj).getPlayerIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerIndex);
    }

    public Player(String nickname, TowerColor associatedTowerColor, int playerIndex) {
        this.nickname = nickname;
        this.associatedTowerColor = associatedTowerColor;
        this.playerIndex = playerIndex;
        this.dashboard = new Dashboard(playerIndex,nickname);
    }

    public TowerColor getAssociatedTowerColor() {return associatedTowerColor;}

    public String getNickname() {return nickname;}

    public Dashboard getDashboard() {return dashboard;}

    public int getPlayerIndex() {return playerIndex;}

    public int getNumberOfCoins() {return numberOfCoins;}
    public void setNumberOfCoins(int numberOfCoins) {this.numberOfCoins = numberOfCoins;}

    public List<Assistant> getHand() {return hand;}

    public Assistant getPlayedAssistantThisTurn() {return playedAssistantThisTurn;}

    public boolean isCharacter8Effect() {return character8Effect;}
    public void setCharacter8Effect(boolean character8Effect) {this.character8Effect = character8Effect;}

    public void setPlayedAssistantThisTurn(Assistant playedAssistantThisTurn) {this.playedAssistantThisTurn = playedAssistantThisTurn;}

    /**
     * This method play an assistant from player's hand, adding it on the list of  playedAssistant in board
     * (and so checking that no one played that Assistant this turn), but also checks if the player is forced to
     * play an already played Assistant.Then remove the chosen Assistant from his hand
     * @param indexOfAssistantToPlay: index of Assistant to play in the list of assistants
     * @throws WrongAssistantIndexEcxeption: client insert wrong index of Assistant
     * @throws AssistantAlreadyBeenPlayedException: client insert an already played Assistant and can play antoher one
     */
    public void playAssistant(int indexOfAssistantToPlay) throws WrongAssistantIndexEcxeption, AssistantAlreadyBeenPlayedException {
        if(hand.size() >  0){
            if(indexOfAssistantToPlay >= 0 && indexOfAssistantToPlay < hand.size()){
                boolean canBePlayed = true;
                int counter = 0;
                for(Assistant a : Game.getInstance().getBoard().getPlayedAssistantThisTurn()){
                    if(hand.get(indexOfAssistantToPlay).getTurnHeavyness() == a.getTurnHeavyness()){
                        canBePlayed = false;
                        break;
                    }
                }
                for(Assistant a : hand){
                    for(Assistant ass : Game.getInstance().getBoard().getPlayedAssistantThisTurn()){
                        if(a.getTurnHeavyness() == ass.getTurnHeavyness()){
                            counter++;
                            break;
                        }
                    }
                }
                if(canBePlayed || counter == hand.size()){
                    playedAssistantThisTurn = hand.get(indexOfAssistantToPlay);
                    playedAssistantThisTurn.setMotherNatureMovements(hand.get(indexOfAssistantToPlay).getMotherNatureMovements());
                    playedAssistantThisTurn.setTurnHeavyness(hand.get(indexOfAssistantToPlay).getTurnHeavyness());
                    hand.remove(indexOfAssistantToPlay);
                    Game.getInstance().getBoard().getPlayedAssistantThisTurn().add(playedAssistantThisTurn);
                }
                else{
                    throw new AssistantAlreadyBeenPlayedException("Assistant has already been played this turn");
                }
            }
            else{
                throw new WrongAssistantIndexEcxeption("Wrong index of Assistant to play");
            }
        }
    }

    /**
     * This method move mother nature, set his position in Board and set isMotherNaturePresent on the island on which is
     * mother nature, paying attention to the fact that mother nature can be only in 0-11 position
     * @throws WrongMotherNatureMovement: this exception checks if the inserted number of movements for mother nature is
     * correct, based on played assistant from player
     */
    public void moveMotherNature(int chosenNumberOfMovements) throws WrongMotherNatureMovement {
        if(chosenNumberOfMovements <= playedAssistantThisTurn.getMotherNatureMovements() && chosenNumberOfMovements >= 1){
            int previousPosition = Game.getInstance().getBoard().getMotherNaturePosition();
            if(previousPosition + chosenNumberOfMovements > Game.getInstance().getBoard().getIslandsInGame().size() - 1){
                Game.getInstance().getBoard().setMotherNaturePosition(previousPosition + chosenNumberOfMovements - Game.getInstance().getBoard().getIslandsInGame().size());
            }
            else{
                Game.getInstance().getBoard().setMotherNaturePosition(Game.getInstance().getBoard().getMotherNaturePosition() + chosenNumberOfMovements);
            }
            Game.getInstance().getBoard().getIslandsInGame().get(Game.getInstance().getBoard().getMotherNaturePosition()).setMotherNaturePresent(true);
            Game.getInstance().getBoard().getIslandsInGame().get(previousPosition).setMotherNaturePresent(false);
        }
        else{
            throw new WrongMotherNatureMovement("chosen MotherNature movement is too low or too much");
        }
    }
}

