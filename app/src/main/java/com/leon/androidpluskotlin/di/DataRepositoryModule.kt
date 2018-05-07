package com.leon.androidpluskotlin.di


import com.leon.androidpluskotlin.data.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataRepositoryModule {

    @Singleton
    @Binds
    fun bindsArticleDataSource(articleDataRepository: ArticleDataRepository): ArticleDataSource

    @Singleton
    @Binds
    fun bindsAnswerDataSource(answerDataRepository: AnswerDataRepository): AnswerDataSource

    @Singleton
    @Binds
    fun bindsQuestionDataSource(questionDataRepository: QuestionDataRepository): QuestionDataSource

    @Singleton
    @Binds
    fun bindsCommentDataSource(commentDataRepository: CommentDataRepository): CommentDataSource


}
