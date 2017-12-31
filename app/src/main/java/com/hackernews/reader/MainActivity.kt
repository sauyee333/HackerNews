package com.hackernews.reader

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hackernews.reader.injection.Injection
import com.hackernews.reader.rest.model.Story
import com.hackernews.reader.ui.news.NewStoryPresenter
import com.hackernews.reader.ui.news.NewsInterface
import com.hackernews.reader.ui.news.NewsStoriesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class MainActivity : AppCompatActivity(), NewsInterface.NewsView {

    private lateinit var mContext: Context
    private lateinit var mActivity: Activity

    private val mCompositeSubscription = CompositeSubscription()
    private var mGetStorySub: Subscription? = null
    private var mNewsPresenter: NewsInterface.Presenter? = null
    private var mNewsStoriesAdapter: NewsStoriesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNewsPresenter = NewStoryPresenter(Injection.provideHackerRepository(),
                Schedulers.io(), AndroidSchedulers.mainThread())
        (mNewsPresenter as NewsInterface.Presenter).attachView(this)

        mNewsStoriesAdapter = NewsStoriesAdapter(null, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mNewsStoriesAdapter

        textview_error_message.setOnClickListener({
            getTopStories()
        })
    }

    override fun onDestroy() {
        if (!mCompositeSubscription.isUnsubscribed) {
            mCompositeSubscription.unsubscribe()
        }
        super.onDestroy()
    }

    private fun getTopStories() {
        mNewsPresenter?.getTopStories()
    }

    private fun getTopStories2() {
        Injection.provideHackerRepository().getHackerTopStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<String>>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onNext(storyList: List<String>) {
                        for (storyId in storyList) {
                            getStoryDetails(storyId)
                        }
                    }
                });
    }

    private fun getStoryDetails(story: String) {
        Injection.provideHackerRepository().getHackerStory(story)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Story>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onNext(story: Story) {
//                        _Debug("getStoryDetails")
//                        _Debug("story: " + story.toString())
                    }
                });
    }

    private fun getStory() {
        Injection.provideHackerRepository().showHackerStory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<Story>>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                    override fun onNext(storyList: List<Story>) {
                    }
                });
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
        textview_error_message.visibility = View. GONE
    }
}
