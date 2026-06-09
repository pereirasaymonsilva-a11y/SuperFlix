package com.superflix.app.ui.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.superflix.app.R
import com.superflix.app.data.api.SuperFlixApi
import com.superflix.app.data.repositories.CalendarRepository
import kotlinx.coroutines.launch

class CalendarActivity : AppCompatActivity() {
    
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private val api = SuperFlixApi()
    private lateinit var calendarRepo: CalendarRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        
        calendarRepo = CalendarRepository(api)
        
        tabLayout = findViewById(R.id.tabLayout)
        recyclerView = findViewById(R.id.calendarRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        loadCalendar()
    }
    
    private fun loadCalendar() {
        lifecycleScope.launch {
            val calendar = calendarRepo.getCalendar()
            // TODO: Parse and display calendar data
        }
    }
}