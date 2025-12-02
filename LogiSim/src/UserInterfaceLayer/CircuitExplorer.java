/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaceLayer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

import BusinessLayer.Project;
import BusinessLayer.Canvas;
import BusinessLayer.Circuit;

/**
 * A panel that provides a user interface for exploring, creating, removing,
 * saving, and reusing circuits within a {@link Project}.
 * <p>
 * It displays a list of circuits in the project and provides buttons for:
 * <ul>
 *     <li>Creating a new circuit</li>
 *     <li>Removing an existing circuit</li>
 *     <li>Saving a circuit with a custom name</li>
 *     <li>Reusing/merging an existing circuit into the current one</li>
 * </ul>
 * </p>
 * <p>
 * The panel integrates with a {@link Canvas} object to reflect the currently
 * selected circuit and updates it when circuits are modified.
 * </p>
 * 
 * Example usage:
 * <pre>
 *     Project project = new Project("MyProject");
 *     Canvas canvas = new Canvas();
 *     CircuitExplorer explorer = new CircuitExplorer(project, canvas);
 * </pre>
 * 
 * @author HP
 */
public class CircuitExplorer extends JPanel {
    
    /** The list component displaying circuit names. */
    public JList<String> circuitList;
    /** The model backing the circuit list. */
    public DefaultListModel<String> circuitModel;
    /** Button to add a new circuit. */
    private static JButton addCircuitBtn;
    /** Button to remove a selected circuit. */
    private static JButton removeCircuitBtn;
     /** Button to save the current circuit with a name. */
    private static JButton saveCircuitBtn;
    /** Button to reuse or merge a selected circuit into the current one. */
    private static JButton reuseCircuitBtn;
     /** Reference to the project containing circuits. */
    private Project project;
    /** Reference to the canvas displaying the current circuit. */
    private Canvas canvas;
    
    /**
     * Clears all circuits from the list model.
     */
    public void clearList(){
        circuitModel.clear();
    }
    
     /**
     * Adds all circuits from the project to the list model.
     */
    public void addCircuitsInList(){
        for (Circuit c : project.getcircuits()) {
            circuitModel.addElement(c.getCircuitName());
        }
    }
    
    /**
     * Creates a CircuitExplorer panel for the given project and canvas.
     * Initializes the list, buttons, and their event handlers.
     * 
     * @param p The project whose circuits are to be managed.
     * @param c The canvas used to display and interact with the current circuit.
     */
    public CircuitExplorer(Project p,Canvas c) {
        
        project = p;
        canvas = c;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 400));
        setBackground(Color.WHITE);
        
        TitledBorder titleBorder = BorderFactory.createTitledBorder("Circuit Explorer");
        
        titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        titleBorder.setTitleColor(Color.darkGray);
        setBorder(titleBorder);
        
        circuitModel = new DefaultListModel<>();
        circuitList = new JList<>(circuitModel);
        
        JPanel buttonPanel1 = new JPanel(new FlowLayout());
        buttonPanel1.setBackground(Color.white);
        addCircuitBtn = new JButton("New Circuit");
        removeCircuitBtn = new JButton("Remove");
        
        buttonPanel1.add(addCircuitBtn);
        buttonPanel1.add(removeCircuitBtn);
        
        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 3));
        buttonPanel2.setBackground(Color.white);

        buttonPanel2.setPreferredSize(new Dimension(150, 35));
        buttonPanel2.setMaximumSize(new Dimension(150, 35));

        saveCircuitBtn = new JButton("Save Circuit");
        reuseCircuitBtn = new JButton("Reuse");
        buttonPanel2.add(saveCircuitBtn);
        buttonPanel2.add(reuseCircuitBtn);
        
        add(buttonPanel2, BorderLayout.NORTH);
        add(new JScrollPane(circuitList), BorderLayout.CENTER);
        add(buttonPanel1, BorderLayout.SOUTH);
        
        saveCircuitBtn.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               JTextField nameField = new JTextField();

                Object[] message = {
                    "Circuit Name:", nameField
                };

                int option = JOptionPane.showOptionDialog(
                        null,                     // parent component
                        message,                  // message (label + text field)
                        "Save Circuit",           // title
                        JOptionPane.YES_NO_OPTION,// buttons type
                        JOptionPane.QUESTION_MESSAGE,
                        null,                     // icon
                        new String[]{"Save", "No"}, // custom button texts
                        "Save"                    // default button
                );
                
                if (option == 0) { // Save button
                    String circuitName = nameField.getText().trim();
                    if (!circuitName.isEmpty()) {
                        if (circuitModel.contains(circuitName)) {
                            JOptionPane.showMessageDialog(null, "A circuit with this name already exists!");
                            return;
                        }
                        circuitModel.addElement(circuitName);
                        project.getLastCircuit().setCircuitName(circuitName);
                    } else {
                        JOptionPane.showMessageDialog(null, "Circuit name cannot be empty!");
                    }
                } else {
                    System.out.println("User clicked No / cancelled");
                }
           }
        });
        
        removeCircuitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = circuitList.getSelectedIndex();

                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "Select a circuit first!");
                    return;
                }

                // Ask for confirmation
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this circuit?",
                        "Delete Circuit",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    return; 
                }

                String selectedCircuitName = circuitModel.get(index);


                Circuit circuitToRemove = project.getCircuitByName(selectedCircuitName);
                if (circuitToRemove != null) {
                    project.removeCircuit(project.getName(),selectedCircuitName);
                }
                
                circuitModel.remove(index);

                if (project.getCurrentCircuit() == circuitToRemove) {
                    canvas.clearCanvas();
                }
            }
        });

        
        addCircuitBtn.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               project.createNewCircuit();
               canvas.setCircuit(project.getCurrentCircuit());
               canvas.clearCanvas();
           }
        });
        
        reuseCircuitBtn.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               int index = circuitList.getSelectedIndex();

                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "Select a circuit first!");
                    return;
                }

                String selectedName = circuitModel.get(index);

                Circuit source = project.getCircuitByName(selectedName);
                Circuit target = project.getCurrentCircuit();

                project.mergeCircuits(source, target);
                canvas.setCircuit(target);
                canvas.syncWithCircuit();
                canvas.repaint();
           }
        });
        
    }
}