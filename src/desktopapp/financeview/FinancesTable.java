package desktopapp.financeview;

import desktopapp.actions.MainActions;
import desktopapp.view.MainViewActions;
import repos.InMemoryRepository;
import service.model.ActivityModel;
import service.model.UserModel;
import service.entity.User;
import service.entity.Entity;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import view.entity.UserViewModel;

import view.MainView;

public class FinancesTable extends JFrame implements MainView, MainActions, MainViewActions {

    User user;
    UserModel userModel;
    private final ExpensePanel expensePanel;
    private final IncomePanel incomePanel;
    ActivityModel activityModel;
    private final InMemoryRepository repository;

    public FinancesTable(UserModel userModel, ActivityModel activityModel) {
        this.repository = new InMemoryRepository();
        this.userModel = userModel;
        this.activityModel = activityModel;

        setTitle("Финансовая информация");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        expensePanel = new ExpensePanel();
        add(expensePanel, BorderLayout.CENTER);

        incomePanel = new IncomePanel(this::saveIncome);
        add(incomePanel, BorderLayout.NORTH);

        JPanel bottomPanel = getBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        if (user!=null){setVisible(true);}
    }

    private JPanel getBottomPanel() {
        JPanel bottomPanel = new JPanel();
        JButton addSectorButton = new JButton("Добавить сферу");
        JButton addExpenseButton = new JButton("Пополнить расходы");
        JButton statsButton = new JButton("Статистика");

        addSectorButton.addActionListener(this::addField);
        addExpenseButton.addActionListener(this::addExpense);
        statsButton.addActionListener(this::showStatistics);

        bottomPanel.add(addSectorButton);
        bottomPanel.add(addExpenseButton);
        bottomPanel.add(statsButton);
        return bottomPanel;
    }

    @Override
    public void showSelf() {
        setVisible(true);
    }

    @Override
    public void hideSelf() {
        setVisible(false);
    }

    @Override
    public void addField(ActionEvent e) {
        String fieldName = JOptionPane.showInputDialog(this, "Введите название новой сферы:");
        if (fieldName == null || fieldName.trim().isEmpty()) {
            showError("Название поля не может быть пустым");
            return;
        }
        boolean success = activityModel.addField(user, fieldName);
        if (success) {
            repository.update(user);
            showUser(new UserViewModel(user));
        } else {
            showError("Не удалось добавить сферу");
        }
    }

    @Override
    public void addExpense(ActionEvent e) {
        Expense expenseData = expensePanel.addExpense();
        if (expenseData != null) {
            activityModel.addExpense(user, expenseData.type(), expenseData.amount());
            showUser(new UserViewModel(user));
            repository.update(user);
        } else {
            showError("Ошибка при добавлении расхода.");
        }
    }

    @Override
    public void showStatistics(ActionEvent e) {
        Map<String, Object> stats = activityModel.getStatistics(user);
        StringBuilder statsText = new StringBuilder();
        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            statsText.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(this, statsText.toString(), "Статистика", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void saveIncome(ActionEvent e) {
        if (user == null) {
            System.out.println("Ошибка: текущий пользователь не установлен.");
            return;
        }
        String incomeType = e.getActionCommand();
        double delta = 0.0;
        if ("parents".equalsIgnoreCase(incomeType)) {
            delta = incomePanel.getParentIncome();
        } else if ("scholarship".equalsIgnoreCase(incomeType)) {
            delta = incomePanel.getScholarshipIncome();
        } else {
            System.out.println("Неизвестный источник дохода: " + incomeType);
            return;
        }

        activityModel.addIncome(user, incomeType, delta);

        double updatedValue = user.getIncome().stream()
                .filter(ent -> ent.getField().equalsIgnoreCase(incomeType))
                .mapToDouble(Entity::getValue)
                .findFirst().orElse(0.0);

        incomePanel.setIncome(incomeType, updatedValue);
        System.out.println("Доход для '" + incomeType + "' обновлен на: " + updatedValue);
    }


    @Override
    public void showUser(UserViewModel userViewModel) {
        this.user = userViewModel.getUser();
        expensePanel.getTableModel().setRowCount(0);
        expensePanel.expenses.clear();

        if (user.getOutcome() != null && !user.getOutcome().isEmpty()) {
            for (Entity category : user.getOutcome()) {
                expensePanel.getTableModel().addRow(new Object[]{
                        category.getField(),
                        category.getValue()
                });
                expensePanel.expenses.put(category.getField(), category.getValue());
            }
        }

        double parentsIncome = user.getIncome().stream()
                .filter(ent -> ent.getField().equalsIgnoreCase("parents"))
                .mapToDouble(ent -> ent.getValue())
                .findFirst().orElse(0.0);
        double scholarshipIncome = user.getIncome().stream()
                .filter(ent -> ent.getField().equalsIgnoreCase("scholarship"))
                .mapToDouble(ent -> ent.getValue())
                .findFirst().orElse(0.0);

        incomePanel.setIncome("parents", parentsIncome);
        incomePanel.setIncome("scholarship", scholarshipIncome);
    }

    @Override
    public void showExpences(List<String> income, List<String> outcome) {
        expensePanel.updateExpenses(outcome);
    }

    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

}
