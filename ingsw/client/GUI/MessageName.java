package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.communication.CommandName;

public enum MessageName {
    /**
     * This is an enum class that contains every type of message that the client can receive from the server.
     * It's made to make more readble the code in the MessageHandler and in the server.
     **/
    STARTINGMESSAGE("The interface will go on when all the other players that started before you will insert their" +
            " nickname"),
    CLIREQUEST("You want to use CLI or GUI? Write in this way. graphic CLI if you want CLI, graphic GUI if you" +
            " want GUI"),
    SETUPREQUEST("You are the first player in game. Select the number of players and variant.Write in this way: "
            + CommandName.GAMESETUP.getValue() + " <number> <variant>. ('expert' or 'normal')"),
    NICKNAMENOTEAM("Write your nickname in this way:" + CommandName.NICKNAMEREQUEST.getValue() + " <nickname>.Then " +
            "the game will continue when all the players will be connected."),
    NICKNAMETEAMSBLACK("Write your nickname in this way:" + CommandName.NICKNAMEREQUEST.getValue() + " <nickname>.Then " +
            "the game will continue when all the players will be connected. You are in the black team"),
    NICKNAMETEAMSWHITE("Write your nickname in this way:" + CommandName.NICKNAMEREQUEST.getValue() + " <nickname>.Then " +
            "the game will continue when all the players will be connected. You are in the white team"),
    PLAYASSISTANTREQUEST("Now it's your turn.Insert the command to play Assistant in this way: " + CommandName.PLAYASSISTANT.getValue()
            + " <indexOfAssistantToPlay>"),
    CHARACTERREQUEST("You want to play a character? If yes, write 'y', else 'n'"),
    INDEXOFCHARACTERREQUEST("Write the number of Character you want to play(1,2,3,4 etc.)"),
    CHARACTER1REQUEST("If you want to activate Character1, write in this way. character1 <islandIndex> <pawnColor>  " +
            "(if red, write 'r'.if green, wirte 'g' and so"),
    CHARACTER2REQUEST("If you want to activate Character2, write in this way. character2"),
    CHARACTER3REQUEST("If you want to activate Character3, write in this way. character3 <islandIndex>"),
    CHARACTER4REQUEST("If you want to activate Character4, write in this way. character4 "),
    CHARACTER5REQUEST("If you want to activate Character5, write in this way. character5 <islandIndex> "),
    CHARACTER6REQUEST("If you want to activate Character6, write in this way. character6 <islandIndex>"),
    CHARACTER7REQUEST("If you want to activate Character7, write in this way. character7 <studentFromEntrance> <studentFromEntrance> <studentFromEntrance>  " +
            "<studentFromCharacter> <studentFromCharacter> <studentFromCharacter>" +
            "(if red, write 'r'.if green, wirte 'g' and so)" +
            "(you can chose until 3 students to exchange, import thing is to write first studentFromEntrance, then studentFromCharacter in order)"),
    CHARACTER8REQUEST("If you want to activate Character8, write in this way. character8"),
    CHARACTER9REQUEST("If you want to activate Character9, write in this way. character9 <pawnColor>  " +
            "(if red, write 'r'.if green, wirte 'g' and so"),
    CHARACTER10REQUEST("If you want to activate Character10, write in this way. character10 <studentFromEntrance> <studentFromEntrance> <studentFromHall>  " +
            "<studentFromHall>" +
            "(if red, write 'r'.if green, wirte 'g' and so)" +
            "(you can chose until 2 students to exchange, import thing is to write first studentFromEntrance, then studentFromHall in order)"),
    CHARACTER11REQUEST("If you want to activate Character11, write in this way. character11 <pawnColor>  " +
            "(if red, write 'r'.if green, wirte 'g' and so"),
    CHARACTER12REQUEST("If you want to activate Character12, write in this way. character12 <pawnColor>  " +
            "(if red, write 'r'.if green, wirte 'g' and so"),
    CANTPLAYCHARACTER("You can't play any character.Write whatever you want,then will start the real action phase"),
    MOVEMOTHERNATUREREQUEST("Now you have to move mother nature, based on assistant you played in planning phase.Write in this way. mothernature <numberOFMovements>"),
    HALLORISLANDREQUEST("Now it's your turn in action phase: if you want to add student on island, write island, else " +
            "write hall"),
    STUDENTONISLANDREQUEST("Ok, you have to insert the student you want to move on island.Write in this way: " +
            "island <islandIndex> <student> (if student you want to move is red, insert 'r', if it's yellow" +
            " 'y' etc."),
    STUDENTINHALLREQUEST("Ok, you have to insert the student you want to move in hall.Write in this way: " +
            "hall <student> (if student you want to move is red, insert 'r', if it's yellow" +
            " 'y' etc."),
    CLOUDREQUEST("Ok, chose the cloud wich with you want to refill your entrance. Write in this way. cloud <indexOfCloud>"),
    UNIFYISLANDCHECK("Now let's see if there are islands to unify.Write whatever you want to go on"),
    BUILDTOWERCHECK("Now let's see if you can build a tower on this island.Write whatever you want to go on"),
    ENDGAME("Game over. The winner is: "),
    GAMESTATE("Ok, now it's your turn.Write whatever you want to go on"),
    //
    CONFIRMGAMESETUPTEAMS("Ok, the game has been initialized. You are in the Black team.Next player" +
            "that will connect to server will be in team with you"),
    CONFIRMGAMESETUP("Ok, the game has been initialized."),
    CONFIRMSTARTGAME("Ok, now the game is started.Game will continue when the casual starting player will" +
            " play an assistant"),
    CONFIRMSTARTGAMEEXPERT("Ok, now the game is started.Characters in game are: "),//51
    CONFIRMTOGOON("Ok, let's go on."),
    CONFIRMASSISTANTPLAYED("Ok, assistant has been played correctly.Waiting for other players to play."),
    CONFIRMSTATE("Ok, this is the state after all players before you"),
    CONFIRMBUILTEDTOWER("Ok, a Tower has been builted here."),
    CONFIRMUNIFYISLANDS("Ok, islands that could be unified are now unified."),
    CONFIRMCLOUD("Ok, entrance has been refilled.Waiting to start a new turn"),
    CONFIRMADDISLAND("Ok, let's add it to island"),
    CONFIRMADDHALL("Ok, let's add it to hall"),
    CONFIRMSTUDENTISLANDCORRECTLY("Ok, student has been added to the island correctly."),
    CONFIRMSTUDENTHALLCORRECTLY("Ok, student has been added in the hall correctly."),
    CONFIRMMOTHERNATUREMOVEDCORRECTLY("Ok, Mother Nature has been moved correctly"),
    CONFIRMPLAYCHARACTER("Ok, so select it."),
    CONFIRMDONTPLAYCHARACTER("Ok, no character has been played from you."),
    CONFIRMPLAYSELECTEDCHARACTER1("Ok, so let's play character 1"),
    CONFIRMCHARACTER1("Ok, Character1 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER2("Ok, so let's play character 2"),
    CONFIRMCHARACTER2("Ok, Character2 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER3("Ok, so let's play character 3"),
    CONFIRMCHARACTER3("Ok, Character3 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER4("Ok, so let's play character 4"),
    CONFIRMCHARACTER4("Ok, Character4 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER5("Ok, so let's play character 5"),
    CONFIRMCHARACTER5("Ok, Character5 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER6("Ok, so let's play character 6"),
    CONFIRMCHARACTER6("Ok, Character6 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER7("Ok, so let's play character 7"),
    CONFIRMCHARACTER7("Ok, Character7 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER8("Ok, so let's play character 8"),
    CONFIRMCHARACTER8("Ok, Character8 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER9("Ok, so let's play character 9"),
    CONFIRMCHARACTER9("Ok, Character9 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER10("Ok, so let's play character 10"),
    CONFIRMCHARACTER10("Ok, Character10 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER11("Ok, so let's play character 11"),
    CONFIRMCHARACTER11("Ok, Character11 has been played correctly.Let's go on."),
    CONFIRMPLAYSELECTEDCHARACTER12("Ok, so let's play character 12"),
    CONFIRMCHARACTER12("Ok, Character12 has been played correctly.Let's go on."),
    CONFRIMTOGOONCHARACTER("Ok, let's go."),
    PING("Client connected."),
    CONFIRMPING("Ok"),
    ADJOURNEDGAMESTATE("The turn's player has made some changes on the board: "),
    STARTTURN("Ok, it's your turn."),
    ;

    private final String value;


    public String getValue() {
        return value;
    }

    MessageName(String value) {
        this.value = value;
    }
}
