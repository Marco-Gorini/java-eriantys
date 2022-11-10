package it.polimi.ingsw.model.gamemodel;

public enum Variant {
    NORMAL("normal"),
    EXPERT("expert");

    public static Variant fromValue(String s){
        for(Variant w : Variant.values()) {
            if (w.getValue().equalsIgnoreCase(s)) {
                return w;
            }
        }
        throw new IllegalArgumentException("Variant not found: " + s);
    }


    private final String value;

    public String getValue() {
        return value;
    }

    Variant(String value) {
        this.value = value;
    }
}
