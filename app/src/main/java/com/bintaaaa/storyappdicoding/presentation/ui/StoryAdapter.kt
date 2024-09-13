package com.bintaaaa.storyappdicoding.presentation.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bintaaaa.storyappdicoding.data.models.resposne.ListStoryItem
import com.bintaaaa.storyappdicoding.databinding.ItemStoryBinding
import com.bumptech.glide.Glide

class StoryAdapter(): ListAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(item: ListStoryItem){
            binding.tvItemName.text = item.name
            Glide.with(itemView.context).load(item.photoUrl).into(binding.ivItemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        holder.binding(story)
    }
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ListStoryItem> =
            object : DiffUtil.ItemCallback<ListStoryItem>() {
                override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                    return oldItem.name == newItem.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                    return oldItem == newItem
                }
            }
    }
}