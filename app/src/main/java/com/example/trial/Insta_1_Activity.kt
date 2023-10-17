package com.example.trial

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.easynewsvideomaker.easynewsvideomaker.merge_file.CallBackOfQuery
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegCallBack
import com.easynewsvideomaker.easynewsvideomaker.merge_file.LogMessage
import com.example.trial.databinding.ActivityInsta1Binding
import com.example.trial.databinding.DialogFileSaveBinding
import com.example.trial.databinding.ProgressBarBinding
import com.example.trial.merge_file.FFmpegQueryExtension
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

class Insta_1_Activity : AppCompatActivity() {
    lateinit var binding: ActivityInsta1Binding
    private val STORAGE_PERMISSION_CODE = 101
    lateinit var editeDialog: Dialog
//    lateinit var dialogEditBinding: DialogEditBinding
    private var mDefaultColor = 0
    var videoPath = ""
    private val PICK_VIDEO_REQUEST = 1
    private var selectedVideoUri: Uri? = null
    lateinit var progressDialog: Dialog
    var convertImagePath = ""
    lateinit var ffmpegQueryExtension: FFmpegQueryExtension
    lateinit var transparentBitmap: Bitmap
    private var userInputText: String = ""
    var textPath = "Text Scroll is here"
    var gif = " "
    var height: Int? = 0
    var width: Int? = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsta1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ffmpegQueryExtension = FFmpegQueryExtension()
        progressDialog()
        initview()
    }

    private fun progressDialog() {
        progressDialog = Dialog(this)
        var progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressDialog.setContentView(progressBarBinding.root)

        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initview() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )

        binding.frameView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, PICK_VIDEO_REQUEST)
        }

        var mediaController = MediaController(this)
        mediaController.setAnchorView(binding.vidView)
        mediaController.setMediaPlayer(binding.vidView)
        binding.vidView.setMediaController(mediaController);


        binding.cdExploreBtnReel1.setOnClickListener {
            saveFrameLayoutAsImage()

            val dialog = Dialog(this@Insta_1_Activity)
            val dialogBinding = DialogFileSaveBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnSubmit.setOnClickListener {
                var fileName = dialogBinding.edtText.text.toString()

                Log.e("TAG", "file Name: $fileName")

                progressDialog.show()

                addTextOnVideoFun(fileName)
//                mixVideo(fileName)

                dialog.dismiss()
            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }

        binding.loutLogo1.setOnClickListener {
            val Gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            Gallery_Launcher.launch(Gallery)
        }


    }

    private fun setText(userInputText: String, textview: TextView) {
        val i = IntArray(1)
        i[0] = 0
        val length = userInputText.length  // Use the length property of the String

        textview.setText("")
        val handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (i[0] < length) {
                    val c = userInputText[i[0]]
                    textview.append(c.toString())
                    i[0]++
                }
            }
        }

        val timer = Timer()
        val taskEverySplitSecond = object : TimerTask() {
            override fun run() {
                handler.sendEmptyMessage(0)
                if (i[0] == length) {
                    timer.cancel()
                }
            }
        }
        timer.schedule(taskEverySplitSecond, 1, 200)
    }

    fun saveFrameLayoutAsImage() {
        // Create a transparent Bitmap
        transparentBitmap = Bitmap.createBitmap(
            binding.frameView.getWidth(),
            binding.frameView.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        transparentBitmap.eraseColor(Color.TRANSPARENT)

        // Capture the content of the FrameLayout
        val canvas = Canvas(transparentBitmap)
        binding.frameView.draw(canvas)

        // Save the Bitmap as an image file
        val file = File(Environment.getExternalStorageDirectory(), "transparent_image.png")
        try {
            val outputStream = FileOutputStream(file)
            transparentBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        convertImagePath = file.absolutePath
        Toast.makeText(this, "image saved", Toast.LENGTH_SHORT).show()
//        // Display the captured image in an ImageView
//        imageView.setImageBitmap(transparentBitmap)
    }


    // main extension code
//    private fun addImageOnVideo(fileName: String) {
//        var outputPathBrakingNews1 =
//            Environment.getExternalStorageDirectory().path + "/Download/$fileName.mp4"
//        var tvInputPathVideo = videoPath!!
//        var tvInputPathImage = convertImagePath!!
//
//
//        val query = ffmpegQueryExtension.addImageOnVideo(
//            tvInputPathVideo,
//            tvInputPathImage,
//
//            outputPathBrakingNews1
//        )
//        CallBackOfQuery().callQuery(query, object : FFmpegCallBack {
//            override fun process(logMessage: LogMessage) {
//                Log.d("FFmpeg", logMessage.text)
//            }
//
//            override fun success() {
//
//                progressDialog.dismiss()
//                Toast.makeText(this@Insta_1_Activity, "Video Download Success", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//            override fun cancel() {
//
//                progressDialog.dismiss()
//                Toast.makeText(this@Insta_1_Activity, "Video Download Cancel", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//            override fun failed() {
//
//                progressDialog.dismiss()
//                Toast.makeText(this@Insta_1_Activity, "Video Download Fail", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })
//    }



    private fun addTextOnVideoFun(fileName: String) {

        var outputPath =
            Environment.getExternalStorageDirectory().path + "/Download/$fileName.mp4"

        var InputVideo = videoPath!!

        var imageInputDirectory = convertImagePath!!

        var textInput =binding.txtAdditionalTextReel1.text.toString()

        var RepoterOnScreenX = binding.loutbg.left.toFloat()
        var RepoterOnScreenY = binding.loutbg.top.toFloat()

        val videoWidth = getVideoWidth(InputVideo)
        val videoHeight = getVideoHeight(InputVideo)

        val query = ffmpegQueryExtension.addTextOnVideo2(
            InputVideo,
            imageInputDirectory,
            textInput,
            videoWidth,
            videoHeight,
            outputPath,
        )
        CallBackOfQuery().callQuery(query, object : FFmpegCallBack {
            override fun process(logMessage: LogMessage) {

            }

            override fun success() {

                progressDialog.dismiss()
                Toast.makeText(this@Insta_1_Activity, "Video Download Success", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun cancel() {

                progressDialog.dismiss()
                Toast.makeText(this@Insta_1_Activity, "Video Download Cancel", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun failed() {

                progressDialog.dismiss()
                Toast.makeText(this@Insta_1_Activity, "Video Download Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getVideoWidth(videoPath: String): Int {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(videoPath)
        val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
        return width?.toInt() ?: 0 // Error handling
    }

    // Function to get the video's height
    private fun getVideoHeight(videoPath: String): Int {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(videoPath)
        val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
        return height?.toInt() ?: 0 // Error handling
    }




    var Gallery_Launcher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val uri = data!!.data
            binding.logo1.setImageURI(uri)
            binding.logotext.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedVideoUri = data.data

                if (selectedVideoUri != null) {
                    // Set the selected video URI to the VideoView
                    binding.vidView.setVideoURI(selectedVideoUri)
                    binding.vidView.requestFocus()
                    // Start playing the video
                    binding.vidView.visibility = View.VISIBLE

//                    binding.imgView.visibility = View.INVISIBLE
                    binding.vidView.start()

//                    videoPath=selectedVideoUri.path
                    var videoPath = getVideoPathFromURI(selectedVideoUri!!)
                    Log.e("TAG", "onActivityResult:${videoPath} ")
                }
            }
        }

    }


    private fun getVideoPathFromURI(contentUri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            this.contentResolver.query(contentUri, projection, null, null, null)

        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            it.moveToFirst()
            videoPath = it.getString(columnIndex)
            Log.e("TAG", "getPath:${videoPath} ")
            return videoPath
        }
        // If the cursor is null, the query failed
        return contentUri.path // Fallback to using the URI's path
    }

    fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}