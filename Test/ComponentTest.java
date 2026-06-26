import BusinessLayer.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Point;

/**
 * Unit tests for the Component base class and its subclasses.
 * 
 * <p>This test suite validates behavior of logic components, including
 * basic gates (AND, OR, NOT, NAND, NOR, XOR), Switches, and LEDs.</p>
 * 
 * <p>Test coverage includes:</p>
 * <ul>
 *     <li>Component position, dimensions, and movement.</li>
 *     <li>Component selection state.</li>
 *     <li>Pin management: input and output pins.</li>
 *     <li>Cloning of components and pin independence.</li>
 *     <li>Switch and LED specific behaviors.</li>
 *     <li>All gate types initialization and properties.</li>
 * </ul>
 * 
 * Author: HP
 */
public class ComponentTest {
    
    private AND component;

    /**
     * Sets up a fresh AND gate before each test.
     */
    @Before
    public void setUp() {
        component = new AND(100, 50);
    }

    /**
     * Tests initial position of the component.
     */
    @Test
    public void testComponentPosition() {
        assertEquals(100, component.getX());
        assertEquals(50, component.getY());
    }

    /**
     * Tests component width and height.
     */
    @Test
    public void testComponentDimensions() {
        assertEquals(60, component.getWidth());
        assertEquals(40, component.getHeight());
    }

    /**
     * Tests moving the component updates its position.
     */
    @Test
    public void testComponentMove() {
        component.move(150, 100);
        assertEquals(150, component.getX());
        assertEquals(100, component.getY());
    }

    /**
     * Tests the contains(Point) method for various points.
     */
    @Test
    public void testComponentContains() {
        Point p1 = new Point(100, 50);
        Point p2 = new Point(110, 60);
        Point p3 = new Point(200, 200);

        assertTrue(component.contains(p1));
        assertTrue(component.contains(p2));
        assertFalse(component.contains(p3));
    }

    /**
     * Tests selecting and deselecting the component.
     */
    @Test
    public void testComponentSelection() {
        assertFalse(component.isSelected());
        component.setSelected(true);
        assertTrue(component.isSelected());
        component.setSelected(false);
        assertFalse(component.isSelected());
    }

    /**
     * Tests retrieval of input pins.
     */
    @Test
    public void testGetInputPins() {
        assertNotNull(component.getInputPins());
        assertEquals(2, component.getInputPins().size());
    }

    /**
     * Tests retrieval of output pins.
     */
    @Test
    public void testGetOutputPins() {
        assertNotNull(component.getOutputPins());
        assertEquals(1, component.getOutputPins().size());
    }

    /**
     * Tests properties of multiple gates (OR, NOT).
     */
    @Test
    public void testMultipleGates() {
        OR or = new OR(100, 100);
        NOT not = new NOT(100, 150);
        assertEquals(2, or.getInputPins().size());
        assertEquals(1, or.getOutputPins().size());
        assertEquals(1, not.getInputPins().size());
        assertEquals(1, not.getOutputPins().size());
    }

    /**
     * Tests cloning preserves component properties.
     */
    @Test
    public void testClonePreservesProperties() {
        component.move(200, 150);
        component.setSelected(true);
        Component clone = component.cloneComponent();
        assertEquals(component.getX(), clone.getX());
        assertEquals(component.getY(), clone.getY());
        assertEquals(component.getWidth(), clone.getWidth());
        assertEquals(component.getHeight(), clone.getHeight());
        assertNotSame(component, clone);
    }

    /**
     * Tests that cloned components have independent pins.
     */
    @Test
    public void testCloneHasIndependentPins() {
        Component clone = component.cloneComponent();
        component.getInputPins().get(0).setValue(true);
        assertNotEquals(component.getInputPins().get(0).getValue(),
                        clone.getInputPins().get(0).getValue());
    }

    /**
     * Tests Switch component behavior (only output pin, state).
     */
    @Test
    public void testSwitchComponent() {
        Switch sw = new Switch(100, 50);
        assertEquals(0, sw.getInputPins().size());
        assertEquals(1, sw.getOutputPins().size());
        assertFalse(sw.getState());
    }

    /**
     * Tests LED component behavior (only input pin, state).
     */
    @Test
    public void testLEDComponent() {
        LED led = new LED(100, 50);
        assertEquals(1, led.getInputPins().size());
        assertEquals(0, led.getOutputPins().size());
        assertFalse(led.getState());
    }

    /**
     * Tests that moving a component updates pin positions.
     */
    @Test
    public void testComponentMoveUpdatesPins() {
        int originalX0 = component.getInputPins().get(0).getX();
        int originalY0 = component.getInputPins().get(0).getY();
        component.move(300, 200);
        assertNotEquals(originalX0, component.getInputPins().get(0).getX());
        assertNotEquals(originalY0, component.getInputPins().get(0).getY());
    }

    /**
     * Tests initialization of all gate types (AND, OR, NOT, NAND, NOR, XOR).
     */
    @Test
    public void testAllGateTypes() {
        Component[] gates = {
            new AND(0, 0),
            new OR(0, 0),
            new NOT(0, 0),
            new NAND(0, 0),
            new NOR(0, 0),
            new XOR(0, 0)
        };
        for (Component gate : gates) {
            assertNotNull(gate);
            assertNotNull(gate.getInputPins());
            assertNotNull(gate.getOutputPins());
        }
    }

    /**
     * Tests source (Switch) and sink (LED) components' pins.
     */
    @Test
    public void testSourceAndSinkComponents() {
        Switch sw = new Switch(0, 0);
        LED led = new LED(100, 0);
        assertEquals(0, sw.getInputPins().size());
        assertEquals(1, sw.getOutputPins().size());
        assertEquals(1, led.getInputPins().size());
        assertEquals(0, led.getOutputPins().size());
    }
}
