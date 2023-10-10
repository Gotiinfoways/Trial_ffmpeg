package com.example.trial

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.easynewsvideomaker.easynewsvideomaker.merge_file.CallBackOfQuery
import com.easynewsvideomaker.easynewsvideomaker.merge_file.FFmpegCallBack
import com.example.trial.merge_file.FFmpegQueryExtension
import com.easynewsvideomaker.easynewsvideomaker.merge_file.LogMessage
import com.example.trial.databinding.ActivityMainBinding
import com.example.trial.databinding.DialogFileSaveBinding
import com.example.trial.databinding.ProgressBarBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding

    private val PICK_VIDEO_REQUEST = 1
    private var selectedVideoUri: Uri? = null
    var gifPath=""
    lateinit var ffmpegQueryExtension: FFmpegQueryExtension
    var height: Int? = 0
    var width: Int? = 0
    var videoPath: String? = null
    var imagePathTxtBraking1 = ""
    var textPath = "Hello guys !! Welcome to Easy News  Maker App Create Your News"
    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        ffmpegQueryExtension = FFmpegQueryExtension()

        progressDialog()
        initView()

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

    private fun initView() {



        // Load the GIF image from resources
        val gifResourceId = R.raw.tenor // Replace with the actual resource ID
        val gifBitmap = BitmapFactory.decodeResource(resources, gifResourceId)

//        mainBinding.imgNewsLoge.setImageBitmap(gifBitmap)
        // Convert the GIF image to a Base64 string
        val base64String = convertBitmapToBase64(gifBitmap)

        gifPath=base64String
        Log.e("TAG", "ddsddd: $base64String", )
        Log.e("TAG", "initView: $gifPath", )

        Glide.with(this)
            .load(base64String)
            .into(mainBinding.imgNewsLoge);

        mainBinding.linImportVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, PICK_VIDEO_REQUEST)
        }

        // creating object of
        // media controller class
        var mediaController = MediaController(this)
        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(mainBinding.vidView)
        // sets the media player to the videoView
        mediaController.setMediaPlayer(mainBinding.vidView)
        //volume set
