package com.pf.aforo.ui.home.supervisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

internal class BranchOfficeAdapter_2(private var listBranchOffice: ArrayList<BranchOffice>) :
    RecyclerView.Adapter<BranchOfficeAdapter_2.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var circleimageViewFuncionario: CircleImageView = view.findViewById(R.id.circleImageViewFuncionario)
        var textViewNombreSucursal: TextView = view.findViewById(R.id.textViewNombreSucursal)
        var textViewCapacidadMax: TextView = view.findViewById(R.id.textViewCapacidadMaxima)
        var textViewOcupacion: TextView = view.findViewById(R.id.textViewOcupacion)
        var textViewNombreFuncionario: TextView = view.findViewById(R.id.textViewNombreFuncionario)
        var textViewDisponible: TextView = view.findViewById(R.id.textViewDisponible)
    }

    override fun onBindViewHolder(holder: BranchOfficeAdapter_2.MyViewHolder, position: Int) {
        val item = listBranchOffice[position]

        var array = intArrayOf(
            R.drawable.woman_perfil, R.drawable.woman_profile_2, R.drawable.woman_profile_3,
            R.drawable.man_profile, R.drawable.man_profile_2, R.drawable.man_profile_3, R.drawable.man_profile_4)
        val randomNumber = (0 until array.size).random()

        holder.circleimageViewFuncionario.setImageResource(array[randomNumber])
        holder.textViewNombreSucursal.text = item.name
        holder.textViewCapacidadMax.text = "Cap. Máxima: " + item.maxCapacity
        holder.textViewOcupacion.text = "Ocupación: " + item.currentCapacity
        holder.textViewNombreFuncionario.text = "Funcionario: " + item.refUser
        holder.textViewDisponible.text = "Disponible: " + (item.maxCapacity - item.currentCapacity).toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchOfficeAdapter_2.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_sucursal_home, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listBranchOffice.size
    }
}
