package com.babcock.app1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mFirstName: String? = null
    private var mMiddleName: String? = null
    private var mLastName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get the buttons
        val mNameButtonSubmit = findViewById<Button>(R.id.nameButton)
        val mMainPageButtonSubmit = findViewById<Button>(R.id.mainPageButton)
        //make the buttons do something in onClick
        mNameButtonSubmit.setOnClickListener(this)
        mMainPageButtonSubmit.setOnClickListener(this)

//        val fTrans = supportFragmentManager.beginTransaction()
//        fTrans.replace(R.id.fl_main, NameFragment())
//        fTrans.commit()
    }

    override fun onClick(view: View) {
        when (view.id) { //this will redraw the fragment based on which button is clicked
            R.id.nameButton -> {
                //show name page fragment
                val fTrans = supportFragmentManager.beginTransaction()
                fTrans.replace(R.id.fl_main, NameFragment())
                fTrans.commit()
            }
           R.id.mainPageButton -> {
               val fTrans = supportFragmentManager.beginTransaction()
               fTrans.replace(R.id.fl_main, NameFragment())
               fTrans.commit()
           }
        }
    }
}