package www.sanju.lynkz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import www.sanju.lynkz.Activity.LoginActivity
import www.sanju.lynkz.Custom.BottomNavigationBehavior
import www.sanju.lynkz.Fragments.ExploreFragment
import www.sanju.lynkz.Fragments.HomeFragment
import www.sanju.lynkz.Fragments.ProfileFragment


class MainActivity : AppCompatActivity() {


    private var mAuth: FirebaseAuth? = null
    private var mAuthListner: AuthStateListener? = null
    private var mCurrentUsers: FirebaseUser? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        FirebaseApp.getApps(this)
        mAuth = FirebaseAuth.getInstance()
        mCurrentUsers = mAuth!!.currentUser


        // updateToken(FirebaseInstanceId.getInstance().getToken());

        // updateToken(FirebaseInstanceId.getInstance().getToken());
        if (mCurrentUsers != null) {

            Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show()
        } else {
            val loginIntent = Intent(this@MainActivity, LoginActivity::class.java)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(loginIntent)
            finish()
        }


        mAuthListner = AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                val loginIntent =
                    Intent(this@MainActivity, LoginActivity::class.java)
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(loginIntent)
                finish()
            }
        }


        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav!!.setOnNavigationItemSelectedListener(navListner)

        // attaching bottom sheet behaviour - hide / show on scroll
        val layoutParams = bottomNav!!.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationBehavior()

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            HomeFragment()
        ).commit()


    }

    private var bottomNav: BottomNavigationView? = null
    private val navListner =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {

                R.id.nav_home -> selectedFragment = HomeFragment()

                R.id.nav_explore -> selectedFragment = ExploreFragment()

                R.id.nav_profile -> selectedFragment = ProfileFragment()
            }

            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                selectedFragment!!
            ).commit()

            true
        }


    override fun onStart() {
        super.onStart()

        mAuthListner?.let { mAuth?.addAuthStateListener(it) }

    }

}
