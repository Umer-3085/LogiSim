import BusinessLayer.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Integration tests for complex circuit scenarios.
 * Tests realistic circuit configurations and multi-component interactions.
 */
public class CircuitIntegrationTest {
    
    private Circuit circuit;
    
    @Before
    public void setUp() {
        circuit = new Circuit("Integration Test");
    }
    
    @Test
    public void testFullAdderCircuit() {
        /**
         * Simple full adder test: 2-input XOR for Sum
         * Switch A -> XOR
         * Switch B -> XOR
         * Output -> LED (Sum indicator)
         */
        
        Switch a = new Switch(0, 0);
        Switch b = new Switch(50, 0);
        XOR xor = new XOR(100, 0);
        LED led = new LED(150, 0);
        
        circuit.addComponent(a);
        circuit.addComponent(b);
        circuit.addComponent(xor);
        circuit.addComponent(led);
        
        // Connect A -> XOR input 1
        Connector w1 = new Connector(a.getOutputPins().get(0).getX(), a.getOutputPins().get(0).getY());
        w1.connectToPin(a.getOutputPins().get(0), true);
        w1.connectToPin(xor.getInputPins().get(0), false);
        circuit.addConnector(w1);
        
        // Connect B -> XOR input 2
        Connector w2 = new Connector(b.getOutputPins().get(0).getX(), b.getOutputPins().get(0).getY());
        w2.connectToPin(b.getOutputPins().get(0), true);
        w2.connectToPin(xor.getInputPins().get(1), false);
        circuit.addConnector(w2);
        
        // Connect XOR output -> LED
        Connector w3 = new Connector(xor.getOutputPins().get(0).getX(), xor.getOutputPins().get(0).getY());
        w3.connectToPin(xor.getOutputPins().get(0), true);
        w3.connectToPin(led.getInputPins().get(0), false);
        circuit.addConnector(w3);
        
        // Test 0 XOR 0 = 0
        a.setState(false);
        b.setState(false);
        circuit.updateCircuit();
        assertFalse("LED off for 0 XOR 0", led.getState());
        
        // Test 0 XOR 1 = 1
        a.setState(false);
        b.setState(true);
        circuit.updateCircuit();
        assertTrue("LED on for 0 XOR 1", led.getState());
        
        // Test 1 XOR 0 = 1
        a.setState(true);
        b.setState(false);
        circuit.updateCircuit();
        assertTrue("LED on for 1 XOR 0", led.getState());
        
        // Test 1 XOR 1 = 0
        a.setState(true);
        b.setState(true);
        circuit.updateCircuit();
        assertFalse("LED off for 1 XOR 1", led.getState());
    }
    
    @Test
    public void testNORBasedNOTGate() {
        /**
         * Build a NOT gate using two NOR gates:
         * Input -> NOR gate (both inputs tied together)
         */
        
        Switch input = new Switch(0, 0);
        NOR nor = new NOR(50, 0);
        LED output = new LED(100, 0);
        
        circuit.addComponent(input);
        circuit.addComponent(nor);
        circuit.addComponent(output);
        
        // Connect input to both NOR inputs
        Connector w1 = new Connector(input.getOutputPins().get(0).getX(), input.getOutputPins().get(0).getY());
        w1.connectToPin(input.getOutputPins().get(0), true);
        w1.connectToPin(nor.getInputPins().get(0), false);
        circuit.addConnector(w1);
        
        Connector w2 = new Connector(input.getOutputPins().get(0).getX(), input.getOutputPins().get(0).getY());
        w2.connectToPin(input.getOutputPins().get(0), true);
        w2.connectToPin(nor.getInputPins().get(1), false);
        circuit.addConnector(w2);
        
        // Connect NOR output to LED
        Connector w3 = new Connector(nor.getOutputPins().get(0).getX(), nor.getOutputPins().get(0).getY());
        w3.connectToPin(nor.getOutputPins().get(0), true);
        w3.connectToPin(output.getInputPins().get(0), false);
        circuit.addConnector(w3);
        
        // Test: input 0 -> NOT(0) = 1
        input.setState(false);
        circuit.updateCircuit();
        assertTrue("NOR-based NOT should invert: 0->1", output.getState());
        
        // Test: input 1 -> NOT(1) = 0
        input.setState(true);
        circuit.updateCircuit();
        assertFalse("NOR-based NOT should invert: 1->0", output.getState());
    }
    
