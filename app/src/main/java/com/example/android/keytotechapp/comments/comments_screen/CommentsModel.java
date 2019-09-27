package com.example.android.keytotechapp.comments.comments_screen;

import android.app.Application;
import android.os.Bundle;

import com.example.android.keytotechapp.comments.networking.NetworkService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.keytotechapp.comments.comments_screen.CommentsFragment.EXTRAS_LOWER_BOUND;
import static com.example.android.keytotechapp.comments.comments_screen.CommentsFragment.EXTRAS_UPPER_BOUND;
import static com.example.android.keytotechapp.comments.comments_screen.CommentsFragment.LOAD_COMMENTS_PER_ONCE;

public class CommentsModel extends AndroidViewModel implements CommentsFragmentContract.Model {

    public static final String BUNDLE_LOWER_BOUND = "BUNDLE_LOWER_BOUND";
    public static final String BUNDLE_UPPER_BOUND = "BUNDLE_UPPER_BOUND";
    public static final String BUNDLE_COMMENTS_LIST = "BUNDLE_COMMENTS_LIST";

    private int mLowerBound;
    private int mUpperBound;
    private List<Comment> mCommentsList = new ArrayList<>();
    private int mCounter = 0;
    private MutableLiveData<Integer> mCommentsQuantity = new MutableLiveData<>();
    private int mCommentsQuantityValue;

    public CommentsModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void saveState(Bundle state) {
        state.putInt(BUNDLE_LOWER_BOUND, mLowerBound);
        state.putInt(BUNDLE_UPPER_BOUND, mUpperBound);
        state.putSerializable(BUNDLE_COMMENTS_LIST, (Serializable) mCommentsList);
    }

    @Override
    public void restoreState(Bundle state) {
        mLowerBound = state.getInt(BUNDLE_LOWER_BOUND);
        mUpperBound = state.getInt(BUNDLE_UPPER_BOUND);
        mCommentsList = (List<Comment>) state.getSerializable(BUNDLE_COMMENTS_LIST);
    }

    @Override
    public int getLowerBound() {
        return mLowerBound;
    }

    @Override
    public int getUpperBound() {
        return mUpperBound;
    }

    @Override
    public MutableLiveData<Integer> getCommentsQuantity() {
        return mCommentsQuantity;
    }

    @Override
    public void initData(Bundle args) {
        mLowerBound = args.getInt(EXTRAS_LOWER_BOUND);
        mUpperBound = args.getInt(EXTRAS_UPPER_BOUND);
        checkCommentsQuantity();
    }

    @Override
    public void getNewCommentsList(final OnFinishedListener onFinishedListener, int loadFrom) {
        int commentsToLoad = loadFrom+LOAD_COMMENTS_PER_ONCE <= mUpperBound ? loadFrom+LOAD_COMMENTS_PER_ONCE : mUpperBound;
        mCounter = loadFrom-1;
        for (int i = loadFrom; i <= commentsToLoad; i++) {
            Call<Comment> call = NetworkService.getInstance().getJSONApi().getCommentWithID(i);
            call.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if (response.body() != null) {
                        mCommentsList.add(response.body());
                        mCounter++;
                        if (mCounter == commentsToLoad) {
                            Collections.sort(mCommentsList);
                            onFinishedListener.onFinished(mCommentsList, mCounter);
                        }
                    } else {
                        Collections.sort(mCommentsList);
                        onFinishedListener.onFinished(mCommentsList, mCounter);
                        mCommentsQuantity.setValue(mCommentsQuantityValue);
                    }
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    onFinishedListener.onFailure(t);
                }
            });
        }
    }

    private void checkCommentsQuantity() {
        Call<List<Comment>> call = NetworkService.getInstance().getJSONApi().getComments();
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.body() != null) {
                    mCommentsQuantityValue = response.body().size();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }

    @Override
    public List<Comment> getCommentsList() {
        return mCommentsList;
    }
}
