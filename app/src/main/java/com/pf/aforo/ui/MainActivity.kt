package com.pf.aforo.ui

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pf.aforo.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.lights)
        builder.setTitle("ALERTA EN C5N!!!")
        builder.setMessage("LA CAPACIDAD DE AFORO ESTÁ CASI EN EL LÍMITE, POR FAVOR TOMAR MEDIDAS CONTINGENTES...")
        builder.setPositiveButton("INFORMADO", { dialogInterface: DialogInterface, i ->
            // Ver la leyenda de INFORMADO o poner ACEPTAR, usar el semáforo como icono
            // Acá podemos redireccionar a otro código
        })
        // builder.setNegativeButton("CANCELAR", { dialogInterface:DialogInterface, i -> }) Posibilidad de otro botón para ejecución alternativa
        builder.show()
    }

}