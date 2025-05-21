package service.entity;

public class BasicUser {
    private String login;
    private String password;
    private String name;

    public BasicUser(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getName() { return name; }

    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }

    public void UserModifyData(String newLogin, String newPassword, String newName){
        if (newLogin != null && !newLogin.isEmpty()) {
            this.login = newLogin;
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            this.password = newPassword;
        }
        if (newName != null && !newName.isEmpty()) {
            this.name = newName;
        }
    }
}