//        mainBinding.vidView.setOnPreparedListener { mp -> setVolumeControl(mp) }
        // sets the media controller to the videoView
        mainBinding.vidView.setMediaController(mediaController);


        //Video Export
        mainBinding.cdExploreBtn.setOnClickListener {

            saveFrameLayoutAsImage()
            val dialog = Dialog(this)
            val dialogBinding = DialogFileSaveBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnSubmit.setOnClickListener {
                var fileName = dialogBinding.edtText.text.toString()

                Log.e("TAG", "file Name: $fileName")

                progressDialog.show()

//                addImageOnVideo(fileName)
//                mixVideo(fileName)
                addGifOnVideoFun(fileName)
//                addTextOnVideoFun(fileName)
                dialog.dismiss()
            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))   //dialog box TRANSPARENT
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
//            val fragment = VideoExportFragment()
//            val transaction = requireActivity().supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.container, fragment)
//            transaction.addToBackStack(null)
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            transaction.commit()


        }
    }
    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        // Convert Bitmap to byte array
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        // Convert byte array to Base64 string
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun saveFrameLayoutAsImage() {
        // Create a transparent Bitmap
        val transparentBitmap = Bitmap.createBitmap(
            mainBinding.frameView.getWidth(),
            mainBinding.frameView.getHeight(),
            Bitmap.Config.ARGB_8888
        )
        transparentBitmap.eraseColor(Color.TRANSPARENT)

        // Capture the content of the FrameLayout
        val canvas = Canvas(transparentBitmap)
        mainBinding.frameView.draw(canvas)

        // Save the Bitmap as an image file
        val file = File(Environment.getExternalStorageDirectory(), "transparent_image.png")
        try {
            val outputStream = FileOutputStream(file)
            transparentBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        imagePathTxtBraking1 = file.absolutePath
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//        // Display the captured image in an ImageView
//        imageView.setImageBitmap(transparentBitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedVideoUri = data.data

                if (selectedVideoUri != null) {
                    // Set the selected video URI to the VideoView
                    mainBinding.vidView.setVideoURI(selectedVideoUri)
                    mainBinding.vidView.requestFocus()


                    // Start playing the video
                    mainBinding.vidView.visibility = View.VISIBLE
                    mainBinding.linVideoZoom.visibility = View.VISIBLE
                    mainBinding.linBtn.visibility = View.VISIBLE
                    mainBinding.imgView.visibility = View.INVISIBLE
                    mainBinding.vidView.start()

//                    videoPath=selectedVideoUri.path
                    var videoPath = getVideoPathFromURI(selectedVideoUri!!)
                    Log.e("TAG", "onActivityResult:${videoPath} ")
                }
            }
        }

    }

    // Helper method to get the actual path from a URI
    private fun getVideoPathFromURI(contentUri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? =
            contentResolver.query(contentUri, projection, null, null, null)

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

    var outputPathBrakingNews1: String = ""
    private fun addImageOnVideo(fileName: String) {


        outputPathBrakingNews1 =
            Environment.getExternalStorageDirectory().path + "/Download/$fileName.mp4"

        var tvInputPathVideo = videoPath!!


        var tvInputPathImage = imagePathTxtBraking1!!


        // Get the location of the TextView on the screen
        val locationOnScreen = IntArray(2)
        mainBinding.linBreakingNews.getLocationOnScreen(locationOnScreen)
        //Get the x and y coordinates


        val query = ffmpegQueryExtension.addImageOnVideo(
            tvInputPathVideo,
            tvInputPathImage,

            outputPathBrakingNews1
        )
        CallBackOfQuery().callQuery(query, object : FFmpegCallBack {
            override fun process(logMessage: LogMessage) {

            }

            override fun success() {

                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Video Download Success", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun cancel() {

                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Video Download Cancel", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun failed() {

                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Video Download Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addTextOnVideoFun(fileName: String) {


        outputPathBrakingNews1 =
            Environment.getExternalStorageDirectory().path + "/Download/$fileName.mp4"

        var tvInputPathVideo = videoPath!!


        var tvInputPathImage = imagePathTxtBraking1!!


        // Get the location of the TextView on the screen
        val locationOnScreen = IntArray(2)
        mainBinding.linBreakingNews.getLocationOnScreen(locationOnScreen)
        //Get the x and y coordinates


        val query = ffmpegQueryExtension.addTextOnVideo(
            tvInputPathVideo,
            tvInputPathImage,
            textPath,
            outputPathBrakingNews1
        )
        CallBackOfQuery().callQuery(query, object : FFmpegCallBack {
            override fun process(logMessage: LogMessage) {

            }

            override fun success() {

                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Video Download Success", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun cancel() {

                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Video Download Cancel", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun failed() {

                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Video Download Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addGifOnVideoFun(fileName: String) {


        outputPathBrakingNews1 =
            Environment.getExternalStorageDirectory().path + "/Download/$fileName.mp4"

        var tvInputPathVideo = videoPath!!


        var tvGifPath = gifPath


        // Get the location of the TextView on the screen
        val locationOnScreen = IntArray(2)
        mainBinding.linBreakingNews.getLocationOnScreen(locationOnScreen)
        //Get the x and y coordinates


        val query = ffmpegQueryExtension.addGifOnVideo(
            tvInputPathVideo,
            tvGifPath,
            textPath,
            outputPathBrakingNews1
        )
        CallBackOfQuery().callQuery(query, object : FFmpegCallBack {
            override fun process(logMessage: LogMessage) {

            }

            override fun success() {

                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Video Download Success", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun cancel() {

                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Video Download Cancel", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun failed() {

                progressDialog.dismiss()
                Toast.makeText(this@MainActivity, "Video Download Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }
}