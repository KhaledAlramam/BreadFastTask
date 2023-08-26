package com.khaled.breadfasttask.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.khaled.breadfasttask.data.model.Post
import com.khaled.breadfasttask.databinding.AdapterPostItemBinding

class CustomViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

class PostAdapter(val onClick: (Post) -> Unit) : ListAdapter<Post, CustomViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterPostItemBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        val currentBinding = holder.binding as AdapterPostItemBinding
        currentBinding.apply {
            includePostHeader.postTitle.text = currentItem.title
            includePostHeader.postContent.text = currentItem.body
            root.setOnClickListener { onClick(currentItem) }
        }
    }

}