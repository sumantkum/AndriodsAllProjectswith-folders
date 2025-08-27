package com.example.unit3app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Fragm2 : Fragment() {
    private lateinit var tvDisplay: TextView
    private var inputData: String = "No data received"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_fragm2, container, false)

        tvDisplay = rootView.findViewById(R.id.tvDisplay)

        inputData = arguments?.getString("Inputdata") ?: "No data received"
        tvDisplay.text = inputData

        return rootView
    }
}
