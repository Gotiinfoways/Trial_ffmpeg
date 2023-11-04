package com.example.trial.Record_Audio

import android.os.Handler
import android.os.Looper

class Timer(listener: OnTimerTickListener)  {

    interface OnTimerTickListener{
        fun onTimertick(duration : String)
    }

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    private var duration = 0L
    private var delay = 100L

    init {
        runnable = Runnable{
            duration += delay
            handler.postDelayed(runnable,delay)
            listener.onTimertick(format())
        }
    }

    fun start(){
        handler.postDelayed(runnable,delay)
    }

    fun pause(){
        handler.removeCallbacks(runnable)
    }

    fun Stop(){
        handler.removeCallbacks(runnable)
        duration = 0L
    }

    fun format():String {
        val millis : Long = duration % 1000 /10
        val seconds : Long = (duration/1000)% 60
        val minutes : Long = (duration/(1000*60))%60
        val hours : Long = (duration/(1000*60*60))

        var formatted : String = if (hours > 0)
            "%02d:%02d:%02d:%02d".format(hours,minutes,seconds,millis)
        else
            "%02d:%02d:%02d".format(minutes,seconds,millis)
        return formatted
    }
}