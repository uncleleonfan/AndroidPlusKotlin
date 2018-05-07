package com.leon.androidpluskotlin.ui.activity

import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.avos.avoscloud.AVObject
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.EXTRA_QUESTION
import com.leon.androidpluskotlin.contract.AddAnswerContract
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.presenter.AddAnswerPresenter
import kotlinx.android.synthetic.main.activity_add_answer.*
import javax.inject.Inject


class AddAnswerActivity : BaseActivity(), AddAnswerContract.View {

    @Inject
    lateinit var mAddAnswerPresenter: AddAnswerPresenter

    override fun getLayoutResId(): Int = R.layout.activity_add_answer


    override fun init() {
        super.init()
        val supportActionBar = supportActionBar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar.setTitle(R.string.answer)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.publish, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.publish -> publishAnswer()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun publishAnswer() {
        val title = mAnswer.text.toString().trim()
        if (title.isEmpty()) {
            Snackbar.make(mScrollView, R.string.answer_not_null, Snackbar.LENGTH_SHORT).show()
        } else {
            val answer = mAnswer.getText().toString().trim()
            val serialised = intent.getStringExtra(EXTRA_QUESTION)
            try {
                val question = AVObject.parseAVObject(serialised) as Question
                mAddAnswerPresenter.publishAnswer(answer, question)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


    override fun onPublishAnswerSuccess() {
        Snackbar.make(mScrollView, R.string.publish_success, Snackbar.LENGTH_SHORT)
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        finish()
                    }
                })
                .show()
    }

    override fun onPublishAnswerFailed() {
        Snackbar.make(mScrollView, R.string.publish_failed, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        mAddAnswerPresenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mAddAnswerPresenter.dropView()
    }
}
