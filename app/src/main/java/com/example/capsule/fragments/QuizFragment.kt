package com.example.capsule.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.capsule.R
import com.example.capsule.databinding.FragmentQuizBinding
import com.example.capsule.datamodel.QuestionModel
import com.example.capsule.di.Resource
import com.example.capsule.viewmodel.QuizViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val viewmodel by viewModels<QuizViewmodel>()
    private val questionsList = mutableListOf<QuestionModel>()
    private var selectedOption: AppCompatButton? = null
    private var correctAnswer: Int = 0

    // Index to track the current question
    private var currentQuestionIndex = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        onbservetheLifecycle()
        nextButtonClick()
        clickListnerForeachOptionButton()

        binding.verifyButton.setOnClickListener {
            verifyAnswer()
        }


        binding.result.setOnClickListener {
            viewmodel.showResult(correctAnswer.toString(), questionsList.size.toString())
            binding.quiz.visibility = View.GONE
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.correct.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.res.visibility = View.VISIBLE
                        binding.res.text = it.data

                        Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()

                    }

                    else -> Unit

                }
            }
        }

        if (currentQuestionIndex == questionsList.size - 1) {
            calculateResult(selectedOption!!)
        }
    }

    private fun clickListnerForeachOptionButton() {
        // Set click listeners for option buttons
        binding.option1Btn.setOnClickListener { onOptionButtonClicked(binding.option1Btn) }
        binding.option2Btn.setOnClickListener { onOptionButtonClicked(binding.option2Btn) }
        binding.option3Btn.setOnClickListener { onOptionButtonClicked(binding.option3Btn) }
        binding.option4Btn.setOnClickListener { onOptionButtonClicked(binding.option4Btn) }
    }

    private fun nextButtonClick() {


        binding.nextquestion.setOnClickListener {
            calculateResult(selectedOption!!)
            // Check if there are more questions to display
            if (currentQuestionIndex < questionsList.size - 1) {
                // Increment the current question index
                currentQuestionIndex++
                resetOptionButtonBackground()
                // Display the next question
                displayQuestion()
                clickListnerForeachOptionButton()

            } else if (currentQuestionIndex == questionsList.size) {
                // Disable the button if it's the last question
                binding.nextquestion.isEnabled = false
            }

        }
    }


    private fun onbservetheLifecycle() {
        lifecycleScope.launchWhenStarted {
            viewmodel.question.collectLatest {
                when (it) {

                    is Resource.Loading -> {
                        binding.quiz.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Error -> {
                        Log.d("error", it.message.toString())
                        binding.quiz.visibility = View.GONE
                        binding.progressBar.visibility = View.INVISIBLE
                    }

                    is Resource.Success -> {
                        Log.d("data", it.data.toString())
                        if (it.data != null) {
                            questionsList.addAll(it.data)
                            Log.d("questionList", questionsList.toString())
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.quiz.visibility = View.VISIBLE

                            displayQuestion()
                        }


                    }

                    else -> Unit

                }
            }
        }
    }

    private fun displayQuestion() {
        val question = questionsList[currentQuestionIndex]
        // Update the UI with the current question
        binding.quizQuestionTv.text = question.question.toString()
        binding.option1Btn.text = question.opt1.toString()
        binding.option2Btn.text = question.opt2.toString()
        binding.option3Btn.text = question.opt3.toString()
        binding.option4Btn.text = question.opt4.toString()

        binding.option1Btn.isEnabled = true
        binding.option2Btn.isEnabled = true
        binding.option3Btn.isEnabled = true
        binding.option4Btn.isEnabled = true
        // Update the question number text
        binding.questionNumberButton.text =
            "Question ${currentQuestionIndex + 1}/${questionsList.size}"
    }

    private fun onOptionButtonClicked(button: AppCompatButton) {
        // Reset the background color of all option buttons
        resetOptionButtonBackground()
        // Update the selected option
        selectedOption = button
        selectedOption!!.setBackgroundColor(resources.getColor(R.color.selectedColor))
        // Enable the "Verify Answer" button
        binding.verifyButton.isEnabled = true

    }

    private fun resetOptionButtonBackground() {
        binding.option1Btn.setBackgroundColor(resources.getColor(R.color.originalColor))
        binding.option2Btn.setBackgroundColor(resources.getColor(R.color.originalColor))
        binding.option3Btn.setBackgroundColor(resources.getColor(R.color.originalColor))
        binding.option4Btn.setBackgroundColor(resources.getColor(R.color.originalColor))
    }

    private fun verifyAnswer() {
        val selectedAnswer = selectedOption?.text
        val question = questionsList[currentQuestionIndex]
        if (selectedAnswer == question.ans) {
            selectedOption!!.setBackgroundColor(resources.getColor(R.color.correctans))

        } else {
            selectedOption!!.setBackgroundColor(resources.getColor(R.color.Incorrectans))

        }
        binding.option1Btn.isEnabled = false
        binding.option2Btn.isEnabled = false
        binding.option3Btn.isEnabled = false
        binding.option4Btn.isEnabled = false
    }

    fun calculateResult(selectedOption: AppCompatButton) {
        val selectedAnswer = selectedOption?.text
        val question = questionsList[currentQuestionIndex]
        if (selectedAnswer == question.ans) {
            correctAnswer += 1

        }
    }
}