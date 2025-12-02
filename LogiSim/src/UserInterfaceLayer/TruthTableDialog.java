package UserInterfaceLayer;

import BusinessLayer.TruthTableGenerator;
import BusinessLayer.Circuit;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * A modal dialog that displays the truth table and Boolean expressions
 * of a given circuit. The dialog shows a JTable for the truth table
 * and a JTextArea for the Boolean expressions.
 * 
 * <p>Usage:
 * <pre>
 * Circuit circuit = ...;
 * TruthTableDialog dialog = new TruthTableDialog(parentFrame, circuit);
 * dialog.setVisible(true);
 * </pre>
 * 
 * <p>If the circuit contains no switches, an error message is displayed
 * and the dialog will not show a table.
 * 
 * @author HP
 */
public class TruthTableDialog extends JDialog {

    /**
     * Constructs a TruthTableDialog for a given circuit.
     * 
     * @param parent the parent JFrame to center the dialog on
     * @param circuit the Circuit object whose truth table and Boolean expressions are displayed
     */
    public TruthTableDialog(JFrame parent, Circuit circuit) {
        super(parent, "Truth Table & Boolean Expression", true);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        TruthTableGenerator generator = new TruthTableGenerator(circuit);

        // 1. Generate Data
        List<String> inputLabels = generator.getInputLabels();
        List<String> outputLabels = generator.getOutputLabels();

        if (inputLabels.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No switches found in the circuit!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Create Table
        String[] columnNames = new String[inputLabels.size() + outputLabels.size()];
        int col = 0;
        for (String s : inputLabels)
            columnNames[col++] = s;
        for (String s : outputLabels)
            columnNames[col++] = s;

        Object[][] data = generator.generateTruthTable();

        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane tableScroll = new JScrollPane(table);

        // 3. Create Expression Area
        JTextArea expressionArea = new JTextArea();
        expressionArea.setEditable(false);
        expressionArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        Map<String, String> expressions = generator.generateExpressions();
        StringBuilder sb = new StringBuilder();
        sb.append("Boolean Expressions:\n");
        sb.append("--------------------\n");
        for (Map.Entry<String, String> entry : expressions.entrySet()) {
            sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }
        expressionArea.setText(sb.toString());

        // 4. Layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, new JScrollPane(expressionArea));
        splitPane.setDividerLocation(300);

        add(splitPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
