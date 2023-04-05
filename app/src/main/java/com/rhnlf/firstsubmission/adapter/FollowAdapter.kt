package com.rhnlf.firstsubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rhnlf.firstsubmission.data.remote.response.FollowResponse
import com.rhnlf.firstsubmission.databinding.ItemGithubBinding

class FollowAdapter(private val listData: List<FollowResponse>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listData[position]

        holder.binding.tvUsername.text = user.login

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.binding.ivProfile)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listData[holder.adapterPosition])
        }
    }

    override fun getItemCount() = listData.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FollowResponse)
    }

    inner class ViewHolder(val binding: ItemGithubBinding) : RecyclerView.ViewHolder(binding.root)
}