package com.leon.androidpluskotlin.data


import com.leon.androidpluskotlin.data.model.Article

interface ArticleDataSource {

    fun saveArticle(article: Article, callback: SaveCallback)

    fun getArticlesByTag(tagId: Int, callback: LoadCallback<Article>)

    fun getMoreArticlesByTag(tagId: Int, callback: LoadCallback<Article>)

    fun getArticle(position: Int, tag: Int): Article

    fun getUserSharedArticles(userId: String, callback: LoadCallback<Article>)

    fun getMoreUserSharedArticles(userId: String, callback: LoadCallback<Article>)

    fun refreshUserShareArticles(userId: String, callback: LoadCallback<Article>)

    fun getShareArticle(position: Int): Article

    fun getUserFavouredArticles(userId: String, callback: LoadCallback<Article>)

    fun getMoreUserFavouredArticles(userId: String, callback: LoadCallback<Article>)

    fun refreshUserFavouredArticles(userId: String, callback: LoadCallback<Article>)
}
