package com.lives.gamermama.gmta

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    lateinit var uemail: EditText
    lateinit var upassword: EditText
    lateinit var loginBtn: Button
    private lateinit var auth: FirebaseAuth
    lateinit var noAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        uemail = findViewById(R.id.email)
        upassword = findViewById(R.id.passcode)
        loginBtn = findViewById(R.id.login)

        //if user haven't account go to registration activity
        noAccount = findViewById(R.id.noAc)
        noAccount.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
           // overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }
        //login check
        loginBtn.setOnClickListener {
            val email: String = uemail.text.toString()
            val passCode: String = upassword.text.toString()

            if(email.isEmpty() || passCode.isEmpty()){
                //when somthing goes wrong! it displays ERROR!!
                showMessage("Please Verify all the Fields!!")
                loginBtn.visibility = View.VISIBLE
            } else {
                //everything is OK, now we can start creating user account..
                LoginAccount(email, passCode)
            }
        }
        fun onStart() {
            super.onStart()
            // Check if user is signed in (non-null) and update UI accordingly.
            val currentUser = auth.currentUser
            updateUI(currentUser)
        }
    }

    //when login goto LiveStreamActivity
    private fun updateUI(currentUser: FirebaseUser?) {
        startActivity(Intent(this@LoginActivity, LiveStreamingActivity::class.java))
        //overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in)
    }


    fun LoginAccount(email: String, passCode: String) {
        auth.signInWithEmailAndPassword(email, passCode)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    showMessage("Gonna Rock!! ;)")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    showMessage("!! Authentication Failed !!")
                }
            }

    }

    private fun showMessage(txtInfo: String) {
        Toast.makeText(this, txtInfo, Toast.LENGTH_LONG).show()
    }
}
