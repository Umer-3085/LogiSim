import BusinessLayer.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Point;

/**
 * Test suite for Component base class and interactions.
 * Tests component positioning, selection, pin management, and cloning.
 */
public class ComponentTest {
    
    private AND component;
    
    @Before
    public void setUp() {
        component = new AND(100, 50);
    }
    
    @Test
    public void testComponentPosition() {
        assertEquals(100, component.getX());
        assertEquals(50, component.getY());
    }
    
    @Test
    public void testComponentDimensions() {
        assertEquals(60, component.getWidth());
        assertEquals(40, component.getHeight());
    }
    
    @Test
    public void testComponentMove() {
        component.move(150, 100);
        
        assertEquals(150, component.getX());
        assertEquals(100, component.getY());
    }
    
    @Test
    public void testComponentContains() {
        Point p1 = new Point(100, 50);
        Point p2 = new Point(110, 60);
        Point p3 = new Point(200, 200);
        
        assertTrue("Component should contain center point", component.contains(p1));
        assertTrue("Component should contain point within bounds", component.contains(p2));
        assertFalse("Component should not contain distant point", component.contains(p3));
    }
    
    @Test
    public void testComponentSelection() {
        assertFalse("Should not be selected initially", component.isSelected());
        
        component.setSelected(true);
        assertTrue(component.isSelected());
        
        component.setSelected(false);
        assertFalse(component.isSelected());
    }
    
    @Test
    public void testGetInputPins() {
        assertNotNull(component.getInputPins());
        assertEquals(2, component.getInputPins().size());
    }
    
    @Test
    public void testGetOutputPins() {
        assertNotNull(component.getOutputPins());
        assertEquals(1, component.getOutputPins().size());
    }
    
    @Test
    public void testMultipleGates() {
        OR or = new OR(100, 100);
        NOT not = new NOT(100, 150);
        
        assertEquals(2, or.getInputPins().size());
        assertEquals(1, or.getOutputPins().size());
        
        assertEquals(1, not.getInputPins().size());
        assertEquals(1, not.getOutputPins().size());
    }
    
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
    
    @Test
    public void testCloneHasIndependentPins() {
        Component clone = component.cloneComponent();
        
        // Modify original pins
        component.getInputPins().get(0).setValue(true);
        
        // Clone pins should not be affected
        assertNotEquals(component.getInputPins().get(0).getValue(), 
                       clone.getInputPins().get(0).getValue());
    }
    
    @Test
    public void testSwitchComponent() {
        Switch sw = new Switch(100, 50);
        
        assertEquals(0, sw.getInputPins().size());
        assertEquals(1, sw.getOutputPins().size());
        assertFalse(sw.getState());
    }
    
    @Test
    public void testLEDComponent() {
        LED led = new LED(100, 50);
        
        assertEquals(1, led.getInputPins().size());
        assertEquals(0, led.getOutputPins().size());
        assertFalse(led.getState());
    }
    
    @Test
    public void testComponentMoveUpdatesPins() {
        int originalX0 = component.getInputPins().get(0).getX();
        int originalY0 = component.getInputPins().get(0).getY();
        
        component.move(300, 200);
        
        // Pin positions should change
        assertNotEquals(originalX0, component.getInputPins().get(0).getX());
        assertNotEquals(originalY0, component.getInputPins().get(0).getY());
    }
    
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
    
    @Test
    public void testSourceAndSinkComponents() {
        Switch sw = new Switch(0, 0);
        LED led = new LED(100, 0);
        
        // Switch has only output
        assertEquals(0, sw.getInputPins().size());
        assertEquals(1, sw.getOutputPins().size());
        
        // LED has only input
        assertEquals(1, led.getInputPins().size());
        assertEquals(0, led.getOutputPins().size());
    }
}
