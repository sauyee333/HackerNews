package com.hackernews.reader.ui.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackernews.reader.R;
import com.hackernews.reader.rest.model.Story;

import java.util.List;

/**
 * Created by sauyee on 31/12/17.
 */

public class NewsStoriesAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private List<Story> items;
    private final Context context;

    public NewsStoriesAdapter(List<Story> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_stories, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Story item = items.get(position);
        holder.textview_item_number.setText(Integer.toString(position + 1));
        holder.textview_story_title.setText(item.getTitle());
        holder.textview_url.setText(item.getUrl());
        holder.textview_score.setText(item.getScore() + " " + context.getString(R.string.points));
        holder.textview_username.setText(item.getBy());
//        holder.textview_post_time.setText(item.getTime());
        List<Integer> commentList = item.getKids();
        if (commentList != null) {
            int commentSize = commentList.size();
            if (commentSize > 0) {
                holder.textview_comments_count.setText(commentSize + " " + context.getString(R.string.comments));
                holder.textview_comments_count.setVisibility(View.VISIBLE);
            } else {
                holder.textview_comments_count.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void setItems(List<Story> storyList) {
        this.items = storyList;
        notifyDataSetChanged();
    }
}
