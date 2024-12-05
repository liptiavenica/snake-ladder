package SnakeLadder.Model;

import java.awt.Color;

public class Teleporter extends Square {

    /**
     * kotak yang akan dituju bila player mendarat di teleporter ini.
     */    
    private Square dest;

    /**
     * Constructor untuk menginisialisasi posisi horizontal dan vertikal kotak,
     * kotak yang akan dituju bila player mendarat di teleporter ini, dan tipe 
     * dari kotak (square/snake/ladder).
     * @param x posisi horizontal kotak
     * @param y posisi vertikal kotak
     * @param dest kotak yang akan dituju bila player mendarat di teleporter ini
     * @param type tipe kotak (square/snake/ladder)
     */
    public Teleporter(int x, int y,Square dest, String type) {
        super(x, y);
        this.dest = dest;
        this.type = type;
    }

    /**
     * Mengembalikan kotak yang akan dituju bila player mendarat di teleporter ini
     * @return Square dest
     */
    public Square getDest() {
        return dest;
    }

    /**
     * Mengeset kotak yang akan dituju bila player mendarat di teleporter ini
     * @param Square dest kotak yang akan diset
     */
    public void setDest(Square dest) {
        this.dest = dest;
    }
}
