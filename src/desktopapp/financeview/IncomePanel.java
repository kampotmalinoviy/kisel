package desktopapp.financeview;

import view.IncomePanelView;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.function.Consumer;
import java.util.function.Function;

public class IncomePanel extends JPanel implements IncomePanelView {

    private final JTextField parentField;
    private final JTextField scholarshipField;
    private final JButton parentSaveButton;
    private final JButton scholarshipSaveButton;

    public double parentTotal;
    public double scholarshipTotal;

    public IncomePanel(ActionListener saveIncomeHandler) {
        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Поступления");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);
        add(new JLabel());
        parentTotal = 0;
        scholarshipTotal = 0;

        JLabel parentLabel = new JLabel("Родители:");
        add(parentLabel);

        JPanel parentPanel = new JPanel(new BorderLayout(5, 0));
        parentField = new JTextField();


        parentField.setText(String.valueOf(parentTotal));
        parentPanel.add(parentField, BorderLayout.CENTER);
        parentSaveButton = new JButton("Сохранить");
        parentSaveButton.setActionCommand("parents");
        parentSaveButton.setEnabled(false);
        parentPanel.add(parentSaveButton, BorderLayout.EAST);
        add(parentPanel);

        JLabel scholarshipLabel = new JLabel("Стипендия:");
        add(scholarshipLabel);

        JPanel scholarshipPanel = new JPanel(new BorderLayout(5, 0));
        scholarshipField = new JTextField();
        scholarshipField.setText(String.valueOf(scholarshipTotal));
        scholarshipPanel.add(scholarshipField, BorderLayout.CENTER);
        scholarshipSaveButton = new JButton("Сохранить");
        scholarshipSaveButton.setActionCommand("scholarship");
        scholarshipSaveButton.setEnabled(false);
        scholarshipPanel.add(scholarshipSaveButton, BorderLayout.EAST);
        add(scholarshipPanel);

        parentField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { update(); }
            @Override
            public void removeUpdate(DocumentEvent e) { update(); }
            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
            private void update() {
                parentSaveButton.setEnabled(!parentField.getText().trim().isEmpty());
            }
        });
        scholarshipField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { update(); }
            @Override
            public void removeUpdate(DocumentEvent e) { update(); }
            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
            private void update() {
                scholarshipSaveButton.setEnabled(!scholarshipField.getText().trim().isEmpty());
            }
        });

        parentField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (parentField.getText().trim().equals(String.valueOf(parentTotal))) {
                    parentField.setText("");
                }
            }
        });
        scholarshipField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (scholarshipField.getText().trim().equals(String.valueOf(scholarshipTotal))) {
                    scholarshipField.setText("");
                }
            }
        });


        parentSaveButton.addActionListener(e -> {
            String input = parentField.getText().trim();
            if (!input.isEmpty()) {
                try {
                    Double.parseDouble(input);
                    parentSaveButton.setEnabled(false);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(IncomePanel.this,
                            "Введите корректное число для 'Родители'!",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        scholarshipSaveButton.addActionListener(e -> {
            String input = scholarshipField.getText().trim();
            if (!input.isEmpty()) {
                try {
                    Double.parseDouble(input);
                    scholarshipSaveButton.setEnabled(false);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(IncomePanel.this,
                            "Введите корректное число для 'Стипендия'!",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        parentSaveButton.addActionListener(saveIncomeHandler);
        scholarshipSaveButton.addActionListener(saveIncomeHandler);
    }

    public int getParentIncome() {
        try {
            return (int) Double.parseDouble(parentField.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int getScholarshipIncome() {
        try {
            return (int) Double.parseDouble(scholarshipField.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setIncome(String source, double value) {
        if ("parents".equalsIgnoreCase(source)) {
            parentTotal = value;
            parentField.setText(String.valueOf(value));
        } else if ("scholarship".equalsIgnoreCase(source)) {
            scholarshipTotal = value;
            scholarshipField.setText(String.valueOf(value));
        }
    }

    @Override
    public void showSelf() {
        setVisible(true);
    }

    @Override
    public void hideSelf() {
        setVisible(false);
    }
}