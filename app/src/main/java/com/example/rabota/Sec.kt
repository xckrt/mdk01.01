package com.example.rabota

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Sec : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    lateinit var inputnumber: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputEditText = findViewById(R.id.editText)
        resultTextView = findViewById(R.id.result)
        spinnerFrom = findViewById(R.id.spin1)
        spinnerTo = findViewById(R.id.spin2)

        val units = resources.getStringArray(R.array.popa)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter


        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                convert()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                convert()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convert()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onResume() {
        super.onResume()


        inputEditText.text.clear()
        resultTextView.text = ""
        spinnerFrom.setSelection(0)
        spinnerTo.setSelection(0)
    }

    private fun convert() {
        val input = inputEditText.text.toString().toDoubleOrNull()
        if (input == null || spinnerFrom.selectedItem == null || spinnerTo.selectedItem == null) {
            resultTextView.text = ""
            return
        }

        val fromUnit = spinnerFrom.selectedItem.toString()
        val toUnit = spinnerTo.selectedItem.toString()

        val conversionFactor = getConversionFactor(fromUnit, toUnit)
        val result = input * conversionFactor
        resultTextView.text = result.toString()
    }

    private fun getConversionFactor(fromUnit: String, toUnit: String): Double {
        val unitToBytes = mapOf(
            "Байт" to 1.0,
            "Килобайт" to 1024.0,
            "Мегабайт" to 1024.0 * 1024.0,
            "Гигабайт" to 1024.0 * 1024.0 * 1024.0,
            "Терабайт" to 1024.0 * 1024.0 * 1024.0 * 1024.0,
            "Петабайт" to 1024.0 * 1024.0 * 1024.0 * 1024.0 * 1024.0
        )

        val fromFactor = unitToBytes[fromUnit] ?: return 1.0
        val toFactor = unitToBytes[toUnit] ?: return 1.0

        return fromFactor / toFactor
    }

    fun ss(view: View) {
        val intent = Intent(this,fierd::class.java)

            intent.putExtra("result",resultTextView.text.toString())
            intent.putExtra("conv",inputEditText.text.toString())
        startActivity(intent)
    }
}