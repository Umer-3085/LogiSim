package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

public class XOR extends Component {

    public XOR(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        g.drawArc(x - 30, y, width, height, 270, 180);
        
        // Extra small curve for XOR
        g.drawArc(x - 20, y, width, height, 270, 180);

        // Main OR curve
        g.drawArc(x - 2, y, width, height, 270, 180);

        // Input lines closer to gate
        g.drawLine(x - 20, y + 10, x + 15, y + 10);
        g.drawLine(x - 20, y + height - 10, x + 15, y + height - 10);

        // Output line
        g.drawLine(x + width, y + height / 2, x + width + 20, y + height / 2);

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 25, y - 5, width + 50, height + 10);
            g.setColor(prev);
        }
    }
}
