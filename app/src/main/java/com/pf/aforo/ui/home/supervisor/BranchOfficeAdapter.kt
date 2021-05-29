package com.pf.aforo.ui.home.supervisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice
import de.hdodenhof.circleimageview.CircleImageView

internal class BranchOfficeAdapter(private var listBranchOffice: ArrayList<BranchOffice>) :
    RecyclerView.Adapter<BranchOfficeAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageBranchOffice: CircleImageView = view.findViewById(R.id.circleImageViewProfile)
        var textViewName: TextView = view.findViewById(R.id.textViewNombre)
        var textViewBranchOffice: TextView = view.findViewById(R.id.textViewOrganizacion)
        var textViewMt2ancho: TextView = view.findViewById(R.id.textViewMt2ancho)
        var textViewMt2alto: TextView = view.findViewById(R.id.textViewMt2alto)
        var textViewCapacity: TextView = view.findViewById(R.id.textViewCapacidadActual)
        var textViewFuncionario: TextView = view.findViewById(R.id.textViewFuncionarioAsignado)
        var imageBtnEdit: ImageButton = view.findViewById(R.id.imageButtonEditarSucursal)

    }

    override fun onBindViewHolder(holder: BranchOfficeAdapter.MyViewHolder, position: Int) {
        val item = listBranchOffice[position]
        holder.textViewName.text = item.name
        holder.textViewBranchOffice.text = item.refOrganization
        holder.textViewMt2ancho.text = "Ancho: " + item.width
        holder.textViewMt2alto.text = "Alto: " + item.length
        holder.textViewCapacity.text = "Capacidad actual: " + item.maxCapacity.toString()
        holder.textViewFuncionario.text = "Funcionario asignado: no posee"

        holder.imageBtnEdit.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putParcelable("BranchOffice", listBranchOffice[position])
            view.findNavController().navigate(R.id.action_sucursalesSupervisorFragment_to_editSucursalFragment, bundle)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchOfficeAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_sucursal, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listBranchOffice.size
    }


}
