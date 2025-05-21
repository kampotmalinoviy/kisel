
import desktopapp.LoginWindow;
import desktopapp.RegistrationWindow;
import desktopapp.WelcomeWindow;
import desktopapp.financeview.FinancesTable;
import service.model.ActivityModel;
import service.model.AuthModel;
import repos.InMemoryRepository;
import service.model.UserModel;
import view.*;

public class Main {
    public static void main(String[] args) {
        InMemoryRepository repository = new InMemoryRepository();
        AuthModel authModel = new AuthModel(repository);
        UserModel userModel = new UserModel(repository);
        ActivityModel activityModel = new ActivityModel();

        WelcomeView welcomeView = new WelcomeWindow();
        LoginView loginView = new LoginWindow(userModel, authModel);
        RegistrationView registrationView = new RegistrationWindow(userModel);
        MainView mainView = new FinancesTable(userModel,activityModel);

        loginView.setMainView(mainView);
        welcomeView.setLoginView(loginView);
        welcomeView.setRegistrationView(registrationView);
        registrationView.setWelcomeView(welcomeView);
    }
}
