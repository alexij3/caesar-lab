package sample.cipher;

import sample.Alphabet;

public class BBS implements Cipher {

    private Alphabet alphabet;
    private int p;
    private int q;
    private int x;

    public BBS(Alphabet alphabet, int p, int q, int x) {
        this.alphabet = alphabet;
        this.p = p;
        this.q = q;
        this.x = x;
    }

    @Override
    public String encrypt(String initialText) {
        return null;
    }

    @Override
    public String decrypt(String encryptedText) {
        return null;
    }

    @Override
    public String decryptOne(String encryptedText, Alphabet alphabet, int step) {
        return null;
    }

    private int bbs() {
        int n = q * p;
        x = x * x  % n;
        return x % 2;
    }
}
