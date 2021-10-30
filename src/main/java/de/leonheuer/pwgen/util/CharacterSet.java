package de.leonheuer.pwgen.util;

public enum CharacterSet {

    CAPS("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    SMALL("abcdefghijklmnopqrstuvwxyz"),
    NUMBERS("0123456789"),
    CHARS("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"),
    ;

    private final String chars;

    CharacterSet(String chars) {
        this.chars = chars;
    }

    public String getChars() {
        return chars;
    }

}
