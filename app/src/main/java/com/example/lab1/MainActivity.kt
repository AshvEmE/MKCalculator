package com.example.lab1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab1.ui.theme.Lab1Theme
import kotlin.math.pow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}
@Composable
fun CalculatorScreen() {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var history by remember { mutableStateOf(listOf<String>()) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Функція для обробки натискання кнопок
    fun onButtonClick(value: String) {
        when (value) {
            "C" -> {
                expression = ""
                result = ""
            }
            "=" -> {
                val calcResult = calculateExpression(expression)  // Обчислюємо результат
                result = calcResult
                // Додаємо вираз і результат в історію
                history = listOf("$expression = $calcResult") + history
                expression = "" // очищуємо вираз після обчислення
            }
            else -> {
                expression += value  // Додаємо цифри або оператори до виразу
            }
        }
    }

    val buttons = listOf(
        listOf("7", "8", "9", "/"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "-"),
        listOf("0", ".", "^", "+"),
        listOf("C", "=")
    )

    val buttonHeight = 48.dp
    val buttonSpacing = 6.dp

    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            // Дисплей і історія
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = expression,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .heightIn(min = 48.dp)
                )
                Text(
                    text = result,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 36.dp)
                )

                // Історія обчислень
                Text(
                    text = "History",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(history) { item ->
                        Text(
                            text = item,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }

            // Кнопки калькулятора
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                buttons.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(buttonHeight),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        row.forEach { label ->
                            CalculatorButton(
                                label,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            ) {
                                onButtonClick(label)
                            }
                        }
                    }
                }
            }
        }
    } else {
        // Портретний режим
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = expression,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = result,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 36.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Історія обчислень
            Text(
                text = "History",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(history) { item ->
                    Text(
                        text = item,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопки калькулятора
            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    row.forEach { label ->
                        CalculatorButton(label, Modifier.weight(1f).fillMaxHeight()) {
                            onButtonClick(label)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .border(0.5.dp, Color.Gray, RoundedCornerShape(4.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

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

