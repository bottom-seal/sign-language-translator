package com.example.adjustment

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.adjustment.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val menuBtn = findViewById<ImageView>(R.id.menuBtn)
        navigationView.itemIconTintList = null//sidebar menu's icons were original
        bottomNavView.itemIconTintList = null
        menuBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            navController = Navigation.findNavController(this, R.id.navHostFragment)
            //NavigationUI.setupWithNavController(navigationView, navController)
            //ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open_menu,R.string.close_menu)
            //NavController navController = Navigation.findNavController(this,R.id.navHostFragment)
            navigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.btnHome -> replaceFragment(HomeFragment())

                   // R.id.btnSearch -> replaceFragment(SearchFragment())
                    R.id.btnSign -> replaceFragment(ScanFragment())
                    R.id.btnContest -> replaceFragment(ContestFragment())
                    R.id.btnSettings -> replaceFragment(SettingFragment())
                    else -> {

                    }

                }
                drawerLayout.closeDrawer(GravityCompat.START)
                true

            }

        }

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.btnHome -> replaceFragment(HomeFragment())
           //    R.id.btnSearch -> replaceFragment(SearchFragment())
                R.id.btnSign -> replaceFragment(ScanFragment())
                R.id.btnContest -> replaceFragment(ContestFragment())
                R.id.btnSettings -> replaceFragment(SettingFragment())

                else ->{

                }

            }

            true
        }
        //val background = findViewById<ImageView>(R.id.imageView6)
        //background.animate().translationY(-1500F).setDuration(1000).setStartDelay(300)

    }
    fun replaceFragment (fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()




    }

}


