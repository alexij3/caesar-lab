package sample.cipher.gamma;

public class Key {

    private int firstKey;
    private int secondKey;
    private int thirdKey;

    public Key(int firstKey, int secondKey, int thirdKey) {
        this.firstKey = firstKey;
        this.secondKey = secondKey;
        this.thirdKey = thirdKey;
    }

    public int getFirstKey() {
        return firstKey;
    }

    public void setFirstKey(int firstKey) {
        this.firstKey = firstKey;
    }

    public int getSecondKey() {
        return secondKey;
    }

    public void setSecondKey(int secondKey) {
        this.secondKey = secondKey;
    }

    public int getThirdKey() {
        return thirdKey;
    }

    public void setThirdKey(int thirdKey) {
        this.thirdKey = thirdKey;
    }
}
