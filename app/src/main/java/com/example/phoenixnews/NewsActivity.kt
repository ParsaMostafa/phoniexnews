package com.example.phoenixnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.phoenixnews.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    lateinit var binding:ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_news)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
    }
}