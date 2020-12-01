package com.example.instagramcloneapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // signup_btn.setOnClickListener {
        //     startActivity(Intent(this, LoginActivity::class.java))
        // }

        signup_btn.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        val fullName = fullname.text.toString()
        val userName = username.text.toString()
        val email = email.text.toString()
        val password = password.text.toString()

        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this, "Full name is required.", Toast.LENGTH_LONG)
            TextUtils.isEmpty(userName) -> Toast.makeText(this, "User name is required.", Toast.LENGTH_LONG)
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email is required.", Toast.LENGTH_LONG)
            TextUtils.isEmpty(password) -> Toast.makeText(this, "Password is required.", Toast.LENGTH_LONG)

            // TODO: update deprecated functions
            else -> {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Signup")
                progressDialog.setMessage("Please wait for a while ...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            saveUserInfo(fullName, userName, email)
                        }
                        else {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG)
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun saveUserInfo(fullName: String, userName: String, email: String) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["fullName"] = currentUserId
        userMap["userName"] = currentUserId
        userMap["email"] = currentUserId
        userMap["bio"] = "Empty bio"
    }
}