    @Test
    public void testMultiLevelGateCircuit() {
        /**
         * Three-level circuit:
         * Level 1: Two switches
         * Level 2: AND gate
         * Level 3: NOT gate
         * Output: LED
         */
        
        Switch s1 = new Switch(0, 0);
        Switch s2 = new Switch(50, 0);
        AND and = new AND(100, 0);
        NOT not = new NOT(150, 0);
        LED led = new LED(200, 0);
        
        circuit.addComponent(s1);
        circuit.addComponent(s2);
        circuit.addComponent(and);
        circuit.addComponent(not);
        circuit.addComponent(led);
        
        // S1 -> AND input 1
        Connector w1 = new Connector(s1.getOutputPins().get(0).getX(), s1.getOutputPins().get(0).getY());
        w1.connectToPin(s1.getOutputPins().get(0), true);
        w1.connectToPin(and.getInputPins().get(0), false);
        circuit.addConnector(w1);
        
        // S2 -> AND input 2
        Connector w2 = new Connector(s2.getOutputPins().get(0).getX(), s2.getOutputPins().get(0).getY());
        w2.connectToPin(s2.getOutputPins().get(0), true);
        w2.connectToPin(and.getInputPins().get(1), false);
        circuit.addConnector(w2);
        
        // AND output -> NOT input
        Connector w3 = new Connector(and.getOutputPins().get(0).getX(), and.getOutputPins().get(0).getY());
        w3.connectToPin(and.getOutputPins().get(0), true);
        w3.connectToPin(not.getInputPins().get(0), false);
        circuit.addConnector(w3);
        
        // NOT output -> LED
        Connector w4 = new Connector(not.getOutputPins().get(0).getX(), not.getOutputPins().get(0).getY());
        w4.connectToPin(not.getOutputPins().get(0), true);
        w4.connectToPin(led.getInputPins().get(0), false);
        circuit.addConnector(w4);
        
        // Test: 0 AND 0 = 0 -> NOT(0) = 1
        s1.setState(false);
        s2.setState(false);
        circuit.updateCircuit();
        assertTrue("LED on for NOT(0 AND 0)", led.getState());
        
        // Test: 1 AND 1 = 1 -> NOT(1) = 0
        s1.setState(true);
        s2.setState(true);
        circuit.updateCircuit();
        assertFalse("LED off for NOT(1 AND 1)", led.getState());
    }
    
    @Test
    public void testDeMoregansLaw() {
        /**
         * Verify De Morgan's law: NOT(A AND B) = (NOT A) OR (NOT B)
         */
        
        Switch a = new Switch(0, 0);
        Switch b = new Switch(50, 0);
        
        // Left side: NOT(A AND B)
        AND and1 = new AND(100, 0);
        NOT not1 = new NOT(150, 0);
        
        // Right side: (NOT A) OR (NOT B)
        NOT notA = new NOT(100, 50);
        NOT notB = new NOT(150, 50);
        OR or = new OR(200, 50);
        
        circuit.addComponent(a);
        circuit.addComponent(b);
        circuit.addComponent(and1);
        circuit.addComponent(not1);
        circuit.addComponent(notA);
        circuit.addComponent(notB);
        circuit.addComponent(or);
        
        // Build left side chain
        Connector w1 = new Connector(a.getOutputPins().get(0).getX(), a.getOutputPins().get(0).getY());
        w1.connectToPin(a.getOutputPins().get(0), true);
        w1.connectToPin(and1.getInputPins().get(0), false);
        circuit.addConnector(w1);
        
        Connector w2 = new Connector(b.getOutputPins().get(0).getX(), b.getOutputPins().get(0).getY());
        w2.connectToPin(b.getOutputPins().get(0), true);
        w2.connectToPin(and1.getInputPins().get(1), false);
        circuit.addConnector(w2);
        
        Connector w3 = new Connector(and1.getOutputPins().get(0).getX(), and1.getOutputPins().get(0).getY());
        w3.connectToPin(and1.getOutputPins().get(0), true);
        w3.connectToPin(not1.getInputPins().get(0), false);
        circuit.addConnector(w3);
        
        // Build right side chain
        Connector w4 = new Connector(a.getOutputPins().get(0).getX(), a.getOutputPins().get(0).getY());
        w4.connectToPin(a.getOutputPins().get(0), true);
        w4.connectToPin(notA.getInputPins().get(0), false);
        circuit.addConnector(w4);
        
        Connector w5 = new Connector(b.getOutputPins().get(0).getX(), b.getOutputPins().get(0).getY());
        w5.connectToPin(b.getOutputPins().get(0), true);
        w5.connectToPin(notB.getInputPins().get(0), false);
        circuit.addConnector(w5);
        
        Connector w6 = new Connector(notA.getOutputPins().get(0).getX(), notA.getOutputPins().get(0).getY());
        w6.connectToPin(notA.getOutputPins().get(0), true);
        w6.connectToPin(or.getInputPins().get(0), false);
        circuit.addConnector(w6);
        
        Connector w7 = new Connector(notB.getOutputPins().get(0).getX(), notB.getOutputPins().get(0).getY());
        w7.connectToPin(notB.getOutputPins().get(0), true);
        w7.connectToPin(or.getInputPins().get(1), false);
        circuit.addConnector(w7);
        
        // Test all combinations
        int[] inputs = {0, 1};
        for (int aVal : inputs) {
            for (int bVal : inputs) {
                a.setState(aVal == 1);
                b.setState(bVal == 1);
                circuit.updateCircuit();
                
                boolean left = not1.getOutputPins().get(0).getValue();
                boolean right = or.getOutputPins().get(0).getValue();
                
                assertEquals("De Morgan's law violated for inputs " + aVal + "," + bVal, left, right);
            }
        }
    }
    
