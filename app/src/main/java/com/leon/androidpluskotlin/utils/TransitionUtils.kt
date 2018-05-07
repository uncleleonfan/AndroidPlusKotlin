package com.leon.androidpluskotlin.utils

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.EXTRA_ANSWER
import com.leon.androidpluskotlin.app.EXTRA_QUESTION
import com.leon.androidpluskotlin.data.model.Answer
import com.leon.androidpluskotlin.data.model.Question
import com.leon.androidpluskotlin.ui.activity.AnswerDetailActivity
import com.leon.androidpluskotlin.ui.activity.QuestionDetailActivity


fun transitionToQuestionDetail(activity: Activity, question: Question, title: View, questionDes: View) {
    val titleTransitionName = activity.resources.getString(R.string.question_title_transition)
    val titlePair = Pair.create(title, titleTransitionName)
    var activityOptionsCompat: ActivityOptionsCompat? = null
    if (questionDes.visibility == View.VISIBLE) {
        val descTransitionName = activity.resources.getString(R.string.question_des_transition)
        val descPair = Pair.create(questionDes, descTransitionName)
        activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, titlePair, descPair)
    } else {
        activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, titlePair)
    }
    val intent = Intent(activity, QuestionDetailActivity::class.java)
    intent.putExtra(EXTRA_QUESTION, question.toString())
    ActivityCompat.startActivity(activity, intent, activityOptionsCompat!!.toBundle())
}


fun transitionToAnswerDetail(activity: Activity, answer: Answer, titleView: View, answerView: View) {
    val titleTransitionName = activity.resources.getString(R.string.question_title_transition)
    val answerTransitionName = activity.resources.getString(R.string.answer_transition)
    val titlePair = Pair.create(titleView, titleTransitionName)
    val answerPair = Pair.create(answerView, answerTransitionName)
    val activityOptionsCompat = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, titlePair, answerPair)
    val intent = Intent(activity, AnswerDetailActivity::class.java)
    intent.putExtra(EXTRA_ANSWER, answer.toString())
    ActivityCompat.startActivity(activity, intent, activityOptionsCompat.toBundle())
}

