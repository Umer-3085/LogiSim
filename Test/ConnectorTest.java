import BusinessLayer.Connector;
import BusinessLayer.Pin;
import BusinessLayer.AND;
import BusinessLayer.Component;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Connector (Wire) class.
 * 
 * <p>This test suite validates behavior of wires connecting components,
 * including pin connections, value propagation, dragging, movement, selection,
 * and disconnection.</p>
 * 
 * <p>Test coverage includes:</p>
 * <ul>
 *     <li>Wire creation and default properties.</li>
 *     <li>Wire selection state.</li>
 *     <li>Connecting start (output) and end (input) pins.</li>
 *     <li>Drag and move operations.</li>
 *     <li>Hit detection: start, end, and line.</li>
 *     <li>Value propagation through the wire.</li>
 *     <li>Disconnecting pins and reconnecting to new pins.</li>
 *     <li>Wire connectivity status.</li>
 * </ul>
 * 
 * Author: HP
 */
public class ConnectorTest {
    
    private Component sourceComponent;
    private Component targetComponent;
    private Pin outputPin;
    private Pin inputPin;
    private Connector wire;
    
    /**
     * Sets up two AND gates and a wire before each test.
     */
    @Before
    public void setUp() {
        sourceComponent = new AND(100, 50);
        targetComponent = new AND(200, 50);
        
        // Get pins from components
        outputPin = sourceComponent.getOutputPins().get(0);
        inputPin = targetComponent.getInputPins().get(0);
        
        wire = new Connector(outputPin.getX(), outputPin.getY());
    }
    
    /**
     * Tests wire initialization and default end position.
     */
    @Test
    public void testWireCreation() {
        int startX = outputPin.getX();
        int startY = outputPin.getY();
        
        Connector newWire = new Connector(startX, startY);
        
        assertEquals(startX, newWire.getStartX());
        assertEquals(startY, newWire.getStartY());
        // Default end should be 50 pixels to the right
        assertEquals(startX + 50, newWire.getEndX());
        assertEquals(startY, newWire.getEndY());
    }
    
    
    /**
     * Tests selection state toggling of the wire.
     */
    @Test
    public void testWireSelection() {
        assertFalse(wire.isSelected());
        
        wire.setSelected(true);
        assertTrue(wire.isSelected());
        
        wire.setSelected(false);
        assertFalse(wire.isSelected());
    }
    
    /**
     * Tests connecting the wire start to an output pin.
     */
    @Test
    public void testConnectStartToOutputPin() {
        wire.connectToPin(outputPin, true);
        
        assertEquals(outputPin.getX(), wire.getStartX());
        assertEquals(outputPin.getY(), wire.getStartY());
        assertTrue(outputPin.isConnected());
        assertTrue(outputPin.getConnectedWires().contains(wire));
    }
    
    /**
     * Tests connecting the wire end to an input pin.
     */
    @Test
    public void testConnectEndToInputPin() {
        wire.connectToPin(inputPin, false);
        
        assertEquals(inputPin.getX(), wire.getEndX());
        assertEquals(inputPin.getY(), wire.getEndY());
        assertTrue(inputPin.isConnected());
        assertTrue(inputPin.getConnectedWires().contains(wire));
    }
    
    /**
     * Tests full connection from output to input pin.
     */
    @Test
    public void testCompleteWireConnection() {
        wire.connectToPin(outputPin, true);
        wire.connectToPin(inputPin, false);
        
        assertEquals(outputPin.getX(), wire.getStartX());
        assertEquals(inputPin.getX(), wire.getEndX());
        assertTrue(wire.isConnected());
    }
    
    /**
     * Ensures output pins cannot be connected to the wire end.
     */
    @Test
    public void testCannotConnectOutputPinToEnd() {
        // Should not be able to connect an output pin as the end point
        Pin anotherOutput = sourceComponent.getOutputPins().get(0);
        wire.connectToPin(anotherOutput, false);
        
        // End should not be connected to the output pin
        assertNotEquals(anotherOutput.getX(), wire.getEndX());
    }
    
     /**
     * Tests dragging the start point of the wire.
     */
    @Test
    public void testDragStart() {
        int newX = 150;
        int newY = 100;
        
        wire.dragStart(newX, newY);
        
        assertEquals(newX, wire.getStartX());
        assertEquals(newY, wire.getStartY());
    }
    
