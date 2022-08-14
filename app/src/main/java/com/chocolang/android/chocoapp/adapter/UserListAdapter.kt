package com.chocolang.android.chocoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolang.android.chocoapp.R
import com.chocolang.android.chocoapp.repository.model.GitRepository
import com.chocolang.android.chocoapp.repository.model.GitUser

class UserListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: ArrayList<GitUser> = arrayListOf()

    fun add(item: GitUser) {
        this.items.add(item)
        notifyItemInserted(this.items.size)
    }

    fun addAll(items: List<GitUser>) {
        val preSize = items.size
        this.items.addAll(items)
        notifyItemRangeChanged(preSize, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_user, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as ViewHolder).updateUI(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivItemPhoto: ImageView = itemView.findViewById(R.id.iv_user_photo)
        var tvItemName: TextView = itemView.findViewById(R.id.tv_user_login)
        var tvItemId: TextView = itemView.findViewById(R.id.tv_user_id)
        var tvItemNodeId: TextView = itemView.findViewById(R.id.tv_user_node_id)
        var tvItemType: TextView = itemView.findViewById(R.id.tv_user_type)

        fun updateUI(item: GitUser) {
            Glide.with(itemView.context)
                .load(item.avatar_url)
                .into(ivItemPhoto)
            tvItemName.text = item.login
            tvItemId.text = item.id.toString()
            tvItemNodeId.text = item.node_id
            tvItemType.text = item.type
        }
    }
}