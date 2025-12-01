package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

public class OR extends Component {

    public OR(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        // Right OR curve
        g.drawArc(x, y, width, height, 270, 180);

        // Left curve (smaller, overlapping slightly)
        g.drawArc(x - 15, y, width, height, 270, 180);

        // Input lines close to gate
        g.drawLine(x - 15, y + 10, x + 15, y + 10);
        g.drawLine(x - 15, y + height - 10, x + 15, y + height - 10);

        // Output line
        g.drawLine(x + width, y + height / 2, x + width + 20, y + height / 2);

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 20, y - 5, width + 40, height + 10);
            g.setColor(prev);
        }
    }
}
