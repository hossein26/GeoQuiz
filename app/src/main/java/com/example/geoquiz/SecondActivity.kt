package com.example.geoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
private const val KEY_IS_CHEATER = "is_cheater"
const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"


class SecondActivity : AppCompatActivity() {

    private lateinit var showAnswerButton: Button
    private lateinit var answerTextView: TextView
    private lateinit var apiLevelTextView: TextView
    private var answerText = 0
    private var answerISTrue = false


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //get answer text
        questionViewModel.isCheater = savedInstanceState?.getBoolean(KEY_IS_CHEATER, false) ?: false

        //get intent extra
        answerISTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        //initialize
        showAnswerButton = findViewById(R.id.show_answer_button)
        answerTextView = findViewById(R.id.answer_text_view)
        apiLevelTextView = findViewById(R.id.text_view_apilevel)

        val buildNumber = Build.VERSION.SDK_INT.toString()
        answerText = when{
            answerISTrue -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        //listeners
        showAnswerButton.setOnClickListener {
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
            questionViewModel.isCheater = true
        }
        apiLevelTextView.text = "API level : $buildNumber"

        //closing loopholes for cheater
        if (questionViewModel.isCheater){
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IS_CHEATER, questionViewModel.isCheater)
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