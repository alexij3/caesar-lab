package sample.cipher.gamma;

import sample.Alphabet;
import sample.cipher.Cipher;

public class GammaCipher implements Cipher {

    private Alphabet alphabet;
    private Key key;

    public GammaCipher(Alphabet alphabet, Key key) {
        this.alphabet = alphabet;
        this.key = key;
    }

    @Override
    public String encrypt(String initialText) {
        Gamma gamma = Gamma.generateGamma(this.key, initialText.length(), this.alphabet.getAlphabetChars().length());
        int[] gammaValues = gamma.getGamma();
        String[] characters = initialText.split("");
        int[] encryptedMessage = {};
        for (int i = 0; i < characters.length; i++) {
            if (alphabet.getAlphabetChars().indexOf(characters[i]) >= 0) {
                int position = alphabet.getAlphabetChars().indexOf(characters[i]);
                encryptedMessage[i] = (position + gammaValues[i]) % this.alphabet.getAlphabetChars().length();
            } else {
                int position = alphabet.getAlphabetChars().indexOf('q');
                encryptedMessage[i] = (position + gammaValues[i]) % this.alphabet.getAlphabetChars().length();
            }
        }
        return String.valueOf(encryptedMessage);
    }

    @Override
    public String decrypt(String encryptedText) {
        return null;
    }

    @Override
    public String decryptOne(String encryptedText, Alphabet alphabet, int step) {
        return null;
    }
}
