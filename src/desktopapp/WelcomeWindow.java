package desktopapp;
import view.LoginView;
import view.RegistrationView;
import view.WelcomeView;
import javax.swing.*;
import java.awt.*;

import static desktopapp.ButtonHelper.styleButton;

public class WelcomeWindow extends JFrame implements WelcomeView {

    private LoginView loginView;
    private RegistrationView registrationView;

    public WelcomeWindow() {
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        setTitle("Финансовый Трекер");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(173, 216, 230));
        setLayout(new GridLayout(3, 1));

        JLabel welcomeLabel = new JLabel("Добро пожаловать в Финансовый Трекер!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JButton loginButton = new JButton("Войти");
        loginButton.setPreferredSize(new Dimension(170, 40));
        JButton registerButton = new JButton("Зарегистрироваться");
        registerButton.setPreferredSize(new Dimension(170, 40));

        styleButton(loginButton);
        styleButton(registerButton);

        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonGroup.setBackground(new Color(173, 216, 230));
        buttonGroup.add(loginButton);
        buttonGroup.add(registerButton);

        buttonPanel.add(buttonGroup, gbc);
        add(buttonPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> { showLoginWindow();});
        registerButton.addActionListener(e -> { showRegistrationWindow();});

        revalidate();
        repaint();
        showSelf();
    }

    @Override
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void setRegistrationView(RegistrationView registrationView) {
        this.registrationView = registrationView;
    }

    @Override
    public void showLoginWindow() {
        hideSelf();
        loginView.showSelf();
    }

    @Override
    public void showRegistrationWindow() {
        hideSelf();
        registrationView.showSelf();
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
