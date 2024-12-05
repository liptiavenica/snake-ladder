package SnakeLadder.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel {

    /**
     * Graphics2D dimana board digambar
     */
    private Graphics2D g2d;
    /**
     * Jumlah player yang akan digambar
     */
    private int numOfPlayers;
    /**
     * GuiFrame utama
     */
    private GUIFrame parent;
    /**
     * Font yang digunakan
     */
    private Font font;

    /**
     * Constructor untuk menginisialisasi JFrame, numOfPlayer, dan juga font.
     * @param GUIFrame parent
     */
    public PlayerPanel(GUIFrame parent) {
        setOpaque(false);
        this.parent = parent;
        numOfPlayers = parent.getController().getNumOfPlayers();
        font = new Font("Serif", Font.BOLD, 24);
    }

    /**     
     * Method untuk menggambar Player di papan.
     * */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        for (int i = 0; i < numOfPlayers; i++) {
            int[] pos = parent.getController().getPlayerPos(i);
            g2d.setColor(Color.RED);
            BufferedImage img = parent.getController().getPlayerSprite(i);
            g2d.setFont(font);
            if (parent.getController().getSqct(i) != 0) {
                g2d.drawString(parent.getController().getSqct(i) + "", pos[0] + 15, pos[1] - 35);
                g2d.drawString(parent.getController().getSqct(i) + "", 780,600);
                img = img.getSubimage(pos[2] + 196, pos[3], 64, 72);
            } else {
                img = img.getSubimage(pos[2], pos[3], 64, 68);
                g2d.drawString("P" + (i + 1), pos[0] + 15, pos[1] - 35);
            }
            if (!parent.getController().getPlayerIsFacingRight(i)) {
                AffineTransform t = AffineTransform.getScaleInstance(-1, 1);
                t.translate(-img.getWidth(null), 0);
                AffineTransformOp op = new AffineTransformOp(t, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                img = op.filter(img, null);
            }
            g2d.drawImage(img, pos[0], pos[1] - 32, null);
        }
    }
}
