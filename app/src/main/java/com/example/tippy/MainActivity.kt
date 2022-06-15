package com.example.tippy

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator

private const val TAG ="MainActivity"
private const val INITIAL_TIP_PERCENT= 15
class MainActivity : AppCompatActivity() {
    private  lateinit var BaseAmount : EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tipPercent: TextView
    private lateinit var textTotalAmount: TextView
    private lateinit var textbarTip : TextView
    private lateinit var tipDescription: TextView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BaseAmount = findViewById(R.id.baseAmount)
        seekBarTip= findViewById(R.id.seekBarTip)
            tipPercent= findViewById(R.id.tipPercent)
        textTotalAmount= findViewById(R.id.textTotalAmount)
            textbarTip= findViewById(R.id.textBarTip)
        tipDescription= findViewById(R.id.tipDescription)

        seekBarTip.progress=INITIAL_TIP_PERCENT
        tipPercent.text = "$INITIAL_TIP_PERCENT"
        updateTipDescription(INITIAL_TIP_PERCENT)

        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
               Log.i(TAG, "change of seek bar $p1")
                tipPercent.text = "$p1%"
                computeTipandTotal()
                updateTipDescription(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        BaseAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,"after text   change $p0")
                computeTipandTotal()
            }

        })
    }

    @SuppressLint("RestrictedApi")
    private fun updateTipDescription(tipPercent: Int) {
        val Description = when (tipPercent){
            in 0..9 ->"poor"
            in 10..14->"Acceptable"
            in 15..19->"Good"
            in 20..24-> "Great"
            in 25..30 ->"Amazing"
            else -> {""}
        }
        tipDescription.text = Description

       val color = ArgbEvaluator().evaluate(
           tipPercent.toFloat()/seekBarTip.max,
           ContextCompat.getColor(this,R.color.color_worst),
           ContextCompat.getColor(this,R.color.color_best),
       )as Int
        tipDescription.setTextColor(color)
    }

    private fun computeTipandTotal() {
        if(BaseAmount.text.isEmpty()){
            textbarTip.text =""
            textTotalAmount.text =""
            return
        }
        val baseAmount=BaseAmount.text.toString().toDouble()
     val tipPercent=    seekBarTip.progress

       val tipAmount= baseAmount*tipPercent/100
        val total = baseAmount+tipAmount

        textbarTip.text = "%.2f".format(tipAmount )
        textTotalAmount.text = "%.2f".format( total )
    }
}