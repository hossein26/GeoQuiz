package com.example.geoquiz

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
private const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"

class SecondActivity : AppCompatActivity() {

    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView
    private var answerISTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //get intent extra
        answerISTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        //initialize
        showAnswerButton = findViewById(R.id.show_answer_button)
        answerTextView = findViewById(R.id.answer_text_view)

        //listeners
        showAnswerButton.setOnClickListener {
            val answerText = when{
                answerISTrue -> R.string.correct_toast
                else -> R.string.incorrect_toast
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, SecondActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}