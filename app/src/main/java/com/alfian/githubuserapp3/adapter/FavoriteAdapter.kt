package com.alfian.githubuserapp3.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alfian.githubuserapp3.database.FavoriteEntity
import com.alfian.githubuserapp3.databinding.ItemUserFollowsBinding
import com.alfian.githubuserapp3.ui.detail.DetailActivity
import com.bumptech.glide.Glide


class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {
    private val userFavorites = ArrayList<FavoriteEntity>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView =
            ItemUserFollowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userFavorites[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(userFavorites[position]) }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListFavorite(items: List<FavoriteEntity>) {
        userFavorites.clear()
        userFavorites.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = userFavorites.size
    class MyViewHolder(private val binding: ItemUserFollowsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favEntity: FavoriteEntity) {
            with(binding) {
                name.text = favEntity.login
                Glide.with(root)
                    .load(favEntity.avatar_url)
                    .circleCrop()
                    .into(binding.circleImageView)
                root.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.KEY_USER, favEntity)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(favEntity: FavoriteEntity)
    }
}