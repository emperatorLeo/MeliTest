package com.example.melitest.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.melitest.R
import com.example.melitest.commons.fixUrl
import com.example.melitest.databinding.DetailItemFragmentBinding

class DetailItemFragment : Fragment() {

    private var _binding: DetailItemFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: DetailItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailItemFragmentBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.tvDetailState.text = args.detailState
        binding.tvDetailTitle.text = args.detailTitle
        Glide.with(requireContext())
            .load(args.detailImage.fixUrl())
            .into(binding.imgDetailProduct)
        binding.tvDetailPrice.text = getString(R.string.detail_formatter_price, args.detailPrice)
    }


}