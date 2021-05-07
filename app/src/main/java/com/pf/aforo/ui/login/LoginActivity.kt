package com.pf.aforo.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pf.aforo.R
import com.pf.aforo.data.model.User
import com.pf.aforo.databinding.ActivityLoginBinding
import com.pf.aforo.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding;
    private lateinit var loginViewModel: LoginViewModel;
    private var user: User?= null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUI();
        setObservers();
        login();
    }

    private fun setUI (){
        binding = ActivityLoginBinding.inflate(layoutInflater);
        setContentView(binding.root);
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java);

    }

    private fun setObservers () {
        /*loginViewModel.loginResponseLiveData.observe(this, Observer {
            if (it == "Created") {
                initHomeScreen();
            }
        })*/
    }

    private fun login () {
        var email = ""
        var password = ""
        val send = binding.btnSend

        binding.run {

            binding.edtEmail.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    email =  binding.edtEmail.text.toString()
                }
            }

            binding.edtPass.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    password = binding.edtPass.text.toString()
                    hideKeyboard(v);

                }
            }
        }


        send.setOnClickListener {
           // loginViewModel.login(email, password)
           // initHomeScreen();
           validateLogin(email, password)
        }

    }

    private fun initHomeScreen () {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java));
    }

    private fun validateLogin(email: String, password: String) {
        if ((email == "admin@test.com") and (password == "1234")){
            initHomeScreen()
        } else {
            Toast.makeText(applicationContext, "Usuario y/o contraseña incorrecto", Toast.LENGTH_SHORT).show()
        }
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
           getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}