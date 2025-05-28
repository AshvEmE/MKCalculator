package com.example.lab1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lab1.ui.theme.Lab1Theme
import kotlin.math.pow


class MainActivity : AppCompatActivity(), ButtonFragment.ButtonClickListener {

    // Ініціалізація фрагментів
    private lateinit var resultFragment: ResultFragment
    private lateinit var buttonFragment: ButtonFragment
    private lateinit var historyFragment: HistoryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Створення фрагментів
        resultFragment = ResultFragment()
        buttonFragment = ButtonFragment()
        historyFragment = HistoryFragment()


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.result_container, resultFragment)
                .replace(R.id.button_container, buttonFragment)
                .replace(R.id.history_container, historyFragment)
                .commit()  // Виконання транзакції для додавання фрагментів
        }
    }


    override fun onButtonClicked(value: String) {

        val expression = resultFragment.getExpression() + value
        val result = calculateExpression(expression)

        // Оновлюємо вираз і результат
        resultFragment.updateExpression(expression)
        resultFragment.updateResult(result)

        // Додаємо новий запис до історії
        historyFragment.updateHistory(historyFragment.getHistory() + "$expression = $result")
    }

    // Функція для обчислення виразу
    fun calculateExpression(expr: String): String {
        try {
            val tokens = tokenize(expr)
            if (tokens.isEmpty()) return ""

            var current = tokens[0].toDouble()

            var i = 1
            while (i < tokens.size) {
                val op = tokens[i]
                val next = tokens.getOrNull(i + 1)?.toDoubleOrNull() ?: break

                when (op) {
                    "+" -> current += next
                    "-" -> current -= next
                    "*" -> current *= next
                    "/" -> current /= next
                    "^" -> current = current.pow(next)
                    else -> return "Error"
                }
                i += 2
            }
            return current.toString()
        } catch (e: Exception) {
            return "Error"
        }
    }

    // Функція для розбиття виразу на токени
    fun tokenize(expr: String): List<String> {
        val tokens = mutableListOf<String>()
        var number = ""
        for (c in expr) {
            if (c.isDigit() || c == '.') {
                number += c
            } else {
                if (number.isNotEmpty()) {
                    tokens.add(number)
                    number = ""
                }
                if (c != ' ') tokens.add(c.toString())
            }
        }
        if (number.isNotEmpty()) tokens.add(number)
        return tokens
    }
}
