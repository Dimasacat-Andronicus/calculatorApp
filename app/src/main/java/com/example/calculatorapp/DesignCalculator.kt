package com.example.calculatorapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DisplayScreen(textDisplay: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.LightGray)
            .padding(20.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = textDisplay.ifEmpty { "0" },
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun ClickableButton(text: String, buttonColor: Color, onClick: () -> Unit) {
    Button(
        modifier = Modifier.size(80.dp).padding(2.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
        )
    }
}

@Composable
fun MainCalculator() {
    var text by remember { mutableStateOf("") }
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    val buttonList = listOf(
        listOf("AC", "C", "+-", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf(".", "0", "%", "=")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayScreen(text)
        Spacer(modifier = Modifier.height(12.dp))
        buttonList.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { buttonText ->
                    val buttonColor: Color = when (buttonText) {
                        "AC", "C" -> Color(0xFFF5B7B1)
                        "=" -> Color(0xFFAED6F1)
                        else -> Color(0xFFE0E0E0)
                    }
                    ClickableButton(text = buttonText, buttonColor = buttonColor) {
                        when (buttonText) {
                            "AC" -> {
                                text = ""
                                num1 = ""
                                num2 = ""
                                operator = ""
                            }
                            "C" -> {
                                if (text.isNotEmpty()) {
                                    text = text.dropLast(1)
                                    if (operator.isEmpty()) {
                                        num1 = num1.dropLast(1)
                                    } else if (num2.isEmpty()){
                                        operator = operator.dropLast(1)
                                    } else {
                                        num2 = num2.dropLast((1))
                                    }
                                }
                            }
                            "=" -> {
                                if (num1.isNotEmpty() && num2.isNotEmpty() && operator.isNotEmpty()) {
                                    val answer = when (operator) {
                                        "+" -> add(num1, num2)
                                        "-" -> subtract(num1, num2)
                                        "×" -> multiply(num1, num2)
                                        "÷" -> divide(num1, num2)
                                        else -> null
                                    }
                                    text = answer?.let {
                                        if (it % 1 == 0.0) it.toInt().toString() else it.toString()
                                    } ?: "Error"
                                    num1 = ""
                                    num2 = ""
                                    operator = ""
                                }
                            }
                            "+", "-", "×", "÷" -> {
                                if (text.isNotEmpty() && operator.isEmpty()) {
                                    num1 = text
                                    operator = buttonText
                                    text += " $buttonText "
                                }
                            }
                            "+-" -> {
                                if (operator.isEmpty()) {
                                    num1 = if (num1.startsWith("-")) {
                                        num1.drop(1)
                                    } else {
                                        "-$num1"
                                    }
                                    text = num1
                                } else {
                                    num2 = if (num2.startsWith("-")) {
                                        num2.drop(1)
                                    } else {
                                        "-$num2"
                                    }
                                    text = "$num1 $operator $num2"
                                }
                            }
                            "." -> {
                                if (operator.isEmpty()) {
                                    if (!num1.contains(".")) {
                                        num1 += "."
                                        text += "."
                                    }
                                } else {
                                    if (!num2.contains(".")) {
                                        num2 += "."
                                        text += "."
                                    }
                                }
                            }
                            "%" -> {
                                if (operator.isEmpty()) {
                                    num1 = if (num1.isNotEmpty()) {
                                        (num1.toDouble() / 100).toString()
                                    } else {
                                        "0"
                                    }
                                    text = num1
                                } else {

                                    num2 = if (num2.isNotEmpty()) {
                                        (num2.toDouble() / 100).toString()
                                    } else {
                                        "0"
                                    }
                                    text = "$num1 $operator $num2"
                                }
                            }
                            else -> {
                                if (operator.isEmpty()) {
                                    num1 += buttonText
                                } else {
                                    num2 += buttonText
                                }
                                text += buttonText
                            }
                        }
                    }
                }
            }
        }
    }
}