package com.example.repaso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    //lateinit -> dejame que desupés le asignaré un valor
    private lateinit var tvHelloWorld: TextView
    private lateinit var btnProcess: Button
    private lateinit var swWithName: Switch
    private lateinit var etName: EditText
    private lateinit var etMessage: EditText

    private var mensaje: String? = null

    //Comando de voz
    //tts = text to speech

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarVistas()

        //Inicializar comando de voz
        //this en posición 1 y this en posicion 2 son diferentes
        //Posicion 1 = contexto de pantalla
        //Posicion 2 = buscando si esta clase esta implementada la regla del juego

        tts = TextToSpeech(this,this)
        mensaje = "Os pasais tío"

        //las llaves representan funcion que recibe funcion - orden superior
        //Función anónima de una interfaz que implementa una regla del juego
        //Llamada onClick

        btnProcess.setOnClickListener { speakMessage() }
    }

    private fun inicializarVistas() {
        tvHelloWorld = findViewById(R.id.tvHelloWorld)
        btnProcess = findViewById(R.id.btnProcess)
        swWithName = findViewById(R.id.swWithName)
        etName = findViewById(R.id.etName)
        etMessage = findViewById(R.id.etMessage)

        //Si no quieres usar tu vista en una variable
        //findViewById<TextView>(R.id.tvHelloWorld).text
    }

    private fun speakMessage() {
        mensaje = messageValidation()
        tvHelloWorld.text = mensaje
        tts.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun messageValidation(): String{
        if (swWithName.isChecked) return etMessage.text.toString().plus(" " + etName.text.toString())
        return etMessage.text.toString()
    }

    override fun onInit(status: Int) {
        //por defecto, el comando de voz está en ingles
        val respuesta = if (status == TextToSpeech.SUCCESS){
            tts.language = Locale.US
            "Todo ha salido bien"
        } else "Algo ha fallado, pruebe más tarde"
        Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show()
    }
}