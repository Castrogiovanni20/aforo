package com.pf.aforo.ui.home.supervisor

import android.os.Bundle
import android.text.Html
import android.text.Html.fromHtml
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

internal class BranchOfficeAdapter_1(private var listBranchOffice: ArrayList<BranchOffice>) :
    RecyclerView.Adapter<BranchOfficeAdapter_1.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageBranchOffice: CircleImageView = view.findViewById(R.id.circleImageViewProfile)
        var textViewName: TextView = view.findViewById(R.id.textViewNombre)
        var textViewMt2ancho: TextView = view.findViewById(R.id.textViewMt2ancho)
        var textViewMt2alto: TextView = view.findViewById(R.id.textViewMt2alto)
        var textViewCapacity: TextView = view.findViewById(R.id.textViewCapacidadActual)
        var textViewFuncionario: TextView = view.findViewById(R.id.textViewFuncionarioAsignado)
        var imageBtnEdit: ImageButton = view.findViewById(R.id.imageButtonEditarSucursal)
    }

    override fun onBindViewHolder(holder: BranchOfficeAdapter_1.MyViewHolder, position: Int) {
        val item = listBranchOffice[position]
        holder.textViewName.text = item.name
        holder.textViewMt2ancho.text = fromHtml("Ancho: <i>" + item.width + "</i>")
        holder.textViewMt2alto.text = fromHtml("Largo: <i>" + item.length + "</i>")
        holder.textViewCapacity.text = fromHtml("Capacidad actual: <i>" + item.maxCapacity.toString() + "</i>")
        holder.textViewFuncionario.text = fromHtml("Responsable: <i>" + item.refUser + "</i>")

        holder.imageBtnEdit.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putParcelable("BranchOffice", listBranchOffice[position])
            view.findNavController().navigate(R.id.action_sucursalesSupervisorFragment_to_editSucursalFragment, bundle)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchOfficeAdapter_1.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_sucursal, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listBranchOffice.size
    }
}
