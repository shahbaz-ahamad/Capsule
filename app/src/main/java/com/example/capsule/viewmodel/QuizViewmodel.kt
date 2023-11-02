package com.example.capsule.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capsule.datamodel.QuestionModel
import com.example.capsule.di.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewmodel @Inject constructor(
    private val firestore: FirebaseFirestore
) :ViewModel() {
    private  val _question = MutableStateFlow<Resource<List<QuestionModel>>>(Resource.Unspecified())
    val question = _question.asStateFlow()

    private val _correct = MutableStateFlow<Resource<String>>(Resource.Unspecified())
    val correct = _correct.asStateFlow()

    init {
        fetchQuestion()
    }


    //fetching the question from the firebase firestore
    fun fetchQuestion(){

        //to change the state at loading
        viewModelScope.launch {
            _question.emit(Resource.Loading())
        }
        firestore.collection("quiz").document("JqKhvfTLUa9RKGmBwGsK").collection("question")
            .get()
            .addOnSuccessListener {data ->

                val question =data.toObjects(QuestionModel::class.java)
                if (question != null) {
                    _question.value = Resource.Success(question)
                    Log.d("data", question.toString())
                } else {
                    Log.d("data", "Question data is null.")
                }


            }
            .addOnFailureListener{
                //failure
                viewModelScope.launch {
                    _question.emit(Resource.Error(it.message.toString()))
                }
                Log.d("error",it.message.toString())
            }
    }


    fun showResult(correctAns : String , totalQuestion : String){
        viewModelScope.launch {
            _correct.emit(Resource.Loading())
        }
        val msg ="You answered $correctAns out of $totalQuestion questions correctly."
        viewModelScope.launch {
            _correct.value = Resource.Success(msg)
            Log.d("result",msg)
        }
    }
}