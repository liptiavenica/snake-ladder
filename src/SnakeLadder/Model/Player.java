package SnakeLadder.Model;

import java.awt.image.BufferedImage;

public class Player {

    /**
     * posisi Player di koordinat, move counter, square counter, Player ke
     * berapa(1P,2P,etc)
     */
    private int x, y, movect = 0, sqct = 0, playerNum;
    /**
     * Posisi subimage yang akan digunaka saat menggambar sprite
     */
    private int spriteX = 0, spriteY = 0;
    /**
     * Posisi Player di Board
     */
    private Square sq;
    /**
     * Board
     */
    private Board parent;
    /**
     * boolean apakah Player ini berjalan ke depan atau mundur
     */
    private boolean moveForward = true;
    /**
     * boolean apakah layer ini AI atau bukan
     */
    private boolean isAI = false;
    /**
     * Boolean apakan player ini menghadap ke kanan
     */
    private boolean isFacingRight = true;
    /**
     * Kesulitan Player jika isAI= true, 1 =easy,2=normal,3=hard
     */
    private int difficulty = -1;
    /**
     * BufferedImage yang memuat gambar Player
     */
    private BufferedImage sprite;

    
    /**
     * Constructor
     * @param playerNum player ke berapa(1P,2P,etc)
     * @param sprite BufferedImage yang memuat gambar Player
     * @param parent Board
     * @param isAI boolean apakah player AI ayau bukan
     */
    public Player(int playerNum, BufferedImage sprite, Board parent, boolean isAI) {
        this.playerNum = playerNum;
        this.parent = parent;
        this.sprite = sprite;
        this.spriteY = 176 + (172 * playerNum);
        this.isAI = isAI;
        sq = parent.getSqArr()[0][9];
        x = sq.x * 70 + playerNum * 7 - 15;
        y = sq.y * 70 + 35;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter">
    
    /**
     * Method untuk memperoleh apakah player menghadap ke kanan.
     * @return true jika player menghadap ke kanan.
     */
    public boolean isIsFacingRight() {
        return isFacingRight;
    }

    
    /**
     * Method getter yang digunakan untuk mendapatkan sumbu Y dari posisi 
     * player di koordinat.
     * @return posisi player pada sumbu Y di koordinat
     */
    public int getY() {
        return y;
    }

    
    /**
     * Method getter yang digunakan untuk mendapatkan sumbu X dari posisi 
     * player di koordinat.
     * @return posisi player pada sumbu X di koordinat
     */
    public int getX() {
        return x;
    }

    
    /**
     * Method yang digunakan untuk mengetahui apakah layer ini AI atau bukan.
     * @return true jika layer ini AI.
     */
    public boolean isAI() {
        return isAI;
    }

    
    /**
     * Method untuk mendapatkan counter dari square.
     * @return int yang merupakan counter dari square.
     */
    public int getSqct() {
        return sqct;
    }

    
    /**
     * Method yang digunakan untuk mendapatkan gambar Sprite yang 
     * digunakan untuk animasi pemain.
     * @return gambar Sprite yang digunakan untuk animasi pemain
     */
    public BufferedImage getSprite() {
        return sprite;
    }

    
    /**
     * Method yang digunakan untuk mendapatkan informasi apakah 
     * Player ini berjalan kedepan atau mundur.
     * @return true jika Player berjalan kedepan, false jika player berjalan mundur
     */
    public boolean isMoveForward() {
        return moveForward;
    }

    
    /**
     * Method yang digunakan untuk mengetahui posisi subimage pada sumbu X
     * yang akan digunakan saat menggambar sprite.
     * @return posisi subimage pada sumbu X yang akan digunakan saat menggambar sprite.
     */
    public int getSpriteX() {
        return spriteX;
    }

    
    /**
     * Method yang digunakan untuk mengetahui posisi subimage pada sumbu Y
     * yang akan digunakan saat menggambar sprite.
     * @return posisi subimage pada sumbu Y yang akan digunakan saat menggambar sprite.
     */
    public int getSpriteY() {
        return spriteY;
    }

    
    /**
     * Method yang digunakan untuk Mendapatkan tingkat Kesulitan Player (komputer).
     * @return jika isAI= true, 1 =easy,2=normal,3=hard
     */
    public int getDifficulty() {
        return difficulty;
    }
    //</editor-fold>

    /**
     * Setter posisi Player di Papan*
     * @param sqct posisi player (Square)
     */
    public void setSqct(int sqct) {
        this.sqct = sqct;
    }

    /**
     * Setter untuk difficukty (hanya digunakan AI)     
     * @param int difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Method untuk mengupdate sprite
     */
    public void updateSprites() {
        if (spriteX < 128) {
            spriteX += 64;
        } else {
            spriteX = 0;
        }
    }

    /**
     * Method untuk membuat Player berjalan mundur, digunakan saat Player
     * mencapai Square ke 100 namun masih ada lemparan dadu yang tersisa
     */
    public void moveBackWard() {
        x = x + 5;
        movect++;
        if (movect == 14) {
            movect = 0;
            sqct--;
            sq = parent.getSqArr()[sq.getX() + 1][sq.getY()];
        }
        if (sqct == 0) {
            parent.nextPlayer();
            moveForward = !moveForward;
        }
    }

    /**
     * Method untuk membuat Player bergerak ke depan, kemudian mengecek apakan
     * Player mendarat di Teleporter atau di Square ke 100
     *
     * @return true jika Player tepat di Square ke 100, false jika tidak
     */
    public boolean moveForward() {
        if (sq.getY() % 2 != 0) {
            isFacingRight = true;
            if (sq.getX() != 9) {
                x = x + 5;
            } else {
                y = y - 5;
            }
        } else {
            isFacingRight = false;
            if (sq.getX() != 0) {
                x = x - 5;
            } else {
                y = y - 5;
            }
        }
        movect++;
        if (movect == 14) {
            if (sq.getY() % 2 != 0) {
                if (sq.getX() != 9) {
                    sq = parent.getSqArr()[sq.getX() + 1][sq.getY()];
                } else {
                    sq = parent.getSqArr()[sq.getX()][sq.getY() - 1];
                }
            } else {
                if (sq.getX() != 0) {
                    sq = parent.getSqArr()[sq.getX() - 1][sq.getY()];
                } else {
                    sq = parent.getSqArr()[sq.getX()][sq.getY() - 1];
                }
            }
            movect = 0;
            sqct--;
        }
        if (sqct == 0) {
            if (sq.getX() == 0 && sq.getY() == 0) {
                return true;
            } else if (!sq.getType().equals("Square")) {
                Teleporter t = (Teleporter) sq;
                sq = t.getDest();
                x = sq.x * 70 + playerNum * 7 - 15;
                y = sq.y * 70 + 35;
                parent.nextPlayer();
            } else {
                parent.nextPlayer();
            }
        } else {
            if (sq.getX() == 0 && sq.getY() == 0) {
                moveForward = !moveForward;
            }
        }
        return false;
    }
}
