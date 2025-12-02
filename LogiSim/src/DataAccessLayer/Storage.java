/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessLayer;

import java.util.Map;
import java.util.ArrayList;

/**
 * Interface representing a generic storage mechanism for circuits projects.
 * <p>
 * This interface defines methods for saving, loading, listing, and removing
 * projects and circuits in a persistent storage system.
 * Implementations may store data in files, databases, or other storage mechanisms.
 * </p>
 * 
 * Example usage:
 * <pre>
 *     Storage storage = new DataStorage(); // concrete implementation
 *     Map&lt;String,Object&gt; projectData = storage.loadAProject("MyProject");
 * </pre>
 * 
 * @author HP
 */
public interface Storage {
    
     /**
     * Saves a project to persistent storage.
     * 
     * @param projectMap A map containing project data (name, circuits, components, connectors)
     * @return true if the save operation was successful, false otherwise
     */

    public boolean savePorjects(Map<String, Object> projectMap);
    
    /**
     * Resets all projects in the storage.
     * Typically clears all stored data.
     * 
     * @return true if reset was successful, false otherwise
     */
    public boolean resetPorjects();
    
    /**
     * Retrieves the list of all project names available in storage.
     * 
     * @return An ArrayList of project names
     */
    public ArrayList<String> projectNames();
    
    /**
     * Loads a specific project from storage.
     * 
     * @param projectname The name of the project to load
     * @return A map containing the project's data (circuits, components, connectors)
     */
    public Map<String, Object> loadAProject(String projectname);
    
    /**
     * Removes a specific circuit from a project in storage.
     * 
     * @param project The name of the project containing the circuit
     * @param circuit The name of the circuit to remove
     * @return true if removal was successful, false otherwise
     */
    public boolean remove(String project , String circuit );
    
}
