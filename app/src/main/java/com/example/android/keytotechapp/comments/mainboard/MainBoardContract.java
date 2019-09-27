package com.example.android.keytotechapp.comments.mainboard;

import android.os.Bundle;

import com.example.android.keytotechapp.mvpbase.BaseModel;
import com.example.android.keytotechapp.mvpbase.BasePresenter;
import com.example.android.keytotechapp.mvpbase.BaseView;

public class MainBoardContract {

    interface Model extends BaseModel{

        void saveState(Bundle state);

        void restoreState(Bundle state);

        void setLowerBound(int lowerBound);

        void setUpperBound(int upperBound);

        int getLowerBound();

        int getUpperBound();
    }

    interface View extends BaseView{

        void initViewData(int lowerBound, int upperBound);

        void showWarningMessage();

        void showComments();

        void showZeroWarningMessage();

    }

    interface Presenter extends BasePresenter<View, Model>{

        void setLowerBound(int lowerBound);

        void setUpperBound(int upperBound);

        int getLowerBound();

        int getUpperBound();

        void initViewData();

        void validateData();

    }
}
