package com.pf.aforo.ui.home.supervisor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.R
import com.pf.aforo.data.model.UserFuncionario
import de.hdodenhof.circleimageview.CircleImageView

internal class RecyclerAdapter(private var listFuncionarios: ArrayList<UserFuncionario>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageProfile: CircleImageView = view.findViewById(R.id.circleImageViewProfile)
        var fullName: TextView = view.findViewById(R.id.textViewFullName)
        var role: TextView = view.findViewById(R.id.textViewRole)
        var imgButtonEdit: ImageButton = view.findViewById(R.id.imageButtonEditar)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var navController: NavController? = null

        var array = intArrayOf(
            R.drawable.woman_perfil, R.drawable.woman_profile_2, R.drawable.woman_profile_3,
            R.drawable.man_profile, R.drawable.man_profile_2, R.drawable.man_profile_3, R.drawable.man_profile_4)
        val randomNumber = (0 until array.size).random()

        val item = listFuncionarios[position]

        holder.imageProfile.setImageResource(array[randomNumber])
        holder.fullName.text = item.firstName + " " + item.lastName
        holder.role.text = item.role.replace("_", " ")

        holder.imgButtonEdit.setOnClickListener { view ->

            val bundle = Bundle()
            bundle.putParcelable("UserFuncionario", listFuncionarios[position])

            view.findNavController().navigate(R.id.action_usuariosSupervisorFragment_to_editUserFragment, bundle)
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