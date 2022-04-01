package com.mlkit.sample.apicallsample.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlkit.sample.apicallsample.R
import com.mlkit.sample.apicallsample.adapter.MainActivityAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    val viewModel:MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerview()
    }

    private fun setUpRecyclerview() {
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        recyclerView.hasFixedSize()
        viewModel.getLiveData.observe(this){
            recyclerView.adapter= MainActivityAdapter(it)
        }


    }
}