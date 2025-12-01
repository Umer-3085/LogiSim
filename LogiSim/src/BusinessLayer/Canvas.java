package BusinessLayer;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;

public class Canvas extends JPanel {

    private final ArrayList<Component> components = new ArrayList<>();
    private final ArrayList<Connector> connectors = new ArrayList<>();

    private Component selectedComponent = null;
    private Connector selectedConnector = null;

    private int offsetX = 0;
    private int offsetY = 0;
    private int connectorOffsetX = 0;
    private int connectorOffsetY = 0;

    private boolean dragging = false;
    private boolean draggingEnd = false;   // dragging an endpoint
    private boolean draggingStart = false; // dragging start endpoint
    private boolean draggingWhole = false; // dragging entire wire

    private String activeTool = "";

    public Canvas() {
        setBackground(java.awt.Color.WHITE);

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                clearSelections();

                // 1) Check wire endpoints first
                for (int i = connectors.size() - 1; i >= 0; i--) {
                    Connector c = connectors.get(i);
                    if (c.containsStart(p.x, p.y)) {
                        selectedConnector = c;
                        selectedConnector.setSelected(true);
                        draggingStart = true;
                        return;
                    } else if (c.containsEnd(p.x, p.y)) {
                        selectedConnector = c;
                        selectedConnector.setSelected(true);
                        draggingEnd = true;
                        return;
                    } else if (c.containsLine(p.x, p.y)) {
                        selectedConnector = c;
                        selectedConnector.setSelected(true);
                        draggingWhole = true;
                        connectorOffsetX = p.x - c.getStartX();
                        connectorOffsetY = p.y - c.getStartY();
                        return;
                    }
                }

                // 2) Check components
                for (int i = components.size() - 1; i >= 0; i--) {
                    Component c = components.get(i);
                    if (c.contains(p)) {
                        selectedComponent = c;
                        selectedComponent.setSelected(true);
                        offsetX = e.getX() - c.x;
                        offsetY = e.getY() - c.y;
                        dragging = true;
                        return;
                    }
                }

                // 3) Place new component or wire
                if (activeTool != null && !activeTool.isEmpty()) {
                    switch (activeTool) {
                        case "AND": addComponent(new AND(p.x, p.y)); break;
                        case "OR": addComponent(new OR(p.x, p.y)); break;
                        case "XOR": addComponent(new XOR(p.x, p.y)); break;
                        case "NAND": addComponent(new NAND(p.x, p.y)); break;
                        case "NOR": addComponent(new NOR(p.x, p.y)); break;
                        case "NOT": addComponent(new NOT(p.x, p.y)); break;
                        case "Switch": addComponent(new Switch(p.x, p.y)); break;
                        case "LED": addComponent(new LED(p.x, p.y)); break;
                        case "Wire": addConnector(new Connector(p.x, p.y)); break;
                    }
                    activeTool = "";
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging && selectedComponent != null) {
                    selectedComponent.move(e.getX() - offsetX, e.getY() - offsetY);
                    repaint();
                } else if (selectedConnector != null) {
                    if (draggingStart) {
                        selectedConnector.dragStart(e.getX(), e.getY());
                    } else if (draggingEnd) {
                        selectedConnector.dragEnd(e.getX(), e.getY());
                    } else if (draggingWhole) {
                        int dx = e.getX() - selectedConnector.getStartX() - connectorOffsetX;
                        int dy = e.getY() - selectedConnector.getStartY() - connectorOffsetY;
                        selectedConnector.move(dx, dy);
                    }
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                draggingStart = false;
                draggingEnd = false;
                draggingWhole = false;
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public void setActiveTool(String tool) {
        this.activeTool = tool;
    }

    public void addComponent(Component c) {
        components.add(c);
        repaint();
    }

    public void addConnector(Connector c) {
        connectors.add(c);
        repaint();
    }

    private void clearSelections() {
        for (Component c : components) c.setSelected(false);
        for (Connector c : connectors) c.setSelected(false);
        selectedComponent = null;
        selectedConnector = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Component c : components) c.draw(g);
        for (Connector c : connectors) c.draw(g);
    }
}
