package com.pf.aforo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pf.aforo.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /*setContentView(R.layout.activity_main) descomentar y borrar la línea 12 de pruebas */
        setContentView(R.layout.activity_register)

    }
}
