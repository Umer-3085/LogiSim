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

    public Component(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw(Graphics g);

    public boolean contains(Point p) {
        Rectangle r = new Rectangle(x - 20, y - 10, width + 40, height + 20);
        return r.contains(p);
    }

    public void move(int nx, int ny) {
        this.x = nx;
        this.y = ny;
    }

    public void setSelected(boolean s) {
        this.selected = s;
    }

    public boolean isSelected() {
        return selected;
    }
}
