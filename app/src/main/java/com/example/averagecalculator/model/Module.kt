package com.example.averagecalculator.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

data class Module(
    val subject :String,
    val mark : Double,
    val coeff : Int
)
var buffer : Module = Module(
    "",
    0.0,
    0
)


var modules: MutableList<Module> = mutableListOf()



fun addModule(module: Module){
    if(module.subject!="" && module.subject!=null && module.mark!= 0.0 && module.coeff!=0){
        modules.add(module)
        return
    }
    print("incomplete module")
}

