package www.sanju.lynkz.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rengwuxian.materialedittext.MaterialEditText
import www.sanju.lynkz.MainActivity
import www.sanju.lynkz.R

class SignupActivity : AppCompatActivity() {

    private lateinit var mUsers: DatabaseReference
    private lateinit var mSignUpBtn: Button
    private lateinit var mProgress: ProgressDialog
    private lateinit var mAuth: FirebaseAuth
    private lateinit var  loginNow: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)




        mSignUpBtn = findViewById(R.id.csignUpBtn)
        loginNow = findViewById(R.id.csigninBtn)




        loginNow.setOnClickListener {

            finish()
        }
        mProgress = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()

        mUsers = FirebaseDatabase.getInstance().reference.child("Users")


        mSignUpBtn.setOnClickListener {


            startRegister()

        }

        loginNow.setOnClickListener {


            finish()

        }



    }

    private fun startRegister() {


        val emailTxt = findViewById<View>(R.id.cemail) as MaterialEditText
        val passwordTxt = findViewById<View>(R.id.cpassword) as MaterialEditText
        val nameTxt = findViewById<View>(R.id.cusername) as MaterialEditText
        val phoneTxt = findViewById<View>(R.id.cphonenumber) as MaterialEditText


        val email = emailTxt.text.toString()
        val mobileno = phoneTxt.text.toString()
        val username = nameTxt.text.toString()
        val password = passwordTxt.text.toString()



        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(mobileno)) {

            mProgress.setMessage("Signing Up...")
            mProgress.show()

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    // val userId = mAuth.currentUser!!.uid

                    val user = mAuth.currentUser
                    val uid = user!!.uid
                    val userDB = mUsers.child(uid)
                    userDB.child("name").setValue(username)
                    userDB.child("email").setValue(email)
                    userDB.child("phone").setValue(mobileno)



                    mProgress.dismiss()

                    startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                    finish()


                } else {

                    mProgress.dismiss()
                    Toast.makeText(
                        this@SignupActivity,
                        "Error While Creating Account",
                        Toast.LENGTH_LONG
                    ).show()


                }
            }


        }

    }

}
