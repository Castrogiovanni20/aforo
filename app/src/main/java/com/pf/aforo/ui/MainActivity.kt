package com.pf.aforo.ui

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pf.aforo.R
import kotlinx.android.synthetic.main.fragment_home_funcionario_2.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Modelos de alertas
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.rojosemaforo)
        builder.setTitle("ALERTA")
        builder.setMessage("LA CAPACIDAD DE AFORO ESTÁ CASI EN EL LÍMITE, POR FAVOR TOMAR MEDIDAS CONTINGENTES...")
        builder.setPositiveButton("INFORMADO", { dialogInterface: DialogInterface, i ->
            // Ver la leyenda de INFORMADO o poner ACEPTAR, usar el semáforo como icono
            // Acá podemos redireccionar a otro código
        })
        // builder.setNegativeButton("CANCELAR", { dialogInterface:DialogInterface, i -> }) Posibilidad de otro botón para ejecución alternativa
        builder.show()

        // Confirmar eliminación de la sucursal
        val builder_sucursal = AlertDialog.Builder(this)
        builder_sucursal.setIcon(R.drawable.icono2)
        builder_sucursal.setMessage("¿Confirma la eliminación de esta sucursal?")
        builder_sucursal.setPositiveButton("ACEPTAR", {dialogInterface: DialogInterface?, which: Int ->
            // Colocar la lógica de eliminación de sucursal
        })
        builder_sucursal.setNegativeButton("CANCELAR", {dialog: DialogInterface?, which: Int ->
            // No hace nada porque no elimina
        })
    }

}