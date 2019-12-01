package sample.cipher.gamma;

import sample.Alphabet;
import sample.cipher.Cipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GammaCipher implements Cipher {

    private Alphabet alphabet;
    private Key key;

    public GammaCipher(Alphabet alphabet, Key key) {
        this.alphabet = alphabet;
        this.key = key;
    }

    @Override
    public String encrypt(String initialText) {
        List<Integer> gamma = generateGamma(this.key, initialText.length());
        return IntStream
                .range(0, initialText.length())
                .mapToObj(index -> this.getEncryptedCharacterAsNumber(index, initialText.charAt(index), gamma))
                .filter(num -> num > -1)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private int getEncryptedCharacterAsNumber(int index, char character, List<Integer> gamma) {
        String alphabetChars = this.alphabet.getAlphabetChars();
        int characterPosition = alphabetChars.indexOf(character);
        if (characterPosition != 1) {
            return (characterPosition + gamma.get(index)) % alphabetChars.length();
        }
        return characterPosition;
    }

    private List<Integer> generateGamma(Key key, int length) {
        List<Integer> gamma = new ArrayList<>();
        List<Integer> sequence = new ArrayList<>(Arrays.asList(key.getFirstKey(), key.getSecondKey(), key.getThirdKey()));

        for (int i = 3; i < length + 1; i++) {
            sequence.add((sequence.get(i-1) + sequence.get(i-3)) % alphabet.getAlphabetChars().length());
        }

        for (int i = 0; i < length; i++) {
            gamma.add((sequence.get(i) + sequence.get(i+1)) % alphabet.getAlphabetChars().length());
        }

        return gamma;
    }

    @Override
    public String decrypt(String encryptedText) {
        String[] encryptedTextSplit = encryptedText.split(", ");
        List<Integer> gamma = generateGamma(this.key, encryptedTextSplit.length);

        StringBuilder decryptedText = new StringBuilder();
        IntStream
                .range(0, encryptedTextSplit.length)
                .mapToObj(index -> {
                    int position = (Integer.parseInt(encryptedTextSplit[index]) + (this.alphabet.getAlphabetChars().length() - gamma.get(index))) % this.alphabet.getAlphabetChars().length();
                    return this.alphabet.getAlphabetChars().charAt(position);
                })
                .forEach(decryptedText::append);

        return decryptedText.toString();
    }

    @Override
    public String decryptOne(String encryptedText, Alphabet alphabet, int step) {
        return null;
    }
}
