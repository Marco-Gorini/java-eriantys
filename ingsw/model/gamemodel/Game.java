package it.polimi.ingsw.model.gamemodel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.listeners.Observer;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private static Game instance;
    private Board board;
    private Variant gameVariant;
    private List<Player> randomOrderedListOfPlayersThatHaveToPlayAssistant;
    private List<Player> orderedListOfPlayersThatHaveToPlayAssistant;
    private List<Player> orderedListOfPlayersThatHaveToMove;
    int numberOfPlayersInGame;
    int towersOfWhiteTeam;
    int towersOfBlackTeam;
    Random randomNumberForMotherNatureStartingPosition;
    Random randomIndexOfPlayerThatPlayAssistantFirst;
    Random randomIndexForCharacter1;
    Random randomIndexForCharacter2;
    Random randomIndexForCharacter3;
    private boolean initialized;
    private boolean gameOver;
    private boolean firstTurn = true;
    private List<Observer> listObservers;
    private final ObjectMapper objectMapper;
    GameState state = new GameState();
    private boolean changedState = false;


    public void addObserver(Observer ob) {
        listObservers.add(ob);
    }

    private Game() {
        reset();
        objectMapper = new ObjectMapper();
    }

    /**
     * This method is made for test classes, because of Singleton Pattern, I have to be sure that all the paramters
     * are resetted in every test case.
     */
    public void reset() {
        board = new Board();
        randomOrderedListOfPlayersThatHaveToPlayAssistant = new ArrayList<>();
        orderedListOfPlayersThatHaveToPlayAssistant = new ArrayList<>();
        orderedListOfPlayersThatHaveToMove = new ArrayList<>();
        numberOfPlayersInGame = 5;
        towersOfWhiteTeam = 8;
        towersOfBlackTeam = 8;
        randomNumberForMotherNatureStartingPosition = new Random();
        randomIndexOfPlayerThatPlayAssistantFirst = new Random();
        randomIndexForCharacter1 = new Random();
        randomIndexForCharacter2 = new Random();
        randomIndexForCharacter3 = new Random();
        listObservers = new ArrayList<>();
    }

    /**
     * Singleton pattern
     *
     * @return an instance of game
     */
    public synchronized static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public GameState getState() {return state;}


    public synchronized boolean isChangedState() {return changedState;}
    public void setChangedState(boolean changedState) {this.changedState = changedState;}

    public int getTowersOfWhiteTeam() {return towersOfWhiteTeam;}
    public void setTowersOfWhiteTeam(int towersOfWhiteTeam) {this.towersOfWhiteTeam = towersOfWhiteTeam;}

    public int getTowersOfBlackTeam() {return towersOfBlackTeam;}

    public void setTowersOfBlackTeam(int towersOfBlackTeam) {this.towersOfBlackTeam = towersOfBlackTeam;}

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isGameOver() {return gameOver;}

    public int getNumberOfPlayersInGame() {return numberOfPlayersInGame;}

    public Board getBoard() {
        return board;
    }

    public Variant getGameVariant() {return gameVariant;}
    public void setGameVariant(Variant gameVariant) {this.gameVariant = gameVariant;}

    public List<Player> getRandomOrderedListOfPlayersThatHaveToPlayAssistant() {return randomOrderedListOfPlayersThatHaveToPlayAssistant;}
    public void setRandomOrderedListOfPlayersThatHaveToPlayAssistant(List<Player> randomOrderedListOfPlayersThatHaveToPlayAssistant) {this.randomOrderedListOfPlayersThatHaveToPlayAssistant = randomOrderedListOfPlayersThatHaveToPlayAssistant;}

    public List<Player> getOrderedListOfPlayersThatHaveToPlayAssistant() {return orderedListOfPlayersThatHaveToPlayAssistant;}
    public void setOrderedListOfPlayersThatHaveToPlayAssistant(List<Player> orderedListOfPlayersThatHaveToPlayAssistant) {this.orderedListOfPlayersThatHaveToPlayAssistant = orderedListOfPlayersThatHaveToPlayAssistant;}

    public List<Player> getOrderedListOfPlayersThatHaveToMove() {return orderedListOfPlayersThatHaveToMove;}
    public void setOrderedListOfPlayersThatHaveToMove(List<Player> orderedListOfPlayersThatHaveToMove) {this.orderedListOfPlayersThatHaveToMove = orderedListOfPlayersThatHaveToMove;}

    public boolean isFirstTurn() {return firstTurn;}
    public void setFirstTurn(boolean firstTurn) {this.firstTurn = firstTurn;}

    public void setNumberOfPlayersInGame(int numberOfPlayersInGame) {this.numberOfPlayersInGame = numberOfPlayersInGame;}


    /**
     * This method initializate and send to the client the game state, with attributes the client needs
     * to have a clear CLI and GUI view
     * @param message: the string i want to send to client
     * @return a String that Client with CLI has to print.
     * @throws JsonProcessingException
     */
    public String buildGameState(String message) throws JsonProcessingException {
        state.setClouds(board.getClouds());
        state.setIslandsInGame(board.getIslandsInGame());
        state.setDashboards(board.getPlayersInGame().stream().map(Player::getDashboard).collect(Collectors.toList()));
        state.setPlayersCoins(board.getPlayersInGame().stream().map(Player::getNumberOfCoins).collect(Collectors.toList()));
        state.setTotalNumberOfCoins(board.getTotalNumberOfCoins());
        state.setMotherNaturePosition(board.getMotherNaturePosition());
        if (board.getCharacter1().isInGame()) {
            state.setCharacter1IsInGame(true);
            state.setStudentsOnCharacter1(board.getCharacter1().getListofStudentsOnCharacter());
        }
        if (board.getCharacter7().isInGame()) {
            state.setCharacter7IsInGame(true);
            state.setStudentsOnCharacter7(board.getCharacter7().getListOfStudentsOnCharacter());
        }
        if (board.getCharacter11().isInGame()) {
            state.setCharacter11IsInGame(true);
            state.setStudentsOnCharacter11(board.getCharacter11().getListOfStudentsOnCharacter());
        }
        if (gameVariant == Variant.EXPERT) {
            state.setExpert(true);
        }
        HashMap<Integer, List<Integer>> playersHands = new HashMap<>();
        for (Player p : board.getPlayersInGame()) {
            List<Integer> copyHand = new ArrayList<>();
            for (Assistant a : p.getHand()) {
                copyHand.add(a.getTurnHeavyness());
            }
            playersHands.put(p.getPlayerIndex(), copyHand);
        }
        state.setPlayersHand(playersHands);
        state.setMessage(message);
        List<Integer> playedAssistantThisTurn = new ArrayList<>();
        for(int i = 0; i < board.getPlayedAssistantThisTurn().size(); i++){
            playedAssistantThisTurn.add(board.getPlayedAssistantThisTurn().get(i).getTurnHeavyness());
        }
        state.setPlayedAssistantThisTurn(playedAssistantThisTurn);
        return objectMapper.writeValueAsString(state);
    }

    /**
     * This method initializate game, so initializate the bag game, put the assistants in all players'hand,
     * add 12 islands in listOfIslandsInGame (attribute of board), set a random mothernature position and then set
     * motherNatureIsPresent on the random island, initializate the clouds in board, initializate the entrance of all
     * players, put students on clouds, randomly create an ordered list of players that have to play assistants,if
     * gameVariant is expert randomly activate 3 characters to play
     */
    public void inizializateGame() {
        board.getBag().initializateBag();
        for (Player p : board.getPlayersInGame()) {
            p.getHand().add(new Assistant(1, 1));
            p.getHand().add(new Assistant(2, 1));
            p.getHand().add(new Assistant(3, 2));
            p.getHand().add(new Assistant(4, 2));
            p.getHand().add(new Assistant(5, 3));
            p.getHand().add(new Assistant(6, 3));
            p.getHand().add(new Assistant(7, 4));
            p.getHand().add(new Assistant(8, 4));
            p.getHand().add(new Assistant(9, 5));
            p.getHand().add(new Assistant(10, 5));
        }
        for (int i = 0; i < 12; i++) {
            board.getIslandsInGame().add(new Island());
        }
        board.setMotherNaturePosition(randomNumberForMotherNatureStartingPosition.nextInt(12));
        board.getIslandsInGame().get(board.getMotherNaturePosition()).setMotherNaturePresent(true);
        for (int i = 0; i < board.getIslandsInGame().size(); i++) {
            if (!board.getIslandsInGame().get(i).isMotherNaturePresent() && (i != board.getMotherNaturePosition() + 6) && (i != board.getMotherNaturePosition() - 6)) {
                board.getIslandsInGame().get(i).getStudentsOnIsland().add(board.getBag().drawStudent());
            }
        }
        for (int i = 0; i < board.getPlayersInGame().size(); i++) {
            board.getClouds().put(i, new ArrayList<>());
        }
        for (Player player : board.getPlayersInGame()) {
            if (board.getPlayersInGame().size() == 2 || board.getPlayersInGame().size() == 4) {
                for (int i = 0; i < 7; i++) {
                    player.getDashboard().getEntrance().add(board.getBag().drawStudent());
                }
            }
            if (board.getPlayersInGame().size() == 3) {
                for (int i = 0; i < 9; i++) {
                    player.getDashboard().getEntrance().add(board.getBag().drawStudent());
                }
            }
        }
        board.putStudentsOnCloud();
        randomOrderedListOfPlayersThatHaveToPlayAssistant();
        if (gameVariant == Variant.EXPERT) {
            randomChooseOfCharacter();
        }
        initialized = true;
        listObservers.forEach(Observer::onInitialized);
    }

    /**
     * This method calculate all the winning condition ,and it's called
     * only if the players in game are 2 or 3, so if islands in game are 3 it calls indexofWinning
     * Player method, that will calculate the winner, same thing if players'hand are empty or if bag is empty.Then simply
     * return playerIndex of winning player if someone has 0 towers in dashboard.It also set gameOver = true
     * if there is a winning condition. If there is not a winning condition, it returns 5, that is
     * a casual number just to check that the game is not over.
     *
     * @return indexOfWinningPlayer
     */
    public int endGameChecker() {
        int indexOfWinningPlayer = 5;
        if (board.getIslandsInGame().size() <= 3) {
            gameOver = true;
            return indexOfWinningPlayer();
        }
        for (Player playerInGame : board.getPlayersInGame()) {
            if (playerInGame.getDashboard().getNumberOfTowers() == 0) {
                indexOfWinningPlayer = playerInGame.getPlayerIndex();
                gameOver = true;
                return indexOfWinningPlayer;
            }
        }
        for (Player p : board.getPlayersInGame()) {
            if (p.getHand().size() == 0) {
                gameOver = true;
                return indexOfWinningPlayer();
            }
        }
        if (board.getBag().getListOfStudentsInBag().size() == 0) {
            gameOver = true;
            return indexOfWinningPlayer();
        }
        return indexOfWinningPlayer;
    }

    /**
     * * This method calculate all the winning condition ,and it's called
     * only if the players in game are 4 so if islands in game are 3 it calls calculateWinningTeam
     * method, that will calculate the winner, same thing if players'hand are empty or if bag is empty.Then simply
     * return a string for a winning team if someone has 0 towers in dashboard.It also set gameOver = true
     * if there is a winning condition. If there is not a winning condition, it returns "", that is
     * a casual string just to check that the game is not over.
     *
     * @return winningTeam;
     */
    public String endGameCheckerTeams() {
        String winningTeam = "";
        if (board.getIslandsInGame().size() <= 3) {
            gameOver = true;
            return calculateWinningTeam();
        }
        if (towersOfBlackTeam == 0) {
            gameOver = true;
            winningTeam = "Game over.The winner is:  Black Team.";
        }
        if (towersOfWhiteTeam == 0) {
            gameOver = true;
            winningTeam = "Game over.The winner is:  White Team.";
        }
        for (Player p : board.getPlayersInGame()) {
            if (p.getHand().size() == 0) {
                gameOver = true;
                return calculateWinningTeam();
            }
        }
        if (board.getBag().getListOfStudentsInBag().size() == 0) {
            gameOver = true;
            return calculateWinningTeam();
        }
        return winningTeam;
    }

    /**
     * It returns a string: if there is a a winner, it returns the winner's nickname, else the string "there is not
     * a winner"
     *
     * @return a string with winning player's nickname
     */

    public String winningPlayer() {
        int i = endGameChecker();
        if (i != 5) {
            return board.getPlayersInGame().get(i).getNickname();
        } else {
            return "There is not a winner.";
        }
    }

    /**
     * This method is called from controller every starting planning phase, and simply calculated an ordered list
     * of players that have to play assistant.
     */
    public void orderedListOfPlayersThatHaveToPlayAssistant() {
        List<Player> playersInGame = board.getPlayersInGame();
        List<Player> playersThatHaveToPlayAssistant = new ArrayList<>();
        List<Player> orderedListOfPlayers = new ArrayList<>();
        playersThatHaveToPlayAssistant.addAll(playersInGame);
        for (int i = 0; i < playersInGame.size() - 1; i++) {
            int min = 11;
            int indexOfWhoPlayAssistantFirst = 5;
            int indexToRemove = 5;
            for (int j = 0; j < playersThatHaveToPlayAssistant.size(); j++) {
                if (playersThatHaveToPlayAssistant.get(j).getPlayedAssistantThisTurn().getTurnHeavyness() < min) {
                    min = playersThatHaveToPlayAssistant.get(j).getPlayedAssistantThisTurn().getTurnHeavyness();
                    indexOfWhoPlayAssistantFirst = playersThatHaveToPlayAssistant.get(j).getPlayerIndex();
                    indexToRemove = j;
                }
            }
            playersThatHaveToPlayAssistant.remove(indexToRemove);
            orderedListOfPlayers.add(playersInGame.get(indexOfWhoPlayAssistantFirst));
        }
        orderedListOfPlayers.add(playersThatHaveToPlayAssistant.get(0));
        setOrderedListOfPlayersThatHaveToPlayAssistant(orderedListOfPlayers);
    }

    /**
     * This method is called from controller every starting action phase, and simply calculated an ordered list
     * of players that have to move.
     */
    public void orderedListOfPlayersThatHaveToMove() {
        List<Player> playersInGame = board.getPlayersInGame();
        List<Player> playersThatHaveToMove = new ArrayList<>();
        List<Player> orderedListOfPlayers = new ArrayList<>();
        playersThatHaveToMove.addAll(playersInGame);
        for (int i = 0; i < playersInGame.size() - 1; i++) {
            int min = 11;
            int indexOfWhoPlayAssistantFirst = 5;
            int indexToRemove = 5;
            for (int j = 0; j < playersThatHaveToMove.size(); j++) {
                if (playersThatHaveToMove.get(j).getPlayedAssistantThisTurn().getTurnHeavyness() < min) {
                    min = playersThatHaveToMove.get(j).getPlayedAssistantThisTurn().getTurnHeavyness();
                    indexOfWhoPlayAssistantFirst = playersThatHaveToMove.get(j).getPlayerIndex();
                    indexToRemove = j;
                }
            }
            playersThatHaveToMove.remove(indexToRemove);
            orderedListOfPlayers.add(playersInGame.get(indexOfWhoPlayAssistantFirst));
        }
        orderedListOfPlayers.add(playersThatHaveToMove.get(0));
        setOrderedListOfPlayersThatHaveToMove(orderedListOfPlayers);
    }

    /**
     * This method calculate randomly an ordered list of players that have to play assistant
     */
    public void randomOrderedListOfPlayersThatHaveToPlayAssistant() {
        List<Player> orderedListOfPlayers = new ArrayList<>();
        for (int i = 0; i < board.getPlayersInGame().size(); i++) {
            int randomIndex;
            boolean different;
            do {
                different = true;
                randomIndex = randomIndexOfPlayerThatPlayAssistantFirst.nextInt(board.getPlayersInGame().size());
                for (int j = 0; j < orderedListOfPlayers.size(); j++) {
                    if (randomIndex == orderedListOfPlayers.get(j).getPlayerIndex()) {
                        different = false;
                    }
                }
            } while (!different);
            orderedListOfPlayers.add(board.getPlayersInGame().get(randomIndex));
        }
        setRandomOrderedListOfPlayersThatHaveToPlayAssistant(orderedListOfPlayers);
    }

    /**
     * This method randomly activate 3 different Characters and set them the attribute isInGame
     */
    public void randomChooseOfCharacter() {
        int randomChooseForCharacter1 = randomIndexForCharacter1.nextInt(1, 13);
        int randomChooseForCharacter2;
        int randomChooseForCharacter3;
        do {
            randomChooseForCharacter2 = randomIndexForCharacter2.nextInt(1, 13);
        } while (randomChooseForCharacter2 == randomChooseForCharacter1);
        do {
            randomChooseForCharacter3 = randomIndexForCharacter3.nextInt(1, 13);
        } while (randomChooseForCharacter3 == randomChooseForCharacter1 || randomChooseForCharacter3 == randomChooseForCharacter2);
        if (randomChooseForCharacter1 == 1 || randomChooseForCharacter2 == 1 || randomChooseForCharacter3 == 1) {
            board.getCharacter1().setInGame(true);
            ;
            board.getCharacter1().initializateCharacter(board.getBag());
        }
        if (randomChooseForCharacter1 == 2 || randomChooseForCharacter2 == 2 || randomChooseForCharacter3 == 2) {
            board.getCharacter2().setInGame(true);
        }
        if (randomChooseForCharacter1 == 3 || randomChooseForCharacter2 == 3 || randomChooseForCharacter3 == 3) {
            board.getCharacter3().setInGame(true);
        }
        if (randomChooseForCharacter1 == 4 || randomChooseForCharacter2 == 4 || randomChooseForCharacter3 == 4) {
            board.getCharacter4().setInGame(true);
        }
        if (randomChooseForCharacter1 == 5 || randomChooseForCharacter2 == 5 || randomChooseForCharacter3 == 5) {
            board.getCharacter5().setInGame(true);
        }
        if (randomChooseForCharacter1 == 6 || randomChooseForCharacter2 == 6 || randomChooseForCharacter3 == 6) {
            board.getCharacter6().setInGame(true);
        }
        if (randomChooseForCharacter1 == 7 || randomChooseForCharacter2 == 7 || randomChooseForCharacter3 == 7) {
            board.getCharacter7().setInGame(true);
            board.getCharacter7().initializateCharacter(board.getBag());
        }
        if (randomChooseForCharacter1 == 8 || randomChooseForCharacter2 == 8 || randomChooseForCharacter3 == 8) {
            board.getCharacter8().setInGame(true);
        }
        if (randomChooseForCharacter1 == 9 || randomChooseForCharacter2 == 9 || randomChooseForCharacter3 == 9) {
            board.getCharacter9().setInGame(true);
        }
        if (randomChooseForCharacter1 == 10 || randomChooseForCharacter2 == 10 || randomChooseForCharacter3 == 10) {
            board.getCharacter10().setInGame(true);
        }
        if (randomChooseForCharacter1 == 11 || randomChooseForCharacter2 == 11 || randomChooseForCharacter3 == 11) {
            board.getCharacter11().setInGame(true);
            board.getCharacter11().initializateCharacter(board.getBag());
        }
        if (randomChooseForCharacter1 == 12 || randomChooseForCharacter2 == 12 || randomChooseForCharacter3 == 12) {
            board.getCharacter12().setInGame(true);
        }
    }

    /**
     * Simply returns the index of first Player of orderedListOfPlayersThatHaveToPlayAssistant
     *
     * @return first playerIndex
     */
    public int indexOfPlayerThatPlayAssistantFirst() {
        return orderedListOfPlayersThatHaveToPlayAssistant.get(0).getPlayerIndex();
    }

    /**
     * Simply returns the index of firstPlayer of orderedListOfPlayersThatHaveToMove
     *
     * @return first playerIndex
     */
    public int indexOfplayerThatMoveFirst() {
        return orderedListOfPlayersThatHaveToMove.get(0).getPlayerIndex();
    }

    /**
     * This method calculate the minimum number of towers in all players'dashboard.If 2 players have the same number
     * of towers, it counts the number of teachers, and then return the first player that has the major number of teachers.
     *
     * @return: index of winning player
     */
    public int indexOfWinningPlayer() {
        int indexOfWinningPlayer = 5;
        Map<Integer, Integer> towersForPlayer = new HashMap<>();
        for (Player p : board.getPlayersInGame()) {
            towersForPlayer.put(p.getPlayerIndex(), p.getDashboard().getNumberOfTowers());
        }
        int min = 8;
        for (Player p : board.getPlayersInGame()) {
            if (p.getDashboard().getNumberOfTowers() < min) {
                min = p.getDashboard().getNumberOfTowers();
                indexOfWinningPlayer = p.getPlayerIndex();
            }
        }
        int counter = 0;
        for (Player p : board.getPlayersInGame()) {
            if (p.getDashboard().getNumberOfTowers() == min) {
                counter++;
            }
        }
        if (counter == 1) {
            return indexOfWinningPlayer;
        } else {
            Map<Integer, Integer> teachersForPlayer = new HashMap<>();
            for (Player p : board.getPlayersInGame()) {
                int numberOfTeachers = 0;
                if (p.getDashboard().isRedTeacherPresent() && min == p.getDashboard().getNumberOfTowers()) {
                    numberOfTeachers++;
                }
                if (p.getDashboard().isBlueTeacherPresent() && min == p.getDashboard().getNumberOfTowers()) {
                    numberOfTeachers++;
                }
                if (p.getDashboard().isGreenTeacherPresent() && min == p.getDashboard().getNumberOfTowers()) {
                    numberOfTeachers++;
                }
                if (p.getDashboard().isYellowTeacherPresent() && min == p.getDashboard().getNumberOfTowers()) {
                    numberOfTeachers++;
                }
                if (p.getDashboard().isPinkTeacherPresent() && min == p.getDashboard().getNumberOfTowers()) {
                    numberOfTeachers++;
                }
                teachersForPlayer.put(p.getPlayerIndex(), numberOfTeachers);
            }
            int maxTeachers = 0;
            for (int i = 0; i < teachersForPlayer.size(); i++) {
                if (teachersForPlayer.get(i) > maxTeachers) {
                    indexOfWinningPlayer = i;
                }
            }
            return indexOfWinningPlayer;
        }
    }

    /**
     * This method is called from endGameCheckerTeam() and it calculates who is the winning team, bases of the number
     * of towers of white team and the number of Towers of Black team. If there is a draw number, it will calculate
     * the number of teachers for each team.
     *
     * @return
     */
    public String calculateWinningTeam() {
        String winningTeam = "Game over. The winner is: The game is draw";
        if (towersOfWhiteTeam < towersOfBlackTeam) {
            return "Game over.The winner is:  White Team.";
        } else if (towersOfBlackTeam < towersOfWhiteTeam) {
            return "Game over.The winner is:  Black Team.";
        } else {
            int numberOfTeachersWhiteTeam = 0;
            int numberOfTeachersBlackTeam = 0;
            for (Player p : board.getPlayersInGame()) {
                if (p.getAssociatedTowerColor() == TowerColor.BLACK) {
                    if (p.getDashboard().isRedTeacherPresent()) {
                        numberOfTeachersBlackTeam++;
                    }
                    if (p.getDashboard().isBlueTeacherPresent()) {
                        numberOfTeachersBlackTeam++;
                    }
                    if (p.getDashboard().isYellowTeacherPresent()) {
                        numberOfTeachersBlackTeam++;
                    }
                    if (p.getDashboard().isPinkTeacherPresent()) {
                        numberOfTeachersBlackTeam++;
                    }
                    if (p.getDashboard().isGreenTeacherPresent()) {
                        numberOfTeachersBlackTeam++;
                    }
                } else if (p.getAssociatedTowerColor() == TowerColor.WHITE) {
                    if (p.getDashboard().isRedTeacherPresent()) {
                        numberOfTeachersWhiteTeam++;
                    }
                    if (p.getDashboard().isBlueTeacherPresent()) {
                        numberOfTeachersWhiteTeam++;
                    }
                    if (p.getDashboard().isYellowTeacherPresent()) {
                        numberOfTeachersWhiteTeam++;
                    }
                    if (p.getDashboard().isPinkTeacherPresent()) {
                        numberOfTeachersWhiteTeam++;
                    }
                    if (p.getDashboard().isGreenTeacherPresent()) {
                        numberOfTeachersWhiteTeam++;
                    }
                }
            }
            if (numberOfTeachersWhiteTeam > numberOfTeachersBlackTeam) {
                return "Game over.The winner is:  White Team.";
            } else if (numberOfTeachersBlackTeam > numberOfTeachersWhiteTeam) {
                return "Game over.The winner is:  Black Team.";
            }
        }
        return winningTeam;
    }
}
