package com.example.trial

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.trial.databinding.ActivityAudioRecordBinding
import java.io.IOException

class AudioRecordActivity : AppCompatActivity() {

    lateinit var  audioRecordBinding: ActivityAudioRecordBinding
    private val STORAGE_PERMISSION_CODE = 1

    private var mediaRecorder: MediaRecorder? = null
    private val handler = Handler()
    private var recordingStartTime: Long = 0

    private lateinit var recordingDurationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audioRecordBinding= ActivityAudioRecordBinding.inflate(layoutInflater)
        setContentView(audioRecordBinding.root)

        recordingDurationTextView = findViewById(R.id.recordingDurationTextView)

        val recordButton = findViewById<Button>(R.id.recordButton)
        val stopButton = findViewById<Button>(R.id.stopButton)

        recordButton.setOnClickListener {
            if (checkPermission()) {
                startRecording()
            } else {
                requestStoragePermission()
            }
        }

        stopButton.setOnClickListener {
            stopRecording()
        }
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder()
        val audioFilePath = Environment.getExternalStorageDirectory().absolutePath + "/my_audio.3gp"

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder?.setOutputFile(audioFilePath)


        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            recordingStartTime = System.currentTimeMillis()

            // Start a runnable to update the recording duration
            handler.postDelayed(updateDuration, 1000)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.stop()
        mediaRecorder?.reset()
        mediaRecorder?.release()
        mediaRecorder = null
        handler.removeCallbacks(updateDuration)
    }

    private val updateDuration = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            val duration = currentTime - recordingStartTime
            val seconds = duration / 1000
            recordingDurationTextView.text = "Recording Duration: $seconds seconds"
            handler.postDelayed(this, 1000) // Update every second
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            STORAGE_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording()
            } else {
                // Handle permission denied
            }
        }
    }
}