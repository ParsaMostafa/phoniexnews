package com.example.phoenixnews.fragments
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.phoenixnews.databinding.FragmentLogBinding
import com.example.phoenixnews.fragments.viewmodel.ViewmodelPerf
import com.example.phoenixnews.sharedperefence.loginpref

class Fragmentlog : Fragment() {
    private val viewModel: ViewmodelPerf by viewModels()
    private lateinit var binding: FragmentLogBinding
    private lateinit var session: loginpref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = loginpref()



        val usernameTextInput = binding.tilUsername
        val emailTextInput = binding.tilEmail
        val btnLogin = binding.btnLogin

        if(viewModel.isLoggedIn()){
            navigateToNewsActivity()
        }


        usernameTextInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Implement your logic before the text changes for the username field
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Implement your logic when the text changes for the username field
                usernameTextInput.error = null // Clear any previous error message
            }

            override fun afterTextChanged(s: Editable?) {
                // Implement your logic after the text changes for the username field
            }
        })

      emailTextInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Implement your logic before the text changes for the email field
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Implement your logic when the text changes for the email field
                emailTextInput.error = null // Clear any previous error message
            }

            override fun afterTextChanged(s: Editable?) {
                // Implement your logic after the text changes for the email field
            }
        })

        btnLogin.setOnClickListener {
            val usernameText = usernameTextInput.editText?.text.toString().trim()
            val emailText = emailTextInput.editText?.text.toString().trim()

            var errorOccurred = false

            if (usernameText.isEmpty()) {
                usernameTextInput.error = "Username field cannot be empty"
                errorOccurred = true
            } else {
                usernameTextInput.error = null
            }

            if (emailText.isEmpty()) {
                emailTextInput.error = "Email field cannot be empty"
                errorOccurred = true
            }else if (!isEmailValid(emailText)){
                   emailTextInput.error = "Please enter a valid email address"
                errorOccurred = true
            }



            if (!errorOccurred) {
                session.createLoginSession(usernameText, emailText)
                navigateToNewsActivity()
            }
        }

    }

    private fun navigateToNewsActivity() {
        val action = FragmentlogDirections.actionFragmentlogToNewsActivity()
        findNavController().navigate(action)
    }

    private fun isEmailValid(email: String): Boolean {
        val regexPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return regexPattern.matches(email)
    }
}
