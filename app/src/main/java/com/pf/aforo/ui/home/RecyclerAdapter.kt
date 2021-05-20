package com.pf.aforo.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.R
import com.pf.aforo.data.model.UserFuncionario

internal class RecyclerAdapter(private var listFuncionarios: List<UserFuncionario>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.textViewFuncionario)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listFuncionarios[position]
        holder.title.text = item.getFirstName()
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