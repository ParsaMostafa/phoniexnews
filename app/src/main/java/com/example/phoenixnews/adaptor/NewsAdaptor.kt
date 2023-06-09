package com.example.phoenixnews.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.phoenixnews.databinding.ItemPreViewBinding

import com.example.phoenixnews.model.Article

class NewsAdaptor: PagingDataAdapter<Article, NewsAdaptor.ArticleViewHolder>(ARTICLE_COMPARATOR) {

    lateinit var onArticleClickListener : (article: Article) -> Unit

    inner class ArticleViewHolder(private val binding: ItemPreViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article?) {


            article?.let {
                binding.apply {
                    Glide.with(root.context).load(it.urlToImage).into(ivArticleImage)
                    tvSource.text = it.source?.name
                    tvTitle.text = it.title
                    tvDescription.text = it.description
                    tvPublishedAt.text = it.publishedAt

                    root.setOnClickListener {
                        onArticleClickListener(article)
                    }
                }
            }
        }
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemPreViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }
}