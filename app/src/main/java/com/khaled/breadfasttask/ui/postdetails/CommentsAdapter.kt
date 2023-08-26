package com.khaled.breadfasttask.ui.postdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.khaled.breadfasttask.data.model.Comment
import com.khaled.breadfasttask.data.model.Post
import com.khaled.breadfasttask.databinding.AdapterCommentItemBinding
import com.khaled.breadfasttask.databinding.AdapterPostItemBinding

class CustomViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

class CommentsAdapter : ListAdapter<Comment, CustomViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCommentItemBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = getItem(position)
        val currentBinding = holder.binding as AdapterCommentItemBinding
        currentBinding.apply {
            userName.text = currentItem.name
            commentContent.text = currentItem.body
        }
    }

}