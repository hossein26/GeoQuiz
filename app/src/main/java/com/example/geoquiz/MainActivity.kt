package com.example.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var buttonTrue: Button
    private lateinit var buttonFalse: Button
    private lateinit var buttonNext: Button
    private lateinit var buttonPerv: Button
    private lateinit var questionViewModel: QuestionViewModel
    private var numberOfAnswered = 0
    private var numberTrue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //save index when configuration change
        questionViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

        //save index when process death it happens
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        questionViewModel.currentIndex = currentIndex

        initialize()
        listeners()
    }

    private fun listeners() {
        buttonNext.setOnClickListener {
            questionViewModel.nextQuestion()
            updateQuestion()
        }

        buttonPerv.setOnClickListener {
            questionViewModel.pervQuestion()
            updateQuestion()
        }

        buttonTrue.setOnClickListener {
            checkAnswer(true)
        }

        buttonFalse.setOnClickListener {
            checkAnswer(false)
        }

        textView.setOnClickListener {
            questionViewModel.nextQuestion()
            updateQuestion()
        }
    }

    //save index in death process
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, questionViewModel.currentIndex)
    }

    private fun initialize() {
        textView = findViewById(R.id.text_view_questions)
        buttonTrue = findViewById(R.id.button_true)
        buttonFalse = findViewById(R.id.button_false)
        buttonNext = findViewById(R.id.button_next)
        buttonPerv = findViewById(R.id.button_previous)
        updateQuestion()
    }

    private fun updateQuestion() {
        //set question  text
        val textResId = questionViewModel.currentQuestionText
        textView.setText(textResId)

        //check user answered
        if (questionViewModel.currentAnswered) {
            isAnswered(true)
        } else {
            isAnswered(false)
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionViewModel.currentQuestionAnswer
        if (userAnswer == correctAnswer) {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show()
            numberTrue += 1
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }
        isAnswered(true)
        numberOfAnswered += 1

        //percent of answer
        if (numberOfAnswered == questionViewModel.questionBankSize) {
            Toast.makeText(this, "Your score is : ${(numberTrue.toDouble()/ questionViewModel.questionBankSize) * 100}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isAnswered(userAnswered: Boolean) {

        if (userAnswered) {
            questionViewModel.currentAnswered = true
            buttonTrue.isEnabled = false
            buttonFalse.isEnabled = false

        } else {
            questionViewModel.currentAnswered = false
            buttonTrue.isEnabled = true
            buttonFalse.isEnabled = true
        }
    }
}