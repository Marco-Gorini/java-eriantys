package it.polimi.ingsw.model.gamemodel;

public enum PawnColor {
    RED("r"),
    BLUE("b"),
    PINK("p"),
    YELLOW("y"),
    GREEN("g");

    public static PawnColor fromValue(String s){
        for(PawnColor p : PawnColor.values()){
            if(p.getValue().equalsIgnoreCase(s)){
                return p;
            }
        }
        throw new IllegalArgumentException("Variant not found: " + s);
    }

    private final String value;

    public String getValue() {
        return value;
    }

    PawnColor(String value) {
        this.value = value;
    }

}

