package guiapplication.schedulePlanner.Simulator.npc;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class NPCInfo {

    private String[] info;
    private double width;
    private double height;
    private Point2D position;

    public NPCInfo(String[] info, Point2D position) {
        this.info = info;
        this.position = position;
        this.width = 150;
        this.height = 80; //todo calculate these
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Double(position.getX(), position.getY(), width, height));

        g.setColor(Color.BLACK);

        int lineHeight = g.getFontMetrics().getHeight();
        int textY = (int) (this.position.getY() + lineHeight);
        for (String line : this.info) {
            g.drawString(line, (int) (this.position.getX() + 10), textY);
            textY += lineHeight;
        }
    }

    public void updatePosition(double x, double y) {
        this.position = new Point2D.Double(x + 25, y - 50); //todo change these magic offsets
    }

    public void updateInfo(String s, int index) {
        this.info[index] = s;
    }
}
