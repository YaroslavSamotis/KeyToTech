package com.example.android.keytotechapp.comments.mainboard;

import android.os.Bundle;

import androidx.annotation.NonNull;

public class MainBoardPresenter implements MainBoardContract.Presenter {

    private MainBoardContract.Model mModel;
    private MainBoardContract.View mView;
    @Override
    public void setView(@NonNull MainBoardContract.View view) {
        mView = view;
    }

    @Override
    public void setModel(@NonNull MainBoardContract.Model model) {
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
    public void setLowerBound(int lowerBound) {
        mModel.setLowerBound(lowerBound);
    }

    @Override
    public void setUpperBound(int upperBound) {
        mModel.setUpperBound(upperBound);
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
    public void initViewData() {
        mView.initViewData(mModel.getLowerBound(), mModel.getUpperBound());
    }

    @Override
    public void validateData() {
        if (mModel.getLowerBound() == 0){
            mView.showZeroWarningMessage();
        }else if(mModel.getLowerBound() <= mModel.getUpperBound()){
            mView.showComments();
        } else {
            mView.showWarningMessage();
        }
    }
}

