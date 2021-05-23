package com.pf.aforo.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentSplashBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var splashViewModel: SplashViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java);
        setObserver()
    }


    private fun setObserver() {
        splashViewModel.startTimer()
        splashViewModel.flag.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        })
    }

}