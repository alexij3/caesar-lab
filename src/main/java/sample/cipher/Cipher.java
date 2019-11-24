package sample.cipher;

import sample.Alphabet;

public interface Cipher {

    String encrypt(String initialText);

    String decrypt(String encryptedText);

    String decryptOne(String encryptedText, Alphabet alphabet, int step);

}
