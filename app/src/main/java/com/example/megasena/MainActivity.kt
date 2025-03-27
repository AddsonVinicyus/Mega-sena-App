package com.example.megasena

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.megasena.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        with(binding){
            result?.let{
                txtResult.text = "Última aposta: $it"
            }

            btnGen.setOnClickListener {

                val text = editNumber.text.toString()

                numberGenerator(text, txtResult)
            }
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView){
        if(text.isEmpty()){
            displayError()
            return
        }

        val qtd = text.toInt()

        if(qtd < 6 || qtd > 15){
            displayError()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random  = java.util.Random()

        while(numbers.size < qtd){
            val number = random.nextInt(60)
            numbers.add(number + 1)
        }

        val sortedList = numbers.sorted()

        showResult(sortedList, txtResult)

    }

    private fun displayError(){
        Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
    }

    private fun showResult(numbers: List<Int>, txtResult: TextView){
        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()

    }
}

