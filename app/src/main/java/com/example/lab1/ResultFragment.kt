package com.example.lab1
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle

class ResultFragment : Fragment() {
    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView

    private var currentExpression: String = ""
    private var currentResult: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false).apply {
            expressionTextView = findViewById(R.id.expression)
            resultTextView = findViewById(R.id.result)
        }
    }

    fun updateExpression(expression: String) {
        currentExpression = expression
        expressionTextView.text = expression
    }

    fun getExpression(): String {
        return currentExpression
    }

    fun updateResult(result: String) {
        currentResult = result
        resultTextView.text = result
    }

    fun getResult(): String {
        return currentResult
    }
}
