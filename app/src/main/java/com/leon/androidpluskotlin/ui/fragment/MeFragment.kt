package com.leon.androidpluskotlin.ui.fragment


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.SaveCallback
import com.avos.avoscloud.feedback.FeedbackAgent
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.leon.androidpluskotlin.R
import com.leon.androidpluskotlin.app.GlideApp
import com.leon.androidpluskotlin.data.model.User
import com.leon.androidpluskotlin.di.ActivityScoped
import com.leon.androidpluskotlin.ui.activity.*
import com.leon.androidpluskotlin.utils.checkPermissions
import com.leon.androidpluskotlin.utils.saveAvatar
import kotlinx.android.synthetic.main.view_me_favour.*
import kotlinx.android.synthetic.main.view_me_header.*
import kotlinx.android.synthetic.main.view_me_own.*
import kotlinx.android.synthetic.main.view_me_settings.*
import org.jetbrains.anko.startActivity
import java.io.File
import javax.inject.Inject

@ActivityScoped
class MeFragment @Inject
constructor() : BaseFragment(), View.OnClickListener {

    companion object {
        private val REQUEST_MORE_GALLERY_IMAGES = 0
        private val REQUEST_MORE_CAMERA_IMAGES = 1

        private val IMAGE_DIR = Environment.getExternalStorageDirectory().toString() +
                "/Android/data/com.leon.androidplus/images/"

    }

    private lateinit var mBottomSheetDialog: BottomSheetDialog
    private lateinit var mImageName: String
    private lateinit var mUser: User


    override fun getLayoutResId(): Int = R.layout.fragment_me

    override fun init() {
        super.init()
        mUser = AVUser.getCurrentUser(User::class.java)
        mUserName.text = mUser.username
        if (mUser.slogan != null) {
            mSlogan.text = mUser.slogan
        }
        GlideApp.with(this)
                .load(mUser.avatar)
                .transform(CircleCrop())
                .transition(DrawableTransitionOptions().crossFade())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(mAvatar)
        initListeners()
    }

    private fun initListeners() {
        mAvatar.setOnClickListener(this)
        mUserName.setOnClickListener(this)
        mSlogan.setOnClickListener(this)
        mUserShare.setOnClickListener(this)
        mUserQuestions.setOnClickListener(this)
        mUserAnswers.setOnClickListener(this)
        mUserFavourArticles.setOnClickListener(this)
        mUserFavourAnswers.setOnClickListener(this)
        mUserFavourQuestions.setOnClickListener(this)
        mFeedback.setOnClickListener(this)
        mSettings.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mAvatar -> showBottomSheet()
            R.id.mUserName -> showEditUserNameDialog()
            R.id.mSlogan -> showEditSloganDialog()
            R.id.mUserShare -> context.startActivity<UserShareArticleActivity>()
            R.id.mUserQuestions -> context.startActivity<UserQuestionsActivity>()
            R.id.mUserAnswers -> context.startActivity<UserAnswerActivity>()
            R.id.mUserFavourArticles -> context.startActivity<UserFavourArticleActivity>()
            R.id.mUserFavourAnswers -> context.startActivity<UserFavourAnswerActivity>()
            R.id.mUserFavourQuestions -> context.startActivity<UserFavourQuestionActivity>()
            R.id.mFeedback -> {
                val feedbackAgent = FeedbackAgent(context)
                feedbackAgent.startDefaultThreadActivity()
            }
            R.id.mSettings -> context.startActivity<SettingsActivity>()
            R.id.take_photo -> {
                mBottomSheetDialog.dismiss()
                if (activity.checkPermissions()) {
                    navigateToCamera()
                }
            }
            R.id.gallery -> {
                mBottomSheetDialog.dismiss()
                navigateToGallery()
            }

        }
    }


    private fun navigateToGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra("crop", true)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, REQUEST_MORE_GALLERY_IMAGES)
    }


    private fun navigateToCamera() {
        val file = File(IMAGE_DIR)
        if (!file.exists()) {
            file.mkdirs()
        }
        var uri: Uri? = null
        mImageName = System.currentTimeMillis().toString() + ".png"
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            uri = FileProvider.getUriForFile(context,
                    "com.leon.androidpluskotlin.fileProvider", file)
        } else {
            uri = Uri.fromFile(File(IMAGE_DIR + mImageName))
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, REQUEST_MORE_CAMERA_IMAGES)
    }


    private fun showBottomSheet() {
        mBottomSheetDialog = BottomSheetDialog(context)
        val root = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_sheet, null)
        root.findViewById<Button>(R.id.take_photo).setOnClickListener(this)
        root.findViewById<Button>(R.id.gallery).setOnClickListener(this)
        mBottomSheetDialog.setContentView(root)
        mBottomSheetDialog.show()
    }

    private fun showEditUserNameDialog() {
        val content = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null)
        val editText = content.findViewById<EditText>(R.id.dialog_edit)
        editText.setText(mUser.username)
        editText.setSelection(editText.getText().length)
        AlertDialog.Builder(context)
                .setTitle(R.string.edit_user_name)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm) { dialog, which ->
                    val updateUserName = editText.getText().toString().trim()
                    if (!mUser.username.equals(updateUserName)) {
                        updateUserName(updateUserName)
                    }
                }
                .setView(content)
                .show()
    }

    private fun updateUserName(userName: String) {
        mUser.username = userName
        mUser.saveInBackground(object: SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    mUserName.text = userName
                }
            }
        })
    }

    private fun showEditSloganDialog() {
        val content = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null)
        val editText = content.findViewById<EditText>(R.id.dialog_edit)
        editText.setHint(R.string.input_slogan)
        if (mUser.slogan != null) {
            editText.setText(mUser.slogan)
            editText.setSelection(editText.getText().length)
        }
        AlertDialog.Builder(context)
                .setTitle(R.string.edit_slogan)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm) { dialog, which ->
                    val updateSlogan = editText.getText().toString().trim()
                    if (mUser.slogan == null || !mUser.slogan.equals(updateSlogan)) {
                        mSlogan.setText(updateSlogan)
                        mUser.slogan = updateSlogan
                    }
                }
                .setView(content)
                .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_MORE_GALLERY_IMAGES -> if (resultCode == RESULT_OK) {
                val uri = data.data
                GlideApp.with(this).load(uri).transform(CircleCrop()).into(mAvatar)
                saveAvatar(context, uri)
            }
            REQUEST_MORE_CAMERA_IMAGES -> if (resultCode == RESULT_OK) {
                GlideApp.with(context)
                        .load(IMAGE_DIR + mImageName)
                        .transform(CircleCrop())
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(mAvatar)
                saveAvatar(mImageName, IMAGE_DIR + mImageName)

            }
        }
    }

}
