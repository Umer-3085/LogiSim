/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

/**
 * 
 * @author HP
 */
public class Switch extends Component {
    private boolean state = false;

    public Switch(int x, int y) { super(x, y); this.width = 40; this.height = 20; }

    public void toggle() { state = !state; }
    public boolean getState() { return state; }
    public void setState(boolean s) { state = s; }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        int leverWidth = width / 2;
        g.setColor(Color.WHITE);
        g.fillRect(state ? x + width/2 : x, y, leverWidth, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width/2, height);
        g.drawRect(x + width/2, y, width/2, height);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("0", x + 8, y + height - 6);
        g.drawString("1", x + width/2 + 8, y + height - 6);
        g.drawLine(x - 10, y + height/2, x, y + height/2);
        g.drawLine(x + width, y + height/2, x + width + 10, y + height/2);

        if (selected) { Color prev = g.getColor(); g.setColor(Color.BLUE); g.drawRect(x - 12, y - 5, width + 24, height + 10); g.setColor(prev);}
    }

    @Override
    public Point getPin(int pinIndex) {
        if (pinIndex == 0) return new Point(x + width + 10, y + height/2); // only output
        return null;
    }

    @Override
    public int getNumPins() { return 1; }
}
