package kg.gruzovoz.user_page;

import java.util.List;

import kg.gruzovoz.models.UserPage;

public interface UserPageContract {

    interface View{
        void showConfirmLogoutDialog();
        void setAllData(UserPage userPage);

    }

    interface Presenter{
        void getPersonalData();
    }


}
