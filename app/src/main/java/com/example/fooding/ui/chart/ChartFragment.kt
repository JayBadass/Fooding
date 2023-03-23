package com.example.fooding.ui.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.fooding.FoodingApplication
import com.example.fooding.data.source.ChartRepository
import com.example.fooding.databinding.FragmentChartBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import kotlinx.coroutines.launch

class ChartFragment : Fragment() {

    private val viewModel: ChartViewModel by viewModels {
        ChartViewModel.provideFactory(
            repository = ChartRepository(FoodingApplication.appContainer.providePostApiClient())
        )
    }

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setChart()
        setTodayNutrition()
    }

    private fun setTodayNutrition() {
        viewLifecycleOwner.lifecycleScope.launch {
            val nutrition = viewModel.getTodayNutrition()
            binding.nutrition = nutrition
        }
    }

    private fun setChart() {
        val chart: LineChart = binding.chart
        viewLifecycleOwner.lifecycleScope.launch {
            val data = viewModel.getRecentSevenDaysPost()
            chart.data = data
            chart.invalidate()
        }
        val xAxis: XAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.setDrawLabels(false)
        val yAxis: YAxis = chart.axisLeft
        yAxis.granularity = 300.0f
        val rightYAxis: YAxis = chart.axisRight
        rightYAxis.setDrawLabels(false)
    }

}