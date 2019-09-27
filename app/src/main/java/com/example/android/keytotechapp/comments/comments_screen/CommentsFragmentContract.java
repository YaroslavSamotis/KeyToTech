package com.example.android.keytotechapp.comments.comments_screen;

import android.os.Bundle;

import com.example.android.keytotechapp.mvpbase.BaseModel;
import com.example.android.keytotechapp.mvpbase.BasePresenter;
import com.example.android.keytotechapp.mvpbase.BaseView;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

public class CommentsFragmentContract {

    interface View extends BaseView{

        void setDataToRecyclerView(List<Comment> comments, int pageNumber);

        void showProgress();

        void hideProgress();

        void onResponseFailure(Throwable t);

        LifecycleOwner getLifecycleOwner();

        void showMaximumQuantityMessage(int quantiity);

    }

    interface Model extends BaseModel{

        void saveState(Bundle state);

        void restoreState(Bundle state);

        int getLowerBound();

        int getUpperBound();

        void initData(Bundle args);

        void getNewCommentsList(final OnFinishedListener listener, int pageNumber);

        List<Comment> getCommentsList();

        MutableLiveData<Integer> getCommentsQuantity();

        interface OnFinishedListener {

            void onFinished(List<Comment> movieArrayList, int pageNumber);

            void onFailure(Throwable t);
        }
    }

    interface Presenter extends BasePresenter<View, Model>{

        int getLowerBound();

        int getUpperBound();

        List<Comment> getComments();

        void initData(Bundle args);

        void requestDataFromServer();

        void getMoreData(int bound);

    }
}
