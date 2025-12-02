import BusinessLayer.Connector;
import BusinessLayer.Pin;
import BusinessLayer.AND;
import BusinessLayer.Component;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for Connector (Wire) class.
 * Tests wire creation, pin connections, value propagation, and interactions.
 */
public class ConnectorTest {
    
    private Component sourceComponent;
    private Component targetComponent;
    private Pin outputPin;
    private Pin inputPin;
    private Connector wire;
    
    @Before
    public void setUp() {
        sourceComponent = new AND(100, 50);
        targetComponent = new AND(200, 50);
        
        // Get pins from components
        outputPin = sourceComponent.getOutputPins().get(0);
        inputPin = targetComponent.getInputPins().get(0);
        
        wire = new Connector(outputPin.getX(), outputPin.getY());
    }
    
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
    
    @Test
    public void testWireSelection() {
        assertFalse(wire.isSelected());
        
        wire.setSelected(true);
        assertTrue(wire.isSelected());
        
        wire.setSelected(false);
        assertFalse(wire.isSelected());
    }
    
    @Test
    public void testConnectStartToOutputPin() {
        wire.connectToPin(outputPin, true);
        
        assertEquals(outputPin.getX(), wire.getStartX());
        assertEquals(outputPin.getY(), wire.getStartY());
        assertTrue(outputPin.isConnected());
        assertTrue(outputPin.getConnectedWires().contains(wire));
    }
    
    @Test
    public void testConnectEndToInputPin() {
        wire.connectToPin(inputPin, false);
        
        assertEquals(inputPin.getX(), wire.getEndX());
        assertEquals(inputPin.getY(), wire.getEndY());
        assertTrue(inputPin.isConnected());
        assertTrue(inputPin.getConnectedWires().contains(wire));
    }
    
    @Test
    public void testCompleteWireConnection() {
        wire.connectToPin(outputPin, true);
        wire.connectToPin(inputPin, false);
        
        assertEquals(outputPin.getX(), wire.getStartX());
        assertEquals(inputPin.getX(), wire.getEndX());
        assertTrue(wire.isConnected());
    }
    
    @Test
    public void testCannotConnectOutputPinToEnd() {
        // Should not be able to connect an output pin as the end point
        Pin anotherOutput = sourceComponent.getOutputPins().get(0);
        wire.connectToPin(anotherOutput, false);
        
        // End should not be connected to the output pin
        assertNotEquals(anotherOutput.getX(), wire.getEndX());
    }
    
    @Test
    public void testDragStart() {
        int newX = 150;
        int newY = 100;
        
        wire.dragStart(newX, newY);
        
        assertEquals(newX, wire.getStartX());
        assertEquals(newY, wire.getStartY());
    }
    
    @Test
    public void testDragEnd() {
        int newX = 250;
        int newY = 150;
        
        wire.dragEnd(newX, newY);
        
        assertEquals(newX, wire.getEndX());
        assertEquals(newY, wire.getEndY());
    }
    
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
    
    @Test
    public void testContainsStart() {
        int startX = wire.getStartX();
        int startY = wire.getStartY();
        
        assertTrue(wire.containsStart(startX, startY));
        assertTrue(wire.containsStart(startX + 3, startY)); // Within 8px radius
        assertFalse(wire.containsStart(startX + 20, startY)); // Outside 8px radius
    }
    
    @Test
    public void testContainsEnd() {
        int endX = wire.getEndX();
        int endY = wire.getEndY();
        
        assertTrue(wire.containsEnd(endX, endY));
        assertTrue(wire.containsEnd(endX - 3, endY)); // Within 8px radius
        assertFalse(wire.containsEnd(endX - 20, endY)); // Outside 8px radius
    }
    
    @Test
    public void testContainsLine() {
        // Create horizontal wire from (0,0) to (100,0)
        Connector hWire = new Connector(0, 0);
        hWire.dragEnd(100, 0);
        
        assertTrue(hWire.containsLine(50, 0)); // On the line
        assertTrue(hWire.containsLine(50, 3)); // Close to line
        assertFalse(hWire.containsLine(50, 20)); // Far from line
    }
    
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
    
    @Test
    public void testDisconnectStart() {
        wire.connectToPin(outputPin, true);
        assertTrue(outputPin.isConnected());
        
        wire.disconnectFromPin(true);
        assertFalse(outputPin.isConnected());
    }
    
    @Test
    public void testDisconnectEnd() {
        wire.connectToPin(inputPin, false);
        assertTrue(inputPin.isConnected());
        
        wire.disconnectFromPin(false);
        assertFalse(inputPin.isConnected());
    }
    
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
    
    @Test
    public void testIsConnected() {
        assertFalse(wire.isConnected());
        
        wire.connectToPin(outputPin, true);
        wire.connectToPin(inputPin, false);
        
        assertTrue(wire.isConnected());
    }
}
