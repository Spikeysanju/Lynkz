package www.sanju.lynkz.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.rengwuxian.materialedittext.MaterialEditText
import www.sanju.lynkz.MainActivity
import www.sanju.lynkz.R

class LoginActivity : AppCompatActivity() {

    private lateinit var uEmail: MaterialEditText
    private lateinit var  uPassword: MaterialEditText
    private lateinit var mLoginBtn: Button
    private lateinit var mRegBtn: TextView
    private lateinit var mUsersDB: DatabaseReference
    private lateinit var mProgress: ProgressDialog
    private lateinit var mAuth: FirebaseAuth
    private lateinit var forgotpass: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        uEmail = findViewById(R.id.emailInput)
        uPassword = findViewById(R.id.passwordInput)
        mLoginBtn = findViewById(R.id.signinBtn)
        mRegBtn = findViewById(R.id.signupBtn)




        mRegBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }


        mProgress = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()

        forgotpass = findViewById(R.id.forgotpass)
        forgotpass.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }


        mLoginBtn.setOnClickListener {

            startLogin()
        }

    }


    private fun startLogin() {
        val email = uEmail.text!!.toString().trim { it <= ' ' }
        val password = uPassword.text!!.toString().trim { it <= ' ' }

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgress.setMessage("Signing In...")
            mProgress.show()

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {



                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()

                } else {

                    mProgress.dismiss()
                    Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_LONG).show()


                }
            }


        }


    }





}
