package com.hackernews.reader.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackernews.reader.R;
import com.hackernews.reader.rest.model.CommentContent;
import com.hackernews.reader.rest.model.Comments;

import java.util.List;

import cn.okayj.axui.preordertreeadapter.PreOrderTreeAdapter;

/**
 * Created by sauyee on 31/12/17.
 */

public class NewsCommentAdapter extends PreOrderTreeAdapter<CommentContent,
        NewsCommentAdapter.NewsCommentHolder> {
    private List<Comments> items;
    private CommentContent rootNode;
    private final Context context;
    private NewsInterface.NewsCommentListener listener;

    public NewsCommentAdapter(List<Comments> items, Context context, NewsInterface.NewsCommentListener listener) {
        this.items = items;
        this.context = context;
        this.listener = listener;
        rootNode = new CommentContent(null);
        updateNodes();
    }

    @Override
    protected CommentContent root() {
        return rootNode;
    }

    @Override
    protected boolean ignoreRoot() {
        return true;
    }

    @Override
    protected int getChildSize(CommentContent commentContent) {
        return commentContent.subContentSize();
    }

    @Override
    protected CommentContent getChild(CommentContent parent, int childPosition) {
        return parent.getSubContent(childPosition);
    }

    @Override
    protected int getViewTypeCount() {
        return 1;
    }

    @Override
    protected int getViewType(CommentContent commentContent, int depth) {
        return 0;
    }

    @Override
    protected NewsCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_comments, parent, false);
        return new NewsCommentHolder(view);
    }

    @Override
    protected void onBindViewHolder(NewsCommentHolder holder, final CommentContent commentContent) {
        final Comments comments = commentContent.getContent();
        if (comments != null) {
            holder.textview_comment_details.setText(comments.getText());
//            holder.textview_comment_time.setText(comments.getTime());
            holder.textview_comment_username.setText(comments.getBy());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        List<String> commentList = comments.getKids();
                        if (commentList != null && commentList.size() > 0) {
                            listener.onCommentClicked(commentList, commentContent);
                        }
                    }
                }
            });
        }
    }

    private void updateNodes() {
        if (items != null && items.size() > 0) {
            for (Comments comments : items) {
                CommentContent commentContent = new CommentContent(comments);
                rootNode.addSubContent(commentContent);
            }
        }
    }

    public void setCommentList(List<Comments> commentsList) {
        this.items = commentsList;
        updateNodes();
        notifyDataSetChanged();
    }

    public void addSubComments(List<Comments> commentsList, CommentContent commentContent) {
        for (Comments comments : commentsList) {
            CommentContent subCommentContent = new CommentContent(comments);
            commentContent.addSubContent(subCommentContent);
        }
        notifyDataSetChanged();
    }

    public class NewsCommentHolder extends cn.okayj.axui.viewholder.ViewHolder<CommentContent> {
        final View itemView;
        final TextView textview_comment_username;
        final TextView textview_comment_time;
        final TextView textview_comment_details;

        public NewsCommentHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textview_comment_username = (TextView) itemView.findViewById(R.id.textview_comment_username);
            textview_comment_time = (TextView) itemView.findViewById(R.id.textview_comment_time);
            textview_comment_details = (TextView) itemView.findViewById(R.id.textview_comment_details);
        }
    }
}
