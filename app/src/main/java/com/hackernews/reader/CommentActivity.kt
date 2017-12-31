package com.hackernews.reader

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hackernews.reader.injection.Injection
import com.hackernews.reader.rest.model.Comments
import com.hackernews.reader.rest.model.Story
import com.hackernews.reader.ui.news.NewsCommentAdapter
import com.hackernews.reader.ui.news.NewsCommentPresenter
import com.hackernews.reader.ui.news.NewsInterface
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class CommentActivity : AppCompatActivity(), NewsInterface.NewsView {

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity

    private var mNewsCommentPresenter: NewsCommentPresenter? = null
    private var mNewsStoriesAdapter: NewsCommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNewsCommentPresenter = NewsCommentPresenter(Injection.provideHackerRepository(),
                Schedulers.io(), AndroidSchedulers.mainThread())
        mNewsCommentPresenter?.attachView(this)

        mNewsStoriesAdapter = NewsCommentAdapter(null, this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mNewsStoriesAdapter?.asRecyclerAdapter()

        textview_error_message.setOnClickListener({
            getComments()
        })
    }

    private fun getComments() {
        val commentIdList = ArrayList(Arrays.asList("16007744", "16007987"))
        mNewsCommentPresenter?.getStoryComments(commentIdList)
    }

    override fun showNewsList(storyList: MutableList<Story>?) {
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
        mNewsStoriesAdapter?.setItems(commentsList)
    }

    override fun onStorySelected(commentList: MutableList<Int>?) {
    }
}
