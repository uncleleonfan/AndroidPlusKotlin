package com.leon.androidpluskotlin.ui.activity


import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.TextView
import com.avos.avoscloud.AVObject
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.adapter.CommentListAdapter
import com.leon.androidpluskotlin.app.EXTRA_ANSWER
import com.leon.androidpluskotlin.contract.CommentContract
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.data.model.Comment
import com.leon.androidpluskotlin.presenter.CommentPresenter
import kotlinx.android.synthetic.main.activity_comment.*
import javax.inject.Inject


class CommentActivity : BaseActivity(), CommentContract.View {


    @Inject
    lateinit var mCommentPresenter: CommentPresenter

    lateinit var mCommentListAdapter: CommentListAdapter

    lateinit var mAnswer: Answer

    private val mTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            mSendButton.isEnabled = s.isNotEmpty()
        }
    }

    private val mOnEditorActionListener = TextView.OnEditorActionListener { v, actionId, event ->
        sendComment()
        true
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_comment
    }

    override fun init() {
        super.init()
        initActionBar()
        initBottomBar()
        initRecyclerView()
        initSwipeRefreshLayout()

        val serialised = intent.getStringExtra(EXTRA_ANSWER)
        try {
            mAnswer = AVObject.parseAVObject(serialised) as Answer
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mCommentPresenter.takeView(this)
        startLoadComments()
    }

    private fun startLoadComments() {
        mSwipeRefreshLayout.isRefreshing = true
        mCommentPresenter.loadComments(mAnswer.objectId)
    }

    private fun initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        mSwipeRefreshLayout.setOnRefreshListener { mCommentPresenter.loadComments(mAnswer.objectId) }
    }

    private fun initRecyclerView() {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mCommentListAdapter = CommentListAdapter(this)
        mRecyclerView.adapter = mCommentListAdapter
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = mRecyclerView.layoutManager as LinearLayoutManager
                    if (layoutManager.findLastVisibleItemPosition() == mCommentListAdapter.itemCount - 1) {
                        mCommentPresenter.loadMoreComments(mAnswer.objectId)
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mCommentPresenter.dropView()
    }

    private fun initBottomBar() {
        mSendEdit.setOnEditorActionListener(mOnEditorActionListener)
        mSendEdit.addTextChangedListener(mTextWatcher)
        mSendButton.isEnabled = false
        mSendButton.setOnClickListener{ sendComment()}
    }

    private fun initActionBar() {
        val supportActionBar = supportActionBar
        supportActionBar!!.setTitle(getString(R.string.comment))
        supportActionBar.setDisplayHomeAsUpEnabled(true)
    }


    private fun sendComment() {
        val commentString = mSendEdit.text.toString().trim()
        mCommentPresenter.sendComment(mAnswer, null, commentString)
        hideSoftKeyboard()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> supportFinishAfterTransition()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSendCommentSuccess() {
        Snackbar.make(mCoordinatorLayout, getString(R.string.send_success), Snackbar.LENGTH_SHORT).show()
        mSendEdit.editableText.clear()
        startLoadComments()
    }

    override fun onSendCommentFailed() {
        Snackbar.make(mCoordinatorLayout, getString(R.string.send_failed), Snackbar.LENGTH_SHORT).show()
    }

    override fun onLoadCommentsSuccess(comments: List<Comment>) {
        mSwipeRefreshLayout.isRefreshing = false
        mCommentListAdapter.replaceData(comments)
    }

    override fun onLoadCommentFailed() {
        mSwipeRefreshLayout.isRefreshing = false
        Snackbar.make(mCoordinatorLayout, getString(R.string.load_comments_failed), Snackbar.LENGTH_SHORT).show()
    }

    override fun onLoadMoreCommentsSuccess() {
        mCommentListAdapter.notifyDataSetChanged()
    }

    override fun onLoadMoreCommentFailed() {
        Snackbar.make(mCoordinatorLayout, getString(R.string.load_more_comments_failed), Snackbar.LENGTH_SHORT).show()
    }

}
