package sample.cipher;

import sample.Alphabet;

public class SDES implements Cipher {

    public int K1, K2;
    public static final int P10[] = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    public static final int P10max = 10;
    public static final int P8[] = { 6, 3, 7, 4, 8, 5, 10, 9};
    public static final int P8max = 10;
    public static final int P4[] = { 2, 4, 3, 1};
    public static final int P4max = 4;

    public static final int IP[] = { 2, 6, 3, 1, 4, 8, 5, 7};
    public static final int IPmax = 8;
    public static final int IPMinus[] = { 4, 1, 3, 5, 7, 2, 8, 6};
    public static final int IPMinusMax = 8;

    public static final int FK[] = { 4, 1, 2, 3, 2, 3, 4, 1};
    public static final int FKmax = 4;

    public static final int S0[][] = {{ 1, 0, 3, 2},
            { 3, 2, 1, 0},
            { 0, 2, 1, 3},
            { 3, 1, 3, 2}};
    public static final int S1[][] = {{ 0, 1, 2, 3},
            { 2, 0, 1, 3},
            { 3, 0, 1, 2},
            { 2, 1, 0, 3}};

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

    private int permute( int x, int p[], int pmax){
        int y = 0;
        for(int i = 0; i < p.length; ++i) {
            y <<= 1;
            y |= (x >> (pmax - p[i])) & 1;
        }
        return y;
    }

    private int F( int R, int K){
        int t = permute( R, FK, FKmax) ^ K;
        int t0 = (t >> 4) & 0xF;
        int t1 = t & 0xF;
        t0 = S0[ ((t0 & 0x8) >> 2) | (t0 & 1) ][ (t0 >> 1) & 0x3 ];
        t1 = S1[ ((t1 & 0x8) >> 2) | (t1 & 1) ][ (t1 >> 1) & 0x3 ];
        t = permute( (t0 << 2) | t1, P4, P4max);
        return t;
    }

    private int fK( int m, int K){
        int L = (m >> 4) & 0xF;
        int R = m & 0xF;
        return ((L ^ F(R,K)) << 4) | R;
    }

    private int SW( int x) {
        return ((x & 0xF) << 4) | ((x >> 4) & 0xF);
    }
}
