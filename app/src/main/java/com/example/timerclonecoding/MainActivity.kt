package com.example.timerclonecoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

import com.example.timerclonecoding.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(){

    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var index :Int = 1


    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabStart.setOnClickListener {

            isRunning = !isRunning
            if (isRunning) start() else pause()
        }


        binding.fabReset.setOnClickListener {
            reset()
        }

        binding.btnLab.setOnClickListener {
            if(time!=0) lapTime()
        }


    }

    private fun start(){
        binding.fabStart.setImageResource(R.drawable.ic_pause)

        timerTask = kotlin.concurrent.timer(period = 10){
            time++

            val sec = time / 100
            val mil = time % 100

            runOnUiThread {
                binding.secText.text ="$sec"
                binding.milliText.text = "$mil"
            }
        }
    }

    private fun pause(){
        binding.fabStart.setImageResource(R.drawable.ic_video_play_button)
        timerTask?.cancel()
    }

    private fun lapTime(){
        val lapTime = time

        val text = TextView(this).apply{

            setTextSize(20f)
            text = "${lapTime/100}.${lapTime%100}"
        }

        binding.lapLayout.addView(text,0)
        index++
    }

    private fun reset(){
        timerTask?.cancel()

        time=0
        isRunning=false
        binding.fabStart.setImageResource(R.drawable.ic_video_play_button)
        binding.secText.text="0"
        binding.milliText.text="00"

        binding.lapLayout.removeAllViews()
        index=1

    }



    override fun onDestroy() {
        mBinding=null
        super.onDestroy()
    }

}