package com.example.mealplanner.startup_activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.mealplanner.HelperClass
import com.example.mealplanner.R
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnRegister: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var login: TextView
    private lateinit var helperClass: HelperClass

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        initUI()

        login.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnRegister.setOnClickListener {
            progressBar.visibility = (View.VISIBLE)
            val email = email.text.toString()
            val password = password.text.toString()
            val isChecked = helperClass.validatePassword(password)
            if (!isChecked) {
                progressBar.visibility = (View.GONE)
                Toast.makeText(this, "Password is not valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {

                if (TextUtils.isEmpty(email)) {
                    progressBar.visibility = (View.GONE)
                    Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(password)) {
                    progressBar.visibility = (View.GONE)
                    Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = (View.GONE)
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }
        }
    }

    private fun initUI() {
        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        btnRegister = findViewById(R.id.btnRegister)
        progressBar = findViewById(R.id.progressBar)
        login = findViewById(R.id.loginNow)
        helperClass = HelperClass()
    }
}