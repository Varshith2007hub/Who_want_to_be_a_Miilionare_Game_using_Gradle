package million_dollar_showdown.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

public class FlowChartPanel extends JPanel {
    static JTable flowChartTable;

    public FlowChartPanel() {
        initializeFlowChartPanel();
    }

    private void initializeFlowChartPanel() {
        setLayout(new FlowLayout());

        String[] columnNames = { "Question", "Prize" };
        Object[][] data = {
                { "Question 1", "$ 500" },
                { "Question 2", "$ 1000" },
                { "Question 3", "$ 5000" },
                { "Question 4", "$ 10000" },
                { "Question 5", "$ 20000" },
                { "Question 6", "$ 45000" },
                { "Question 7", "$ 75000" },
                { "Question 8", "$ 200000" },
                { "Question 9", "$ 500000" },
                { "Question 10", "$ 1000000" }
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        flowChartTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);

                // Determine the row that should have a green background
                int greenRow = GameView.stage - 2;

                if (row == greenRow) {
                    component.setBackground(Color.GREEN);
                } else {
                    component.setBackground(Color.WHITE);
                }
                if (row == 3 || row == 7) {
                    ((JComponent) component).setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
                }
                return component;
            }
        };
        GameView.setTimerDuration();
        GameView.updateTimerLabel();
        flowChartTable.setRowHeight(27);
        flowChartTable.setEnabled(false);

        add(flowChartTable);
    }

    public static void repaintTable() {
        flowChartTable.repaint();
    }

    public Dimension getPreferredSize() {
        return flowChartTable.getPreferredSize();
    }
}