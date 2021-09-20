package com.task.cinqueuex.auth.ui.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.task.cinqueuex.R
import com.task.cinqueuex.auth.api.AuthApi
import com.task.cinqueuex.auth.data.repo.AuthRepo
import com.task.cinqueuex.auth.ui.viewmodel.AuthViewModel
import com.task.cinqueuex.databinding.FragmentLoginBinding
import com.task.cinqueuex.utils.*
import com.task.cinqueuex.utils.LoadingDialog

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val ERROR = "This field is required."
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.bind(view)

        loadingDialog = LoadingDialog(requireActivity())

        val repo = AuthRepo(RetrofitInstance.buildApi(AuthApi::class.java))
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.emailEditText.addTextChangedListener(textWatcher(binding.emailInputLayout))
        binding.passwordEditText.addTextChangedListener(textWatcher(binding.passwordInputLayout))


        binding.loginButton.setOnClickListener {
            viewModel.loginResponse.removeObservers(viewLifecycleOwner)
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            val bool = checkValidityOfLoginFields(email, password)

            if(bool) {
                loadingDialog.startLoading()
                viewModel.login(email, password)
                viewModel.loginResponse.observe(this, {response->
                    loadingDialog.stopLoading()
                    when(response) {
                        is ApiResponseHandler.Success -> {
                            MaterialAlertDialogBuilder(requireContext()).setTitle("Success: 200")
                                .setMessage(response.value.message)
                                .setPositiveButton("Ok") {dialog, which->
                                    dialog.dismiss()
                                }
                                .show()
                        }

                        is ApiResponseHandler.Failure -> {

                            if(response.isNetworkError) {
                                Toast.makeText(requireContext(), "Connection Error", Toast.LENGTH_SHORT).show()
                            }else {
                                MaterialAlertDialogBuilder(requireContext()).setTitle("Error: ${response.errorCode}")
                                    .setPositiveButton("Ok") {dialog, which->
                                        dialog.dismiss()
                                    }.show()
                            }


                        }
                    }
                })
            }
        }


        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }


    private fun checkValidityOfLoginFields(email: String, password: String): Boolean {
        val isAllFieldsValid: Boolean

        if (email.isEmpty()) {
            isAllFieldsValid = false
            showError(binding.emailInputLayout, ERROR)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            val message = "Email Invalid"
            showError(binding.emailInputLayout, message)
            isAllFieldsValid = false
        } else if (password.isEmpty()) {
            showError(binding.passwordInputLayout, ERROR)
            isAllFieldsValid = false
        } else if (password.length < 5) {
            val message = "Password length should be greater than 5"
            showError(binding.passwordInputLayout, message)
            isAllFieldsValid = false
        } else isAllFieldsValid = true

        return isAllFieldsValid

    }
}