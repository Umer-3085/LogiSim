package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

public class NOT extends Component {

    public NOT(int x, int y) {
        super(x, y);
        this.width = 50;
        this.height = 40;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        // Triangle
        g.drawLine(x, y, x, y + height);
        g.drawLine(x, y, x + width - 10, y + height / 2);
        g.drawLine(x, y + height, x + width - 10, y + height / 2);

        // Small circle
        int circleRadius = 8;
        g.drawOval(x + width - 10, y + height / 2 - circleRadius / 2, circleRadius, circleRadius);

        // Input line
        g.drawLine(x - 20, y + height / 2, x, y + height / 2);

        // Output line
        g.drawLine(x + width - 10 + circleRadius, y + height / 2, x + width - 10 + circleRadius + 20, y + height / 2);

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 25, y - 5, width + 40, height + 10);
            g.setColor(prev);
        }
    }
}
