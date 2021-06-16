package com.pf.aforo.ui.home.funcionario

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.databinding.FragmentHomeFuncionarioBinding

//Imports de librerias para los graficos:
import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.data.PieData as PieData



class HomeFragmentFuncionario : Fragment(R.layout.fragment_home_funcionario) {
    private lateinit var binding: FragmentHomeFuncionarioBinding
    private lateinit var homeFuncionarioViewModel: HomeFuncionarioViewModel
    private val UNAUTHORIZED_CODE: String = "401"

    //GRAFICOS variables:
    lateinit var piechart: PieChart
    lateinit var barChar: BarChart


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeFuncionarioBinding.bind(view)
        homeFuncionarioViewModel = ViewModelProvider(this).get(HomeFuncionarioViewModel::class.java)
        setTopBar()
        getBranchOfficeById()
        getHistoricData()
        setObservers()

        //Graficos:
        //piechart = findViewById(R.id.piechart)
        //barChar = findViewById(R.id.barChart)
        piechart = binding.piechart
        barChar = binding.barChart

        setPieChart()
        setBarCharValues()
    }

    private fun getHistoricData() {
        //TODO: Implementar llamada a endpoint de historico. Crear observer que llame a setBarChart()
    }

    private fun setObservers() {
        homeFuncionarioViewModel.getBranchOfficeByIdResponse.observe(viewLifecycleOwner, this.getBranchOfficeByIdSuccessObserver)
        homeFuncionarioViewModel.getBranchOfficeByIdFailureResponse.observe(viewLifecycleOwner, this.getBranchOfficeByIdFailureObserver)
    }

    private val getBranchOfficeByIdSuccessObserver = Observer<BranchOffice> { branchOffice ->
        setHeaderUI(branchOffice)
    }

    private fun setHeaderUI(branchOffice: BranchOffice) {
        binding.titulo.setText(branchOffice.name)
        binding.domicilio.setText(branchOffice.description)
        binding.funcionario.setText(arguments?.get("userFullName").toString())
        binding.ancho.setText(branchOffice.width.toString())
        binding.alto.setText(branchOffice.length.toString())
        binding.capmax.setText(branchOffice.maxCapacity.toString())
    }

    private val getBranchOfficeByIdFailureObserver = Observer<Any?> { error ->
        if(error == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
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

    private fun getBranchOfficeById(){
        val refBranchOffice = arguments?.get("refBranchOffice").toString()
        homeFuncionarioViewModel.getBranchOfficeById("Bearer ${getToken()}", refBranchOffice)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    /////GRAFICOS/////////////////////////////////
    //Codigo para la Torta
    private fun setPieChart() {

        // xvalues
        val xvalues = ArrayList<String>()
        xvalues.add("Ocupación")
        xvalues.add("Disponible")


        // yvalues Estos valores son los que nos devuelve en tiempo real
        var piechartentry = ArrayList<Entry>()
        piechartentry.add(Entry(43.5f, 0))
        piechartentry.add(Entry(55.5f, 1))
        //piechartentry.add(Entry(68.5f, 2))
        //piechartentry.add(Entry(valorRecibidoDeOcupacion, 0))
        //piechartentry.add(Entry(valorCalculadoDeEspacioDisponible, 1)) EL VALOR EXPRESARLO EN CANTIDAD DE PERSONAS  O PORCENTAJE ?

        // colors los indices se manejan como paralelos
        val colors = ArrayList<Int>()
        colors.add(Color.RED)
        colors.add(Color.GREEN)
        //colors.add(Color.YELLOW)

        // fill the chart
        val piedataset = PieDataSet(piechartentry, "")

      //piedataset.color = resources.getColor(R.color.green) otra opción de dar un solo color
        piedataset.colors = colors

        piedataset.sliceSpace = 3f

        //prueba de textsize
        piedataset.valueTextSize = 12f

        val data = PieData(xvalues, piedataset)
        piechart.data = data

        piechart.holeRadius = 5f
        piechart.setBackgroundColor(resources.getColor(R.color.white))

        piechart.setDescription("")
        piechart.animateY(3000)

        val legend: Legend = piechart.legend
        legend.position = Legend.LegendPosition.LEFT_OF_CHART
        legend.textColor = resources.getColor(R.color.colorPrimaryDark)
        legend.textSize = 10f // OTRA PRUEBA DE TEXT SIZE
    }

    //Codigo para la Barra
    private fun setBarCharValues() {
        // x axis values

        val xvalues = ArrayList<String>()

        xvalues.add("1hs")
        xvalues.add("2hs")
        xvalues.add("3hs")
        xvalues.add("4hs")
        xvalues.add("5hs")
        xvalues.add("6hs")
        xvalues.add("7hs")
        xvalues.add("8hs")
        xvalues.add("9hs")
        xvalues.add("10hs")
        xvalues.add("11hs")
        xvalues.add("12hs")
        xvalues.add("13hs")
        xvalues.add("14hs")
        xvalues.add("15hs")
        xvalues.add("16hs")
        xvalues.add("17hs")
        xvalues.add("18hs")
        xvalues.add("19hs")
        xvalues.add("20hs")
        xvalues.add("21hs")
        xvalues.add("22hs")
        xvalues.add("23hs")
        xvalues.add("24hs")

        // y axis values or bar data

        val yaxis = arrayOf<Float>(2.0f, 8.2f, 6f, 7.8f, 13.4f, 8f, 5f, 2.0f, 6f, 5.5f, 7.8f, 3.4f, 8f, 5f, 4.7f, 1.5f, 3.5f, 4f, 3.7f, 7.3f, 1.9f, 2.3f, 4.4f, 6f )

        // bar entries
        val barentries = ArrayList<BarEntry>()

        for ( i in 0..yaxis.size - 1) {
            barentries.add(BarEntry(yaxis[i], i))
        }
        /*
        barentries.add(BarEntry(4f, 0))
        barentries.add(BarEntry(3.5f, 1))
        barentries.add(BarEntry(8.2f, 2))
        barentries.add(BarEntry(5.6f, 3))
        barentries.add(BarEntry(2f, 4))
        barentries.add(BarEntry(6f, 5))
        barentries.add(BarEntry(9f, 6))

         */

        // bardata set
        val bardataset = BarDataSet(barentries, "Horarios más concurridos por Hora")

        // make a bar data
        val data = BarData(xvalues, bardataset)

        barChar.data = data

        barChar.setDescription("")
        //barChar.setBackgroundColor(resources.getColor(R.color.white))
        barChar.animateXY(3000,3000)
    }
}


