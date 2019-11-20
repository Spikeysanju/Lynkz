package www.sanju.lynkz

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import www.sanju.lynkz.Custom.BottomNavigationBehavior
import www.sanju.lynkz.Fragments.ExploreFragment
import www.sanju.lynkz.Fragments.HomeFragment
import www.sanju.lynkz.Fragments.ProfileFragment

class MainActivity : AppCompatActivity() {



    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




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

}
