package view;

public interface WelcomeView extends View {
    void setLoginView(LoginView loginView);
    void setRegistrationView(RegistrationView registrationView);
    void showLoginWindow();
    void showRegistrationWindow();
}
