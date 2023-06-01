package com.example.phoenixnews.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.phoenixnews.R
import com.example.phoenixnews.databinding.FragmentlogoutBinding
import com.example.phoenixnews.fragments.viewmodel.ViewmodelPerf
import com.example.phoenixnews.sharedperefence.loginpref

class Fragmentlogout : Fragment() {
    private lateinit var binding: FragmentlogoutBinding
    private val viewModel: ViewmodelPerf by viewModels()
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnLogout: Button
    private lateinit var session: loginpref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentlogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUsername = view.findViewById(R.id.tvUsername)
        tvEmail = view.findViewById(R.id.tvEmail)
        btnLogout = view.findViewById(R.id.btnLogout)

        session = loginpref()


        val user: HashMap<String, String> = session.getUserDetails()
        val username = user[loginpref.KEY_USERNAME]
        val email = user[loginpref.KEY_EMAIL]

        tvUsername.text = username
        tvEmail.text = email

        btnLogout.setOnClickListener {
            // Create and show the dialog
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    session.logoutUser()
                    // Navigate to the login fragment
                    val action = FragmentlogoutDirections.actionFragmentlogoutToLoginActivity()
                    findNavController().navigate(action)


                }
                .setNegativeButton("No", null)
                .show()
        }
    }
}