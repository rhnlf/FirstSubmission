package com.rhnlf.firstsubmission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rhnlf.firstsubmission.activity.DetailActivity
import com.rhnlf.firstsubmission.data.User
import com.rhnlf.firstsubmission.databinding.ItemGithubBinding

class MainAdapter(private val listData: List<User>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

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
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = listData.size

    class ViewHolder(val binding: ItemGithubBinding) : RecyclerView.ViewHolder(binding.root)
}