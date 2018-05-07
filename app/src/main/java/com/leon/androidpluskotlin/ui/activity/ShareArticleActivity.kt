package com.leon.androidpluskotlin.ui.activity

import android.content.Intent
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem

import com.avos.avoscloud.AVUser
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.data.ArticleDataSource
import com.leon.androidpluskotlin.data.SaveCallback
import com.leon.androidpluskotlin.data.model.Article
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.utils.matchShareUrl
import com.leon.androidpluskotlin.widget.TagLayout
import kotlinx.android.synthetic.main.activity_share_article.*
import org.jetbrains.anko.startActivity

import javax.inject.Inject


class ShareArticleActivity : BaseActivity() {


    private var mTag = -1

    @Inject
    lateinit var mArticleDataSource: ArticleDataSource

    override fun getLayoutResId(): Int {
        return R.layout.activity_share_article
    }

    override fun init() {
        super.init()
        val user = AVUser.getCurrentUser(User::class.java)
        if (user == null) {
            startActivity<LoginActivity>()
            finish()
            return
        }

        val supportActionBar = supportActionBar
        supportActionBar!!.setTitle(R.string.share)
        supportActionBar.setDisplayHomeAsUpEnabled(true)

        val text = intent.getStringExtra(Intent.EXTRA_TEXT)
        mArticleUrl.text = text.matchShareUrl()

        mTagLayout.setTags(resources.getStringArray(R.array.article_category_without_hot))

        mTagLayout.setOnTagSelectedListener(object : TagLayout.OnTagSelectedListener{
            override fun onTagSelected(tag: String, position: Int) {
                mTag = position + 1
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.publish, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> startActivity<MainActivity>()
            R.id.publish -> publishArticle()
        }
        return true
    }

    override fun onBackPressed() {
        startActivity<MainActivity>()
    }

    private fun publishArticle() {
        val title = mArticleTitle.getText().toString().trim()
        if (title.isEmpty()) {
            Snackbar.make(mScrollView, getString(R.string.title_not_null), Snackbar.LENGTH_SHORT).show()
            return
        }

        if (mTag == -1) {
            Snackbar.make(mScrollView, getString(R.string.tag_not_null), Snackbar.LENGTH_SHORT).show()
            return
        }

        val article = Article()
        article.title = title
        val desc = mArticleDescription.getText().toString().trim()
        article.setDesc(desc)
        article.url = mArticleUrl.getText().toString()
        article.setTag(mTag)

        val user = AVUser.getCurrentUser(User::class.java)
        article.user = user
        mArticleDataSource.saveArticle(article, object : SaveCallback {
            override fun onSaveSuccess() {
                Snackbar.make(mScrollView, getString(R.string.publish_success), Snackbar.LENGTH_SHORT).show()
            }

            override fun onSaveFailed(errorMsg: String) {
                Snackbar.make(mScrollView, getString(R.string.publish_failed), Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}
