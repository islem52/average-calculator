package com.example.averagecalculator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.averagecalculator.model.Module


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(modifier = Modifier.padding(top=50.dp)) {
                    MainScreen()
            }
        }
    }
}
@Composable
    fun EduUnit(subject: String, mark: Int, coeff: Int) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .shadow(elevation = 4.dp)
            )
            {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),

                    ) {
                    Spacer(modifier = Modifier.width(25.dp))
                    Text(
                        text = subject,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "edit module")
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Spacer(modifier = Modifier.width(25.dp))
                    Text(
                        text = "Grade: $mark"
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Coefficient: $coeff")
                    Spacer(modifier = Modifier.width(50.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
@Composable
    fun MainScreen() {
        val modules = rememberSaveable { mutableStateOf(listOf<Module>()) }
        Column {
            ModuleBuffer(modules = modules)
            LazyColumn {
                items(modules.value) { item ->
                    EduUnit(subject = item.subject, mark = item.mark, coeff = item.coeff)
                }
            }

        }
    }

@Composable
fun ModuleBuffer(modules:MutableState<List<Module>>) {
    fun addModule(mod: Module) {
        modules.value = modules.value + mod

    }
    var subject by rememberSaveable {
        mutableStateOf("")
    }
    var mark by rememberSaveable {
        mutableStateOf("")
    }
    var coeff by rememberSaveable {
        mutableStateOf("")
    }
    var showError by rememberSaveable {
        mutableStateOf(false)
    }
    var weightedAverage by rememberSaveable {
        mutableStateOf(0.0)
    }
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .shadow(elevation = 4.dp)
        )
        {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.width(25.dp))
                Text(text = "Name:")
                Spacer(modifier = Modifier.width(5.dp))
                TextField(value = subject, onValueChange = { newSubject ->
                    subject = newSubject
                })

                Spacer(modifier = Modifier.width(10.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(25.dp))
                Text(text = "Grade:")
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(modifier = Modifier
                    .width(55.dp)
                    .height(50.dp),
                    value = mark, onValueChange = { newMark ->
                        mark = newMark
                    })
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Coefficient:")
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp),
                    value = coeff,
                    onValueChange = { newCoeff ->
                        coeff = newCoeff
                    },
                )
                Spacer(modifier = Modifier.width(50.dp))
            }
            Spacer(modifier = Modifier.height(5.dp))
            if (showError) {
                Text(
                    text = "Error: invalid input or duplicate module name",
                    color = Color.Red,
                    modifier = Modifier.padding(start = 40.dp)
                )
            }
            Row {
                Button(
                    onClick = {
                        val isDuplicate = modules.value.any() { it.subject == subject }
                        val isValideGrade = mark.toDoubleOrNull() != null
                        val isValidCoefficient = coeff.toDoubleOrNull() != null
                        if (!isValidCoefficient || isDuplicate|| !isValideGrade || coeff.equals(0)) {
                            showError = true
                        } else {
                            val newModule = Module(subject, mark.toInt(), coeff.toInt())
                            addModule(mod = newModule)
                            subject=""
                            mark=""
                            coeff=""
                            showError= false
                        }
                    },
                    Modifier.padding(10.dp),
                    enabled = (subject.isNotBlank() && mark.isNotBlank() && coeff.isNotBlank())
                ) {
                    Text(text = "Submit")
                }
                Spacer(Modifier.weight(1f))

                Button(
                    onClick = {
                        /*TODO*/weightedAverage = calculateWeightedAverage(modules = modules.value)
                        showDialog = true
                    },
                    Modifier.padding(10.dp),
                    enabled = modules.value.size>1
                ) {
                    Text(text = "calculate")
                }
                Spacer(Modifier.width(40.dp))
            }
            if (showDialog){
                weightedAverageDialog(average = weightedAverage, onDismiss = {showDialog = false})

            }
        }
    }
}

@Composable
fun weightedAverageDialog(average : Double , onDismiss : ()->Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Magenta
                    )
                ) {
                Text(text = "OK")
            }
        },
        title = { Text(text = "sememster average")},
        text = { Text(text = "your average is $average")},

        )
}

fun calculateWeightedAverage(modules: List<Module>): Double {
    return modules.sumOf { it.mark * it.coeff }.toDouble()/modules.sumOf { it.coeff }
}


