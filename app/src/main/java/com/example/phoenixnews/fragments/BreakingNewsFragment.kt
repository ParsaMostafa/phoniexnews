package com.example.phoenixnews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phoenixnews.R
import com.example.phoenixnews.adaptor.NewsAdaptor
import com.example.phoenixnews.databinding.FragmentBreakingNewsBinding
import com.example.phoenixnews.fragments.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


    class BreakingNewsFragment : Fragment() {

        private lateinit var binding: FragmentBreakingNewsBinding
        private val viewModel: NewsViewModel by navGraphViewModels(R.id.graph)
        private val newsAdapter = NewsAdaptor()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setupRecyclerView()
            observeNews()
        }

        private fun setupRecyclerView() {
            binding.rvBreakingNews.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = newsAdapter
            }

            newsAdapter.setOnItemClickListener { article ->
                article?.let {
                    val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it.url)
                    findNavController().navigate(action)
                }
            }
        }

        private fun observeNews() {
            lifecycleScope.launch {
                viewModel.getBreakingNewsFlow("us").collectLatest { pagingData ->
                    newsAdapter.submitData(pagingData)
                }
            }
        }
    }

