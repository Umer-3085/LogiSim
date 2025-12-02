package BusinessLayer;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;

public class Canvas extends JPanel {

    private final ArrayList<Component> components = new ArrayList<>();
    private final ArrayList<Connector> connectors = new ArrayList<>();

    // Circuit simulator
    private Circuit circuit;

    private Component selectedComponent = null;
    private Connector selectedConnector = null;

    private int offsetX = 0;
    private int offsetY = 0;
    private int connectorOffsetX = 0;
    private int connectorOffsetY = 0;

    private boolean dragging = false;
    private boolean draggingEnd = false; // dragging an endpoint
    private boolean draggingStart = false; // dragging start endpoint
    private boolean draggingWhole = false; // dragging entire wire

    // Track if mouse has moved to distinguish click from drag
    private int pressX = 0;
    private int pressY = 0;
    private static final int DRAG_THRESHOLD = 5; // pixels

    private String activeTool = "";

    private static final int PIN_SNAP_THRESHOLD = 15; // pixels

    public Canvas(Circuit c) {
        
        circuit = c;
        
        setBackground(java.awt.Color.WHITE);
        setFocusable(true); // Required for keyboard events

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow(); // Get keyboard focus for Delete key
                Point p = e.getPoint();
                clearSelections();

                // 1) Check components first (so they are "on top" of wires)
                for (int i = components.size() - 1; i >= 0; i--) {
                    Component c = components.get(i);
                    if (c.contains(p)) {
                        selectedComponent = c;
                        selectedComponent.setSelected(true);
                        offsetX = e.getX() - c.x;
                        offsetY = e.getY() - c.y;

                        // Store press position to detect drag vs click
                        pressX = e.getX();
                        pressY = e.getY();

                        repaint(); // Ensure selection is visible immediately
                        return;
                    }
                }

                // 2) Check wire endpoints/lines
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

                // 3) Place new component or wire
                if (activeTool != null && !activeTool.isEmpty()) {
                    switch (activeTool) {
                        case "AND":
                            addComponent(new AND(p.x, p.y));
                            break;
                        case "OR":
                            addComponent(new OR(p.x, p.y));
                            break;
                        case "XOR":
                            addComponent(new XOR(p.x, p.y));
                            break;
                        case "NAND":
                            addComponent(new NAND(p.x, p.y));
                            break;
                        case "NOR":
                            addComponent(new NOR(p.x, p.y));
                            break;
                        case "NOT":
                            addComponent(new NOT(p.x, p.y));
                            break;
                        case "Switch":
                            addComponent(new Switch(p.x, p.y));
                            break;
                        case "LED":
                            addComponent(new LED(p.x, p.y));
                            break;
                        case "Wire":
                            addConnector(new Connector(p.x, p.y));
                            break;
                    }
                    activeTool = "";
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Check if mouse has moved beyond threshold to start dragging
                if (selectedComponent != null && !dragging) {
                    int dx = e.getX() - pressX;
                    int dy = e.getY() - pressY;
                    if (Math.abs(dx) > DRAG_THRESHOLD || Math.abs(dy) > DRAG_THRESHOLD) {
                        dragging = true;
                    }
                }

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
                // If component was selected but not dragged, it's a click
                if (selectedComponent != null && !dragging) {
                    // If it's a switch, toggle it
                    if (selectedComponent instanceof Switch) {
                        ((Switch) selectedComponent).toggle();
                        circuit.propagateFrom(selectedComponent);
                        repaint();
                    }
                }

                // Try to snap wire endpoints to nearby pins
                if (selectedConnector != null) {
                    if (draggingStart || draggingEnd) {
                        snapWireToPin(selectedConnector, draggingStart);
                        circuit.updateCircuit();
                        repaint();
                    }
                }

                dragging = false;
                draggingStart = false;
                draggingEnd = false;
                draggingWhole = false;
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);

        // Keyboard listener for Delete key
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    deleteSelected();
                }
            }
        });
    }

    public void setActiveTool(String tool) {
        this.activeTool = tool;
    }

    public void addComponent(Component c) {
        components.add(c);
        circuit.addComponent(c);
        repaint();
    }

    public void addConnector(Connector c) {
        connectors.add(c);
        circuit.addConnector(c);
        repaint();
    }

    /**
     * Snap wire endpoint to nearest pin if within threshold.
     */
    private void snapWireToPin(Connector wire, boolean isStart) {
        int x = isStart ? wire.getStartX() : wire.getEndX();
        int y = isStart ? wire.getStartY() : wire.getEndY();

        Pin nearestPin = null;
        double minDistance = PIN_SNAP_THRESHOLD;

        // Find nearest pin of correct type
        for (Component comp : components) {
            Pin pin = comp.findNearestPin(x, y, PIN_SNAP_THRESHOLD);
            if (pin != null) {
                double dist = pin.distanceTo(x, y);
                if (dist < minDistance) {
                    // Check pin type compatibility
                    if (isStart && pin.getType() == Pin.PinType.OUTPUT) {
                        nearestPin = pin;
                        minDistance = dist;
                    } else if (!isStart && pin.getType() == Pin.PinType.INPUT) {
                        nearestPin = pin;
                        minDistance = dist;
                    }
                }
            }
        }

        // Connect to pin if found
        if (nearestPin != null) {
            wire.connectToPin(nearestPin, isStart);
        } else {
            // Disconnect if no pin nearby
            wire.disconnectFromPin(isStart);
        }
    }

    /**
     * Trigger full circuit simulation update.
     */
    public void simulateCircuit() {
        circuit.updateCircuit();
        repaint();
    }

    /**
     * Get the circuit simulator.
     */
    public Circuit getCircuit() {
        return circuit;
    }

    /**
     * Delete the currently selected component or connector.
     */
    private void deleteSelected() {
        if (selectedComponent != null) {
            deleteComponent(selectedComponent);
            selectedComponent = null;
            repaint();
        } else if (selectedConnector != null) {
            deleteConnector(selectedConnector);
            selectedConnector = null;
            repaint();
        }
    }

    /**
     * Delete a component and all its connected wires.
     */
    private void deleteComponent(Component comp) {
        // Collect all wires connected to this component
        ArrayList<Connector> wiresToRemove = new ArrayList<>();

        for (Pin pin : comp.getInputPins()) {
            wiresToRemove.addAll(pin.getConnectedWires());
        }
        for (Pin pin : comp.getOutputPins()) {
            wiresToRemove.addAll(pin.getConnectedWires());
        }

        // Remove all connected wires
        for (Connector wire : wiresToRemove) {
            deleteConnector(wire);
        }

        // Remove component from lists
        components.remove(comp);
        circuit.removeComponent(comp);
    }

    /**
     * Delete a connector and disconnect it from pins.
     */
    private void deleteConnector(Connector conn) {
        // Disconnect from pins
        conn.disconnectFromPin(true); // start
        conn.disconnectFromPin(false); // end

        // Remove from lists
        connectors.remove(conn);
        circuit.removeConnector(conn);
    }

    private void clearSelections() {
        for (Component c : components)
            c.setSelected(false);
        for (Connector c : connectors)
            c.setSelected(false);
        selectedComponent = null;
        selectedConnector = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Component c : components)
            c.draw(g);
        for (Connector c : connectors)
            c.draw(g);
    }
    
    public void clearCanvas() {
        
        components.clear();
        connectors.clear();
        
        if (circuit != null) {
            circuit.getComponents().clear();
            circuit.getConnectors().clear();
        }
        
        selectedComponent = null;
        selectedConnector = null;
        
        dragging = false;
        draggingStart = false;
        draggingEnd = false;
        draggingWhole = false;
        
        repaint();
    }
    
    public void setCircuit(Circuit c){
        circuit = c;
    }
    
    public void syncWithCircuit() {
        components.clear();
        connectors.clear();
        components.addAll(circuit.getComponents());
        connectors.addAll(circuit.getConnectors());
        repaint();
    }

    
}
