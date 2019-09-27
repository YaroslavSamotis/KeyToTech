package com.example.android.keytotechapp.comments.mainboard;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class MainBoardModel extends AndroidViewModel implements MainBoardContract.Model {

    public static final String BUNDLE_LOWER_BOUND = "BUNDLE_LOWER_BOUND";
    public static final String BUNDLE_UPPER_BOUND = "BUNDLE_UPPER_BOUND";

    private int mLowerBound;
    private int mUpperBound;

    public MainBoardModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void saveState(Bundle state) {
        state.putInt(BUNDLE_LOWER_BOUND, mLowerBound);
        state.putInt(BUNDLE_UPPER_BOUND, mUpperBound);
    }

    @Override
    public void restoreState(Bundle state) {
        mLowerBound = state.getInt(BUNDLE_LOWER_BOUND);
        mUpperBound = state.getInt(BUNDLE_UPPER_BOUND);
    }

    @Override
    public void setLowerBound(int lowerBound) {
        mLowerBound = lowerBound;
    }

    @Override
    public void setUpperBound(int upperBound) {
        mUpperBound = upperBound;
    }


    @Override
    public int getLowerBound() {
        return mLowerBound;
    }

    @Override
    public int getUpperBound() {
        return mUpperBound;
    }

}
