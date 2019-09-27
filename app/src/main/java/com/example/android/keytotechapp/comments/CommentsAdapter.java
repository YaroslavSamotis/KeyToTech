package com.example.android.keytotechapp.comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.keytotechapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private Context mContext;
    private List<Comment> mCommentsList;

    public CommentsAdapter(Context context, List<Comment> comments) {
        mContext = context;
        mCommentsList = comments;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, null);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        StringBuilder title = new StringBuilder()
                .append(mCommentsList.get(position).getId())
                .append(" ")
                .append(mCommentsList.get(position).getTitle());
        holder.mCommentText.setText(title.toString());
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }

    @Override
    public long getItemId(int position) {
        return mCommentsList.get(position).getId();
    }

    public void setCommentsList(List<Comment> comments){
        mCommentsList = comments;
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder{

        public ImageView mCommentIcon;
        public TextView mCommentText;

        CommentsViewHolder(View itemView) {
            super(itemView);
            mCommentIcon = itemView.findViewById(R.id.comment_icon);
            mCommentText = itemView.findViewById(R.id.title_header);
        }
    }
}
