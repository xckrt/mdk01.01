package com.example.rabota

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.SharedPreferences
import android.view.View

class MainActivity2 : AppCompatActivity() {

    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "MyPrefs"
    private val KEY_LOGIN = "ects"
    private val KEY_PASSWORD = "ects2023"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        loginEditText = findViewById(R.id.login)
        passwordEditText = findViewById(R.id.pass)


        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)


        val savedLogin = sharedPreferences.getString(KEY_LOGIN, null)
        val savedPassword = sharedPreferences.getString(KEY_PASSWORD, null)


        if (savedLogin != null && savedPassword != null) {
            showLoginDialog(savedLogin, savedPassword)
        }
    }

    private fun showLoginDialog(savedLogin: String, savedPassword: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ввести сохраненные данные для входа?")


        val loginInput = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT
            setText(savedLogin)
            hint = "Login"
        }

        val passwordInput = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            setText(savedPassword)
            hint = "Password"
        }


        val layout = androidx.constraintlayout.widget.ConstraintLayout(this).apply {
            setPadding(30, 30, 30, 30)

        }

        builder.setView(layout)


        builder.setPositiveButton("OK") { _, _ ->
            loginEditText.setText(loginInput.text.toString())
            passwordEditText.setText(passwordInput.text.toString())
        }


        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }


        builder.show()
    }

    override fun onPause() {
        super.onPause()


        with(sharedPreferences.edit()) {
            putString(KEY_LOGIN, loginEditText.text.toString())
            putString(KEY_PASSWORD, passwordEditText.text.toString())
            apply()
        }
    }

    fun a(view: View) {

        val loginEditText = findViewById<EditText>(R.id.login)
        val passwordEditText = findViewById<EditText>(R.id.pass)

        val login = loginEditText.text.toString()
        val password = passwordEditText.text.toString()


        val correctLogin = "ects"
        val correctPassword = "ects2023"
        val messageBuilder = StringBuilder()

        if (login != correctLogin) {
            messageBuilder.append("Неверный логин.\n")
        }
        if (password != correctPassword) {
            messageBuilder.append("Неверный пароль.")
        }

        if (messageBuilder.isEmpty()) {

            val intent = Intent(this, Sec::class.java)
            startActivity(intent)
        } else {

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Ошибка входа")
            alertDialogBuilder.setMessage(messageBuilder.toString())
            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialogBuilder.show()
        }
    }
}