package kg.gruzovoz.user_page;

import java.util.List;

import kg.gruzovoz.models.UserPage;

public interface UserPageContract {

    interface View{
        void showConfirmLogoutDialog();
        void setAllData(UserPage userPage);
        void notAuthorized();
        void showError();

    }

    interface Presenter{
        void getPersonalData();
        void logout();
    }


}
