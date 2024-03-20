package com.muhammadwaleed.i210438

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VerifyActivity : AppCompatActivity() {

    private lateinit var etCode1: EditText
    private lateinit var etCode2: EditText
    private lateinit var etCode3: EditText
    private lateinit var etCode4: EditText
    private lateinit var etCode5: EditText
    private lateinit var btnVerify: Button
    private lateinit var tvResendCodeTimer: TextView
    private lateinit var gvNumberPad: GridView
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        val phoneNumber = intent.getStringExtra("Phone")
        val tvSentCodeTo = findViewById<TextView>(R.id.tvSentCodeTo)
        tvSentCodeTo.text = "We've sent a code to $phoneNumber"

        etCode1 = findViewById(R.id.etCode1)
        etCode2 = findViewById(R.id.etCode2)
        etCode3 = findViewById(R.id.etCode3)
        etCode4 = findViewById(R.id.etCode4)
        etCode5 = findViewById(R.id.etCode5)
        btnVerify = findViewById(R.id.btnVerify)
        tvResendCodeTimer = findViewById(R.id.tvResendCodeTimer)
        gvNumberPad = findViewById(R.id.gvNumberPad)

        setupNumberPad()
        setupEditTexts()
        startResendCodeTimer()

        btnVerify.setOnClickListener {



            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupNumberPad() {
        val numbers = (1..9).toList().map { it.toString() } + "" + "0" + "\u232B"
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers)
        gvNumberPad.adapter = adapter
        gvNumberPad.setOnItemClickListener { _, view, _, _ ->
            val number = (view as TextView).text.toString()
            handleNumberPadInput(number)
        }
    }

    private fun handleNumberPadInput(number: String) {
        val currentEditText = findCurrentEditText()

        if (currentEditText != null && currentEditText.text.isEmpty()) {
            currentEditText.setText(number)
            val nextEditText = findNextEditText(currentEditText)
            nextEditText?.requestFocus()
        }
    }

    private fun findCurrentEditText(): EditText? {
        val allEditTexts = arrayOf(etCode1, etCode2, etCode3, etCode4, etCode5)
        return allEditTexts.firstOrNull { it.isFocused }
    }

    private fun findNextEditText(currentEditText: EditText): EditText? {
        val allEditTexts = arrayOf(etCode1, etCode2, etCode3, etCode4, etCode5)
        val currentIndex = allEditTexts.indexOf(currentEditText)
        return if (currentIndex < allEditTexts.size - 1) {
            allEditTexts[currentIndex + 1]
        } else {
            null
        }
    }

    private fun setupEditTexts() {
        val allEditTexts = arrayOf(etCode1, etCode2, etCode3, etCode4, etCode5)
        allEditTexts.forEach { editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s != null && s.length == 1) {
                        allEditTexts.getOrNull(allEditTexts.indexOf(editText) + 1)?.requestFocus()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun startResendCodeTimer() {
        tvResendCodeTimer.isEnabled = false
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvResendCodeTimer.text = "Send code again in ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                tvResendCodeTimer.text = "Resend Code"
                tvResendCodeTimer.isEnabled = true
            }
        }.start()
    }
}
