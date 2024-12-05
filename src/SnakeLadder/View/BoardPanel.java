package SnakeLadder.View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {

    /**
     * Graphics2D dimana board digambar
     */
    private Graphics2D g2d;
    /**
     * Jumlah Teleporter
     */
    private int numOfTeleporter;
    /**
     * GUIFrame utama
     */
    private GUIFrame parent;  

    /**
     * Constructor yang digunakan untuk menginisialisasi JFrame.
     * @param parent GUIFrame utama
     */
    public BoardPanel(GUIFrame parent) throws IOException {
        setOpaque(false);
        this.parent = parent;  
    }

    
    /**
     * Method yang digunakan untuk menggambarkan Board ke Panel.
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        drawBoard();
        drawTeleporters();
    }

    /**
     * Method untuk menggambar semua Teleporter di Board.
     */
    private void drawTeleporters() {
        numOfTeleporter = parent.getController().getNumOFTeleporters();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        BufferedImage image= null;
        URL url= getClass().getClassLoader().getResource("danger.png");
        try {      
            image= ImageIO.read(url);
        } catch (IOException ex) {
            Logger.getLogger(BoardPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        BufferedImage image2= null;
        URL url2= getClass().getClassLoader().getResource("sad.png");
        try {      
            image2= ImageIO.read(url2);
        } catch (IOException ex) {
            Logger.getLogger(BoardPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
        
        for (int i = 0; i < numOfTeleporter; i++) {
            int[] temp = parent.getController().getTeleporter(i);
            if (temp[0] == 1) {
                g2d.setColor(Color.red);
                int[] x={temp[1]* 70 + 40,temp[1]* 70 + 45,temp[3]* 70 + 45,temp[3]* 70 + 40};
                int[] y={temp[2]* 70 + 30,temp[2]* 70 + 30, temp[4]*70 + 40, temp[4]*70 + 40};
                g2d.fillPolygon(x, y, 4);
                g2d.drawImage(image, temp[1]* 70 + 15 , temp[2]* 70 + 25, 67,50, this);
                g2d.drawImage(image2, temp[3]* 70 + 15, temp[4]*70 + 20, 50,50, this);
                
            } else {
                g2d.setColor(Color.blue);
                int[] x={temp[1]* 70 + 10,temp[1]* 70 + 15,temp[3]* 70 + 15,temp[3]* 70 + 10};
                int[] y={temp[2]* 70 + 30,temp[2]* 70 + 30, temp[4]*70 + 30, temp[4]*70 + 30};
                g2d.fillPolygon(x, y, 4);
                
                int[] x2={temp[1]* 70 + 50,temp[1]* 70 +55,temp[3]* 70 + 55,temp[3]* 70 + 50};
                int[] y2={temp[2]* 70 + 30,temp[2]* 70 + 30, temp[4]*70 + 30, temp[4]*70 + 30};
                g2d.fillPolygon(x2, y2, 4);
            }
            //g2d.drawLine(temp[1] * 70 + 25, temp[2] * 70 + 25, temp[3] * 70 + 25, temp[4] * 70 + 25);
        }

    }

    /**
     * Method yang digunakan untuk menggambar Board.
     */
    private void drawBoard() {  
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                g2d.setColor(Color.WHITE);
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        g2d.setColor(parent.getController().getSquareColor(i, j));
                    }
                } else {
                    if (j % 2 != 0) {
                        g2d.setColor(parent.getController().getSquareColor(i, j));
                    }
                }
                g2d.fillRect(i * 70 + 10, j * 70 + 10, 70, 70);
                g2d.setColor(Color.black);
                int ct;
                if (j % 2 == 0) {
                    ct = 10 * (10 - j) - i;
                } else {
                    ct = 10 * (10 - j) + i - 9;
                }
                g2d.drawString(ct + "", i * 70 + 10, j * 70 + 25);
            }
        }
    }
}
