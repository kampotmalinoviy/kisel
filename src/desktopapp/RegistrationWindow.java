package desktopapp;

import service.model.UserModel;
import service.entity.BasicUser;
import view.RegistrationView;
import view.WelcomeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static desktopapp.ButtonHelper.styleButton;

public class RegistrationWindow extends UserInputBase implements RegistrationView {

    private final UserModel userModel;
    private WelcomeView welcomeView;

    private JTextField loginField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField nameField;

    public RegistrationWindow(UserModel userModel) {
        this.userModel = userModel;
        showRegistrationScreen();
    }

    private void showRegistrationScreen() {
        getContentPane().removeAll();
        setSize(1920, 1080);
        JLabel nameLabel = new JLabel("Имя:");
        nameField = new JTextField(15);
        JLabel loginLabel = new JLabel("Логин:");
        loginField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Пароль:");
        passwordField = new JPasswordField(15);
        JLabel confirmPasswordLabel = new JLabel("Повторите пароль:");
        confirmPasswordField = new JPasswordField(15);
        JButton registerButton = new JButton("Создать аккаунт");
        registerButton.setPreferredSize(new Dimension(120, 20));
        styleButton(registerButton);

        formPanel.add(nameLabel, gbc);
        formPanel.add(nameField, gbc);
        formPanel.add(loginLabel, gbc);
        formPanel.add(loginField, gbc);
        formPanel.add(passwordLabel, gbc);
        formPanel.add(passwordField, gbc);
        formPanel.add(confirmPasswordLabel, gbc);
        formPanel.add(confirmPasswordField, gbc);

        gbc.insets = new Insets(15, 0, 0, 0);
        formPanel.add(registerButton, gbc);
        add(formPanel, BorderLayout.CENTER);

        registerButton.addActionListener(this::tryRegister);

        revalidate();
        repaint();
    }

    public void tryRegister(ActionEvent e) {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String name = nameField.getText();

            if (!password.equals(confirmPassword)) {
                showError();
                return;
            }

            userModel.addUser(new BasicUser(login, password, name));
            showWelcomeWindow();

    }

    @Override
    public void setWelcomeView(WelcomeView welcomeView) {
        this.welcomeView = welcomeView;
    }

    @Override
    public void showWelcomeWindow() {
        hideSelf();
        welcomeView.showSelf();
    }

    @Override
    public void showError() {
        JOptionPane.showMessageDialog(null, "Пароли не совпадают!", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
