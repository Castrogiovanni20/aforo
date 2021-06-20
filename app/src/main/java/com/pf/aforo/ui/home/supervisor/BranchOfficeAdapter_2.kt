package com.pf.aforo.ui.home.supervisor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice

internal class BranchOfficeAdapter_2(private var listBranchOffice: ArrayList<BranchOffice>) :
    RecyclerView.Adapter<BranchOfficeAdapter_2.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewNombreSucursal: TextView = view.findViewById(R.id.textViewNombreSucursal)
        var textViewCapacidadMax: TextView = view.findViewById(R.id.textViewCapacidadMaxima)
        var textViewOcupacion: TextView = view.findViewById(R.id.textViewOcupacion)
        var textViewNombreFuncionario: TextView = view.findViewById(R.id.textViewNombreFuncionario)
        var textViewDisponible: TextView = view.findViewById(R.id.textViewDisponible)
        var imgButtonDetail: ImageButton = view.findViewById(R.id.imageButtonDetail)
        var lightsGraphic : ImageView = view.findViewById(R.id.lights)
    }

    override fun onBindViewHolder(holder: BranchOfficeAdapter_2.MyViewHolder, position: Int) {
        val item = listBranchOffice[position]
        val maxCapacity = "CAP. Máxima: ${item.maxCapacity}"
        val currentCapacity = "Ocupación: ${item.currentCapacity}"
        holder.textViewNombreSucursal.text = item.name
        holder.textViewCapacidadMax.text = maxCapacity
        holder.textViewOcupacion.text = currentCapacity
        holder.textViewNombreFuncionario.text = "Funcionario: " + item?.refUser
        holder.textViewDisponible.text = "Disponible: " + (item.maxCapacity - item.currentCapacity).toString()
        setLights(holder, item.maxCapacity, item.currentCapacity)

        holder.imgButtonDetail.setOnClickListener { view ->
            view.findNavController().navigate(HomeFragmentSupervisorDirections.actionHomeFragmentSupervisorToHomeFragmentFuncionario(item.refUser, item.id))
        }
    }

    private fun setLights(holder: BranchOfficeAdapter_2.MyViewHolder, maxCapacity: Int, currentCapacity: Int) {
        val occupied = currentCapacity.toDouble() * 100 / maxCapacity.toDouble()
        when {
            occupied <= 60 -> holder.lightsGraphic.setImageResource(R.mipmap.icon_smgreen)
            occupied > 60 && occupied <= 80 -> holder.lightsGraphic.setImageResource(R.mipmap.icon_smgyell)
            occupied > 80 -> holder.lightsGraphic.setImageResource(R.mipmap.icon_smfred)
        }
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
