package com.example.melitest.results.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.melitest.api.MeliItem
import com.example.melitest.commons.OnItemClickListener
import com.example.melitest.commons.fixUrl
import com.example.melitest.databinding.MeliItemBinding

class MeliAdapter(
    private val mProducts: MutableList<MeliItem>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MeliAdapter.MeliHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeliHolder {
        val binding = MeliItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return MeliHolder(binding)
    }

    override fun onBindViewHolder(holder: MeliHolder, position: Int) {
        with(holder) {
            with(mProducts[position]) {
                Glide.with(context)
                    .load(thumbnail.fixUrl())
                    .into(binding.imgItemProduct)
                binding.tvNameItemProduct.text = title
                binding.tvPriceItemProduct.text = price.toString()
            }
        }
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClickListener(mProducts[position])
        }
    }

    override fun getItemCount() = mProducts.size

    inner class MeliHolder(val binding: MeliItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun addData(products: List<MeliItem>) {
        mProducts.addAll(products)
    }

}