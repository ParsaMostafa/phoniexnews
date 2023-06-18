package com.example.phoenixnews.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phoenixnews.R
import com.example.phoenixnews.adaptor.NewsAdaptor
import com.example.phoenixnews.databinding.FragmentSearchNewsBinding
import com.example.phoenixnews.fragments.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding
    private val viewModel: NewsViewModel by navGraphViewModels(R.id.graph)
    private lateinit var newsAdapter: NewsAdaptor
    private lateinit var tvNoResults: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsAdaptor()
        tvNoResults = binding.tvNoResults

        binding.rvSearchNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }

        newsAdapter.onArticleClickListener = {
            it.let {
                viewModel.article = it
                val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(it.url)
                findNavController().navigate(action)

            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called when the text is changed
                searchNews()
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text has changed
                if (s.isNullOrEmpty()) {   lifecycleScope.launch { newsAdapter.submitData(PagingData.empty()) }



                }
            }
        })

        newsAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar2.visibility = View.VISIBLE
            } else {
                binding.progressBar2.visibility = View.GONE
            }

            val errorState = loadState.refresh as? LoadState.Error
            val error = errorState?.error
            if (error != null) {
                Toast.makeText(requireContext(), "An error occurred: $error", Toast.LENGTH_SHORT).show()
            }

            // Show/hide the "No results found" message based on the load state
            val isEmpty = loadState.refresh is LoadState.NotLoading && newsAdapter.itemCount == 0
            tvNoResults.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }
    }

    private fun searchNews() {
        val query = binding.etSearch.text.toString().trim()
        if (query.isNotEmpty()) {
            val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                lifecycleScope.launch {
                    // Emit an empty list to show loading state
                    newsAdapter.submitData(PagingData.empty())

                    viewModel.searchForNews(query).collectLatest { pagingData ->
                        newsAdapter.submitData(pagingData)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Please enter a search query", Toast.LENGTH_SHORT).show()
        }
    }
}

