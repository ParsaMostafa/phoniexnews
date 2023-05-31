package com.example.phoenixnews.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.phoenixnews.databinding.ItemPreViewBinding
import com.example.phoenixnews.model.Article


class Adapter2 : RecyclerView.Adapter<Adapter2.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemPreViewBinding) : RecyclerView.ViewHolder(binding.root)


    companion object{
        private val differCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }}


    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter2.ArticleViewHolder {
        val binding = ItemPreViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.binding.apply {
            if (article?.urlToImage != null) {
                Glide.with(root.context).load(article.urlToImage).into(ivArticleImage)
            }
            tvSource.text = article?.source?.name
            tvTitle.text = article?.title
            tvDescription.text = article?.description
            tvPublishedAt.text = article?.publishedAt

            root.setOnClickListener {
                onItemClicklistener?.invoke(article)
            }
        }
    }

    private var onItemClicklistener: ((Article?) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article?) -> Unit) {
        onItemClicklistener = listener
    }
}