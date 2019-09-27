package com.example.android.keytotechapp.comments.mainboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.keytotechapp.R;
import com.example.android.keytotechapp.mvpbase.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class MainBoardFragment extends BaseFragment<MainBoardContract.Presenter> implements MainBoardContract.View, View.OnClickListener {

    public static final String MAIN_BOARD_FRAGMENT_TAG = "MAIN_BOARD_FRAGMENT_TAG";

    private EditText mLowerBound;
    private EditText mUpperBound;
    private Button mShowComments;

    private MainBoardContract.Presenter mPresenter;
    private OnShowCommentsClick mShowCommentListener;
    @NonNull
    @Override
    protected MainBoardContract.Presenter createPresenter() {
        MainBoardPresenter presenter = new MainBoardPresenter();
        presenter.setView(this);
        MainBoardModel model = ViewModelProviders.of(this).get(MainBoardModel.class);
        presenter.setModel(model);
        return presenter;
    }

    public static MainBoardFragment getInstance(){
        return new MainBoardFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState, MainBoardContract.Presenter presenter) {
        super.onCreate(savedInstanceState, presenter);
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_board, null);
        initViews(view);
        if (savedInstanceState != null){
            mPresenter.initViewData();
        }
        return view;
    }

    private void initViews(View view){
        mShowComments = view.findViewById(R.id.show_comments);
        mShowComments.setEnabled(false);
        mShowComments.setOnClickListener(this);

        mLowerBound = view.findViewById(R.id.lower_bound);
        mLowerBound.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!getLowerBound().isEmpty()) {
                    mPresenter.setLowerBound(getLowerBoundNumber());
                    mShowComments.setEnabled(!getUpperBound().isEmpty());
                } else {
                    mShowComments.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mUpperBound = view.findViewById(R.id.upper_bound);
        mUpperBound.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!getUpperBound().isEmpty()) {
                    mPresenter.setUpperBound(getUpperBoundNumber());
                    mShowComments.setEnabled(!getLowerBound().isEmpty());
                } else {
                    mShowComments.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_comments){
            mPresenter.validateData();
        }
    }

    private int getLowerBoundNumber(){
        String number = getLowerBound();
        return number.isEmpty() ? -1 : Integer.parseInt(number);
    }

    private int getUpperBoundNumber(){
        String number = getUpperBound();
        return number.isEmpty() ? -1 : Integer.parseInt(number);
    }

    private String getLowerBound(){
        return mLowerBound.getText().toString();
    }

    private String getUpperBound(){
        return mUpperBound.getText().toString();
    }

    @Override
    public void initViewData(int lowerBound, int upperBound) {
        mLowerBound.setText(String.valueOf(lowerBound));
        mUpperBound.setText(String.valueOf(upperBound));
    }

    @Override
    public void showWarningMessage() {
        Toast.makeText(getActivity(), R.string.main_board_warning_message, Toast.LENGTH_SHORT).show();
        mUpperBound.requestFocus();
    }

    @Override
    public void showZeroWarningMessage() {
        Toast.makeText(getActivity(), R.string.main_board_zero_value_warning_message, Toast.LENGTH_SHORT).show();
        mLowerBound.requestFocus();
    }

    @Override
    public void showComments() {
        if (mShowCommentListener != null) {
            mShowCommentListener.showComments(mPresenter.getLowerBound(), mPresenter.getUpperBound());
        }
    }

    public interface OnShowCommentsClick{
        void showComments(int lowerBound, int upperBound);
    }

    public void setOnShowCommentListener(OnShowCommentsClick listener){
        mShowCommentListener = listener;
    }
}
