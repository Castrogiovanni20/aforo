package com.pf.aforo.ui.home.supervisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.R
import com.pf.aforo.data.model.UserFuncionario

internal class RecyclerAdapter(private var listFuncionarios: ArrayList<UserFuncionario>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.textViewFuncionario)
        var btnEditar: Button = view.findViewById(R.id.btnEditar)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var navController: NavController? = null
        val item = listFuncionarios[position]
        holder.title.text = item.firstName

        holder.btnEditar.setOnClickListener { view ->

            val bundle = Bundle()
            bundle.putParcelable("UserFuncionario", listFuncionarios[position])

            view.findNavController().navigate(R.id.action_homeFragmentSupervisor_to_editUserFragment, bundle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_funcionario, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listFuncionarios.size
    }

}