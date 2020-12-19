package com.example.finalproject

import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.fragments.TimeFragment
import com.example.finalproject.fragments.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_bus_information.*

class BusInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_information)
        val actionbar = supportActionBar
        actionbar!!.title = "公車動態介面"
        actionbar.setDisplayHomeAsUpEnabled(true)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.lunch,
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = adapter
        setUpTabs()
    }
    private fun setUpTabs(){
        val adapter=ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(TimeFragment(), "往起點")
        adapter.addFragment(TimeFragment(), "往終點")
        viewPager.adapter=adapter
        tabs.setupWithViewPager(viewPager)
    }
    //Create Refresh Button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    //Click Back Button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}