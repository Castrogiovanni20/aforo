package com.pf.aforo.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pf.aforo.R
import com.pf.aforo.databinding.ActivityRecyclerFuncionariosBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerFuncionariosBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_funcionarios)
        setUI()
        setObservers()
        setClickListeners()
    }

    private fun setUI () {
        binding = ActivityRecyclerFuncionariosBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setObservers () {

    }

    private fun setClickListeners () {
        binding.btnAgregarFuncionario.setOnClickListener {
            initAddUserActivity()
        }
    }

    private fun initAddUserActivity () {
        startActivity(Intent(this@HomeActivity, AddUserActivity::class.java))
    }
}
