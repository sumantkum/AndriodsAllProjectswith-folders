package com.example.unit3app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class Fragm1 : Fragment() {

    private lateinit var btnEnter: Button
    private lateinit var etInput: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_fragm1, container, false)

        btnEnter = rootView.findViewById(R.id.btnEnter)
        etInput = rootView.findViewById(R.id.etInput)

        btnEnter.setOnClickListener {
            val data = etInput.text.toString()
            (activity as? onPassData)?.onDataPass(data)
        }

        return rootView
    }
}
