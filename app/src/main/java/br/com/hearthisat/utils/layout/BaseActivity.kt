package br.com.hearthisat.utils.layout

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import br.com.hearthisat.R
import br.com.hearthisat.dialog.LoadingDialog
import br.com.hearthisat.enums.ContentType
import br.com.hearthisat.extensions.*
import br.com.hearthisat.utils.general.askPermission
import com.afollestad.assent.AssentActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import pl.aprilapps.easyphotopicker.EasyImage


abstract class BaseActivity : AssentActivity(), AnkoLogger {
    var loadingDialog : LoadingDialog? = null

    companion object {
        const val ACTIVITY_RESULT_REQUEST_CODE = 98
        const val REQUEST_TAKE_GALLERY_VIDEO = 97
        const val REQUEST_TAKE_GALLERY_IMAGE = 96
        const val REQUEST_TAKE_GALLERY_AUDIO = 95
        const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
    }

    fun getContent(type: ContentType){
        when (type){
            ContentType.Camera -> {
                askPermission(Manifest.permission.CAMERA, granted = {
                    EasyImage.openCamera(this, 0)
                }, notGranted = {
                    error { "Was not possible to open the camera: Permission not granted." }
                })
            }

            ContentType.Photo -> {
                askPermission(Manifest.permission.CAMERA, granted = {
                    askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, granted = {
                        EasyImage.openChooserWithGallery(this, getString(R.string.select_image), 0)
                    }, notGranted = {
                        error { "Was not possible to open the gallery: Permission READ_EXTERNAL_STORAGE not granted." }
                    })
                }, notGranted = {
                    error { "Was not possible to open the gallery: Permission CAMERA not granted." }
                })
            }

            ContentType.Video -> {
                askPermission(Manifest.permission.CAMERA, granted = {
                    askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, granted = {
                        val intent = Intent()
                        intent.type = "video/*"
                        intent.action = Intent.ACTION_PICK
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_video)), REQUEST_TAKE_GALLERY_VIDEO)
                    }, notGranted = {
                        error { "Was not possible to open the gallery: Permission READ_EXTERNAL_STORAGE not granted." }
                    })
                }, notGranted = {
                    error { "Was not possible to open the gallery: Permission CAMERA not granted." }
                })
            }

            ContentType.Audio -> {
                askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, granted = {
                    val intent = Intent()
                    intent.type = "audio/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(intent, getString(R.string.select_audio)), REQUEST_TAKE_GALLERY_AUDIO)
                }, notGranted = {
                    error { "Was not possible to open the gallery: Permission READ_EXTERNAL_STORAGE not granted." }
                })
            }
        }
    }

    fun openKeyboard(editText : EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun closeKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window?.currentFocus?.windowToken, 0)
    }

    fun clearCache() {
        val directory = cacheDir.listFiles()
        if (directory != null) {
            for (file in directory) {
                file.delete()
            }
        }
    }

    fun startLoading(){
        loadingDialog.caseNull {
            loadingDialog = LoadingDialog(this)
        }

        loadingDialog!!.show()
    }

    fun stopLoading(){
        loadingDialog.caseNotNull {
            loadingDialog!!.dismiss()
        }
    }
}
