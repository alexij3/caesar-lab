package sample;

import java.util.ArrayList;
import java.util.List;

public class CaesarCipher implements Cipher {

    public CaesarCipher() {
    }

    @Override
    public String encrypt(String initialText, Alphabet alphabet, int step) {
        StringBuilder encryptedText = new StringBuilder();
        String alphabetChars = alphabet.getAlphabetChars();
        initialText.chars()
                .mapToObj(c -> (char) c)
                .map(character -> this.encryptCharacter(character, alphabetChars, step))
                .forEach(encryptedText::append);
        return encryptedText.toString();
    }

    @Override
    public List<String> decrypt(String encryptedText, Alphabet alphabet) {
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
        return decryptedStrings;
    }

    @Override
    public String decrypt(String encryptedText, Alphabet alphabet, int step) {
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

}
