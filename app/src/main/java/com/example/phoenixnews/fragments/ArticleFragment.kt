package com.example.phoenixnews.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.example.phoenixnews.R
import com.example.phoenixnews.databinding.FragmentArticleBinding
import com.example.phoenixnews.fragments.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment: Fragment(R.layout.fragment_article) {
    private val viewModel: NewsViewModel by navGraphViewModels(R.id.graph)
    lateinit var binding: FragmentArticleBinding
    private val args: ArticleFragmentArgs by navArgs()






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(args.url)
        }
        binding.floatingActionButton.setOnClickListener {
            viewModel.article?.let { it1 -> viewModel.saveArticle(it1) }
            Snackbar.make(view, "مقاله با موفقیت ذخیره شد", Snackbar.LENGTH_SHORT).show()


        }

    }
}