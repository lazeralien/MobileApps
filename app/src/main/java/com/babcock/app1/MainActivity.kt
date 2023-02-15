package com.babcock.app1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity(), NameFragment.OnStringListener {
    private lateinit var sharedPref: SharedPreferences
    private val PREFS_NAME = "MyPrefs"
    private val KEY_NAME = "name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //have to add this before using the sharedPref in onCreate
        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)




        //swaps between fragments
        findViewById<Button>(R.id.nameButton).setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_main, NameFragment())
                addToBackStack(null)
                commit()
            }
        }
        findViewById<Button>(R.id.mainPageButton).setOnClickListener {
            supportFragmentManager.beginTransaction().apply {

            //Next 5 lines is bundle information of the name to be sent to DisplayFragment
            val sentData = Bundle()
            val name = sharedPref.getString(KEY_NAME, "No Name Yet")
            sentData.putString("NAME", name)
            val disFragment = DisplayFragment()
            disFragment.arguments = sentData

                replace(R.id.fl_main, disFragment, "fl_display") //Note this is modified based on bundle above
                addToBackStack(null)
                commit()
            }
        }

    }

    /**
     * This allows NameFragment to send the first and last names to this activity
     */
    override fun onStringSent(str: String) {
        //TODO: handle the string from the fragment
        //saves string to sharedpref
        val editor = sharedPref.edit()
        editor.putString(KEY_NAME, str)
        editor.apply()
    }
}