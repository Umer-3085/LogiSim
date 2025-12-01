/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

/**
 * 
 * @author HP
 */
public class Connector {
    private int startX, startY;
    private int endX, endY;
    private boolean selected = false;

    // Attached components and pin indices
    private Component attachedStartComponent = null;
    private int attachedStartPin = -1;
    private Component attachedEndComponent = null;
    private int attachedEndPin = -1;

    private final int defaultLength = 50;

    public Connector(int x, int y) {
        this.startX = x;
        this.startY = y;
        this.endX = x + defaultLength;
        this.endY = y;
    }

    public void setSelected(boolean sel) { this.selected = sel; }
    public boolean isSelected() { return selected; }

    // Move whole wire (only if endpoints are free)
    public void move(int dx, int dy) {
        if (attachedStartComponent == null) { startX += dx; startY += dy; }
        if (attachedEndComponent == null)   { endX += dx; endY += dy; }
    }

    // Drag start/end points (only if not attached)
    public void dragStart(int x, int y) { if (attachedStartComponent == null) { startX = x; startY = y; } }
    public void dragEnd(int x, int y)   { if (attachedEndComponent == null)   { endX = x; endY = y; } }

    // Attach to component pin
    public void attachStart(Component c, int pinIndex) {
        attachedStartComponent = c;
        attachedStartPin = pinIndex;
        Point p = c.getPin(pinIndex);
        startX = p.x;
        startY = p.y;
    }

    public void attachEnd(Component c, int pinIndex) {
        attachedEndComponent = c;
        attachedEndPin = pinIndex;
        Point p = c.getPin(pinIndex);
        endX = p.x;
        endY = p.y;
    }

    // Detach endpoints
    public void detachStart() { attachedStartComponent = null; attachedStartPin = -1; }
    public void detachEnd()   { attachedEndComponent = null; attachedEndPin = -1; }

    // Update positions if attached components moved
    public void update() {
        if (attachedStartComponent != null && attachedStartPin >= 0) {
            Point p = attachedStartComponent.getPin(attachedStartPin);
            startX = p.x;
            startY = p.y;
        }
        if (attachedEndComponent != null && attachedEndPin >= 0) {
            Point p = attachedEndComponent.getPin(attachedEndPin);
            endX = p.x;
            endY = p.y;
        }
    }

    // Mouse hit tests
    public boolean containsStart(int px, int py) { return distance(px, py, startX, startY) <= 8; }
    public boolean containsEnd(int px, int py)   { return distance(px, py, endX, endY) <= 8; }

    public boolean containsLine(int px, int py) {
        int dx = endX - startX;
        int dy = endY - startY;
        if (dx == 0 && dy == 0) return false;
        double distance = Math.abs(dy*px - dx*py + endX*startY - endY*startX) / Math.sqrt(dx*dx + dy*dy);
        return distance <= 5;
    }

    private double distance(int x1, int y1, int x2, int y2) {
        int dx = x2-x1, dy = y2-y1;
        return Math.sqrt(dx*dx + dy*dy);
    }

    // Draw wire
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(startX, startY, endX, endY);
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

    // Getters for attached components
    public Component getAttachedStartComponent() { return attachedStartComponent; }
    public int getAttachedStartPin() { return attachedStartPin; }
    public Component getAttachedEndComponent() { return attachedEndComponent; }
    public int getAttachedEndPin() { return attachedEndPin; }

    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getEndX() { return endX; }
    public int getEndY() { return endY; }
}
