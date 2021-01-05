package com.nuveq.sojibdemo.feature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.nuveq.sojibdemo.R
import com.nuveq.sojibdemo.model.FakeData

class FakeAdapter(private val interaction: Interaction? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FakeData>() {

        override fun areItemsTheSame(oldItem: FakeData, newItem: FakeData): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(oldItem: FakeData, newItem: FakeData): Boolean {
            return oldItem.image == newItem.image
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VocationalTrainingHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false),
                interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VocationalTrainingHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<FakeData>) {
        differ.submitList(list)
    }

    class VocationalTrainingHolder constructor(itemView: View, private val interaction: Interaction?) :
            RecyclerView.ViewHolder(itemView) {

        fun bind(item: FakeData) {
            itemView.setOnClickListener {
                interaction!!.onItemSelected(layoutPosition)
            }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int)
    }
}