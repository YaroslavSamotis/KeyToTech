package com.example.android.keytotechapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.example.android.keytotechapp.comments.comments_screen.CommentsFragment;
import com.example.android.keytotechapp.comments.mainboard.MainBoardFragment;
import static com.example.android.keytotechapp.comments.comments_screen.CommentsFragment.COMMENTS_FRAGMENT_TAG;
import static com.example.android.keytotechapp.comments.mainboard.MainBoardFragment.MAIN_BOARD_FRAGMENT_TAG;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMainBoardFragment();
    }

    private void showMainBoardFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MAIN_BOARD_FRAGMENT_TAG);
        if (fragment == null) {
            fragment = MainBoardFragment.getInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_frame_layout, fragment, MAIN_BOARD_FRAGMENT_TAG).commit();
        }
        ((MainBoardFragment) fragment).setOnShowCommentListener(this::showCommentsFragment);
    }

    private void showCommentsFragment(int lowerBound, int upperBound){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(COMMENTS_FRAGMENT_TAG);
        if (fragment == null){
            fragment = CommentsFragment.getInstance(lowerBound, upperBound);
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_frame_layout, fragment, COMMENTS_FRAGMENT_TAG).addToBackStack(COMMENTS_FRAGMENT_TAG).commit();
        }
    }


}
