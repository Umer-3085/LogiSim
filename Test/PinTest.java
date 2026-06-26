import BusinessLayer.Pin;
import BusinessLayer.AND;
import BusinessLayer.Component;
import BusinessLayer.Connector;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for the {@link Pin} class.
 * <p>
 * This class covers creation, state management, spatial positioning, wire connections,
 * cloning behavior, and utility functions like distance calculation.
 * </p>
 */
public class PinTest {

    private Component testComponent;
    private Pin inputPin;
    private Pin outputPin;

    /**
     * Sets up the test environment before each test method.
     * Initializes a sample component (AND gate) and two pins (INPUT and OUTPUT).
     */
    @Before
    public void setUp() {
        testComponent = new AND(100, 50);
        inputPin = new Pin(50, 60, Pin.PinType.INPUT, testComponent);
        outputPin = new Pin(150, 70, Pin.PinType.OUTPUT, testComponent);
    }

    /**
     * Tests the construction of a Pin object.
     * Verifies correct assignment of coordinates, type, and owner component.
     */
    @Test
    public void testPinCreation() {
        assertEquals(50, inputPin.getX());
        assertEquals(60, inputPin.getY());
        assertEquals(Pin.PinType.INPUT, inputPin.getType());
        assertEquals(testComponent, inputPin.getOwner());
    }

    /**
     * Tests the default value of a new Pin.
     * Pins should initialize to 'false' (low signal).
     */
    @Test
    public void testPinValueInitialization() {
        assertFalse("Pin value should be false initially", inputPin.getValue());
    }

    /**
     * Tests the setter and getter for the pin's boolean value.
     */
    @Test
    public void testSetAndGetValue() {
        inputPin.setValue(true);
        assertTrue(inputPin.getValue());

        inputPin.setValue(false);
        assertFalse(inputPin.getValue());
    }

    /**
     * Tests updating the spatial position of the pin.
     */
    @Test
    public void testUpdatePosition() {
        inputPin.updatePosition(100, 200);
        assertEquals(100, inputPin.getX());
        assertEquals(200, inputPin.getY());
    }

    /**
     * Tests that the Pin correctly reports its type (INPUT vs OUTPUT).
     */
    @Test
    public void testPinTypes() {
        assertEquals(Pin.PinType.INPUT, inputPin.getType());
        assertEquals(Pin.PinType.OUTPUT, outputPin.getType());
    }

    /**
     * Tests adding a wire connection to a pin.
     * Verifies that the pin registers as connected and the wire list is updated.
     */
    @Test
    public void testAddWire() {
        Connector wire = new Connector(50, 60);
        inputPin.addWire(wire);

        assertTrue("Pin should be connected", inputPin.isConnected());
        assertEquals(1, inputPin.getConnectedWires().size());
        assertTrue(inputPin.getConnectedWires().contains(wire));
    }

    /**
     * Tests adding multiple distinct wires to a single pin.
     */
    @Test
    public void testAddMultipleWires() {
        Connector wire1 = new Connector(50, 60);
        Connector wire2 = new Connector(100, 100);

        outputPin.addWire(wire1);
        outputPin.addWire(wire2);

        assertEquals(2, outputPin.getConnectedWires().size());
    }

    /**
     * Tests removing a wire from a pin.
     * Verifies that the pin handles disconnection correctly.
     */
    @Test
    public void testRemoveWire() {
        Connector wire = new Connector(50, 60);
        inputPin.addWire(wire);
        assertTrue(inputPin.isConnected());

        inputPin.removeWire(wire);

        assertFalse("Pin should have no wires after removal", inputPin.isConnected());
        assertEquals(0, inputPin.getConnectedWires().size());
    }

    /**
     * Tests that wire connections on different pins do not interfere with each other.
     * Removing a wire from 'inputPin' should not affect 'outputPin'.
     */
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

    /**
     * Tests the distance calculation method.
     * Verifies Euclidean distance computation from the pin to a given point.
     */
    @Test
    public void testDistanceCalculation() {
        Pin pin = new Pin(0, 0, Pin.PinType.INPUT, testComponent);

        assertEquals(5.0, pin.distanceTo(3, 4), 0.001);
        assertEquals(0.0, pin.distanceTo(0, 0), 0.001);
    }

    /**
     * Tests the cloning of a Pin.
     * <p>
     * Ensures that the cloned pin has the same properties (value, type, relative position)
     * but is assigned to a new owner component.
     * </p>
     */
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

    /**
     * Tests that attempting to add the same wire instance twice does not result in duplicates.
     */
    @Test
    public void testDuplicateWireNotAdded() {
        Connector wire = new Connector(50, 60);
        inputPin.addWire(wire);
        inputPin.addWire(wire); // duplicate attempt

        assertEquals(1, inputPin.getConnectedWires().size());
    }

    /**
     * Tests that {@code getConnectedWires()} returns a new list or safe copy.
     * Verifies that subsequent calls return distinct list objects to protect encapsulation.
     */
    @Test
    public void testGetConnectedWiresCopy() {
        Connector wire = new Connector(50, 60);
        inputPin.addWire(wire);

        var list1 = inputPin.getConnectedWires();
        var list2 = inputPin.getConnectedWires();

        assertNotSame("Should return a new list each time", list1, list2);
        assertEquals(list1.size(), list2.size());
    }
}