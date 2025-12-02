import BusinessLayer.Project;
import BusinessLayer.Circuit;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ProjectTest {
    
    private Project project;
    
    @Before
    public void setUp() {
        project = new Project();
    }
    
    @Test
    public void testProjectInitialization() {
        assertEquals("Project should have 1 circuit initially", 1, project.getcircuits().size());
        assertNotNull("Current circuit should not be null", project.getCurrentCircuit());
    }
    
    @Test
    public void testSetProjectName() {
        project.setName("MyProject");
        assertEquals("Project name should be set", "MyProject", project.getName());
    }
    
    @Test
    public void testGetLastCircuit() {
        Circuit lastCircuit = project.getLastCircuit();
        assertNotNull("Last circuit should not be null", lastCircuit);
        assertEquals("Last circuit name should be 'Circuit 1'", "Circuit 1", lastCircuit.getCircuitName());
    }
    

    @Test
    public void testGetCircuitByName() {
        Circuit circuit = project.getLastCircuit();
        circuit.setCircuitName("MyCircuit");
        
        Circuit retrieved = project.getCircuitByName("MyCircuit");
        assertNotNull("Retrieved circuit should not be null", retrieved);
        assertEquals("Retrieved circuit should have correct name", "MyCircuit", retrieved.getCircuitName());
    }
    
    @Test
    public void testGetNonExistentCircuitByName() {
        Circuit retrieved = project.getCircuitByName("NonExistent");
        assertNull("Retrieved circuit should be null for non-existent name", retrieved);
    }
}
