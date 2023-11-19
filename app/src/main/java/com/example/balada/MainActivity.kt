package com.example.balada

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.balada.db.Repository
import java.util.logging.Logger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository(this)

        val total = repository.getExpenses()

        Logger.getLogger("MainActivity").info("Total: $total")

        setContent {
            Form(this)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form(context: Context) {
    var text by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val repository by remember { mutableStateOf(Repository(context)) }
    var expenses by remember { mutableStateOf(repository.getExpenses()) }
    var incomes by remember { mutableStateOf(repository.getIncomes()) }


    var radioOptions = listOf("Entrada", "Saída")
    var selectedOption by remember { mutableStateOf(radioOptions[0]) }

    fun clearInputs() {
        text = ""
        amount = ""
        date = ""
        selectedOption = radioOptions[0]
    }

    fun save() {
        Logger.getLogger("MainActivity").info("Salvando $text $amount $date $selectedOption")

        if (text.isEmpty() || amount.isEmpty() || date.isEmpty()) {
            return
        }

        repository.save(text, amount.toDouble(), date, selectedOption)

        clearInputs()
    }

    fun resume() {
        context.startActivity(Intent(context, ResumeActivity::class.java))
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Cadastro da Balada",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Valor") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Data") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            radioOptions.forEach { option ->
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { selectedOption = option }
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { save() }) {
                Text("Salvar")
            }
            Button(onClick = { resume() }) {
                Text("Resumo")
            }
        }
    }
}