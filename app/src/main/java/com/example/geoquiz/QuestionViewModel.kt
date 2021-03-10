package com.example.geoquiz

import androidx.lifecycle.ViewModel

class QuestionViewModel : ViewModel() {

    var currentIndex = 0
    var isCheater = false

    private val questionBank = listOf<Question>(
        Question(R.string.alp_mountains, false),
        Question(R.string.amazon_river, false),
        Question(R.string.england_capital, false),
        Question(R.string.iran_capital, true),
        Question(R.string.iran_mountain, true)
    )

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    var currentAnswered: Boolean
        get() = questionBank[currentIndex].isAnswered
        set(value) {
            questionBank[currentIndex].isAnswered = value
        }

    val questionBankSize: Int
        get() = questionBank.size

    fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun pervQuestion() {
        currentIndex = if (currentIndex == 0) {
            questionBank.size - 1
        } else {
            (currentIndex - 1) % questionBank.size
        }
    }
}