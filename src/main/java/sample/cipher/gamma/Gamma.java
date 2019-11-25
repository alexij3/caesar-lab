package sample.cipher.gamma;

public class Gamma {

    private int[] gamma;

    public Gamma(int[] gamma) {
        this.gamma = gamma;
    }

    public int[] getGamma() {
        return gamma;
    }

    public static Gamma generateGamma(Key key, int length, int alphabetLength) {
        int firstKey = key.getFirstKey();
        int secondKey = key.getSecondKey();
        int thirdKey = key.getThirdKey();

        int[] sequence = {firstKey, secondKey, thirdKey};

        for (int i = 3; i < length + 1; i++) {
            sequence[i] = (sequence[i-1] + sequence[i-3]) % alphabetLength;
        }

        int[] gamma = {};

        for (int i = 0; i < length; i++) {
            gamma[i] = (sequence[i] + sequence[i+1]) % alphabetLength;
        }

        return new Gamma(gamma);
    }

}
