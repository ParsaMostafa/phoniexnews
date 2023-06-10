package com.example.phoenixnews.fragments


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phoenixnews.App
import com.example.phoenixnews.R
import com.example.phoenixnews.adaptor.MultiViewAdaptor
import com.example.phoenixnews.databinding.FragmentBreakingNewsBinding
import com.example.phoenixnews.fragments.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

class BreakingNewsFragment : Fragment() {

    private lateinit var binding: FragmentBreakingNewsBinding
    private val viewModel: NewsViewModel by navGraphViewModels(R.id.graph)
    private var newsAdapter = MultiViewAdaptor()

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
        fetchBreakingNews()
    }

    private fun setupRecyclerView() {
        binding.rvBreakingNews.apply {
            layoutManager = LinearLayoutManager(App.appContext)
            adapter = newsAdapter
        }
    }

    private fun fetchBreakingNews() {
        Log.d("1408", "Fetching breaking news...")
        viewModel.getBreakingNewsFlow("us")
    }

    private fun observeNews() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            lifecycleScope.launch {
                viewModel.listflow.collect { articles ->
                    newsAdapter.submitList(articles)
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Please check your internet connection",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
