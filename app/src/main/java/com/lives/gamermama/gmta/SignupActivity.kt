package com.lives.gamermama.gmta

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignupActivity : AppCompatActivity() {

    lateinit var rUserName: EditText
    lateinit var rEmail: EditText
    lateinit var rPassCode: EditText
    private lateinit var regBtn: Button
    lateinit var haveAccount: TextView
    lateinit var loadProgressBar: ProgressBar
    private lateinit var auth: FirebaseAuth //firebase auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        rUserName = findViewById(R.id.rUsername)
        rEmail = findViewById(R.id.rEmail)
        rPassCode = findViewById(R.id.rPassword)
        regBtn = findViewById(R.id.signup)
        haveAccount = findViewById(R.id.haveAc)
        loadProgressBar = findViewById(R.id.loadProgressBar)
        auth = FirebaseAuth.getInstance()
        loadProgressBar.visibility = View.INVISIBLE

        // when the text clicks go to login activity
        haveAccount.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }


        auth = FirebaseAuth.getInstance()
        regBtn.setOnClickListener {
            regBtn.visibility = View.INVISIBLE
            loadProgressBar.visibility = View.VISIBLE
            val username: String = rUserName.text.toString()
            val email: String = rEmail.text.toString()
            val passCode: String = rPassCode.text.toString()

            if(email.isEmpty() || username.isEmpty() || passCode.isEmpty()){
                //when somthing goes wrong! it displays ERROR!!
                showMessage("Please Verify all the Fields!!")
                regBtn.visibility = View.VISIBLE
                loadProgressBar.visibility = View.INVISIBLE
            } else {
                //everything is OK, now we can start creating user account..
                CreateAccount(username, email, passCode)
            }




        }
    }

    private fun CreateAccount(username: String, email: String, passCode: String) {
        //this function creates user account with specific email and password..
        auth.createUserWithEmailAndPassword(email, passCode)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    showMessage("Your Account has created Successfully :)")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    showMessage(":'(  Account Creation Failed"+ task.exception!!.message)
                    regBtn.visibility = View.VISIBLE
                    loadProgressBar.visibility = View.INVISIBLE
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this@SignupActivity, LiveStreamingActivity::class.java))
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
        finish()
    }

    private fun showMessage(errorText: String){
        Toast.makeText(this, errorText,Toast.LENGTH_LONG).show()
    }
}




/*
* package com.lives.gamermama.gmta

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LiveStreamActivity : AppCompatActivity() {
    lateinit var logoutBtn: Button //logout memory erase
    private lateinit var authAccount: FirebaseAuth //firebase authentication
    lateinit var userDetails: FirebaseUser //getting user details
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_stream)
        logoutBtn = findViewById(R.id.logout)
        authAccount = FirebaseAuth.getInstance()
        userDetails = authAccount.currentUser!!

        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var intent = Intent(this@LiveStreamActivity, LoginActivity::class.java)
          //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
          //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            Toast.makeText(this, "we miss you buddy come again :'(", Toast.LENGTH_SHORT).show()
        }
    }
}
*/
