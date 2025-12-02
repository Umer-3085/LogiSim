/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import BusinessLayer.XOR;
import BusinessLayer.Component;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author HP
 */
public class XORTest {

    @Test
    public void testPinsInitialization() {

        XOR xorGate;
        xorGate = new XOR(100, 50);

        assertEquals("Should have 2 input pins", 2, xorGate.getInputPins().size());
        assertEquals("Should have 1 output pin", 1, xorGate.getOutputPins().size());

        assertEquals(80, xorGate.getInputPins().get(0).getX());
        assertEquals(60, xorGate.getInputPins().get(0).getY());

        assertEquals(80, xorGate.getInputPins().get(1).getX());
        assertEquals(80, xorGate.getInputPins().get(1).getY());

        assertEquals(180, xorGate.getOutputPins().get(0).getX());
        assertEquals(70, xorGate.getOutputPins().get(0).getY());
    }

    @Test
    public void testComputeLogic() {

        XOR xorGate;
        xorGate = new XOR(100, 50);

        // false XOR false = false
        xorGate.getInputPins().get(0).setValue(false);
        xorGate.getInputPins().get(1).setValue(false);
        xorGate.compute();
        assertFalse(xorGate.getOutputPins().get(0).getValue());

        // true XOR false = true
        xorGate.getInputPins().get(0).setValue(true);
        xorGate.getInputPins().get(1).setValue(false);
        xorGate.compute();
        assertTrue(xorGate.getOutputPins().get(0).getValue());

        // true XOR true = false
        xorGate.getInputPins().get(1).setValue(true);
        xorGate.compute();
        assertFalse(xorGate.getOutputPins().get(0).getValue());
    }

    @Test
    public void testMoveAndUpdatePins() {

        XOR xorGate;
        xorGate = new XOR(100, 50);
        xorGate.move(200, 100);

        assertEquals(180, xorGate.getInputPins().get(0).getX());
        assertEquals(110, xorGate.getInputPins().get(0).getY());

        assertEquals(180, xorGate.getInputPins().get(1).getX());
        assertEquals(130, xorGate.getInputPins().get(1).getY());

        assertEquals(280, xorGate.getOutputPins().get(0).getX());
        assertEquals(120, xorGate.getOutputPins().get(0).getY());
    }

    @Test
    public void testCloneComponent() {

        XOR xorGate;
        xorGate = new XOR(100, 50);

        Component clone = xorGate.cloneComponent();
        assertNotSame("Clone should be a different object", xorGate, clone);
        assertEquals(xorGate.getX(), clone.getX());
        assertEquals(xorGate.getY(), clone.getY());
        assertEquals(xorGate.getWidth(), clone.getWidth());
        assertEquals(xorGate.getHeight(), clone.getHeight());
        assertEquals(xorGate.getInputPins().size(), clone.getInputPins().size());
        assertEquals(xorGate.getOutputPins().size(), clone.getOutputPins().size());

    }

    @Test
    public void testSelection() {

        XOR xorGate;
        xorGate = new XOR(100, 50);

        xorGate.setSelected(true);
        assertTrue(xorGate.isSelected());

        xorGate.setSelected(false);
        assertFalse(xorGate.isSelected());
    }

}
