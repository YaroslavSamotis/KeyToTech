package com.example.android.keytotechapp.comments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.keytotechapp.R;
import com.example.android.keytotechapp.mvpbase.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsFragment extends BaseFragment<CommentsFragmentContract.Presenter> implements CommentsFragmentContract.View {

    public static final String COMMENTS_FRAGMENT_TAG = "COMMENTS_FRAGMENT_TAG";
    public static final String EXTRAS_LOWER_BOUND = "EXTRAS_LOWER_BOUND";
    public static final String EXTRAS_UPPER_BOUND = "EXTRAS_UPPER_BOUND";
    public static final int LOAD_COMMENTS_PER_ONCE = 12;

    private RecyclerView mRecyclerView;
    private CommentsAdapter mCommentsAdapter;
    private CommentsFragmentContract.Presenter mPresenter;
    private ProgressBar mProgressBar;

    private boolean mIsLoading = true;
    private final int mVisibleThreshold = 3;
    private int mLastVisibleItem, mTotalItemCount;
    private int mPageNumber;
    private LinearLayoutManager mLayoutManager;


    @NonNull
    @Override
    protected CommentsFragmentContract.Presenter createPresenter() {
        CommentsPresenter presenter = new CommentsPresenter();
        presenter.setView(this);
        CommentsModel model = ViewModelProviders.of(this).get(CommentsModel.class);
        presenter.setModel(model);
        return presenter;
    }

    public static CommentsFragment getInstance(int lowerBound, int upperBound){
        CommentsFragment frg = new CommentsFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRAS_LOWER_BOUND, lowerBound);
        args.putInt(EXTRAS_UPPER_BOUND, upperBound);
        frg.setArguments(args);
        return frg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState, CommentsFragmentContract.Presenter presenter) {
        super.onCreate(savedInstanceState, presenter);
        mPresenter = presenter;
        if (savedInstanceState == null) {
            mPresenter.initData(getArguments());
        } else {
            mPresenter.restoreState(savedInstanceState);
        }
        mPageNumber = mPresenter.getLowerBound();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.comments_fragment, null);
        initViews(view);
        setListeners();
        if (savedInstanceState == null) {
            mPresenter.requestDataFromServer();
        }
        return view;
    }

    private void initViews(View view){
        mRecyclerView = view.findViewById(R.id.comments_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mCommentsAdapter = new CommentsAdapter(getContext(), mPresenter.getComments().isEmpty() ? new ArrayList<>() : mPresenter.getComments());
        mRecyclerView.setAdapter(mCommentsAdapter);

        mProgressBar = view.findViewById(R.id.progress_bar);
    }

    private void setListeners() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalItemCount = mLayoutManager.getItemCount();
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if(!mIsLoading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold) && mPageNumber < mPresenter.getUpperBound()){
                    mPresenter.getMoreData(mPageNumber);
                    mIsLoading = true;
                }
            }
        });
    }

    @Override
    public void setDataToRecyclerView(List<Comment> comments, int pageNumber) {
        mCommentsAdapter.setCommentsList(comments);
        mCommentsAdapter.notifyDataSetChanged();
        mIsLoading = false;
        mPageNumber = pageNumber+1;
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResponseFailure(Throwable t) {
        Toast.makeText(getActivity(), R.string.comments_download_failure_message, Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Override
    public void showMaximumQuantityMessage(int quantiity) {
        Toast.makeText(getActivity(), getString(R.string.comments_maximum_available_comments) + quantiity, Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
    }
}
