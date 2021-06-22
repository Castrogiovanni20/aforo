package com.pf.aforo.ui

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.pf.aforo.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // FirebaseApp.initializeApp(this)


        findViewById<View>(R.id.main_activity).setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                if (currentFocus != null) {
                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                }
                return true
            }
        })


    }




}