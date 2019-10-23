package kg.gruzovoz.details;

import kg.gruzovoz.BaseContract;

public interface DetailContract {

    interface DetailView {
        void setViewInfo();
    }

    interface DetailPresenter {
        void populateInfo();
    }
}
