package com.example.phoenixnews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phoenixnews.adaptor.NewsAdaptor
import com.example.phoenixnews.databinding.WorkManagerLayoutBinding
import kotlinx.coroutines.launch

class workmanagerResult: Fragment() {

    private lateinit var binding: WorkManagerLayoutBinding
    private val newsAdapter = NewsAdaptor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WorkManagerLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
        fun observeBreakingNews() {
            // Observe the breaking news flow
            lifecycleScope.launch {
                // Collect the paging data and submit it to the adapter
                newsAdapter.submitData(breakingNewsFlow.collect())
            }
        }



    }


}
