package com.hackernews.reader.rest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sauyee on 1/1/18.
 */

public class CommentContent {
    private Comments content;

    private List<CommentContent> subContentList = new ArrayList<>();

    public CommentContent(Comments content) {
        this.content = content;
    }

    public Comments getContent() {
        return content;
    }

    public void addSubContent(CommentContent subContent) {
        subContentList.add(subContent);
    }

    public int subContentSize() {
        return subContentList.size();
    }

    public CommentContent getSubContent(int position) {
        return subContentList.get(position);
    }

}
