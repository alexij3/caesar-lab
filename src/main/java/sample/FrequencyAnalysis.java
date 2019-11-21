package sample;

import java.util.Map;
import java.util.stream.Collectors;

public class FrequencyAnalysis {

    private final Cipher cipher;

    public FrequencyAnalysis(Cipher cipher) {
        this.cipher = cipher;
    }

    public String decrypt(String encryptedString, Alphabet alphabet) {
        Map.Entry<Character, Long> theMostFrequentChar = encryptedString.chars()
                .mapToObj(c -> (char) c)
                .filter(character -> alphabet.getAlphabetChars().indexOf(character) >= 0)
                .collect(Collectors.groupingBy(Character::charValue, Collectors.counting()))
                .entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get();

        char encryptedStringMostFrequentChar = theMostFrequentChar.getKey();
        char theMostFrequentAlphabetChar = Character.isLowerCase(encryptedStringMostFrequentChar) ?
                Character.toLowerCase(alphabet.getTheMostFrequentCharacter()) : alphabet.getTheMostFrequentCharacter();

        String alphabetChars = alphabet.getAlphabetChars();

        int step = alphabetChars.indexOf(theMostFrequentAlphabetChar) - alphabetChars.indexOf(encryptedStringMostFrequentChar);

        return cipher.decrypt(encryptedString, alphabet, step);
    };
}
