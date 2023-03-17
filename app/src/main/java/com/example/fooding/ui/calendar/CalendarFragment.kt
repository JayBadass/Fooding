package com.example.fooding.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooding.FoodingApplication
import com.example.fooding.data.model.Post
import com.example.fooding.data.source.CalendarRepository
import com.example.fooding.databinding.FragmentCalendarBinding
import com.example.fooding.ui.common.PostClickListener
import com.example.fooding.util.DateFormatText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment(), PostClickListener {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CalendarViewModel by viewModels {
        CalendarViewModel.provideFactory(
            repository = CalendarRepository(FoodingApplication.appContainer.providePostApiClient())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarViewModel = viewModel
        setLayout()
        setDateClick()
        setDateText()
//        setCalendarEvent()
    }

    private fun setLayout() {
        val adapter = PostListAdapter(this)
        binding.rvPostList.adapter = adapter
        binding.rvPostList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setDateClick() {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startDateString = "2023-03-01"
        val startDate = format.parse(startDateString)
        val adapter = PostListAdapter(this)
        binding.rvPostList.adapter = adapter
        binding.calendarView.minDate = startDate!!.time
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val dateString = "$year-${month + 1}-$dayOfMonth"
            val dateLong = format.parse(dateString)!!.time
            Log.d("date", dateString)
            binding.textLocalDate.text = dateString
            lifecycleScope.launch {
                val postList = viewModel.loadPost(dateLong)
                adapter.submitList(postList)
            }
        }
    }

    private fun setDateText() {
        val localDate = DateFormatText.getCurrentDate()
        binding.textLocalDate.text = localDate
    }

//    private fun setCalendarEvent() {
//        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//        val startDate = format.parse("2023-03-01")?.time
//        val endDate = format.parse(DateFormatText.getCurrentDate())?.time
//        val viewList = mutableListOf<View>()
//        lifecycleScope.launch {
//            val postList: List<Post> = viewModel.updateCalendarEvent()
//            for (i in startDate!!..endDate!!) {
//                for (element in postList) {
//                    if (i == element.date) {
//                        binding.calendarView.date = i
//                        binding.calendarView.setOnDateChangeListener { view, _, _, _ ->
//                            viewList.add(view)
//                            Log.d("viewList","$viewList")
//                        }
//                    }
//                }
//            }
//        }
//        for (i in 0 until viewList.size) {
//            Log.d("viewList", "$viewList")
//            viewList[i].setBackgroundColor(Color.RED)
//        }
//    }

    override fun onPostClick(post: Post) {
        TODO("Not yet implemented")
    }
}