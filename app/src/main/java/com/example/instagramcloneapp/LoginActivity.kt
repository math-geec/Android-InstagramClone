package com.example.instagramcloneapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Tag
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

private const val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signup_link.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        login_btn.setOnClickListener {
            // prevent clicking multiple times
            login_btn.isEnabled = false
            loginUser()
        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun loginUser() {
        val email = email.text.toString()
        val password = password.text.toString()

        when{
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email is required.", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is required.", Toast.LENGTH_LONG).show()

            // TODO: update deprecated functions
            else -> {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Login")
                progressDialog.setMessage("Please wait for a while ...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        login_btn.isEnabled = true
                        if (task.isSuccessful){
                            progressDialog.dismiss()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                        else {
                            val message = task.exception!!.toString()
                            Log.e(TAG, "signInWithEmail failed", task.exception)
                            Toast.makeText(this, "Error: Login failed", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }
}