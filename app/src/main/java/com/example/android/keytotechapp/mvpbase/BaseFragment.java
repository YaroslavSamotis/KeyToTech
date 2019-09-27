package com.example.android.keytotechapp.mvpbase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

    private P mPresenter;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (savedInstanceState != null) {
            mPresenter.restoreState(savedInstanceState);
        }
        onCreate(savedInstanceState, mPresenter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mPresenter.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    protected void onCreate(Bundle savedInstanceState, P presenter) {

    }

    protected P getPresenter() {
        return mPresenter;
    }

    @NonNull
    protected abstract P createPresenter();

}
