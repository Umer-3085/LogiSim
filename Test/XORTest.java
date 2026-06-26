/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import BusinessLayer.XOR;
import BusinessLayer.Component;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the {@link XOR} gate component.
 * <p>
 * This class verifies proper initialization of pins, XOR logic computation,
 * movement behavior, cloning behavior, and selection toggling of the XOR gate.
 * </p>
 *
 * Usage example:
 * <pre>
 *     XOR xorGate = new XOR(100, 50);
 *     xorGate.getInputPins().get(0).setValue(true);
 *     xorGate.getInputPins().get(1).setValue(false);
 *     xorGate.compute();
 * </pre>
 *
 * @author HP
 */
public class XORTest {

    /**
     * Tests whether input and output pins of the XOR gate
     * are correctly initialized at expected positions.
     */
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

    /**
     * Tests XOR logic computation:
     * <ul>
     *   <li>false XOR false = false</li>
     *   <li>true XOR false = true</li>
     *   <li>true XOR true = false</li>
     * </ul>
     */
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

    /**
     * Tests that moving the XOR gate updates the positions of its
     * input and output pins correctly relative to the new coordinates.
     */
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

    /**
     * Tests the cloning functionality of the XOR gate.
     * Ensures the clone is a distinct object but has identical properties.
     */
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

    /**
     * Tests selection state toggling of the XOR component using
     * {@link Component#setSelected(boolean)} and {@link Component#isSelected()}.
     */
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
