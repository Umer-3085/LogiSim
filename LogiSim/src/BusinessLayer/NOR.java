package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

public class NOR extends Component {

    public NOR(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        // Main OR curve
        g.drawArc(x, y, width, height, 270, 180);

        // Extra left curve
        g.drawArc(x - 10, y, width, height, 270, 180);

        // Input lines
        g.drawLine(x - 20, y + 10, x+15, y + 10);
        g.drawLine(x - 20, y + height - 10, x+15, y + height - 10);

        // Small circle at output
        int circleRadius = 8;
        g.drawOval(x + width, y + height / 2 - circleRadius / 2, circleRadius, circleRadius);

        // Output line
        g.drawLine(x + width + circleRadius, y + height / 2, x + width + circleRadius + 20, y + height / 2);

        // Selection
        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 25, y - 5, width + 50, height + 10);
            g.setColor(prev);
        }
    }
}
