package it.polimi.ingsw.controller.communication;

import it.polimi.ingsw.controller.communication.commands.*;
import it.polimi.ingsw.exception.CommandNotFoundException;
import it.polimi.ingsw.exception.SyntaxErrorException;
import it.polimi.ingsw.model.gamemodel.PawnColor;
import it.polimi.ingsw.model.gamemodel.Variant;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {

    /**
     * This method check the string inserted from the client, then clean it and execute relative code.Then run the command based
     * on the prefix the client writes.
     *
     * @param command: string from client
     * @param playerIndex: playerIndex attribute of clientHandler
     * @return: new command based on prefix of String the client inserted
     * @throws CommandNotFoundException: wrong command
     * @throws SyntaxErrorException: syntax error
     */
    public static Command parse(String command, int playerIndex) throws CommandNotFoundException, SyntaxErrorException {
        if (command.startsWith(CommandName.GAMESETUP.getValue())) {
            String[] arrayString = command.split(" ");
            if (arrayString.length == 3) {
                try {
                    int numberOfPlayers = Integer.parseInt(arrayString[1]);
                    Variant gameVariant = Variant.fromValue(arrayString[2]);
                    if ((numberOfPlayers <= 1 || numberOfPlayers > 4)) {
                        throw new SyntaxErrorException("Syntax error: " + command);
                    }
                    return new GameSetUpCommand(numberOfPlayers, gameVariant);
                } catch (Exception ex) {
                    throw new SyntaxErrorException("Syntax error: " + command);
                }
            } else {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.NICKNAMEREQUEST.getValue())) {
            String nickname = command.replace(CommandName.NICKNAMEREQUEST.getValue(), "").trim();
            if (nickname.isEmpty()) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
            return new NicknameCommand(nickname, playerIndex);
        } else if (command.startsWith(CommandName.PLAYASSISTANT.getValue())) {
            String commandPlayAssistant = command.replace(CommandName.PLAYASSISTANT.getValue(), "").trim();
            try {
                int indexOfAssistant = Integer.parseInt(commandPlayAssistant);
                return new PlayAssistantCommand(playerIndex, indexOfAssistant);
            } catch (Exception ex) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.PLAYCHARACTER1.getValue())) {
            String[] arrayString = command.split(" ");
            if (arrayString.length == 3) {
                try {
                    int islandIndex = Integer.parseInt(arrayString[1]);
                    PawnColor pawnColor = PawnColor.fromValue(arrayString[2]);
                    return new Character1Command(islandIndex, pawnColor, playerIndex);
                } catch (Exception ex) {
                    throw new SyntaxErrorException("Syntax error: " + command);
                }
            } else {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.PLAYCHARACTER2.getValue())) {
            return new Character2Command(playerIndex);
        } else if (command.startsWith(CommandName.PLAYCHARACTER3.getValue())) {
            String commandPlayCharacter3 = command.replace(CommandName.PLAYCHARACTER3.getValue(), "").trim();
            try {
                int islandIndex = Integer.parseInt(commandPlayCharacter3);
                return new Character3Command(islandIndex, playerIndex);
            } catch (Exception ex) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.PLAYCHARACTER4.getValue())) {
            return new Character4Command(playerIndex);
        } else if (command.startsWith(CommandName.PLAYCHARACTER5.getValue())) {
            String commandPlayCharacter5 = command.replace(CommandName.PLAYCHARACTER5.getValue(), "").trim();
            try {
                int islandIndex = Integer.parseInt(commandPlayCharacter5);
                return new Character5Command(islandIndex, playerIndex);
            } catch (Exception ex) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.PLAYCHARACTER6.getValue())) {
            String commandPlayCharacter6 = command.replace(CommandName.PLAYCHARACTER6.getValue(), "").trim();
            try {
                int islandIndex = Integer.parseInt(commandPlayCharacter6);
                return new Character6Command(islandIndex, playerIndex);
            } catch (Exception ex) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.PLAYCHARACTER7.getValue())) {
            String[] commandPlayCharacter7 = command.split(" ");
            if (commandPlayCharacter7.length % 2 == 1) {
                if (commandPlayCharacter7.length == 3) {
                    try {
                        PawnColor pawnColorEntrance = PawnColor.fromValue(commandPlayCharacter7[1]);
                        PawnColor pawnColorCharacter = PawnColor.fromValue(commandPlayCharacter7[2]);
                        List<PawnColor> studentsFromEntrance = new ArrayList<>();
                        List<PawnColor> studentsFromCharacter = new ArrayList<>();
                        studentsFromEntrance.add(pawnColorEntrance);
                        studentsFromCharacter.add(pawnColorCharacter);
                        return new Character7Command(studentsFromCharacter, studentsFromEntrance, playerIndex);
                    } catch (Exception ex) {
                        throw new SyntaxErrorException("Syntax error: " + command);
                    }
                }
                if (commandPlayCharacter7.length == 5) {
                    try {
                        List<PawnColor> studentsFromEntrance = new ArrayList<>();
                        List<PawnColor> studentsFromCharacter = new ArrayList<>();
                        studentsFromEntrance.add(PawnColor.fromValue(commandPlayCharacter7[1]));
                        studentsFromEntrance.add(PawnColor.fromValue(commandPlayCharacter7[2]));
                        studentsFromCharacter.add(PawnColor.fromValue(commandPlayCharacter7[3]));
                        studentsFromCharacter.add(PawnColor.fromValue(commandPlayCharacter7[4]));
                        return new Character7Command(studentsFromCharacter, studentsFromEntrance, playerIndex);
                    } catch (Exception ex) {
                        throw new SyntaxErrorException("Syntax error: " + command);
                    }
                }
                if (commandPlayCharacter7.length == 7) {
                    try {
                        List<PawnColor> studentsFromEntrance = new ArrayList<>();
                        List<PawnColor> studentsFromCharacter = new ArrayList<>();
                        studentsFromEntrance.add(PawnColor.fromValue(commandPlayCharacter7[1]));
                        studentsFromEntrance.add(PawnColor.fromValue(commandPlayCharacter7[2]));
                        studentsFromEntrance.add(PawnColor.fromValue(commandPlayCharacter7[3]));
                        studentsFromCharacter.add(PawnColor.fromValue(commandPlayCharacter7[4]));
                        studentsFromCharacter.add(PawnColor.fromValue(commandPlayCharacter7[5]));
                        studentsFromCharacter.add(PawnColor.fromValue(commandPlayCharacter7[6]));
                        return new Character7Command(studentsFromCharacter, studentsFromEntrance, playerIndex);
                    } catch (Exception ex) {
                        throw new SyntaxErrorException("Syntax error: " + command);
                    }
                } else {
                    throw new SyntaxErrorException("Syntax error: " + command);
                }
            } else {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.PLAYCHARACTER8.getValue())) {
            return new Character8Command(playerIndex);
        } else if (command.startsWith(CommandName.PLAYCHARACTER9.getValue())) {
            String commandPlayCharacter9 = command.replace(CommandName.PLAYCHARACTER9.getValue(), "").trim();
            try {
                PawnColor pawnColor = PawnColor.fromValue(commandPlayCharacter9);
                return new Character9Command(pawnColor, playerIndex);
            } catch (Exception ex) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.PLAYCHARACTER10.getValue())) {
            String[] commandPlayCharacter10 = command.split(" ");
            if (commandPlayCharacter10.length % 2 == 1) {
                if (commandPlayCharacter10.length == 3) {
                    try {
                        PawnColor pawnColorEntrance = PawnColor.fromValue(commandPlayCharacter10[1]);
                        PawnColor pawnColorHall = PawnColor.fromValue(commandPlayCharacter10[2]);
                        List<PawnColor> studentsFromEntrance = new ArrayList<>();
                        List<PawnColor> studentsFromHall = new ArrayList<>();
                        studentsFromEntrance.add(pawnColorEntrance);
                        studentsFromHall.add(pawnColorHall);
                        return new Character10Command(studentsFromHall, studentsFromEntrance, playerIndex);
                    } catch (Exception ex) {
                        throw new SyntaxErrorException("Syntax error: " + command);
                    }
                }
                if (commandPlayCharacter10.length == 5) {
                    try {
                        List<PawnColor> studentsFromEntrance = new ArrayList<>();
                        List<PawnColor> studentsFromHall = new ArrayList<>();
                        studentsFromEntrance.add(PawnColor.fromValue(commandPlayCharacter10[1]));
                        studentsFromEntrance.add(PawnColor.fromValue(commandPlayCharacter10[2]));
                        studentsFromHall.add(PawnColor.fromValue(commandPlayCharacter10[3]));
                        studentsFromHall.add(PawnColor.fromValue(commandPlayCharacter10[4]));
                        return new Character10Command(studentsFromHall, studentsFromEntrance, playerIndex);
                    } catch (Exception ex) {
                        throw new SyntaxErrorException("Syntax error: " + command);
                    }
                } else {
                    throw new SyntaxErrorException("Syntax error: " + command);
                }
            } else {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.PLAYCHARACTER11.getValue())) {
            String commandPlayCharacter11 = command.replace(CommandName.PLAYCHARACTER11.getValue(), "").trim();
            try {
                PawnColor pawnColor = PawnColor.fromValue(commandPlayCharacter11);
                return new Character11Command(pawnColor, playerIndex);
            } catch (Exception ex) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.PLAYCHARACTER12.getValue())) {
            String commandPlayCharacter12 = command.replace(CommandName.PLAYCHARACTER12.getValue(), "").trim();
            try {
                PawnColor pawnColor = PawnColor.fromValue(commandPlayCharacter12);
                return new Character12Command(pawnColor, playerIndex);
            } catch (Exception ex) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.MOVESTUDENTISLAND.getValue())) {
            String[] arrayString = command.split(" ");
            if (arrayString.length == 3) {
                try {
                    int islandIndex = Integer.parseInt(arrayString[1]);
                    PawnColor pawnColor = PawnColor.fromValue(arrayString[2]);
                    return new MoveStudentOnIslandCommand(pawnColor, islandIndex, playerIndex);
                } catch (Exception ex) {
                    throw new SyntaxErrorException("Syntax error: " + command);
                }
            } else {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.MOVESTUDENTHALL.getValue())) {
            String commandMoveStudentHall = command.replace(CommandName.MOVESTUDENTHALL.getValue(), "").trim();
            try {
                PawnColor pawnColor = PawnColor.fromValue(commandMoveStudentHall);
                return new MoveStudentOnHallCommand(pawnColor, playerIndex);
            } catch (Exception ex) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.MOTHERNATURE.getValue())) {
            String commandMoveMotherNature = command.replace(CommandName.MOTHERNATURE.getValue(), "").trim();
            try {
                int movements = Integer.parseInt(commandMoveMotherNature);
                return new MoveMotherNatureCommand(movements, playerIndex);
            } catch (Exception ex) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        } else if (command.startsWith(CommandName.CHOOSECLOUD.getValue())) {
            String commandChooseCloud = command.replace(CommandName.CHOOSECLOUD.getValue(), "").trim();
            try {
                int chosenCloud = Integer.parseInt(commandChooseCloud);
                return new ChoseCloudCommand(chosenCloud, playerIndex);
            } catch (Exception ex) {
                throw new SyntaxErrorException("Syntax error: " + command);
            }
        }
        else {
            throw new CommandNotFoundException("Command not found: " + command);
        }
    }
}
