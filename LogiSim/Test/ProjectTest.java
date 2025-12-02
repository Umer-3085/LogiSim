import BusinessLayer.Project;
import BusinessLayer.Circuit;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for the {@link Project} class.
 * <p>
 * The Project class manages a collection of circuits and
 * stores metadata such as the project name and the currently
 * active circuit. These tests ensure correct initialization,
 * circuit retrieval, and project name handling.
 * </p>
 *
 * Usage example:
 * <pre>
 *     Project project = new Project();
 *     project.setName("Sample");
 *     Circuit c = project.getLastCircuit();
 * </pre>
 */
public class ProjectTest {
    
    private Project project;
    
    /**
     * Initializes a fresh Project instance before each test.
     */
    @Before
    public void setUp() {
        project = new Project();
    }
    
    /**
     * Tests that the project initializes correctly.
     * Ensures that a default circuit is created and the
     * current circuit reference is not null.
     */
    @Test
    public void testProjectInitialization() {
        assertEquals("Project should have 1 circuit initially", 1, project.getcircuits().size());
        assertNotNull("Current circuit should not be null", project.getCurrentCircuit());
    }
    
    /**
     * Tests that the project name can be assigned and retrieved properly.
     */
    @Test
    public void testSetProjectName() {
        project.setName("MyProject");
        assertEquals("Project name should be set", "MyProject", project.getName());
    }
    
    /**
     * Tests retrieval of the last circuit in the project.
     * The default circuit should be named "Circuit 1".
     */
    @Test
    public void testGetLastCircuit() {
        Circuit lastCircuit = project.getLastCircuit();
        assertNotNull("Last circuit should not be null", lastCircuit);
        assertEquals("Last circuit name should be 'Circuit 1'", "Circuit 1", lastCircuit.getCircuitName());
    }

    /**
     * Tests retrieving a circuit by its custom name.
     * Ensures correct matching and return of an existing circuit.
     */
    @Test
    public void testGetCircuitByName() {
        Circuit circuit = project.getLastCircuit();
        circuit.setCircuitName("MyCircuit");
        
        Circuit retrieved = project.getCircuitByName("MyCircuit");
        assertNotNull("Retrieved circuit should not be null", retrieved);
        assertEquals("Retrieved circuit should have correct name", "MyCircuit", retrieved.getCircuitName());
    }
    
    /**
     * Tests that requesting a non-existent circuit name returns null.
     */
    @Test
    public void testGetNonExistentCircuitByName() {
        Circuit retrieved = project.getCircuitByName("NonExistent");
        assertNull("Retrieved circuit should be null for non-existent name", retrieved);
    }
}
