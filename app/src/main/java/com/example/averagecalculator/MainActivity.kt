package com.example.averagecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.averagecalculator.model.Module
import com.example.averagecalculator.model.addModule
import com.example.averagecalculator.model.buffer
import com.example.averagecalculator.model.modules
import com.example.averagecalculator.ui.theme.AverageCalculatorTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AverageCalculatorTheme {

                //Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->}
          /*  Screen()
                LazyColumn  {
                    items(modules){
                        item->
                        EduUnit(subject = item.subject, mark = item.mark, coeff = item.coeff)
                    }
                }*/
            }
        }
    }
}

@Composable
fun EduUnit(subject: String,mark:Double,coeff:Int) {
Box {

        Column(  modifier= Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            // .padding(16.dp)
            .shadow(elevation = 4.dp)
        )
        {
            Spacer(modifier = Modifier.height(20.dp))
            Row (
                modifier=Modifier.fillMaxWidth(),

            ){
                Spacer(modifier = Modifier.width(25.dp))
                Text(text = subject,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Outlined.Edit, contentDescription ="edit module" )
                Spacer(modifier = Modifier.width(10.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Spacer(modifier = Modifier.width(25.dp))
                Text(text = "Grade: $mark"
                   )

                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Coefficient: $coeff")
                Spacer(modifier = Modifier.width(50.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
}

}


//@Preview(showBackground = true)
@Composable
fun State(){
    var state = remember {
        mutableStateOf(0)
    }
    Row (verticalAlignment = Alignment.CenterVertically
    ){
        Button(onClick = { state.value += 1 }) {
            Text(text = state.value.toString())
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "add")
        }
    }

}


@Composable
fun Screen(){
     val modules : MutableList<Module> = remember {
         mutableListOf()
     }
    ModuleBuffer()
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview(){
    Screen()
}

@Composable
fun ModuleBuffer() {
        var buffer :Module = buffer
    Box {
        val subject = remember {
            mutableStateOf(buffer.subject)
        }
        val mark = remember{
            mutableStateOf(buffer.mark.toString())
        }
        val coeff = remember {
            mutableStateOf(buffer.coeff.toString())
        }

        Column(  modifier= Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            //.padding(16.dp)
            .shadow(elevation = 4.dp)
        )
        {
            Spacer(modifier = Modifier.height(20.dp))
            Row (
                modifier=Modifier.fillMaxWidth(),
                ){
                Spacer(modifier = Modifier.width(25.dp))
                Text(text = "Name:")
                Spacer(modifier = Modifier.width(5.dp))

                TextField(value = subject.value, onValueChange = {newSubject->
                    subject.value = newSubject
                })

                Spacer(modifier = Modifier.width(10.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(25.dp))

                Text(text = "Grade:")
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(modifier= Modifier
                    .width(55.dp)
                    .height(50.dp),

                    value = mark.value, onValueChange = {newMark ->
                        mark.value = newMark})
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Coefficient:")
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(modifier= Modifier
                    .width(50.dp)
                    .height(50.dp),

                    value = coeff.value, onValueChange = {newCoeff ->
                    coeff.value = newCoeff})
                Spacer(modifier = Modifier.width(50.dp))
            }
            Spacer(modifier = Modifier.height(5.dp))
            Button(onClick = { addModule(buffer) },Modifier.padding(10.dp)) {
                Text(text = "Submit")
            }
        }
    }


}
