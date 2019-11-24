package sample.cipher;

import sample.Alphabet;

import java.util.stream.Collectors;

public class AffineCipher implements Cipher {

    private Alphabet alphabet;
    private int a;
    private int b;

    public AffineCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public AffineCipher(Alphabet alphabet, int a, int b) {
        this.alphabet = alphabet;
        this.a = a;
        this.b = b;
    }

    public Alphabet getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String encrypt(String initialText) {
        StringBuilder encryptedText = new StringBuilder();
        initialText.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> !Character.isSpaceChar(c))
                .map(this::encryptCharacter)
                .forEach(encryptedText::append);
        return encryptedText.toString();
    }

    @Override
    public String decrypt(String encryptedText) {
        String shiftedAlphabetChars = this.buildShiftedAlphabetChars();
        StringBuilder decryptedText = new StringBuilder();
        encryptedText.chars()
                .mapToObj(c -> (char) c)
                .map(character -> {
                    if (this.alphabet.getAlphabetChars().indexOf(character) >= 0) {
                        int position = shiftedAlphabetChars.indexOf(character);
                        return this.alphabet.getAlphabetChars().charAt(position);
                    }
                    return character;
                })
                .forEach(decryptedText::append);
        return decryptedText.toString();
    }

    private String buildShiftedAlphabetChars() {
        StringBuilder shiftedAlphabedChars = new StringBuilder();
        this.alphabet.getAlphabetChars().chars()
                .mapToObj(c -> (char) c)
                .filter(c -> !Character.isSpaceChar(c))
                .map(this::encryptCharacter)
                .forEach(shiftedAlphabedChars::append);
        return shiftedAlphabedChars.toString();
    }

    @Override
    public String decryptOne(String encryptedText, Alphabet alphabet, int step) {
        return encryptedText;
    }

    private char encryptCharacter(char character) {
        if (this.alphabet.getAlphabetChars().indexOf(character) < 0) {
            return character;
        }
        int newPosition = (a * this.alphabet.getAlphabetChars().indexOf(character) + b) % this.alphabet.getAlphabetChars().length();
        return this.alphabet.getAlphabetChars().charAt(newPosition);
    }
}
