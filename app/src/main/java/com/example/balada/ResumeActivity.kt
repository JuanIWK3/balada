package com.example.balada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.balada.db.Repository

class ResumeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository(this)
        val expenses = repository.getExpenses()
        val incomes = repository.getIncomes()
        val difference = incomes - expenses

        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ValueRow("Total de entradas", incomes)
                ValueRow("Total de saídas", expenses)
                ValueRow("Dinheiro Disponível", difference)
                Button(onClick = { finish() }) {
                    Text(text = "Voltar")
                }
            }
        }
    }
}

@Composable
fun ValueRow(text: String, value: Double) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "R$ ${value.toString()}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}