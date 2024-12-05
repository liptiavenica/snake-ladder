package SnakeLadder.Model;

import java.awt.Color;
import java.util.Random;

public class Board {

    /**
     * Attribut Jumlah ular
     */
    private int numOfSnakes;
    
    /**
     * Attribut Jumlah tangga
     */
    private int numOfLadders;
    
    /**
     * Attribut Jumlah player
     */
    private int numOfPlayers;
    
    /**
     * Attribut Player saat ini
     */
    private int currentPlayer = 0;
    
    /**
     * boolean untuk mengetahui apakah Player saat ini boleh melempar dadu lagi
     */
    private boolean isRepeat = false;
    
    /**
     * Array of Square yang memodelkan Papan.
     */
    private Square[][] sqArr = new Square[10][10];
    
    /**
     * Array of Teleporter memuat semua ular dan tangga.
     */
    private Teleporter[] tArr;
    /**
     * Array of Player memuat semua Player
     */
    private Player[] playerArr;
    
    /**
     * Attribut yang merepresentasikan Dadu
     */
    private Dice dice;

    /**
     * Constructor untuk menginisialisasi dice, numOfLadders, numOfSnakes,
     *  playerArr, tArr, sqArr.
     * @param numOfSnakes jumlah ular
     * @param numOfLadders jumlah tangga
     */
    public Board(int numOfSnakes, int numOfLadders) {
        dice = new Dice();
        this.numOfLadders = numOfLadders;
        this.numOfSnakes = numOfSnakes;
        playerArr = new Player[this.numOfPlayers];
        tArr = new Teleporter[numOfSnakes + numOfLadders];
        randomizeBoard(numOfSnakes, numOfLadders);
        for (int i = 0; i < sqArr.length; i++) {
            for (int j = 0; j < sqArr[0].length; j++) {
                if (sqArr[i][j] == null) {
                    sqArr[i][j] = new Square(i, j);
                }
            }
        }
    }

    
    /**
     * Method yang digunakan untuk mengeset array dari playyer.
     * @param playerArr array of Player
     */
    public void setPlayerArr(Player[] playerArr) {
        this.playerArr = playerArr;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter">
    /**
     * 
     * @return 
     */
    public Square[][] getSqArr() {
        return sqArr;
    }

    /**
     * 
     * @return 
     */
    public Player[] getPlayerArr() {
        return playerArr;
    }

    /**
     * 
     * @return 
     */
    public int getNumOfTeleporters() {
        return numOfSnakes + numOfLadders;
    }

    /**
     * 
     * @return 
     */
    public int getNumOfPlayers() {
        return playerArr.length;
    }

    /**
     * 
     * @return 
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * 
     * @return 
     */
    public Teleporter[] gettArr() {
        return tArr;
    }

    //</editor-fold>
    
    /**
     * Method untuk memilih Player yang mendapat giliran untuk bermain
     * selanjutnya.
     */
    public void nextPlayer() {
        if (isRepeat) {
            isRepeat = false;
        } else {
            currentPlayer++;
            if (currentPlayer == playerArr.length) {
                currentPlayer = 0;
            }
        }
        if (playerArr[currentPlayer].isAI()) {
            rollDice(true);
        }
    }

    /**
     * Melempar dadu Player saat, jika mendapat angka maksimu boleh melempar lagi
     * @param isAI mengecek apakah Player AI atau bukan , jika ya maka mengecek difficulty jika easy maks dadu = 3, normal =6, hard = 9
     */
    public void rollDice(boolean isAI) {
        int n = 6;
        if (isAI) {
            if (playerArr[currentPlayer].getDifficulty() == 0) {
                n = 3;
            } else if (playerArr[currentPlayer].getDifficulty() == 2) {
                n = 9;
            }
        }
        int temp = dice.roll(n);
        if (temp == n) {
            isRepeat = true;
        }
        playerArr[currentPlayer].setSqct(temp);
    }

    /**
     * Method yang dipangil di constructor untuk mengacak posisi ular dan tangga
     * @param snake jumlah ular
     * @param ladder jumlah tangga
     */
    private void randomizeBoard(int snake, int ladder) {
        Random rand = new Random();
        for (int i = 0; i < snake; i++) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(8);
            int newx = rand.nextInt(10);
            int newy = rand.nextInt(9-y) + y + 1;
            if (sqArr[x][y] == null && sqArr[newx][newy] == null) {
                sqArr[newx][newy] = new Square(newx, newy);
                sqArr[x][y] = new Teleporter(x, y, sqArr[newx][newy], "Snake");
                tArr[i] = (Teleporter) sqArr[x][y];
            } else {
                i--;
            }
        }
        for (int i = 0; i < ladder; i++) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(8) + 1;
            int newx = rand.nextInt(10);
            int newy = y - rand.nextInt(y) - 1;
            if (sqArr[x][y] == null && sqArr[newx][newy] == null) {
                sqArr[newx][newy] = new Square(newx, newy);
                sqArr[x][y] = new Teleporter(x, y, sqArr[newx][newy], "Ladder");
                tArr[i + snake] = (Teleporter) sqArr[x][y];
            } else {
                i--;
            }
        }
    }
}
