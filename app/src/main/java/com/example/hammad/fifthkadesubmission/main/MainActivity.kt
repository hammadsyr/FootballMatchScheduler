package com.example.hammad.fifthkadesubmission.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.main.fragment.FavFragment
import com.example.hammad.fifthkadesubmission.main.fragment.MatchesFragment
import com.example.hammad.fifthkadesubmission.main.fragment.TeamsFragment


class MainActivity : AppCompatActivity(){
    private val MY_PERMISSIONS_MULTIPLE = 1
    private var toolbar : ActionBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission()
        }
        toolbar = supportActionBar
        val navigation = findViewById(R.id.bottom_navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        toolbar?.setTitle(getString(R.string.matches))
        loadFragment(MatchesFragment())

    }

    private val mOnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {

                when (item.getItemId()) {
                    R.id.matches -> {
                        toolbar?.setTitle(getString(R.string.matches))
                        loadFragment(MatchesFragment())
                        return true
                    }
                    R.id.teams -> {
                        toolbar?.setTitle(getString(R.string.teams))
                        loadFragment(TeamsFragment())
                        return true
                    }
                    R.id.favorites -> {
                        toolbar?.setTitle(getString(R.string.favorites))
                        loadFragment(FavFragment())
                        return true
                    }
                }

                return false
            }
        }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.WRITE_CALENDAR,
                    Manifest.permission.READ_CALENDAR),
                    MY_PERMISSIONS_MULTIPLE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_MULTIPLE -> {
                //j is sum of the granted permission. List of permission granted, check every single item in String[] permissions
                var j = 0
                for (grantResult in grantResults) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED)
                        j++
                }
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && j / grantResults.size == 1) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    Log.d("permission denied", "permission denied Disable")
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}