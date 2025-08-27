package com.example.unit3app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class ReceiverFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_receiver_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.textView)

        viewModel.message.observe(viewLifecycleOwner) { msg ->
            textView.text = msg
        }

        return view
    }
}







// Develpo a grocey store shooping where user can browser through the different cotegories of product eg fruits , vegitable dariy etc.display grid view  each product shoulde be have image name price and And Add to cart button implement a cart features where
// can implement explicit intent their selectedd items and proced to checkout. upon sucessfull checkout display confirm mesg with total price paid using custom toast  give me simple code in Andriod Kotlin