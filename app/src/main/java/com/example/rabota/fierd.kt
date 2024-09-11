package com.example.rabota

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class fierd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fierd)


        val inputNumber = intent.getStringExtra("conv")
        val result = intent.getStringExtra("result")

        val vvedchisTextView = findViewById<TextView>(R.id.vvedchiss)
        val resultTextView = findViewById<TextView>(R.id.result)

        vvedchisTextView.text = inputNumber
        resultTextView.text = result
    }


    fun b(view: View) {

        val intent = Intent(this, MainActivity2::class.java)


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
