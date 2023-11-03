package com.example.trial

import android.Manifest
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.trial.databinding.ActivityAudioRecordBinding
import java.io.IOException
import java.util.concurrent.TimeUnit

class AudioRecordActivity : AppCompatActivity() {

    lateinit var audioRecordBinding: ActivityAudioRecordBinding
    private var recorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var isRecording = false
    private var recordingStartTime: Long = 0

//    private val handler = Handler()

    //    private lateinit var mediaPlayer: MediaPlayer
//    var playButton: Button? = null
//    var seekBar: SeekBar? = null
//    var durationText: TextView? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audioRecordBinding = ActivityAudioRecordBinding.inflate(layoutInflater)
        setContentView(audioRecordBinding.root)


//        playButton = findViewById(R.id.playButton)
//        seekBar = findViewById(R.id.seekBar)
//        durationText = findViewById(R.id.durationText)
        // Request necessary permissions
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            1
        )

//        val recordButton = findViewById<Button>(R.id.recordButton)
//        playButton = findViewById<Button>(R.id.playButton)

        audioRecordBinding.recordButton.setOnClickListener {
            if (!isRecording) {
                startRecording()
                audioRecordBinding.recordButton.text = "Stop Recording"
            } else {
                stopRecording()
                audioRecordBinding.recordButton.text = "Record Audio"
            }
            isRecording = !isRecording
        }

        audioRecordBinding.playButton!!.setOnClickListener {
            if (!isRecording) {
                startPlaying()
////                updateSeekBar()
//                playButton.text = "Stop"
            }
        }

    }

    private fun startRecording() {
        recorder = MediaRecorder()
        recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        val filePath = "${Environment.getExternalStorageDirectory()}/audio_record.3gp"
        recorder?.setOutputFile(filePath)

        try {
            recorder?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        recorder?.start()

        recordingStartTime = System.currentTimeMillis()

        handler.post(updateDuration)
    }

    private fun stopRecording() {
        recorder?.stop()
        recorder?.release()
        recorder = null
        handler.removeCallbacks(updateDuration)
    }

    private fun startPlaying() {
        mediaPlayer = MediaPlayer()
        val filePath = "${Environment.getExternalStorageDirectory()}/audio_record.3gp"

        try {
            mediaPlayer?.setDataSource(filePath)
            mediaPlayer?.prepare()
            mediaPlayer?.start()

            updateSeekBar()
            audioRecordBinding.playButton!!.text = "Stop Audio"


        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaPlayer?.setOnCompletionListener {
            stopPlaying()
            audioRecordBinding.playButton!!.text = "Play Audio"
        }


        audioRecordBinding.seekBar!!.max = mediaPlayer!!.duration

//        mediaPlayer.setOnCompletionListener {
//            playButton.text = "Play"
//        }

        audioRecordBinding.seekBar!!.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun stopPlaying() {
        mediaPlayer?.release()
//        mediaPlayer!!.pause()
        mediaPlayer = null
    }

    private val updateDuration = object : Runnable {
        override fun run() {
            val durationText = findViewById<TextView>(R.id.durationText)
            val currentTime = System.currentTimeMillis()
            val elapsedMillis = currentTime - recordingStartTime
            val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedMillis)
            val minutes = seconds / 60
            val remainingSeconds = seconds % 60
            durationText.text = "Duration: $minutes:${String.format("%02d", remainingSeconds)}"
            handler.postDelayed(this, 1000)
        }
    }

//    private fun updateSeekBar() {
//        handler.postDelayed(object : Runnable {
//            override fun run() {
//                audioRecordBinding.seekBar.progress = mediaPlayer!!.currentPosition
//                updateDurationText(mediaPlayer!!.currentPosition)
//                if (mediaPlayer!!.isPlaying) {
//                    handler.postDelayed(this, 1000)
//                }
//            }
//        }, 0)
//    }

    private fun updateSeekBar() {
        if (mediaPlayer != null) { // Check if mediaPlayer is not null
            handler.postDelayed(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) { // Check mediaPlayer is still not null
                        audioRecordBinding.seekBar.progress = mediaPlayer!!.currentPosition
                        updateDurationText(mediaPlayer!!.currentPosition)
                        if (mediaPlayer!!.isPlaying) {
                            handler.postDelayed(this, 1000)
                        }
                    }
                }
            }, 0)
        }
    }

    private fun updateDurationText(duration: Int) {
        val minutes = duration / 60000
        val seconds = (duration % 60000) / 1000
        audioRecordBinding.durationText!!.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.release()
    }
}