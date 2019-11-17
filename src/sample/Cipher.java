package sample;

import java.util.List;

public interface Cipher {

    String encrypt(String initialText, Alphabet alphabet, int step);

    List<String> decrypt(String encryptedText, Alphabet alphabet);

    String decrypt(String encryptedText, Alphabet alphabet, int step);

}
