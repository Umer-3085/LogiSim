/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import BusinessLayer.AND;
import BusinessLayer.Component;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for the AND gate component.
 * <p>
 * This class validates the core behavior of the {@link AND} gate, including
 * pin initialization, logical computation, coordinate updates on movement,
 * instance cloning, and selection state management.
 * </p>
 *
 * @author HP
 */
public class ANDTest {
    
    /**
     * Verifies the initialization of input and output pins.
     * <p>
     * Checks that the AND gate is created with exactly two input pins and one output pin.
     * It also asserts that the pins are placed at the correct (x, y) coordinates
     * relative to the component's position.
     * </p>
     */
    @Test
    public void testPinsInitialization() {
        
        AND andGate;
        andGate = new AND(100, 50);
         
        assertEquals("Should have 2 input pins", 2, andGate.getInputPins().size());
        assertEquals("Should have 1 output pin", 1, andGate.getOutputPins().size());
        
        assertEquals(80, andGate.getInputPins().get(0).getX());
        assertEquals(60, andGate.getInputPins().get(0).getY());

        assertEquals(80, andGate.getInputPins().get(1).getX());
        assertEquals(80, andGate.getInputPins().get(1).getY());

        assertEquals(180, andGate.getOutputPins().get(0).getX());
        assertEquals(70, andGate.getOutputPins().get(0).getY());
    }
    
    /**
     * Verifies the logical computation of the AND gate.
     * <p>
     * Tests the standard truth table for an AND gate:
     * <ul>
     * <li>0 AND 0 &rarr; 0 (False)</li>
     * <li>1 AND 0 &rarr; 0 (False)</li>
     * <li>1 AND 1 &rarr; 1 (True)</li>
     * </ul>
     * </p>
     */
    @Test
    public void testComputeLogic() {
        
        AND andGate;
        andGate = new AND(100, 50);
        
        andGate.getInputPins().get(0).setValue(false);
        andGate.getInputPins().get(1).setValue(false);
        andGate.compute();
        assertFalse(andGate.getOutputPins().get(0).getValue());
        
        andGate.getInputPins().get(0).setValue(true);
        andGate.getInputPins().get(1).setValue(false);
        andGate.compute();
        assertFalse(andGate.getOutputPins().get(0).getValue());

        andGate.getInputPins().get(1).setValue(true);
        andGate.compute();
        assertTrue(andGate.getOutputPins().get(0).getValue());
    }

    /**
     * Verifies that pins update their positions when the component is moved.
     * <p>
     * Moves the component to (200, 100) and ensures both input pins and the output pin
     * move to their expected relative locations.
     * </p>
     */
    @Test
    public void testMoveAndUpdatePins() {

        AND andGate;
        andGate = new AND(100, 50);
        andGate.move(200, 100);

        assertEquals(180, andGate.getInputPins().get(0).getX());
        assertEquals(110, andGate.getInputPins().get(0).getY());

        assertEquals(180, andGate.getInputPins().get(1).getX());
        assertEquals(130, andGate.getInputPins().get(1).getY());

        assertEquals(280, andGate.getOutputPins().get(0).getX());
        assertEquals(120, andGate.getOutputPins().get(0).getY());
    }

    /**
     * Verifies the cloning functionality of the component.
     * <p>
     * Ensures that the cloned object is a new instance (not the same reference)
     * but retains the same state (coordinates, dimensions, and pin counts) as the original.
     * </p>
     */
    @Test
    public void testCloneComponent() {
        
        AND andGate;
        andGate = new AND(100, 50);
        
        Component clone = andGate.cloneComponent();
        assertNotSame("Clone should be a different object", andGate, clone);
        assertEquals(andGate.getX(), clone.getX());
        assertEquals(andGate.getY(), clone.getY());
        assertEquals(andGate.getWidth(), clone.getWidth());
        assertEquals(andGate.getHeight(), clone.getHeight());
        assertEquals(andGate.getInputPins().size(), clone.getInputPins().size());
        assertEquals(andGate.getOutputPins().size(), clone.getOutputPins().size());
        
    }

    /**
     * Verifies the selection state toggling.
     * <p>
     * Tests that the component correctly reports its selection state after being
     * programmatically selected and deselected.
     * </p>
     */
    @Test
    public void testSelection() {
        
        AND andGate;
        andGate = new AND(100, 50);
        
        andGate.setSelected(true);
        assertTrue(andGate.isSelected());

        andGate.setSelected(false);
        assertFalse(andGate.isSelected());
    }
    
}