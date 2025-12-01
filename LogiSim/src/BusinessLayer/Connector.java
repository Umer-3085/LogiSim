package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

public class Connector {
    private int startX, startY; // start point
    private int endX, endY;     // end point
    private boolean selected = false;

    public Connector(int x, int y) {
        this.startX = x;
        this.startY = y;
        this.endX = x + 50; // default horizontal length
        this.endY = y;
    }

    public void setSelected(boolean sel) {
        this.selected = sel;
    }

    public boolean isSelected() {
        return selected;
    }

    // Move whole connector
    public void move(int dx, int dy) {
        startX += dx;
        startY += dy;
        endX += dx;
        endY += dy;
    }

    // Drag only start or end
    public void dragStart(int x, int y) {
        startX = x;
        startY = y;
    }

    public void dragEnd(int x, int y) {
        endX = x;
        endY = y;
    }

    // Check if mouse near start/end points
    public boolean containsStart(int px, int py) {
        int dx = px - startX;
        int dy = py - startY;
        return dx*dx + dy*dy <= 64; // 8px radius
    }

    public boolean containsEnd(int px, int py) {
        int dx = px - endX;
        int dy = py - endY;
        return dx*dx + dy*dy <= 64;
    }

    // Check if mouse is on line for whole-wire drag (approx)
    public boolean containsLine(int px, int py) {
        int dx = endX - startX;
        int dy = endY - startY;
        if (dx == 0 && dy == 0) return false;
        double distance = Math.abs(dy*px - dx*py + endX*startY - endY*startX) / Math.sqrt(dx*dx + dy*dy);
        return distance <= 5; // 5px tolerance
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(startX, startY, endX, endY);

        // Draw endpoints
        g.fillOval(startX - 4, startY - 4, 8, 8);
        g.fillOval(endX - 4, endY - 4, 8, 8);

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawOval(startX - 6, startY - 6, 12, 12);
            g.drawOval(endX - 6, endY - 6, 12, 12);
            g.setColor(prev);
        }
    }

    // Getters
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getEndX() { return endX; }
    public int getEndY() { return endY; }
}
