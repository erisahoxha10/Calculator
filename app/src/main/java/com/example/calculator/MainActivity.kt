package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //tracks when the last one is one operator
    var isLastOperator = false
    var isLastDecimal = false
    var isEqual = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberAction(view: View) {

        if(view is Button) {
            if(isEqual){
                workingsTV.text = ""
                resultsTV.text = ""
                isEqual = false
            }
            if(!isLastDecimal){
                workingsTV.append(view.text)
                isLastOperator = false
                isLastDecimal = view.text == "."
            }

        }
    }

    fun operatorAction(view: View) {
        if(view is Button) {
            if(isEqual){
                resultsTV.text = ""
                isEqual = false
            }
            if(workingsTV.text.length > 0) {
                if (!isLastOperator) {
                    workingsTV.append(view.text)
                    isLastOperator = true
                } else {
                    var newText = workingsTV.text.subSequence(0, workingsTV.text.length - 1)
                    workingsTV.text = newText
                    workingsTV.append(view.text)
                }
            }
        }
    }

    fun equalsAction(view: View) {
        if(workingsTV.text.length > 0) {
            var result = calculate(workingsTV.text)
            resultsTV.text = "=" + result
        }
        isEqual = true
    }

    fun backspaceAction(view: View) {
        if(workingsTV.text.length > 0) {
            val length = workingsTV.length()
            workingsTV.text = workingsTV.text.subSequence(0, length - 1)
        }
    }

    fun allClearAction(view: View) {
        workingsTV.text = ""
        resultsTV.text = ""
    }

    private fun calculate(text: CharSequence): Double {
        var list = mutableListOf<Double>()
        var res = 0
        var op1: String = ""
        var op2: String = ""
        var operator: Char = '+'
        text.forEach {
            if(it.isDigit())
                op2 += it
            else{
                if(op1 != ""){
                    op1 = doOperation(op1, op2, operator).toString()
                }else
                    op1 = op2
                operator = it
                op2 = ""
            }
        }
        return doOperation(op1, op2, operator)
    }

    fun doOperation(op1: String, op2: String, operator : Char): Double{
        if(operator == '+')
            return op1.toDouble() + op2.toDouble()
        else if(operator == '-')
            return op1.toDouble() - op2.toDouble()
        else if(operator == 'x')
            return op1.toDouble() * op2.toDouble()
        else
            return op1.toDouble()/op2.toDouble()
    }
}