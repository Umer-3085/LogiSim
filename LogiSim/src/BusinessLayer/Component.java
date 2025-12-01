/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * 
 * @author HP
 */
public abstract class Component {
    protected int x, y;
    protected int width = 60;
    protected int height = 40;
    protected boolean selected = false;

    public Component(int x, int y) { this.x = x; this.y = y; }

    public abstract void draw(Graphics g);

    public boolean contains(Point p) {
        return new Rectangle(x - 20, y - 10, width + 40, height + 20).contains(p);
    }

    public void move(int nx, int ny) { this.x = nx; this.y = ny; }

    public void setSelected(boolean s) { this.selected = s; }
    public boolean isSelected() { return selected; }

    // **New method: Get pin coordinates**
    // pinIndex = 0,1,... for input pins, last index = output pin
    public abstract Point getPin(int pinIndex);

    // Get number of pins
    public abstract int getNumPins();
}
