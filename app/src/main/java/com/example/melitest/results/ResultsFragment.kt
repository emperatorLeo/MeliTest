package com.example.melitest.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.melitest.R
import com.example.melitest.api.MeliResponse
import com.example.melitest.commons.observe
import com.example.melitest.databinding.ResultsFragmentBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ResultsFragment : Fragment() {

    private var _binding: ResultsFragmentBinding? = null
    private val args : ResultsFragmentArgs by navArgs()
    private val viewModel : ResultsViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.performSearch(args.query)
        _binding = ResultsFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initListeners(){
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun initObservers(){
        observe(viewModel.meliResponse,this::showResults)
    }

    private fun showResults(meliResponse: MeliResponse){
        binding.tvQuery.text = meliResponse.meliItemResponseList[0].title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}