package com.example.android.keytotechapp.comments;

import android.os.Bundle;

import java.util.List;

import androidx.annotation.NonNull;

public class CommentsPresenter implements CommentsFragmentContract.Presenter, CommentsFragmentContract.Model.OnFinishedListener {

    private CommentsFragmentContract.Model mModel;
    private CommentsFragmentContract.View mView;

    @Override
    public void setView(@NonNull CommentsFragmentContract.View view) {
        mView = view;
    }

    @Override
    public void setModel(@NonNull CommentsFragmentContract.Model model) {
        mModel = model;
    }

    @Override
    public void restoreState(@NonNull Bundle savedInstanceState) {
        mModel.restoreState(savedInstanceState);
    }

    @Override
    public void saveState(@NonNull Bundle savedInstanceState) {
        mModel.saveState(savedInstanceState);
    }

    @Override
    public int getLowerBound() {
        return mModel.getLowerBound();
    }

    @Override
    public int getUpperBound() {
        return mModel.getUpperBound();
    }

    @Override
    public List<Comment> getComments() {
        return mModel.getCommentsList();
    }

    @Override
    public void initData(Bundle args) {
        mModel.initData(args);
        mModel.getCommentsQuantity().observe(mView.getLifecycleOwner(), (quantity) -> mView.showMaximumQuantityMessage(quantity));
    }

    @Override
    public void requestDataFromServer() {
        mView.showProgress();
        mModel.getNewCommentsList(this, mModel.getLowerBound());
    }

    @Override
    public void getMoreData(int pageNo) {
        mView.showProgress();
        mModel.getNewCommentsList(this, pageNo);
    }

    @Override
    public void onFinished(List<Comment> movieArrayList, int pageNumber) {
        mView.setDataToRecyclerView(movieArrayList, pageNumber);
        mView.hideProgress();
    }

    @Override
    public void onFailure(Throwable t) {
        mView.onResponseFailure(t);
        mView.hideProgress();
    }
}
