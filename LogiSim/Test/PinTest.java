import BusinessLayer.Pin;
import BusinessLayer.AND;
import BusinessLayer.Component;
import BusinessLayer.Connector;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for Pin class.
 * Tests pin creation, value management, positioning, and wire connections.
 */
public class PinTest {
    
    private Component testComponent;
    private Pin inputPin;
    private Pin outputPin;
    
    @Before
    public void setUp() {
        testComponent = new AND(100, 50);
        inputPin = new Pin(50, 60, Pin.PinType.INPUT, testComponent);
        outputPin = new Pin(150, 70, Pin.PinType.OUTPUT, testComponent);
    }
    
    @Test
    public void testPinCreation() {
        assertEquals(50, inputPin.getX());
        assertEquals(60, inputPin.getY());
        assertEquals(Pin.PinType.INPUT, inputPin.getType());
        assertEquals(testComponent, inputPin.getOwner());
    }
    
    @Test
    public void testPinValueInitialization() {
        assertFalse("Pin value should be false initially", inputPin.getValue());
    }
    
    @Test
    public void testSetAndGetValue() {
        inputPin.setValue(true);
        assertTrue(inputPin.getValue());
        
        inputPin.setValue(false);
        assertFalse(inputPin.getValue());
    }
    
    @Test
    public void testUpdatePosition() {
        inputPin.updatePosition(100, 200);
        assertEquals(100, inputPin.getX());
        assertEquals(200, inputPin.getY());
    }
    
    @Test
    public void testPinTypes() {
        assertEquals(Pin.PinType.INPUT, inputPin.getType());
        assertEquals(Pin.PinType.OUTPUT, outputPin.getType());
    }
    
    @Test
    public void testAddWire() {
        Connector wire = new Connector(50, 60);
        inputPin.addWire(wire);
        
        assertTrue("Wire should be connected", inputPin.isConnected());
        assertEquals(1, inputPin.getConnectedWires().size());
        assertTrue(inputPin.getConnectedWires().contains(wire));
    }
    
    @Test
    public void testAddMultipleWires() {
        Connector wire1 = new Connector(50, 60);
        Connector wire2 = new Connector(100, 100);
        
        outputPin.addWire(wire1);
        outputPin.addWire(wire2);
        
        assertEquals(2, outputPin.getConnectedWires().size());
    }
    
    @Test
    public void testRemoveWire() {
        Connector wire = new Connector(50, 60);
        inputPin.addWire(wire);
        assertTrue(inputPin.isConnected());
        
        inputPin.removeWire(wire);
        assertFalse("Wire should be disconnected", inputPin.isConnected());
        assertEquals(0, inputPin.getConnectedWires().size());
    }
    
    @Test
    public void testWireConnectionIndependence() {
        Connector wire1 = new Connector(50, 60);
        Connector wire2 = new Connector(100, 100);
        
        inputPin.addWire(wire1);
        outputPin.addWire(wire2);
        
        inputPin.removeWire(wire1);
        
        assertFalse(inputPin.isConnected());
        assertTrue(outputPin.isConnected());
        assertEquals(1, outputPin.getConnectedWires().size());
    }
    
    @Test
    public void testDistanceCalculation() {
        Pin pin = new Pin(0, 0, Pin.PinType.INPUT, testComponent);
        
        // Distance to (3, 4) should be 5
        double distance = pin.distanceTo(3, 4);
        assertEquals(5.0, distance, 0.001);
        
        // Distance to same point should be 0
        distance = pin.distanceTo(0, 0);
        assertEquals(0.0, distance, 0.001);
    }
    
    @Test
    public void testClonePin() {
        Component newComponent = new AND(200, 100);
        Pin originalPin = new Pin(50, 60, Pin.PinType.INPUT, testComponent);
        originalPin.setValue(true);
        
        Pin clonedPin = originalPin.clonePin(newComponent);
        
        assertNotSame(originalPin, clonedPin);
        assertEquals(originalPin.getX(), clonedPin.getX());
        assertEquals(originalPin.getY(), clonedPin.getY());
        assertEquals(originalPin.getType(), clonedPin.getType());
        assertEquals(originalPin.getValue(), clonedPin.getValue());
        assertEquals(newComponent, clonedPin.getOwner());
    }
    
    @Test
    public void testDuplicateWireNotAdded() {
        Connector wire = new Connector(50, 60);
        inputPin.addWire(wire);
        inputPin.addWire(wire); // Try to add same wire again
        
        // Should still only have 1 wire
        assertEquals(1, inputPin.getConnectedWires().size());
    }
    
    @Test
    public void testGetConnectedWiresCopy() {
        Connector wire1 = new Connector(50, 60);
        inputPin.addWire(wire1);
        
        java.util.ArrayList<Connector> wires1 = inputPin.getConnectedWires();
        java.util.ArrayList<Connector> wires2 = inputPin.getConnectedWires();
        
        // Should return separate list instances
        assertNotSame(wires1, wires2);
        assertEquals(wires1.size(), wires2.size());
    }
}
