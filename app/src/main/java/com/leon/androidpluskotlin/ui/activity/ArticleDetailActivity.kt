package com.leon.androidpluskotlin.ui.activity

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUser
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.EXTRA_ARTICLE
import com.leon.androidpluskotlin.data.model.Article
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.data.model.UserArticleFavourMap
import kotlinx.android.synthetic.main.activity_article_detail.*


class ArticleDetailActivity : BaseActivity() {


    private var isFavoured = false
    private lateinit var mArticle: Article

    override fun getLayoutResId(): Int = R.layout.activity_article_detail


    override fun init() {
        super.init()
        initArticle()
        initFavour()
        initWebView()
    }

    private fun initArticle() {
        val intent = intent
        val articleString = intent.getStringExtra(EXTRA_ARTICLE)
        try {
            mArticle = AVObject.parseAVObject(articleString) as Article
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun initFavour() {
        val user = AVUser.getCurrentUser(User::class.java)
        isFavoured = user.isFavouredArticle(mArticle.objectId)
        mFavour.isSelected = isFavoured
        mFavour.setOnClickListener {
            isFavoured = !isFavoured
            mFavour.isSelected = isFavoured
            val user = AVUser.getCurrentUser(User::class.java)
            if (isFavoured) {
                UserArticleFavourMap.buildFavourMap(user, mArticle)
            } else {
                UserArticleFavourMap.breakFavourMap(user, mArticle)
            }
        }
    }

    private fun initWebView() {
        mWebView.loadUrl(mArticle.url)
        mWebView.webViewClient = WebViewClient()
        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE)
                }
            }
        }
    }

}
