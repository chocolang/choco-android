package com.chocolang.android.chocoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolang.android.chocoapp.databinding.ViewPersonBinding
import com.chocolang.android.chocoapp.repository.model.GitRepository
import com.chocolang.android.chocoapp.repository.ui.activity.ARG_REPO_ID
import com.chocolang.android.chocoapp.repository.ui.activity.RepositoryActivity

class ListAdapter(private val context: Context) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private lateinit var binding: ViewPersonBinding
    private val items: ArrayList<GitRepository> = arrayListOf()

    fun add(item: GitRepository) {
        this.items.add(item)
        notifyItemInserted(this.items.size)
    }

    fun addAll(items: List<GitRepository>) {
        val preSize = items.size
        this.items.addAll(items)
        notifyItemRangeChanged(preSize, items.size)
    }

    /** default */
//    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        var itemPhoto: ImageView = itemView.findViewById(R.id.iv_person_photo)
//        var itemName: TextView = itemView.findViewById(R.id.tv_person_name)
//        var itemLocation: TextView = itemView.findViewById(R.id.tv_person_location)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.view_person,
            parent,
            false
        )
        return ViewHolder(binding)
        /** default */
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_person, parent, false)
//        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindUpdate(items[position])

        /** default */
//        holder.itemName.text = items[position].id.toString()
//        holder.itemLocation.text = items[position].name
//        Glide.with(context)
//            .load(items[position].owner.avatar_url)
//            .into(holder.itemPhoto)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: ViewPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindUpdate(item: GitRepository) {
            binding.setVariable(BR.myRepository, item)
            binding.setVariable(BR.uiController, this)
            Glide.with(context)
                .load(item.owner.avatar_url)
                .into(binding.ivPersonPhoto)
            binding.root.setOnClickListener {
                context.startActivity(Intent(context, RepositoryActivity::class.java).apply {
                    putExtra(ARG_REPO_ID, item.id)
                })
            }
        }

//        fun onClickRepositoryItem() {
//            context.startActivity(Intent(context, RepositoryActivity::class.java))
//        }
    }
}