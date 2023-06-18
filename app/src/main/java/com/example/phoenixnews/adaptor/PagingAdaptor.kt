package com.example.phoenixnews.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.phoenixnews.databinding.ItemPreViewBinding
import com.example.phoenixnews.databinding.ResponseoneLayoutBinding
import com.example.phoenixnews.model.ArticleItemTypes

class PagingAdaptor :
    PagingDataAdapter<ArticleItemTypes, RecyclerView.ViewHolder>(MyDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is ArticleItemTypes.HeaderModel -> HEADER_VIEW_TYPE
            is ArticleItemTypes.ArticleModel -> ARTICLE_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                val binding = ResponseoneLayoutBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            ARTICLE_VIEW_TYPE -> {
                val binding = ItemPreViewBinding.inflate(inflater, parent, false)
                ArticleViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> item?.let { holder.bind(it as ArticleItemTypes.HeaderModel) }
            is ArticleViewHolder -> item?.let { holder.bind(it as ArticleItemTypes.ArticleModel) }
        }
    }

    companion object {
        private const val HEADER_VIEW_TYPE = 0
        private const val ARTICLE_VIEW_TYPE = 1
    }

    inner class HeaderViewHolder(private val binding: ResponseoneLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(headerModel: ArticleItemTypes.HeaderModel) {
            binding.tvauthor.text = headerModel.author
        }
    }

    inner class ArticleViewHolder(private val binding: ItemPreViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articleModel: ArticleItemTypes.ArticleModel) {
            with(binding) {
                Glide.with(root.context).load(articleModel.urlToImage).into(ivArticleImage)
                tvSource.text = articleModel.source?.name
                tvTitle.text = articleModel.title
                tvDescription.text = articleModel.description
                tvPublishedAt.text = articleModel.publishedAt
            }
        }
    }

    class MyDiffCallback : DiffUtil.ItemCallback<ArticleItemTypes>() {
        override fun areItemsTheSame(oldItem: ArticleItemTypes, newItem: ArticleItemTypes): Boolean {
            return when {
                oldItem is ArticleItemTypes.HeaderModel && newItem is ArticleItemTypes.HeaderModel ->
                    oldItem.author == newItem.author
                oldItem is ArticleItemTypes.ArticleModel && newItem is ArticleItemTypes.ArticleModel ->
                    oldItem.title == newItem.title
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: ArticleItemTypes, newItem: ArticleItemTypes): Boolean {
            return oldItem == newItem
        }
    }
}
