package com.pf.aforo.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.R
import com.pf.aforo.data.model.UserFuncionario

internal class RecyclerAdapter(private var listFuncionarios: ArrayList<UserFuncionario>, private var homeViewModel: HomeViewModel, private var token: String) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.textViewFuncionario)
        var btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listFuncionarios[position]
        holder.title.text = item.getFirstName()

        holder.btnEliminar.setOnClickListener {
            removeItem(position)
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

    fun removeItem(position: Int) {
        var id = listFuncionarios[position].getId()
        homeViewModel.deleteUser("Bearer $token", id)
        listFuncionarios.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listFuncionarios.size)
    }
}