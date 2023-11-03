package com.example.trial

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import com.example.trial.databinding.ActivityRecordingScreenBinding
import com.example.trial.databinding.SaveAudioDialogboxBinding
import java.io.File
import java.io.IOException


private const val LOG_TAG = "AudioRecordTest"
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
class Recording_Screen : AppCompatActivity(){
    lateinit var recordingScreenBinding: ActivityRecordingScreenBinding

    private var fileName: String = ""

    //    private var recordButton: RecordButton? = null
    private var recorder: MediaRecorder? = null

    //    private var playButton: PlayButton? = null
    private var player: MediaPlayer? = null

    private var isRecording = false
    private var isPlaying = false

    private lateinit var timer: Timer
    private var outputFile: File? = null

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecording()
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startPlaying() {
        timer.start()
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }
        }
        recordingScreenBinding.play.setBackgroundResource(R.drawable.play)
        Toast.makeText(this, "start playing", Toast.LENGTH_SHORT).show()
        isPlaying = true


    }

    private fun stopPlaying() {
        timer.pause()

        player?.release()
        player = null
        isPlaying = false
        recordingScreenBinding.play.setBackgroundResource(R.drawable.pause)
        Toast.makeText(this, "stop playing", Toast.LENGTH_SHORT).show()
    }

    private fun startRecording() {
        timer.start()

        val audioName = "my_audio"
        outputFile = File(externalCacheDir, "$audioName.3gp")

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(outputFile!!.absolutePath)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }

            start()
        }
        recordingScreenBinding.start.setBackgroundResource(R.drawable.mute)
        Toast.makeText(this, "start recording", Toast.LENGTH_SHORT).show()
        isRecording = true
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        timer.Stop()
        recordingScreenBinding.start.setBackgroundResource(R.drawable.microphone_round)
        Toast.makeText(this, "stop recording", Toast.LENGTH_SHORT).show()
        isRecording = false

    }

    private fun getAudioDuration(audioFile: File): Long {
        val mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(audioFile.absolutePath)
            mediaPlayer.prepare()
            return mediaPlayer.duration.toLong() // Duration in milliseconds
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            mediaPlayer.release()
        }
        return 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recordingScreenBinding = ActivityRecordingScreenBinding.inflate(layoutInflater)
        setContentView(recordingScreenBinding.root)

        // Record to the external cache directory for visibility
        fileName = "${externalCacheDir!!.absolutePath}/audiorecordtest.3gp"

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        val startButton = findViewById<AppCompatButton>(R.id.start)
        val playButton = findViewById<AppCompatButton>(R.id.play)
        val timerTextView = findViewById<TextView>(R.id.txttimer)

        // Set click listeners for the buttons
        startButton.setOnClickListener {
            if (!isPlaying){
            onRecord(!isRecording)}else{
                Toast.makeText(this, "Audio Is Playing", Toast.LENGTH_SHORT).show()
            }
        }

        playButton.setOnClickListener {
            if (!isRecording) {
                onPlay(!isPlaying)
            }else{
                Toast.makeText(this, "Recording Progress", Toast.LENGTH_SHORT).show()
            }
        }

        recordingScreenBinding.audiodone.setOnClickListener {
            onSaveAudio()
        }

        timer = Timer(object : Timer.OnTimerTickListener {
            override fun onTimertick(duration: String) {
                runOnUiThread {
                    timerTextView.text = duration
                }
            }
        })
    }

    private fun onSaveAudio() {
        val dialog = Dialog(this)
        val dialogBinding : SaveAudioDialogboxBinding = SaveAudioDialogboxBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val edtfilename = dialog.findViewById<EditText>(R.id.edtfilename)
        val btnaudiosave = dialog.findViewById<AppCompatButton>(R.id.btnaudiosave)
        val btnaudiocancel = dialog.findViewById<AppCompatButton>(R.id.btnaudiocancel)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnaudiosave.setOnClickListener {
            val audioName = edtfilename.text.toString().trim()
            if (audioName.isNotEmpty()) {
                val outputFileName = "$audioName.3gp"
                val outputFile = File(externalCacheDir, outputFileName)

                if (outputFile.exists()) {
                    Toast.makeText(this, "File with the same name already exists", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        // Copy the recorded audio file to the new location with the desired name
                        outputFile.createNewFile()
                        outputFile.outputStream().use { output ->
                            val inputFile = File(fileName)
                            inputFile.inputStream().copyTo(output)
                        }

                        Toast.makeText(this, "Audio saved as: $outputFileName", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()

                        Log.e("TAG", "onSaveAudio: $outputFile")

//                        val intent = Intent(this, Diary_Activity::class.java)
//                        intent.putExtra("audioFilePath", outputFile.absolutePath)
//                        intent.putExtra("audioFileName", audioName) // Pass the desired file name as an extra
//                        startActivity(intent)
                    } catch (e: IOException) {
                        Toast.makeText(this, "Error saving audio", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a valid file name", Toast.LENGTH_SHORT).show()
            }
        }

        btnaudiocancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onStop() {
        super.onStop()
        recorder?.release()
        recorder = null
        player?.release()
        player = null
    }

}