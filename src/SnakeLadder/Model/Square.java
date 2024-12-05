package SnakeLadder.Model;

import java.awt.Color;
import java.util.Random;

public class Square {

    /**
     * posisi kotak di papan
     */
    protected int x, y;
    
    /**
     * tipe kotak (square/snake/ladder)
     */
    protected String type;
    
    /**
     * Warna kotak
     */
    protected Color color;

    
    /**
     * Contructor untuk menginisialisasi x, y, color,  dan type.
     * @param x posisi horizontal kotak
     * @param y posisi vertikal kotak
     */
    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        Random rand = new Random();
        int i = rand.nextInt(3);
        Color c;
        if (i == 0) {
            c = new Color(255, 216, 0);
        } else if (i == 1) {
            c = new Color(0, 255, 144);
        } else if (i == 2) {
            c = new Color(255, 127, 182);
        } else {
            c = Color.WHITE;
        }
        this.color = c;
        this.type = "Square";
    }

    /**
     * Method untuk mendapatkan posisi horizontal kotak ini.
     * @return int x yang merupakan posisi horizontal kotak ini.
     */
    public int getX() {
        return x;
    }

    
    /**
     * Method untuk mendapatkan posisi vertikal kotak ini.
     * @return int y yang merupakan posisi vertikal kotak ini.
     */
    public int getY() {
        return y;
    }

    /**
     * Method untuk mendapatkan tipe kotak ini (square/snake/ladder).
     * @return String type yang merupakan tipe kotak ini.
     */
    public String getType() {
        return type;
    }

    /**
     * Metod untuk mendapatkan warna kotak.
     * @return warna dari kotak.
     */
    public Color getColor() {
        return color;
    }
}
