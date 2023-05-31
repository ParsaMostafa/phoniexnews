package com.example.phoenixnews.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.phoenixnews.App
import com.example.phoenixnews.databinding.FragmentLogBinding
import com.example.phoenixnews.sharedperefence.loginpref

class Fragmentlog : Fragment() {

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

        btnLogin.setOnClickListener {
            val usernameText = usernameTextInput.editText?.text.toString().trim()
            val emailText = emailTextInput.editText?.text.toString().trim()

            if (usernameText.isEmpty()) {
                usernameTextInput.error= "username field can not be empty"
            } else
                if (emailText.isEmpty()){
                    emailTextInput.error = "email field can not be empty"
                } else

                if (!isEmailValid(emailText)) {
                emailTextInput.error = "Please enter a valid email address"
            } else {
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
