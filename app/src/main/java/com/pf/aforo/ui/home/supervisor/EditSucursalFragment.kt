package com.pf.aforo.ui.home.supervisor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentEditSucursalBinding

class EditSucursalFragment : Fragment(R.layout.fragment_edit_sucursal) {
    private lateinit var binding: FragmentEditSucursalBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditSucursalBinding.bind(view)
    }
}