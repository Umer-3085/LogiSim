/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessLayer;

import java.util.Map;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public interface Storage {
    
    public boolean savePorjects(Map<String, Object> projectMap);
    
    public boolean resetPorjects();
    
    public ArrayList<String> projectNames();
    
    public Map<String, Object> loadAProject(String projectname);
    
    public boolean remove(String project , String circuit );
    
}
