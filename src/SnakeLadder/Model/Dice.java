package SnakeLadder.Model;

import java.util.Random;

public class Dice {

    /**
     * Method yang digunakan untuk melempar Dadu
     * @param n angka maksimal dadu
     * @return random int 1 to n
     */
    public int roll(int n) {
        return (new Random().nextInt(n) + 1);
    }
}
