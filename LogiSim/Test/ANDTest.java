/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import BusinessLayer.AND;
import BusinessLayer.Component;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author HP
 */
public class ANDTest {
    
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
