package com.pf.aforo.ui.home.funcionario

//Imports de librerias para los graficos:
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.pf.aforo.R
import com.pf.aforo.data.model.BranchOffice
import com.pf.aforo.data.model.DataBranchOfficeHistory
import com.pf.aforo.databinding.FragmentHomeFuncionarioBinding

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
//        getBranchOfficeById()
//        getHistoricData()
//        setObservers()
    }

    override fun onResume(){
        super.onResume()
        if(branchOfficeId != "null"){
            setObservers()
            getBranchOfficeById()
            getHistoricData()
        }
            //else- TODO: limpiar la pantalla, poner algun mensaje de "No tenes una sucursal asignada"
    }

    private fun getHistoricData() {
        homeFuncionarioViewModel.getBranchOfficeHistory("Bearer ${getToken()}", branchOfficeId)
    }

    private fun setObservers() {
        //Current data observers
        homeFuncionarioViewModel.getBranchOfficeByIdResponse.observe(
            viewLifecycleOwner,
            this.getBranchOfficeByIdSuccessObserver
        )
        homeFuncionarioViewModel.getBranchOfficeByIdFailureResponse.observe(
            viewLifecycleOwner,
            this.getBranchOfficeByIdFailureObserver
        )
        //Historic data observers
        homeFuncionarioViewModel.getBranchOfficeHistoryResponse.observe(
            viewLifecycleOwner,
            this.getBranchOfficeHistorySuccessObserver
        )
        homeFuncionarioViewModel.getBranchOfficeHistoryFailureResponse.observe(
            viewLifecycleOwner,
            this.getBranchOfficeHistoryFailureObserver
        )
    }

    private val getBranchOfficeByIdSuccessObserver = Observer<BranchOffice> { branchOffice ->
        setHeaderUI(branchOffice)
        val occupied = getOccupiedPercentage(branchOffice.maxCapacity, branchOffice.currentCapacity)
        val available = 100 - occupied
        setPieChart(occupied, available)
    }

    private val getBranchOfficeHistorySuccessObserver = Observer<Array<DataBranchOfficeHistory>> { histories ->
        //TODO: Convertir al timezone local
        setBarCharValues(groupEventsByHour(histories))
    }

    private fun groupEventsByHour(histories: Array<DataBranchOfficeHistory>): ArrayList<Float> {
        val valuesList = ArrayList<Float>()
        val groupedByHourEvents = histories.groupBy { bo -> bo.timestamp.hours }

        for(i in 0..23){
            val eventsOnIHour = groupedByHourEvents.filterValues { it[0].timestamp.hours == i }.flatMap { it.value }
            if(eventsOnIHour.isEmpty())
                valuesList.add(0f)
            else
                valuesList.add(eventsOnIHour.map { it.value.toFloat() }?.maxOrNull() ?: 0f)
        }
        return valuesList
    }

    private fun getOccupiedPercentage(maxCapacity: Int, currentCapacity: Int): Float {
        return (currentCapacity.toDouble() * 100 / maxCapacity.toDouble()).toFloat()
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

    private fun setPieChart(occupied: Float, available: Float) {
        val xvalues = ArrayList<String>()
        xvalues.add(OCCUPIED_TXT)
        xvalues.add(AVAILABLE_TXT)

        // yvalues Estos valores son los que nos devuelve en tiempo real
        var piechartentry = ArrayList<Entry>()
        piechartentry.add(Entry(occupied, 0))
        piechartentry.add(Entry(available, 1))

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

    private fun setBarCharValues(values: ArrayList<Float>) {
        // x axis values
        val xvalues = ArrayList<String>()
        //Agregamos las horas
        for (i in 0..23) {
            xvalues.add("$i hs")
        }
        // y axis values or bar data
        val yaxis = values

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
        barChar.animateXY(3000, 3000)
    }
}


