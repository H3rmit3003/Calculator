package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    private lateinit var result:EditText
    private lateinit var newNumber:EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) {findViewById<TextView>(R.id.textView)}

    private var operand1:Double? = 0.0
    private var pendingOperation = "="

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        result = findViewById(R.id.editTextNumber)
        newNumber = findViewById(R.id.editTextNumber2)
        displayOperation.text = ""
        // Data Input Buttons
        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonDot: Button = findViewById(R.id.buttonDot)
        // Operations Buttons
        val buttonEquals:Button = findViewById(R.id.buttonEquals)
        val buttonMultiply:Button = findViewById(R.id.buttonMultiply)
        val buttonDivide:Button = findViewById(R.id.buttonDivide)
        val buttonMinus:Button = findViewById(R.id.buttonMinus)
        val buttonPlus:Button = findViewById(R.id.buttonPlus)
        val buttonNeg:Button = findViewById(R.id.buttonNeg)

        val listener = View.OnClickListener {
            val b = it as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val oplistener = View.OnClickListener {
            val op = (it as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value,op)
            } catch (e : NumberFormatException){
                newNumber.setText("")
            }
            pendingOperation = op
            displayOperation.text = pendingOperation
        }
        buttonEquals.setOnClickListener(oplistener)
        buttonDivide.setOnClickListener(oplistener)
        buttonMultiply.setOnClickListener(oplistener)
        buttonMinus.setOnClickListener(oplistener)
        buttonPlus.setOnClickListener(oplistener)
        buttonNeg.setOnClickListener {
            val value = newNumber.text.toString()
            if(value.isEmpty()){
                newNumber.setText("-")
            } else {
                try {
                    var doubleValue = value.toDouble()
                    doubleValue *= -1
                    newNumber.setText(doubleValue.toString())
                } catch (e :NumberFormatException){
                    // newNumber was "-" or "." so clear it
                    newNumber.setText("")
                }
            }
        }


    }
    private fun performOperation(value: Double,operation:String){
        if(operand1 == null){
            operand1 = value
        } else {
            if(pendingOperation == "="){
                pendingOperation = operation
            }
        }
        when(pendingOperation){
            "=" -> operand1 = value
            "/" -> operand1 = if(value == 0.0){
                Double.NaN
            } else {
                operand1!!/value
            }
            "*" -> operand1 = operand1!!*value
            "-" -> operand1 = operand1!!-value
            "+" -> operand1 = operand1!!+value
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }
}