package com.example.melitest.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.example.melitest.R
import com.example.melitest.databinding.SearchFragmentBinding
import com.google.android.material.snackbar.Snackbar


class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null

    companion object {
        const val QUERY = "query"
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.svSearcher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                goToResultsFragment()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.btnSearch.setOnClickListener {
            goToResultsFragment()
        }
    }

    private fun goToResultsFragment() {
        if(binding.svSearcher.query.isNullOrEmpty()){
            Snackbar.make(this.requireView(),"debes insertar un objeto",Snackbar.LENGTH_SHORT).show()
        }else {
            findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment,
                bundleOf(QUERY to binding.svSearcher.query.toString())
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}