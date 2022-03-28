package com.example.melitest.results.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.melitest.api.MeliResponse
import com.example.melitest.commons.observe
import com.example.melitest.databinding.ResultsFragmentBinding
import com.example.melitest.results.viewmodel.ResultsViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ResultsFragment : Fragment() {

    private var _binding: ResultsFragmentBinding? = null
    private val args : ResultsFragmentArgs by navArgs()
    private val viewModel : ResultsViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var meliAdapter: MeliAdapter

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
        initViews()
    }

    private fun initViews(){
        meliAdapter = MeliAdapter(mutableListOf())
        binding.rvSearchResults.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = meliAdapter
        }
    }

    private fun initListeners(){
        binding.rvSearchResults.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(recyclerView.canScrollVertically(-1)){
                    /**Paginacion **/
                }
            }
        })
    }

    private fun initObservers(){
        observe(viewModel.meliResponse,this::showResults)
    }

    private fun showResults(meliResponse: MeliResponse){
      meliAdapter.addData(meliResponse.meliItemResponseList)
      meliAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}