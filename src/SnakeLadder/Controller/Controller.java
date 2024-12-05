package SnakeLadder.Controller;

import SnakeLadder.Model.*;
import SnakeLadder.View.GUIFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Controller implements ActionListener {

    /**
     * Kelas yang memodelkan Board permainan
     */
    private Board board;
    
    /**
     * Frame utama
     */
    private GUIFrame view;
    
    /**
     * Timer yang digunakan untuk animasi sprite
     * Digunakan untuk menggerakan animasinya sprite.
     */
    private Timer timer;
    
    /**
     * int yang digunakan untuk mengatur animasi sprite
     */
    private int frame;

    
    /**
     * Constructor untuk menginisialisasi timer, view, dan mengeset 
     * visiblenya menjadi true.
     */
    public Controller() {
        timer = new Timer(22, this);
        view = new GUIFrame(this);
        view.setVisible(true);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter">
    /**
     * Getter untuk jumlah Teleporter di Board
     * @return int NumOfTeleporter
     */
    public int getNumOFTeleporters() {
        return board.getNumOfTeleporters();
    }

    /**
     * Getter untuk warna kotak di posisi (i,j)
     * @param i
     * @param j
     * @return Color, warna dari kotak
     */
    public Color getSquareColor(int i, int j) {
        return board.getSqArr()[i][j].getColor();
    }

    /**
     * Getter untuk mendapatkan posisi player ke i di Board.
     * @param i number dari player yang ingin diketahui posisinya.
     * @return int sqct (counter square)
     */
    public int getSqct(int i) {
        return board.getPlayerArr()[i].getSqct();
    }

    /**
     * Method untuk mendapatkan posisi dan tujuan sebuah teleporter ke i di
     * board.tArr
     * @param i number dari teleporter yang posisi dan tujuannya ingin diketahui.
     * @return int[] (tipe(0=ladder,1=snake,),x,y,x tujuan,y tujuan)
     */
    public int[] getTeleporter(int i) {
        Teleporter t = board.gettArr()[i];
        int temp = 0;
        if (t.getType().equals("Snake")) {
            temp = 1;
        }
        int[] temp2 = {temp, t.getX(), t.getY(), t.getDest().getX(), t.getDest().getY()};
        return temp2;
    }

    /**
     * Getter jumlah pemain di Board.
     * @return int numOfPlayers
     */
    public int getNumOfPlayers() {
        return board.getNumOfPlayers();
    }

    /**
     * Getter untuk posisi dan sprite player ke i di Board
     * @param i number dari player yang posisi dan spritenya ingin diketahui.
     * @return int[] (x,y,spritex,spritey)
     */
    public int[] getPlayerPos(int i) {
        Player p = board.getPlayerArr()[i];
        int[] temp = {p.getX(), p.getY(), p.getSpriteX(), p.getSpriteY()};
        return temp;
    }

    /**
     * Getter yang mengembalikan apakah Player ke i menghadap ke kanan atau
     * tidak
     * @param i number dari player yang ingin diketahui apakah menghadap ke kanan atau tidak
     * @return boolean isFacingRight
     */
    public boolean getPlayerIsFacingRight(int i) {
        Player p = board.getPlayerArr()[i];
        return p.isIsFacingRight();
    }

    /**
     * Getter yang mengembalikan sprite player ke i
     * @param i number dari player yang akan di-get Spritenya.
     * @return BufferedImage sprite Sprite dari Player ke i
     */
    public BufferedImage getPlayerSprite(int i) {
        return board.getPlayerArr()[i].getSprite();
    }

    //</editor-fold>
    /**
     * Method yang memanggil constructor Player, digunakan saat pertama kali
     * menjalankan game
     *
     * @param vsAI true jika melawan AI, false jika tidak
     * @return Player[] Array dari Player - player yang sudah dibuat
     * @throws IOException
     */
    private Player[] setPlayers(boolean vsAI) throws IOException {
        Player[] playerArr;
        URL imgurl = getClass().getClassLoader().getResource("sprites.png");
        BufferedImage img1 = ImageIO.read(imgurl);
        if (vsAI) {
            playerArr = new Player[2];
            playerArr[0] = new Player(0, img1, board, false);
            playerArr[1] = new Player(1, img1, board, true);
            playerArr[1].setDifficulty(view.getjComboBox2().getSelectedIndex());
        } else {
            playerArr = new Player[view.getjSlider1().getValue()];
            for (int i = 0; i < playerArr.length; i++) {
                playerArr[i] = new Player(i, img1, board, false);
            }
        }
        return playerArr;
    }

    /**
     * Method untuk memerintahkan Board untuk melempar dadu.
     */
    public void rollDice() {
        Player p = board.getPlayerArr()[board.getCurrentPlayer()];
        if (p.getSqct() == 0) {
            board.rollDice(false);
        }
    }

    /**
     * Method untuk menghide pilihan awal, menggambar board dan menstart Timer,
     * menandakan permainan dimulai.
     * Tampilan option untuk setting akan digantikan 
     * dengan tampilan board permainan.
     * @throws IOException
     */
    public void gameStart() throws IOException {
        boolean vsAI;
        board = new Board(3, 3);
        if (view.getjComboBox1().getSelectedIndex() == 1) {
            vsAI = true;
        } else {
            vsAI = false;
        }
        board.setPlayerArr(setPlayers(vsAI));
        timer.start();
    }

    /**
     * Method yang dipanggil setiap 15 ms untuk menganimasi Player.
     * Sehingga palyer dapat bergerak.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        frame++;
        updatePlayers();
        view.getPlayerPanel().repaint();
    }

    /**
     * Method yang digunakan untuk mengupdate posisi dan sprite Player, kemudian
     * mengecek apakan ada player yang menang. Jika ada player yang menang, 
     * maka akan keluar pesan informasi pemain yang menang.
     */
    private void updatePlayers() {
        if (frame % 8 == 0) {
            for (int i = 0; i < board.getPlayerArr().length; i++) {
                board.getPlayerArr()[i].updateSprites();
            }
        }
        Player p = board.getPlayerArr()[board.getCurrentPlayer()];
        if (p.getSqct() != 0) {
            if (p.isMoveForward()) {
                if (p.moveForward()) {
                    view.showWinner("Player " + (board.getCurrentPlayer() + 1) + " Wins!");
                    timer.stop();
                }
            } else {
                p.moveBackWard();
            }
        }
    }
}