    @Test
    public void testComplexBooleanExpression() {
        /**
         * Test: (A OR B) AND (C OR D)
         */
        
        Switch a = new Switch(0, 0);
        Switch b = new Switch(50, 0);
        Switch c = new Switch(100, 0);
        Switch d = new Switch(150, 0);
        
        OR or1 = new OR(200, 0);
        OR or2 = new OR(200, 50);
        AND and = new AND(300, 25);
        LED result = new LED(400, 25);
        
        circuit.addComponent(a);
        circuit.addComponent(b);
        circuit.addComponent(c);
        circuit.addComponent(d);
        circuit.addComponent(or1);
        circuit.addComponent(or2);
        circuit.addComponent(and);
        circuit.addComponent(result);
        
        // A -> OR1
        Connector w1 = new Connector(a.getOutputPins().get(0).getX(), a.getOutputPins().get(0).getY());
        w1.connectToPin(a.getOutputPins().get(0), true);
        w1.connectToPin(or1.getInputPins().get(0), false);
        circuit.addConnector(w1);
        
        // B -> OR1
        Connector w2 = new Connector(b.getOutputPins().get(0).getX(), b.getOutputPins().get(0).getY());
        w2.connectToPin(b.getOutputPins().get(0), true);
        w2.connectToPin(or1.getInputPins().get(1), false);
        circuit.addConnector(w2);
        
        // C -> OR2
        Connector w3 = new Connector(c.getOutputPins().get(0).getX(), c.getOutputPins().get(0).getY());
        w3.connectToPin(c.getOutputPins().get(0), true);
        w3.connectToPin(or2.getInputPins().get(0), false);
        circuit.addConnector(w3);
        
        // D -> OR2
        Connector w4 = new Connector(d.getOutputPins().get(0).getX(), d.getOutputPins().get(0).getY());
        w4.connectToPin(d.getOutputPins().get(0), true);
        w4.connectToPin(or2.getInputPins().get(1), false);
        circuit.addConnector(w4);
        
        // OR1 -> AND
        Connector w5 = new Connector(or1.getOutputPins().get(0).getX(), or1.getOutputPins().get(0).getY());
        w5.connectToPin(or1.getOutputPins().get(0), true);
        w5.connectToPin(and.getInputPins().get(0), false);
        circuit.addConnector(w5);
        
        // OR2 -> AND
        Connector w6 = new Connector(or2.getOutputPins().get(0).getX(), or2.getOutputPins().get(0).getY());
        w6.connectToPin(or2.getOutputPins().get(0), true);
        w6.connectToPin(and.getInputPins().get(1), false);
        circuit.addConnector(w6);
        
        // AND -> LED
        Connector w7 = new Connector(and.getOutputPins().get(0).getX(), and.getOutputPins().get(0).getY());
        w7.connectToPin(and.getOutputPins().get(0), true);
        w7.connectToPin(result.getInputPins().get(0), false);
        circuit.addConnector(w7);
        
        // Test case: (T OR F) AND (T OR F) = T AND T = T
        a.setState(true);
        b.setState(false);
        c.setState(true);
        d.setState(false);
        circuit.updateCircuit();
        assertTrue("(T OR F) AND (T OR F) should be T", result.getState());
        
        // Test case: (F OR F) AND (T OR T) = F AND T = F
        a.setState(false);
        b.setState(false);
        c.setState(true);
        d.setState(true);
        circuit.updateCircuit();
        assertFalse("(F OR F) AND (T OR T) should be F", result.getState());
    }
}
