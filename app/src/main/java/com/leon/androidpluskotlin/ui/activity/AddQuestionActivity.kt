package com.leon.androidpluskotlin.ui.activity

import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.contract.AddQuestionContract
import com.leon.androidpluskotlin.presenter.AddQuestionPresenter
import kotlinx.android.synthetic.main.activity_add_question.*
import javax.inject.Inject


class AddQuestionActivity : BaseActivity(), AddQuestionContract.View {


    @Inject
    lateinit var mAddQuestionPresenter: AddQuestionPresenter

    override fun getLayoutResId(): Int = R.layout.activity_add_question

    override fun init() {
        super.init()
//        supportActionBar?.setTitle(R.string.add_question)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.apply {
            setTitle(R.string.add_question)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.publish, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.publish -> publishQuestion()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun publishQuestion() {
        val title = mQuestionTitle.text.toString().trim()
        if (title.isEmpty()) {
            Snackbar.make(mScrollView, R.string.title_not_null, Snackbar.LENGTH_SHORT).show()
        } else {
            val des = mQuestionDescription.getText().toString().trim()
            mAddQuestionPresenter.publishQuestion(title, des)
        }
    }

    override fun onResume() {
        super.onResume()
        mAddQuestionPresenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mAddQuestionPresenter.dropView()
    }

    override fun onPublishSuccess() {
        Snackbar.make(mScrollView, R.string.publish_success, Snackbar.LENGTH_SHORT)
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        finish()
                    }
                })
                .show()
    }

    override fun onPublishFailed() {
        Snackbar.make(mScrollView, R.string.publish_failed, Snackbar.LENGTH_SHORT).show()
    }
}
