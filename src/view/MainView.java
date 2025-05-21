package view;

import view.entity.UserViewModel;

public interface MainView extends View
{
    void showUser(UserViewModel user);
}
