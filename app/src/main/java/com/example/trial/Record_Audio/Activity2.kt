package com.example.trial.Record_Audio

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trial.R
import com.example.trial.databinding.Activity2Binding
import java.io.IOException
import java.util.concurrent.TimeUnit

class Activity2 : AppCompatActivity() {
    lateinit var recodingBinding: Activity2Binding

    // creating a variable for media recorder object class.
    private var mRecorder: MediaRecorder? = null

    // creating a variable for mediaplayer class
    private var mPlayer: MediaPlayer? = null

    // string variable is created for storing a file name
    private var mFileName: String? = null



    private var isRecording = false
    private var isPlaying = false

    private var recordingStartTime: Long = 0
    private var startTime = 0.0
    private var finalTime = 0.0
    var oneTimeOnly = 0


    private val myHandler = Handler();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recodingBinding= Activity2Binding.inflate(layoutInflater)
        setContentView(recodingBinding.root)

        initView()
    }

    private fun initView() {
        recodingBinding.btnStop.setBackgroundColor(resources.getColor(R.color.grey));
        recodingBinding.btnPlay.setBackgroundColor(resources.getColor(R.color.grey));
        recodingBinding.btnStopPlay.setBackgroundColor(resources.getColor(R.color.grey))

        // Set up the SeekBar listener to control zoom
        recodingBinding.seekbar
            .setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {

                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

        recodingBinding.btnRecord.setOnClickListener(View.OnClickListener { // start recording method will
            // start the recording of audio.
            startRecording()
        })
        recodingBinding.btnStop.setOnClickListener { // pause Recording method will
            // pause the recording of audio.
            pauseRecording()
        }
        recodingBinding.btnPlay.setOnClickListener { // play audio method will play
            // the audio which we have recorded
            playAudio()
                finalTime = mPlayer!!.getDuration().toDouble()
//                startTime = mPlayer!!.getCurrentPosition().toDouble()

                if (oneTimeOnly === 0) {
                    recodingBinding.seekbar.setMax(finalTime as Int)
                    oneTimeOnly = 1
                }

                recodingBinding.durationTextView1.setText(
                    String.format(
                        "%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(finalTime as Long),
                        TimeUnit.MILLISECONDS.toSeconds(finalTime as Long) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime as Long))
                    )
                )

                recodingBinding.durationTextView2.setText(
                    String.format(
                        "%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(startTime as Long),
                        TimeUnit.MILLISECONDS.toSeconds(startTime as Long) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime as Long))
                    )
                )

                recodingBinding.seekbar.setProgress(startTime as Int)
                myHandler.postDelayed(updateDuration, 100)
        }
        recodingBinding.btnStopPlay.setOnClickListener { // pause play method will
            // pause the play of audio
            pausePlaying()
        }

        seekbar()

        recodingBinding.btnSubmit.setOnClickListener {


            Toast.makeText(this, "Your data is Change", Toast.LENGTH_SHORT).show()

        }
    }

    private val updateDuration = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            val duration = currentTime - recordingStartTime
            val seconds = duration / 1000
//            recordingDurationTextView.text = "Recording Duration: $seconds seconds"
            myHandler.postDelayed(this, 1000) // Update every second
        }
    }
    private fun seekbar() {

    }

    private fun startRecording() {
        // check permission method is used to check
        // that the user has granted permission
        // to record and store the audio.


        // setbackgroundcolor method will change
        // the background color of text view.
        recodingBinding.btnStop.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnRecord.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnPlay.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnStopPlay.setBackgroundColor(resources.getColor(R.color.grey))

        // we are here initializing our filename variable
        // with the path of the recorded audio file.
        mFileName = Environment.getExternalStorageDirectory().absolutePath
        mFileName += "/AudioRecording.3gp"

        // below method is used to initialize
        // the media recorder class
        mRecorder = MediaRecorder()

        // below method is used to set the audio
        // source which we are using a mic.
        mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)

        // below method is used to set
        // the output format of the audio.
        mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)

        // below method is used to set the
        // audio encoder for our recorded audio.
        mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        // below method is used to set the
        // output file location for our recorded audio
        mRecorder!!.setOutputFile(mFileName)
        try {
            // below method will prepare
            // our audio recorder class
            mRecorder!!.prepare()
        } catch (e: IOException) {
            Log.e("TAG", "prepare() failed")
        }
        // start method will start
        // the audio recording.
        mRecorder!!.start()
        recodingBinding.idTVstatus.text = "Recording Started"
    }

    fun playAudio() {
        recodingBinding.btnStop.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnRecord.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnPlay.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnStopPlay.setBackgroundColor(resources.getColor(R.color.purple_200))

        // for playing our recorded audio
        // we are using media player class.
        mPlayer = MediaPlayer()
        try {
            // below method is used to set the
            // data source which will be our file name
            mPlayer!!.setDataSource(mFileName)

            // below method will prepare our media player
            mPlayer!!.prepare()

            // below method will start our media player.
            mPlayer!!.start()
            recodingBinding.idTVstatus.text = "Recording Started Playing"
        } catch (e: IOException) {
            Log.e("TAG", "prepare() failed")
        }
    }

    fun pauseRecording() {
        recodingBinding.btnStop.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnRecord.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnPlay.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnStopPlay.setBackgroundColor(resources.getColor(R.color.purple_200))

        // below method will stop
        // the audio recording.
        mRecorder!!.stop()

        // below method will release
        // the media recorder class.
        mRecorder!!.release()
        mRecorder = null
        recodingBinding.idTVstatus.text = "Recording Stopped"

    }

    fun pausePlaying() {
        // this method will release the media player
        // class and pause the playing of our recorded audio.
        mPlayer!!.release()
        mPlayer = null
        recodingBinding.btnStop.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.btnRecord.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnPlay.setBackgroundColor(resources.getColor(R.color.purple_200))
        recodingBinding.btnStopPlay.setBackgroundColor(resources.getColor(R.color.grey))
        recodingBinding.idTVstatus.text = "Recording Play Stopped"

    }


}