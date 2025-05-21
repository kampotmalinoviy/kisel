package desktopapp.financeview;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExpensePanel extends JPanel {

    private final DefaultTableModel tableModel;
    public Map<String, Double> expenses;


    public ExpensePanel() {
        this.expenses = new HashMap<>();
        setLayout(new BorderLayout());

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        this.tableModel = new DefaultTableModel(new String[]{"Сфера", "Потрачено"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        tableContainer.add(scrollPane, BorderLayout.CENTER);
        add(tableContainer, BorderLayout.CENTER);
        setVisible(true);
    }

    public void updateExpenses(List<String> outcomes) {
        tableModel.setRowCount(0);
        for (String outcome : outcomes) {
            tableModel.addRow(new Object[]{ outcome });
        }
    }

    public Expense addExpense() {

       if (expenses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Сначала добавьте хотя бы одну сферу!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return null;
       }

        JComboBox<String> sectorDropdown = new JComboBox<>(expenses.keySet().toArray(new String[0]));
        JTextField expenseField = new JTextField();
        Object[] message = {
                "Выберите сферу:",
                sectorDropdown,
                "Введите сумму:",
                expenseField
        };

        int result = JOptionPane.showConfirmDialog(this, message, "Пополнить расходы", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String selectedSector = (String) sectorDropdown.getSelectedItem();
                String inputText = expenseField.getText().trim();

                if (inputText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Введите сумму!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                int amount = Integer.parseInt(inputText);
                return new Expense(selectedSector, amount);
                }

            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Введите корректное число!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return null;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

}
