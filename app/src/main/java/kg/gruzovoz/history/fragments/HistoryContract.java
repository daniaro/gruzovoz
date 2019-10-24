package kg.gruzovoz.history.fragments;

import kg.gruzovoz.BaseContract;

public interface HistoryContract {

    interface View extends BaseContract.BaseView {
        void showError();
    }

    interface Presenter extends BaseContract.BasePresenter{

    }

}
