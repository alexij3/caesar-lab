package sample.cipher;

import org.apache.commons.lang3.StringUtils;
import sample.Alphabet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RSA implements Cipher {

    private Alphabet alphabet;
    private int q;
    private int p;
    private int e;
    private int d;
    private int n;

    private Map<Character, Integer> alphMap = new HashMap<>();
    private Map<Integer, Character> numbMap = new HashMap<>();

    public RSA(Alphabet alphabet, int q, int p, int e, int d) {
        this.alphabet = alphabet;
        this.q = q;
        this.p = p;
        this.e = e;
        this.d = d;
        this.n = p * q;
        this.setupAlphabet();
    }

    private void setupAlphabet() {
        IntStream
                .range(0, this.alphabet.getAlphabetChars().length())
                .forEach(index -> {
                    char letter = this.alphabet.getAlphabetChars().charAt(index);
                    int value = this.rsa(index);
                    this.alphMap.put(letter, value);
                    this.numbMap.put(value, letter);
                });
    }

    @Override
    public String encrypt(String initialText) {
        return initialText.chars()
                .mapToObj(c -> (char) c)
                .map(Character::toLowerCase)
                .map(character -> {
                    if (!Character.isSpaceChar(character) && this.alphabet.getAlphabetChars().indexOf(character) >= 0) {
                        return alphMap.get(character);
                    }
                    return character;
                })
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    public String decrypt(String encryptedText) {
        return Arrays.stream(encryptedText.split(","))
                .filter(StringUtils::isNumeric)
                .map(Integer::parseInt)
                .map(character -> this.alphabet.getAlphabetChars().charAt(this.reverseRsa(character)))
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }

    private int rsa(int value) {
        return (int) (Math.pow(value, e) % n);
    }

    private int reverseRsa(int value) {
        return (int) (Math.pow(value, d) % n);
    }

    @Override
    public String decryptOne(String encryptedText, Alphabet alphabet, int step) {
        return null;
    }
}
