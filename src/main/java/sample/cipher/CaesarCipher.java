package sample.cipher;

import sample.Alphabet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CaesarCipher implements Cipher {

    private int step;
    private Alphabet alphabet;

    public CaesarCipher() {
    }

    public CaesarCipher(int step, Alphabet alphabet) {
        this.step = step;
        this.alphabet = alphabet;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Alphabet getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public String encrypt(String initialText) {
        StringBuilder encryptedText = new StringBuilder();
        String alphabetChars = this.alphabet.getAlphabetChars();
        initialText.chars()
                .mapToObj(c -> (char) c)
                .map(character -> this.encryptCharacter(character, alphabetChars, this.step))
                .forEach(encryptedText::append);
        return encryptedText.toString();
    }

    @Override
    public String decrypt(String encryptedText) {
        String alphabetChars = alphabet.getAlphabetChars();
        List<String> decryptedStrings = new ArrayList<>();
        StringBuilder decryptedString = new StringBuilder();
        for (int i = 0; i < alphabetChars.length(); i++) {
            final int step = i;
            encryptedText.chars()
                    .mapToObj(c -> (char) c)
                    .map(character -> this.encryptCharacter(character, alphabetChars, step))
                    .forEach(decryptedString::append);
            decryptedStrings.add(decryptedString.toString());
            decryptedString.setLength(0);
        }
        return decryptedStrings.stream().collect(Collectors.joining("\n"));
    }

    @Override
    public String decryptOne(String encryptedText, Alphabet alphabet, int step) {
        return encrypt(encryptedText, alphabet, step);
    }

    private char encryptCharacter(char character, String alphabetChars, int step) {
        int oldPosition = alphabetChars.indexOf(character);
        if (oldPosition >= 0) {
            int newPosition = step % alphabetChars.length();
            if (newPosition < 0 && oldPosition + newPosition < 0) {
                newPosition = alphabetChars.length() + newPosition + oldPosition;
            } else {
                newPosition = oldPosition + newPosition;
                if (newPosition >= alphabetChars.length()) {
                    newPosition = newPosition - alphabetChars.length();
                }
            }
            return alphabetChars.charAt(newPosition);
        }
        return character;
    }

    private String encrypt(String initialText, Alphabet alphabet, int step) {
        StringBuilder encryptedText = new StringBuilder();
        String alphabetChars = alphabet.getAlphabetChars();
        initialText.chars()
                .mapToObj(c -> (char) c)
                .map(character -> this.encryptCharacter(character, alphabetChars, step))
                .forEach(encryptedText::append);
        return encryptedText.toString();
    }

}
