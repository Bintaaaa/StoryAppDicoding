package com.bintaaaa.storyappdicoding.presentation.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bintaaaa.storyappdicoding.data.models.resposne.StoryItem
import com.bintaaaa.storyappdicoding.databinding.ItemStoryBinding
import com.bumptech.glide.Glide
import com.bintaaaa.storyappdicoding.common.`interface`.ClickListener as ClickListener1

class StoryAdapter : PagingDataAdapter<StoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onClickItem: ClickListener1<StoryItem>

    class MyViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binding(item: StoryItem){
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
        holder.itemView.setOnClickListener {
            onClickItem.onItemClick(story!!)
        }
        holder.binding(story!!)
    }
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<StoryItem> =
            object : DiffUtil.ItemCallback<StoryItem>() {
                override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                    return oldItem.name == newItem.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                    return oldItem == newItem
                }
            }
    }

     fun onSetItemClick(item: ClickListener1<StoryItem>){
        this.onClickItem = item
    }
}