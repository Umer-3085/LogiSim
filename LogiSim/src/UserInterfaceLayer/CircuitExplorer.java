/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaceLayer;

/**
 *
 * @author HP
 */
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

import BusinessLayer.Project;
import BusinessLayer.Canvas;
import BusinessLayer.Circuit;

public class CircuitExplorer extends JPanel {
    
    public JList<String> circuitList;
    public DefaultListModel<String> circuitModel;
    private static JButton addCircuitBtn;
    private static JButton removeCircuitBtn;
    private static JButton saveCircuitBtn;
    private static JButton reuseCircuitBtn;
    private Project project;
    private Canvas canvas;
    
    public void clearList(){
        circuitModel.clear();
    }
    
    public void addCircuitsInList(){
        for (Circuit c : project.getcircuits()) {
            circuitModel.addElement(c.getCircuitName());
        }
    }
    
    /**
     * 
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