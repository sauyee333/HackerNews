package com.hackernews.reader.ui.news;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hackernews.reader.R;

/**
 * Created by sauyee on 31/12/17.
 */

public class NewsStoryHolder extends RecyclerView.ViewHolder {
    final View itemView;
    final TextView textview_item_number;
    final TextView textview_story_title;
    final TextView textview_url;
    final TextView textview_score;
    final TextView textview_username;
    final TextView textview_post_time;
    final TextView textview_comments_count;

    public NewsStoryHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        textview_item_number = (TextView) itemView.findViewById(R.id.textview_item_number);
        textview_story_title = (TextView) itemView.findViewById(R.id.textview_story_title);
        textview_url = (TextView) itemView.findViewById(R.id.textview_url);
        textview_score = (TextView) itemView.findViewById(R.id.textview_score);
        textview_username = (TextView) itemView.findViewById(R.id.textview_username);
        textview_post_time = (TextView) itemView.findViewById(R.id.textview_post_time);
        textview_comments_count = (TextView) itemView.findViewById(R.id.textview_comments_count);
    }
}
