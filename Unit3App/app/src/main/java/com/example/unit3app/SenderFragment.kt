package com.example.unit3app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class SenderFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_sender_fragment, container, false)

        val input = view.findViewById<EditText>(R.id.editText)
        val sendButton = view.findViewById<Button>(R.id.sendButton)

        sendButton.setOnClickListener {
            val message = input.text.toString()
            viewModel.setMessage(message)
        }

        return view
    }
}
