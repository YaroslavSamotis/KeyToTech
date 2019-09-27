package com.example.android.keytotechapp.mvpbase;

import android.os.Bundle;

import androidx.annotation.NonNull;

public interface BasePresenter<V extends BaseView, M extends BaseModel> {

    void setView(@NonNull V view);

    void setModel(@NonNull M model);

    void restoreState(@NonNull Bundle savedInstanceState);

    void saveState(@NonNull Bundle outState);
}
