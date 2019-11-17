package sample;

public class Alphabet {

    private final char theMostFrequentCharacter;
    private final String alphabetChars;

    public Alphabet(char theMostFrequentCharacter, String alphabetChars) {
        this.theMostFrequentCharacter = theMostFrequentCharacter;
        this.alphabetChars = alphabetChars;
    }

    public char getTheMostFrequentCharacter() {
        return theMostFrequentCharacter;
    }

    public String getAlphabetChars() {
        return alphabetChars;
    }
}
