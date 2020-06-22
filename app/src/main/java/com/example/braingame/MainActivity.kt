package com.example.braingame

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    lateinit var countDownTimer: CountDownTimer
    var correct_answers =0
    var total_questions =0
    var timeOut = false
    lateinit var textview: TextView
    var tag: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quiz()
    }
    fun quiz(){
        countDownTimer =
            object : CountDownTimer((30 * 1000 + 100).toLong(), 1000) {
                override fun onFinish() {
                    resetValue()
                }

                override fun onTick(millisUntilFinished: Long) {
                    updateTimer((millisUntilFinished / 1000).toInt())
                }

            }
        countDownTimer.start()
        if(!timeOut){
            questionOptions()
        }
    }
    fun questionOptions(){
        var num1 =0
        var num2 = 0
        var sum =0
        num1 = Random.nextInt(0, 100)
        num2 = Random.nextInt(0, 100)
        question.text = "$num1"+" + "+"$num2"
        sum = num1+num2
        total_questions++
        tag = Random.nextInt(0,3)
        val viewGroup= (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(
            0
        ) as ViewGroup
        val grid: GridLayout=viewGroup.findViewById(R.id.grid_layout)
        var i=0
        while(i<4){
            if(i != tag){
                textview = grid.getChildAt(i) as TextView
                textview.text = "${Random.nextInt(0,500)}"
            }
            else{
                textview = grid.getChildAt(i) as TextView
                textview.text = "$sum"
            }
            i++
        }
    }
    fun answer(view: View){
        val counter :TextView = view as TextView
        val tappedCounter = counter.getTag().toString().toInt()
        if(tag == tappedCounter){
            correct_answers++
        }
        result.text = "$correct_answers"+"/"+"$total_questions"
        if(!timeOut) {
            questionOptions()
        }
    }
    fun buttonClick(view: View){
        replay.visibility = View.INVISIBLE
        done.visibility = View.INVISIBLE
        result.text ="0/0"
        timerText.text = "00s"
        timeOut = false
        correct_answers =0
        total_questions =0
        quiz()
    }

    fun resetValue(){
        timerText.text ="00s"
        countDownTimer.cancel()
        replay.visibility = View.VISIBLE
        done.visibility = View.VISIBLE
        timeOut = true
    }

    fun updateTimer(secondsLeft: Int){
        val minutes = secondsLeft/60
        val seconds = secondsLeft -(minutes*60)
        var secondsTime: String = seconds.toString()
        if(seconds <=9){
            secondsTime = "0$seconds"
        }
        timerText.text = "$secondsTime"+"s"
    }
}
