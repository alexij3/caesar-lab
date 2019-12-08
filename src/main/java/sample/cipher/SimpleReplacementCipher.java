package sample.cipher;

import sample.Alphabet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleReplacementCipher implements Cipher {

    private int step;
    private Alphabet alphabet;
    private Map<Integer, Character> charactersMap = new HashMap<>();

    public SimpleReplacementCipher() {
    }

    public SimpleReplacementCipher(int step, Alphabet alphabet) {
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
        String alphabetChars = this.alphabet.getAlphabetChars();
        this.generateCharactersMap(this.step);
        return initialText.toLowerCase().chars()
                .mapToObj(c -> (char) c)
                .filter(character -> this.alphabet.getAlphabetChars().indexOf(character) >= 0)
                .map(character -> this.encryptCharacter(character, alphabetChars, this.step))
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    public String decrypt(String encryptedText) {
        return Arrays.stream(encryptedText.split(","))
                .map(character -> this.charactersMap.get(Integer.parseInt(character)))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    @Override
    public String decryptOne(String encryptedText, Alphabet alphabet, int step) {
        return encrypt(encryptedText, alphabet, step);
    }

    private int encryptCharacter(char character, String alphabetChars, int step) {
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
            return newPosition;
        }
        return -1;
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

    private void generateCharactersMap(int step) {
        this.charactersMap.clear();
        this.alphabet.getAlphabetChars()
                .chars()
                .mapToObj(c -> (char) c)
                .forEach(character -> {
                    int index = this.alphabet.getAlphabetChars().indexOf(character);
                    int newPosition = index + step;
                    if (newPosition < 0 && index + newPosition < 0) {
                        newPosition = this.alphabet.getAlphabetChars().length() + newPosition + index;
                    } else {
                        if (newPosition >= this.alphabet.getAlphabetChars().length()) {
                            newPosition = newPosition - this.alphabet.getAlphabetChars().length();
                        }
                    }
                    this.charactersMap.put(newPosition, character);
                });
    }

    public Map<Integer, Character> getCharactersMap() {
        return charactersMap;
    }
}
