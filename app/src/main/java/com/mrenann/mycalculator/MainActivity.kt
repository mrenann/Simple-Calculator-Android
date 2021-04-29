package com.mrenann.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.mrenann.mycalculator.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calcConfig()
    }

    private fun calcConfig() {
        binding.apply {
            keyPress(btnC,"")
            keyPress(btnParentleft,"(")
            keyPress(btnBtnParentright,")")
            keyPress(btnDivision,"÷")
            keyPress(btn7,"7")
            keyPress(btn8,"8")
            keyPress(btn9,"9")
            keyPress(btnMultiplication,"×")
            keyPress(btn4,"4")
            keyPress(btn5,"5")
            keyPress(btn6,"6")
            keyPress(btnMinus,"-")
            keyPress(btn1,"1")
            keyPress(btn2,"2")
            keyPress(btn3,"3")
            keyPress(btnPlus,"+")
            keyPress(btn0,"0")
            keyPress(btnDot,".")
            keyPress(btnEquals,"=")
        }
    }

    private fun keyPress(btn: AppCompatButton, value: String) {
        btn.setOnClickListener {
            when (value) {
                "" -> {
                    binding.tvConta.text = ""
                    binding.tvResultado.text = ""
                }
                "="-> {
                    showResult()
                }
                else -> {
                    keyPressFunc(value)
                }
            }
        }
    }

    private fun keyPressFunc(value: String) {
        binding.tvConta.text = addToInputText(value)
    }

    private fun addToInputText(buttonValue: String): String? {
        return "${binding.tvConta.text}$buttonValue"
    }

    private fun getInputExpression(): String {
        var expression = binding.tvConta.text.replace(Regex("÷"), "/")
        expression = expression.replace(Regex("×"), "*")
        return expression
    }

    private fun showResult() = try {
        val expression = getInputExpression()
        val result = Expression(expression).calculate()
        if (result.isNaN()) {
            outputMessage(getString(R.string.error_msg),R.color.redColor)
        } else outputMessage(DecimalFormat("0.######").format(result).toString(),R.color.greenColor)
    } catch (e: Exception) {
        outputMessage(getString(R.string.error_msg),R.color.redColor)
    }

    private fun outputMessage(value: String, color: Int) {
        binding.tvResultado.text = value
        binding.tvResultado.setTextColor(ContextCompat.getColor(this, color))
    }

}