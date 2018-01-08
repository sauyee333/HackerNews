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
import com.hackernews.reader.ui.comments.NewsCommentAdapter
import com.hackernews.reader.ui.comments.NewsCommentPresenter
import com.hackernews.reader.ui.news.NewsInterface
import kotlinx.android.synthetic.main.activity_top_story.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*


class CommentActivity : AppCompatActivity(), NewsInterface.NewsView, NewsInterface.NewsCommentListener {

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity

    private var mNewsCommentPresenter: NewsCommentPresenter? = null
    private var mNewsCommentAdapter: NewsCommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        mNewsCommentPresenter = NewsCommentPresenter(Injection.provideHackerRepository(),
                Schedulers.io(), AndroidSchedulers.mainThread())
        mNewsCommentPresenter?.attachView(this)

        mNewsCommentAdapter = NewsCommentAdapter(null, this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mNewsCommentAdapter?.asRecyclerAdapter()

        handleShowPageIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mNewsCommentPresenter?.detachView()
    }

    private fun handleShowPageIntent(intent: Intent?): Boolean {
        val ret = true
        if (intent != null) {
            val commentList = intent.getStringArrayListExtra("commentList")
            commentList?.let {
                getComments(it)
            }
        }
        return ret
    }

    private fun getComments(commentIdList: ArrayList<String>) {
        mNewsCommentPresenter?.getStoryComments(commentIdList)
    }

    private fun getComments2() {
        val commentIdList = ArrayList(Arrays.asList("16044698", "16044525", "16045246"))
        getComments(commentIdList)
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
        mNewsCommentAdapter?.setCommentList(commentsList)
    }

    override fun onCommentClicked(commentList: MutableList<String>?, commentContent: CommentContent) {
        mNewsCommentPresenter?.getSubComments(commentList, commentContent)
    }

    override fun addSubCommentList(commentsList: MutableList<Comments>?, commentContent: CommentContent?) {
        mNewsCommentAdapter?.addSubComments(commentsList, commentContent)
    }
}