     /**
     * Tests dragging the end point of the wire.
     */
    @Test
    public void testDragEnd() {
        int newX = 250;
        int newY = 150;
        
        wire.dragEnd(newX, newY);
        
        assertEquals(newX, wire.getEndX());
        assertEquals(newY, wire.getEndY());
    }
    
    /**
     * Tests moving the whole wire by an offset.
     */
    @Test
    public void testMoveWholeWire() {
        int originalStartX = wire.getStartX();
        int originalStartY = wire.getStartY();
        int originalEndX = wire.getEndX();
        int originalEndY = wire.getEndY();
        
        wire.move(10, 20);
        
        assertEquals(originalStartX + 10, wire.getStartX());
        assertEquals(originalStartY + 20, wire.getStartY());
        assertEquals(originalEndX + 10, wire.getEndX());
        assertEquals(originalEndY + 20, wire.getEndY());
    }
    
     /**
     * Tests if a point is near the wire start (hit detection).
     */
    @Test
    public void testContainsStart() {
        int startX = wire.getStartX();
        int startY = wire.getStartY();
        
        assertTrue(wire.containsStart(startX, startY));
        assertTrue(wire.containsStart(startX + 3, startY)); // Within 8px radius
        assertFalse(wire.containsStart(startX + 20, startY)); // Outside 8px radius
    }
    
    /**
     * Tests if a point is near the wire end (hit detection).
     */
    @Test
    public void testContainsEnd() {
        int endX = wire.getEndX();
        int endY = wire.getEndY();
        
        assertTrue(wire.containsEnd(endX, endY));
        assertTrue(wire.containsEnd(endX - 3, endY)); // Within 8px radius
        assertFalse(wire.containsEnd(endX - 20, endY)); // Outside 8px radius
    }
    
    /**
     * Tests if a point is on or near the wire line.
     */
    @Test
    public void testContainsLine() {
        // Create horizontal wire from (0,0) to (100,0)
        Connector hWire = new Connector(0, 0);
        hWire.dragEnd(100, 0);
        
        assertTrue(hWire.containsLine(50, 0)); // On the line
        assertTrue(hWire.containsLine(50, 3)); // Close to line
        assertFalse(hWire.containsLine(50, 20)); // Far from line
    }
    
     /**
     * Tests propagation of boolean values through the wire.
     */
    @Test
    public void testPropagateValue() {
        wire.connectToPin(outputPin, true);
        wire.connectToPin(inputPin, false);
        
        // Set source pin value
        outputPin.setValue(true);
        
        // Propagate through wire
        wire.propagate();
        
        // Target pin should receive the value
        assertTrue(inputPin.getValue());
    }
    
    /**
     * Tests disconnecting the wire from its start pin.
     */
    @Test
    public void testDisconnectStart() {
        wire.connectToPin(outputPin, true);
        assertTrue(outputPin.isConnected());
        
        wire.disconnectFromPin(true);
        assertFalse(outputPin.isConnected());
    }
    
    
    /**
     * Tests disconnecting the wire from its end pin.
     */
    @Test
    public void testDisconnectEnd() {
        wire.connectToPin(inputPin, false);
        assertTrue(inputPin.isConnected());
        
        wire.disconnectFromPin(false);
        assertFalse(inputPin.isConnected());
    }
    
    /**
     * Tests reconnecting the wire to a new pin after disconnection.
     */
    @Test
    public void testReconnectToNewPin() {
        Component intermediateComponent = new AND(150, 50);
        Pin intermediateInput = intermediateComponent.getInputPins().get(0);
        
        // First connection
        wire.connectToPin(outputPin, true);
        assertTrue(outputPin.isConnected());
        
        // Disconnect and reconnect
        wire.connectToPin(inputPin, false);
        assertEquals(inputPin.getX(), wire.getEndX());
        assertEquals(inputPin.getY(), wire.getEndY());
    }
    
    /**
     * Tests the isConnected() method of the wire.
     */
    @Test
    public void testIsConnected() {
        assertFalse(wire.isConnected());
        
        wire.connectToPin(outputPin, true);
        wire.connectToPin(inputPin, false);
        
        assertTrue(wire.isConnected());
    }
}
