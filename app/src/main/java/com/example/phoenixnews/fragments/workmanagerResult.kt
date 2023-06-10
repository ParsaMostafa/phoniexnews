package com.example.phoenixnews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkerParameters
import com.example.phoenixnews.adaptor.NewsAdaptor
import com.example.phoenixnews.backupworker.NewsWorker
import com.example.phoenixnews.databinding.WorkManagerLayoutBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class workmanagerResult : Fragment() {
    private lateinit var newsWorker: NewsWorker
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

        // Initialize newsWorker
      //  val context = requireContext()
        //val workerParams = WorkerParameters.Builder(context).build()
      //  newsWorker = NewsWorker(context, workerParams)

        observenews()
    }

    private fun observenews() {
        lifecycleScope.launch {
            newsWorker.fetchnewBreakingnews().collectLatest { pagingData ->
                newsAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView2.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }
}
