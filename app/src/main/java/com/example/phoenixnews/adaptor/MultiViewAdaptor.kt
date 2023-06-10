package com.example.phoenixnews.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.phoenixnews.databinding.ItemPreViewBinding
import com.example.phoenixnews.databinding.ResponseoneLayoutBinding
import com.example.phoenixnews.model.ArticleItemTypes


const val header_viewtype = 0
const val article_viewtype = 1
class MultiViewAdaptor () : ListAdapter<ArticleItemTypes,RecyclerView.ViewHolder>(MyDiffCallback()) {



    inner class HeaderViewHolder(val binding: ResponseoneLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindrone (articleItemTypes: ArticleItemTypes.HeaderModel) {
            binding.tvauthor.text = articleItemTypes.author
        }

    }

    inner class ArticleViewHolder(val binding: ItemPreViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindrtwo (articleItemTypes: ArticleItemTypes.ArticleModel) {
            articleItemTypes?.let { item ->
                binding.apply {
                    Glide.with(root.context).load(item.urlToImage).into(ivArticleImage)
                    tvSource.text = item.source?.name
                    tvTitle.text = item.title
                    tvDescription.text = item.description
                    tvPublishedAt.text = item.publishedAt


                }
            }

        }


    }

    class MyDiffCallback : DiffUtil.ItemCallback<ArticleItemTypes>() {
        override fun areItemsTheSame(oldItem: ArticleItemTypes, newItem: ArticleItemTypes): Boolean {
            return when {
                oldItem is ArticleItemTypes.HeaderModel && newItem is ArticleItemTypes.HeaderModel ->
                    oldItem.author == newItem.author
                oldItem is ArticleItemTypes.ArticleModel && newItem is ArticleItemTypes.ArticleModel ->
                    oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ArticleItemTypes, newItem: ArticleItemTypes): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when ( val item = getItem(position)){
            is ArticleItemTypes.HeaderModel -> header_viewtype
            is ArticleItemTypes.ArticleModel -> article_viewtype
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == header_viewtype){
            val binding = ResponseoneLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            HeaderViewHolder(binding)
        } else {
            val binding = ItemPreViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            ArticleViewHolder(binding)
        }


    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> {
                val headerItem = item as ArticleItemTypes.HeaderModel
                holder.bindrone(headerItem)
            }
            is ArticleViewHolder -> {
                val articleItem = item as ArticleItemTypes.ArticleModel
                holder.bindrtwo(articleItem)

            }
        }
    }
}