package com.example.unit6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.unit6.databinding.FragmentWelcomeFragementBinding


class WelcomeFragement : Fragment() {

    private lateinit var binding: FragmentWelcomeFragementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeFragementBinding.inflate(inflater, container, false)
        binding.gotoLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragement_to_loginFragement)
        }
        binding.gotoSignup.setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragement_to_signupFragment)
        }
        return binding.root
    }

}