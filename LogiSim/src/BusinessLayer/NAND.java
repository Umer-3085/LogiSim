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
public class NAND extends Component {

    public NAND(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        int rectWidth = width / 2;
        g.drawRect(x, y, rectWidth, height);
        g.drawArc(x + rectWidth - 1, y, width - rectWidth + 1, height, -90, 180);

        // Input lines
        g.drawLine(x - 20, y + 10, x, y + 10);
        g.drawLine(x - 20, y + height - 10, x, y + height - 10);

        // Small circle at output
        int circleRadius = 8;
        g.drawOval(x + width, y + height / 2 - circleRadius / 2, circleRadius, circleRadius);

        // Output line
        g.drawLine(x + width + circleRadius, y + height / 2, x + width + circleRadius + 20, y + height / 2);

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 25, y - 5, width + 50, height + 10);
            g.setColor(prev);
        }
    }

    @Override
    public Point getPin(int pinIndex) {
        if (pinIndex == 0) return new Point(x - 20, y + 10);
        if (pinIndex == 1) return new Point(x - 20, y + height - 10);
        if (pinIndex == 2) return new Point(x + width + 8 + 20, y + height / 2); // after small circle
        return null;
    }

    @Override
    public int getNumPins() { return 3; }
}
