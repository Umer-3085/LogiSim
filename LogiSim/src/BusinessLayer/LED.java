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
public class LED extends Component {
    private boolean state = false;

    public LED(int x, int y) { super(x, y); this.width = 20; this.height = 40; }

    public void setState(boolean s) { state = s; }
    public boolean getState() { return state; }

    @Override
    public void draw(Graphics g) {
        g.setColor(state ? Color.GREEN : Color.GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        g.drawLine(x + width/2, y + height, x + width/2, y + height + 20);
        g.setColor(Color.BLACK);
        g.drawString(state ? "1" : "0", x + 4, y + height/2 + 4);

        if (selected) { Color prev = g.getColor(); g.setColor(Color.BLUE); g.drawRect(x - 5, y - 5, width + 10, height + 10); g.setColor(prev);}
    }

    @Override
    public Point getPin(int pinIndex) {
        if (pinIndex == 0) return new Point(x + width/2, y + height + 20); // only input
        return null;
    }

    @Override
    public int getNumPins() { return 1; }
}
