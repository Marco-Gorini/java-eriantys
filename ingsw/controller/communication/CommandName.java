package it.polimi.ingsw.controller.communication;

public enum CommandName {
    /**
     * This class is made to manage all the command that could arrive from client, in particular these are the prefixes
     * of every command.
     **/
    NICKNAMEREQUEST("name"),
    GAMESETUP("setup"),
    PLAYASSISTANT("assistant"),
    PLAYCHARACTER1("character1"),
    PLAYCHARACTER2("character2"),
    PLAYCHARACTER3("character3"),
    PLAYCHARACTER4("character4"),
    PLAYCHARACTER5("character5"),
    PLAYCHARACTER6("character6"),
    PLAYCHARACTER7("character7"),
    PLAYCHARACTER8("character8"),
    PLAYCHARACTER9("character9"),
    PLAYCHARACTER10("character10"),
    PLAYCHARACTER11("character11"),
    PLAYCHARACTER12("character12"),
    MOVESTUDENTISLAND("island"),
    MOVESTUDENTHALL("hall"),
    MOTHERNATURE("mothernature"),
    CHOOSECLOUD("cloud");

    private final String value;

    public String getValue() {
        return value;
    }

    CommandName(String value) {
        this.value = value;
    }



}
