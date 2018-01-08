package com.hackernews.reader.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hackernews.reader.R
import com.hackernews.reader.injection.Injection
import com.hackernews.reader.rest.model.CommentContent
import com.hackernews.reader.rest.model.Comments
import com.hackernews.reader.rest.model.Story
import com.hackernews.reader.ui.news.NewsInterface
import com.hackernews.reader.ui.news.NewsStoryAdapter
import com.hackernews.reader.ui.news.NewsStoryPresenter
import kotlinx.android.synthetic.main.activity_top_story.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*


class TopStoriesActivity : AppCompatActivity(), NewsInterface.NewsView, NewsInterface.NewsStoryListener {

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity

    private var mNewsPresenter: NewsInterface.StoryPresenter? = null
    private var mNewsStoriesAdapter: NewsStoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_story)
        mContext = this

        mNewsPresenter = NewsStoryPresenter(Injection.provideHackerRepository(),
                Schedulers.io(), AndroidSchedulers.mainThread())
        mNewsPresenter?.attachView(this)

        mNewsStoriesAdapter = NewsStoryAdapter(null, this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mNewsStoriesAdapter

        getTopStories()
    }

    override fun onDestroy() {
        super.onDestroy()
        mNewsPresenter?.detachView()
    }

    private fun getTopStories() {
        mNewsPresenter?.getTopStories()
    }

    override fun showNewsList(storyList: MutableList<Story>?) {
        recyclerView.visibility = View.VISIBLE
        textview_error_message.visibility = View.GONE
        mNewsStoriesAdapter?.setItems(storyList)
    }

    override fun showError(message: String?) {
        recyclerView.visibility = View.GONE
        message?.let {
            textview_error_message.setText(it)
            textview_error_message.visibility = View.VISIBLE
        }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        textview_error_message.visibility = View.GONE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        textview_error_message.visibility = View.GONE
    }

    override fun showCommentList(commentsList: MutableList<Comments>?) {
    }

    override fun onStorySelected(commentList: MutableList<String>?) {
        val bundle = Bundle()

        bundle.putStringArrayList("commentList", commentList as ArrayList<String>)

        val intent: Intent
        intent = Intent(mContext, CommentActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtras(bundle)
        mContext.startActivity(intent)
    }

    override fun addSubCommentList(commentsList: MutableList<Comments>?, commentContent: CommentContent?) {
    }
}
