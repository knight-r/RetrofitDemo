package com.example.retrofitdemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retrofitdemo.R
import com.example.retrofitdemo.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var _binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View {
        _binding = FragmentSecondBinding.inflate(layoutInflater)
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupData()
    }

//    private fun setupData() {
//        binding.label.text = getString(R.string.second_fragment)
//    }

}