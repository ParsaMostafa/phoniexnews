package com.example.phoenixnews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phoenixnews.R
import com.example.phoenixnews.adaptor.Adapter2
import com.example.phoenixnews.databinding.FragmentSavedNewsBinding
import com.example.phoenixnews.fragments.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SavedNewsFragment: Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    val viewModel: NewsViewModel by navGraphViewModels(R.id.graph)
    private lateinit var adapter2: Adapter2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        adapter2.setOnItemClickListener { article ->
            viewModel.article=article
            findNavController().navigate(
                SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(article?.url ?: ""))

        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val article = adapter2.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerViewsavednews)
        }

        lifecycleScope.launch {
            viewModel.savedArticles.collect { articles ->
                adapter2.differ.submitList(articles)
            }
        }


    }

    private fun setupRecyclerView() {
        adapter2 = Adapter2()
        binding.recyclerViewsavednews.apply {
            adapter = adapter2
            layoutManager = LinearLayoutManager(activity)

        }
    }
}


