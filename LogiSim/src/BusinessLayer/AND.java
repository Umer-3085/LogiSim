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
public class AND extends Component {

    public AND(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width / 2, height);
        g.drawArc(x + width / 2 - 1, y, width / 2 + 1, height, -90, 180);
        g.drawLine(x - 20, y + 10, x, y + 10);
        g.drawLine(x - 20, y + height - 10, x, y + height - 10);
        g.drawLine(x + width / 2 + width / 2, y + height / 2, x + width / 2 + width / 2 + 20, y + height / 2);

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 20, y - 5, width + 40, height + 10);
            g.setColor(prev);
        }
    }

    @Override
    public Point getPin(int pinIndex) {
        if (pinIndex == 0) return new Point(x - 20, y + 10);
        if (pinIndex == 1) return new Point(x - 20, y + height - 10);
        if (pinIndex == 2) return new Point(x + width + 20, y + height / 2);
        return null;
    }

    @Override
    public int getNumPins() { return 3; }
}
