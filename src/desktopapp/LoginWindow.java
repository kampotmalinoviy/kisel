package desktopapp;

import desktopapp.actions.LoginActions;
import desktopapp.financeview.FinancesTable;
import desktopapp.view.LoginViewActions;
import repos.InMemoryRepository;
import service.model.AuthModel;
import service.model.UserModel;
import service.entity.User;
import view.LoginView;
import view.MainView;
import view.entity.UserViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static desktopapp.ButtonHelper.styleButton;

public class LoginWindow extends UserInputBase implements LoginView, LoginActions, LoginViewActions {
    private final AuthModel authModel;
    private MainView mainView;
    private final UserModel model;
    private User user;

    private JTextField loginField;
    private JPasswordField passwordField;
    private InMemoryRepository repository;

    public LoginWindow(UserModel model, AuthModel authModel) {
        this.model = model;
        this.authModel = authModel;
        showLoginScreen();
    }

    private void showLoginScreen() {
        getContentPane().removeAll();
        setSize(1920, 1080);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(173, 216, 230));
        add(topPanel, BorderLayout.NORTH);

        JLabel loginLabel = new JLabel("Логин:");
        loginField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Пароль:");
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Войти");
        loginButton.setPreferredSize(new Dimension(120, 20));
        styleButton(loginButton);

        formPanel.add(loginLabel, gbc);
        formPanel.add(loginField, gbc);
        formPanel.add(passwordLabel, gbc);
        formPanel.add(passwordField, gbc);

        gbc.insets = new Insets(15, 0, 0, 0);
        formPanel.add(loginButton, gbc);
        add(formPanel, BorderLayout.CENTER);

        loginButton.addActionListener(this::doLogin);

        revalidate();
        repaint();
    }

    public void doLogin(ActionEvent e) {

        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        int id = authModel.authUser(login, password);

        if (id != -1)
        {
            user = model.getUser(id);
            showLoginSuccess();
        }
        else
        {
            showLoginError();
        }
    }

    @Override
    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void showLoginError() {
        JOptionPane.showMessageDialog(null, "Неверный логин или пароль!", "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showLoginSuccess() {
        mainView.showUser(new UserViewModel(user));
        mainView.showSelf();
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
