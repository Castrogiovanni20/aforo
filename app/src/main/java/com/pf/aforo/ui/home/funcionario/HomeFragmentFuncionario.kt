package com.pf.aforo.ui.home.funcionario

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.pf.aforo.R
import com.pf.aforo.databinding.FragmentHomeFuncionarioBinding

import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.data.PieData as PieData



class HomeFragmentFuncionario : Fragment(R.layout.fragment_home_funcionario) {
    // private lateinit var binding: FragmentHomeFuncionarioBinding
    private lateinit var binding2: FragmentHomeFuncionarioBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // binding = FragmentHomeFuncionarioBinding.bind(view)
        binding2 = FragmentHomeFuncionarioBinding.bind(view)


        setPieChart()
        setTopBar()



    }

    private fun setTopBar() {
        // binding.topAppBar....
        binding2.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemNotificaciones -> {
                    initNotificacionesFragments()
                    true
                }
                R.id.itemCerrarSesion -> {
                    initLoginFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun initLoginFragment() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        findNavController().navigate(R.id.action_homeFragmentFuncionario_to_loginFragment)
    }

    private fun initNotificacionesFragments() {
        findNavController().navigate(R.id.action_homeFragmentFuncionario_to_notificacionesFragment)
    }

    fun setPieChart() {

        lateinit var piechart: PieChart
        // xvalues
        val xvalues = ArrayList<String>()
        xvalues.add("Ocupación")
        xvalues.add("Disponible")

        // yvalues Estos valores son los que nos devuelve en tiempo real
        var piechartentry = ArrayList<Entry>()
        piechartentry.add(Entry(43f, 0))
        piechartentry.add(Entry(55f, 1))

        //piechartentry.add(Entry(valorRecibidoDeOcupacion, 0))
        //piechartentry.add(Entry(valorCalculadoDeEspacioDisponible, 1)) EL VALOR EXPRESARLO EN CANTIDAD DE PERSONAS  O PORCENTAJE ?

        // colors los indices se manejan como paralelos
        val colors = ArrayList<Int>()
        colors.add(Color.RED)
        colors.add(Color.GREEN)

        // fill the chart
        val piedataset = PieDataSet(piechartentry, "Aforo Actual")

//        piedataset.color = resources.getColor(R.color.green) otra opción de dar un solo color
        piedataset.colors = colors

        piedataset.sliceSpace = 3f

        //prueba de textsize
        piedataset.valueTextSize = 20f

        val data = PieData(xvalues, piedataset)
        piechart.data = data

        piechart.holeRadius = 5f
        //piechart.setBackgroundColor(resources.getColor(R.color.white))
        piechart.setBackgroundColor(Color.WHITE)

        piechart.setDescription("Energy Consumption in 2020")
        piechart.animateY(3000)

        val legend: Legend = piechart.legend
        legend.position = Legend.LegendPosition.LEFT_OF_CHART
        //legend.textColor = resources.getColor(R.color.white)
        legend.textColor = Color.CYAN
        legend.textSize = 18f // OTRA PRUEBA DE TEXT SIZE
    }

}

// Este es el código para implementar el barchart y piechart


//lateinit var barChar: BarChart

/*override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    piechart = findViewById(R.id.piechart)
    barChar = findViewById(R.id.barChart)

    //setBarCharValues()


}*/



/*
fun setBarCharValues() {
    // x axis values

    val xvalues = ArrayList<String>()

    xvalues.add("01")
    xvalues.add("02")
    xvalues.add("03")
    xvalues.add("04")
    xvalues.add("05")
    xvalues.add("06")
    xvalues.add("07")
    xvalues.add("08")
    xvalues.add("09")
    xvalues.add("10")
    xvalues.add("11")
    xvalues.add("12")
    xvalues.add("13")
    xvalues.add("14")
    xvalues.add("15")
    xvalues.add("16")
    xvalues.add("17")
    xvalues.add("18")
    xvalues.add("19")
    xvalues.add("20")
    xvalues.add("21")
    xvalues.add("22")
    xvalues.add("23")
    xvalues.add("24")


    // y axis values or bar data

    val yaxis = arrayOf<Float>(2.0f, 8.2f, 6f, 7.8f, 13.4f, 8f, 5f, 2.0f, 6f, 5.5f, 7.8f, 3.4f, 8f, 5f, 4.7f, 1.5f, 3.5f, 4f, 3.7f, 7.3f, 1.9f, 2.3f, 4.4f, 6f )

    // bar entries
    val barentries = ArrayList<BarEntry>()

    for ( i in 0..yaxis.size - 1) {
        barentries.add(BarEntry(yaxis[i], i))
    }
    *//*
    barentries.add(BarEntry(4f, 0))
    barentries.add(BarEntry(3.5f, 1))
    barentries.add(BarEntry(8.2f, 2))
    barentries.add(BarEntry(5.6f, 3))
    barentries.add(BarEntry(2f, 4))
    barentries.add(BarEntry(6f, 5))
    barentries.add(BarEntry(9f, 6))

     *//*

    // bardata set
    val bardataset = BarDataSet(barentries, "First")

    // make a bar data
    val data = BarData(xvalues, bardataset)

    barChar.data = data

    barChar.setBackgroundColor(resources.getColor(R.color.white))
    barChar.animateXY(3000,3000)*/


// }
