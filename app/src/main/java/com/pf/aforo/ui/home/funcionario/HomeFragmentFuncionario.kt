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
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.databinding.FragmentHomeFuncionarioBinding

//Imports de librerias para los graficos:
import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.pf.aforo.data.model.DataBranchOfficeHistory
import com.github.mikephil.charting.data.PieData as PieData



class HomeFragmentFuncionario : Fragment(R.layout.fragment_home_funcionario) {
    private lateinit var binding: FragmentHomeFuncionarioBinding
    private lateinit var homeFuncionarioViewModel: HomeFuncionarioViewModel
    lateinit var branchOfficeId: String
    private val UNAUTHORIZED_CODE: String = "401"
    private val OCCUPIED_TXT: String = "Ocupación"
    private val AVAILABLE_TXT: String = "Disponible"
    lateinit var piechart: PieChart
    lateinit var barChar: BarChart


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        branchOfficeId = arguments?.get("refBranchOffice").toString()
        binding = FragmentHomeFuncionarioBinding.bind(view)
        piechart = binding.piechart
        barChar = binding.barChart
        homeFuncionarioViewModel = ViewModelProvider(this).get(HomeFuncionarioViewModel::class.java)
        setTopBar()
        getBranchOfficeById()
        getHistoricData()
        setObservers()
        setBarCharValues()
    }

    private fun getHistoricData() {
        homeFuncionarioViewModel.getBranchOfficeHistory("Bearer ${getToken()}", branchOfficeId)
    }

    private fun setObservers() {
        //Current data observers
        homeFuncionarioViewModel.getBranchOfficeByIdResponse.observe(viewLifecycleOwner, this.getBranchOfficeByIdSuccessObserver)
        homeFuncionarioViewModel.getBranchOfficeByIdFailureResponse.observe(viewLifecycleOwner, this.getBranchOfficeByIdFailureObserver)
        //Historic data observers
        homeFuncionarioViewModel.getBranchOfficeHistoryResponse.observe(viewLifecycleOwner, this.getBranchOfficeHistorySuccessObserver)
        homeFuncionarioViewModel.getBranchOfficeHistoryFailureResponse.observe(viewLifecycleOwner, this.getBranchOfficeHistoryFailureObserver)
    }

    private val getBranchOfficeByIdSuccessObserver = Observer<BranchOffice> { branchOffice ->
        setHeaderUI(branchOffice)
        val occupied = getOccupiedPercentage(branchOffice.maxCapacity, branchOffice.currentCapacity)
        val available = 100 - occupied
        setPieChart(occupied, available)
    }

    private val getBranchOfficeHistorySuccessObserver = Observer<Array<DataBranchOfficeHistory>> { branchOffices ->
//        setBarCharValues(groupEventsByHour(branchOffices))
    }

    private fun groupEventsByHour(branchOffices: Array<DataBranchOfficeHistory>?): ArrayList<Int> {
        return ArrayList<Int>()
        //Agrupar los DataBranchOfficeHistory por hora del timestamp
        //Sumarizar los "value" de ese grupo
        //Agregar a la lista esa suma por cada hora
    }

    private fun getOccupiedPercentage(maxCapacity: Int, currentCapacity: Int): Int {
        return currentCapacity * 100 / maxCapacity
    }

    private fun onError(error: Any?){
        if(error == UNAUTHORIZED_CODE){
            Toast.makeText(context, "La sesión ha expirado.", Toast.LENGTH_SHORT).show()
            initLoginFragment()
        }
        else
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun setHeaderUI(branchOffice: BranchOffice) {
        binding.titulo.setText(branchOffice.name)
        binding.domicilio.setText(branchOffice.description)
        binding.funcionario.setText(arguments?.get("userFullName").toString())
        binding.ancho.setText(branchOffice.width.toString())
        binding.alto.setText(branchOffice.length.toString())
        binding.capmax.setText(branchOffice.maxCapacity.toString())
    }

    private val getBranchOfficeHistoryFailureObserver = Observer<Any?> { error ->
        onError(error)
    }

    private val getBranchOfficeByIdFailureObserver = Observer<Any?> { error ->
        onError(error)
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
        homeFuncionarioViewModel.getBranchOfficeById("Bearer ${getToken()}", branchOfficeId)
    }

    private fun getToken(): String {
        val sharedPref = context?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        return sharedPref?.getString("Token", "0").toString()
    }

    private fun setPieChart(occupied: Int, available: Int) {
        val xvalues = ArrayList<String>()
        xvalues.add(OCCUPIED_TXT)
        xvalues.add(AVAILABLE_TXT)

        // yvalues Estos valores son los que nos devuelve en tiempo real
        var piechartentry = ArrayList<Entry>()
        piechartentry.add(Entry(occupied.toFloat(), 0))
        piechartentry.add(Entry(available.toFloat(), 1))

        // colors los indices se manejan como paralelos
        val colors = ArrayList<Int>()
        colors.add(Color.RED)
        colors.add(Color.GREEN)

        // fill the chart
        val piedataset = PieDataSet(piechartentry, "")
        //piedataset.color = resources.getColor(R.color.green) otra opción de dar un solo color
        piedataset.colors = colors
        piedataset.sliceSpace = 3f
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

    private fun setBarCharValues(/*values: ArrayList<Int>*/) {
        // x axis values
        val xvalues = ArrayList<String>()

        //Agregamos las horas
        for (i in 1..24) {
            xvalues.add("$i hs")
        }

        // y axis values or bar data
        val yaxis = arrayOf<Float>(2.0f, 8.2f, 6f, 7.8f, 13.4f, 8f, 5f, 2.0f, 6f, 5.5f, 7.8f, 3.4f, 8f, 5f, 4.7f, 1.5f, 3.5f, 4f, 3.7f, 7.3f, 1.9f, 2.3f, 4.4f, 6f) //Array que me llega

        // bar entries
        val barentries = ArrayList<BarEntry>()
        for (i in 0..yaxis.size - 1) {
            barentries.add(BarEntry(yaxis[i], i))
        }

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


