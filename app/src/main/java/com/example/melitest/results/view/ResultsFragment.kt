package com.example.melitest.results.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.melitest.R
import com.example.melitest.api.MeliItem
import com.example.melitest.api.MeliResponse
import com.example.melitest.commons.OnItemClickListener
import com.example.melitest.commons.observe
import com.example.melitest.databinding.ResultsFragmentBinding
import com.example.melitest.results.model.ResultLoading
import com.example.melitest.results.model.ResultNotFoundError
import com.example.melitest.results.model.ResultServerError
import com.example.melitest.results.model.ResultsState
import com.example.melitest.results.viewmodel.ResultsViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ResultsFragment : Fragment() {

    private var _binding: ResultsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: ResultsFragmentArgs by navArgs()
    private val viewModel: ResultsViewModel by viewModels()

    private lateinit var meliAdapter: MeliAdapter

    companion object {
        const val STATE = "detailState"
        const val TITLE = "detailTitle"
        const val IMAGE = "detailImage"
        const val PRICE = "detailPrice"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ResultsFragmentBinding.inflate(inflater, container, false)

        if (isThereInternetConnection())
            viewModel.performSearch(args.query)
        else showNoInternetConnectionError()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        initViews()
    }

    private fun initViews() {
        meliAdapter = MeliAdapter(mutableListOf(), object : OnItemClickListener {
            override fun onItemClickListener(meliItem: MeliItem) {
                findNavController().navigate(
                    R.id.action_ResultFragment_to_DetailFragment,
                    bundleOf(
                        STATE to meliItem.condition,
                        TITLE to meliItem.title,
                        IMAGE to meliItem.thumbnail,
                        PRICE to meliItem.price.toFloat()
                    )
                )
            }
        })

        binding.rvSearchResults.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = meliAdapter
        }


    }

    private fun initListeners() {
        binding.rvSearchResults.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!binding.rvSearchResults.canScrollVertically(1)) {
                    viewModel.performSearch()
                }
            }
        })
    }

    private fun initObservers() {
        observe(viewModel.meliResponse, this::showResults)
        observe(viewModel.resultsState, this::showState)
    }

    private fun showState(state: ResultsState) {
        when (state) {
            is ResultLoading -> showLoading(state.showLoading)
            is ResultNotFoundError -> showCouldNotFindError()
            is ResultServerError -> showServerError()
        }
    }

    private fun showLoading(makeLoaderVisible: Boolean) {
        if (makeLoaderVisible)
            binding.lavLoading.visibility = View.VISIBLE
        else binding.lavLoading.visibility = View.GONE
    }

    private fun showCouldNotFindError() {
        binding.lavError.apply {
            this.setAnimation(R.raw.not_found_animation)
            this.visibility = View.VISIBLE
            this.repeatCount = Animation.INFINITE
        }
        binding.tvMessageError.apply {
            this.text = context.getString(R.string.could_not_find_error)
            this.visibility = View.VISIBLE
        }
    }

    private fun showNoInternetConnectionError() {
        binding.lavError.apply {
            this.setAnimation(R.raw.no_internet)
            this.visibility = View.VISIBLE
            this.repeatCount = Animation.INFINITE
        }
        binding.tvMessageError.apply {
            this.text = context.getString(R.string.network_connection_error)
            this.visibility = View.VISIBLE
        }
    }

    private fun showServerError() {
        binding.lavError.apply {
            this.setAnimation(R.raw.internal_server_error)
            this.visibility = View.VISIBLE
            this.repeatCount = Animation.INFINITE
        }
        binding.tvMessageError.apply {
            this.text = context.getString(R.string.server_error)
            this.visibility = View.VISIBLE
        }
    }

    private fun showResults(meliResponse: MeliResponse) {
        meliAdapter.addData(meliResponse.meliItemResponseList)
        meliAdapter.notifyDataSetChanged()
    }

    private fun isThereInternetConnection(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}