package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Switch extends Component {
    private boolean state; // true = 1/ON, false = 0/OFF

    public Switch(int x, int y) {
        super(x, y);
        this.width = 40;
        this.height = 20;
        this.state = false;
    }

    public void toggle() {
        state = !state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public void draw(Graphics g) {
        // Draw switch base
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        // Draw lever
        int leverWidth = width / 2;
        g.setColor(Color.WHITE);
        if (state) {
            g.fillRect(x + width / 2, y, leverWidth, height); // Right = 1
        } else {
            g.fillRect(x, y, leverWidth, height); // Left = 0
        }

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width / 2, height); // left part border
        g.drawRect(x + width / 2, y, width / 2, height); // right part border

        // Draw 0/1 text
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.BLACK);
        g.drawString("0", x + 8, y + height - 6);
        g.drawString("1", x + width / 2 + 8, y + height - 6);

        // Input/output lines
        g.drawLine(x - 10, y + height / 2, x, y + height / 2);
        g.drawLine(x + width, y + height / 2, x + width + 10, y + height / 2);

        // Selection highlight
        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 12, y - 5, width + 24, height + 10);
            g.setColor(prev);
        }
    }
}


