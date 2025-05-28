package com.example.lab1

import androidx.fragment.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class ButtonFragment : Fragment() {
    private lateinit var buttonClickListener: ButtonClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ButtonClickListener) {
            buttonClickListener = context
        } else {
            throw RuntimeException("$context must implement ButtonClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buttons, container, false).apply {
            findViewById<Button>(R.id.button_0).setOnClickListener { buttonClickListener.onButtonClicked("0") }
            findViewById<Button>(R.id.button_1).setOnClickListener { buttonClickListener.onButtonClicked("1") }
            findViewById<Button>(R.id.button_2).setOnClickListener { buttonClickListener.onButtonClicked("2") }
            findViewById<Button>(R.id.button_3).setOnClickListener { buttonClickListener.onButtonClicked("3") }
            findViewById<Button>(R.id.button_4).setOnClickListener { buttonClickListener.onButtonClicked("4") }
            findViewById<Button>(R.id.button_5).setOnClickListener { buttonClickListener.onButtonClicked("5") }
            findViewById<Button>(R.id.button_6).setOnClickListener { buttonClickListener.onButtonClicked("6") }
            findViewById<Button>(R.id.button_7).setOnClickListener { buttonClickListener.onButtonClicked("7") }
            findViewById<Button>(R.id.button_8).setOnClickListener { buttonClickListener.onButtonClicked("8") }
            findViewById<Button>(R.id.button_9).setOnClickListener { buttonClickListener.onButtonClicked("9") }
            findViewById<Button>(R.id.button_dot).setOnClickListener { buttonClickListener.onButtonClicked(".") }

            findViewById<Button>(R.id.button_plus).setOnClickListener { buttonClickListener.onButtonClicked("+") }
            findViewById<Button>(R.id.button_minus).setOnClickListener { buttonClickListener.onButtonClicked("-") }
            findViewById<Button>(R.id.button_multiply).setOnClickListener { buttonClickListener.onButtonClicked("*") }
            findViewById<Button>(R.id.button_divide).setOnClickListener { buttonClickListener.onButtonClicked("/") }
            findViewById<Button>(R.id.button_power).setOnClickListener { buttonClickListener.onButtonClicked("^") }

            findViewById<Button>(R.id.button_clear).setOnClickListener { buttonClickListener.onButtonClicked("C") }
            findViewById<Button>(R.id.button_equals).setOnClickListener { buttonClickListener.onButtonClicked("=") }
        }
    }

    interface ButtonClickListener {
        fun onButtonClicked(value: String)
    }
}
