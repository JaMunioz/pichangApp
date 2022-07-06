package cl.uandes.pichangapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.databinding.RegisterBinding
import cl.uandes.pichangapp.ui.viewmodel.RegisterViewModel

class RegisterFragment : Fragment() {
    private lateinit var binding: RegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.register, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerViewModel = viewModel

        observeViewModel()

        val backButton = binding.fromRegisterToLogin
        backButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.login)
        }
    }

    private fun observeViewModel() {
        viewModel.emailLiveData.observe(viewLifecycleOwner) { email ->
            email?.let {
                if (!viewModel.isValidEmail(it) && email.isNotEmpty()) {
                    binding.signUpEmailErrorTextView.visibility = View.VISIBLE
                } else {
                    binding.signUpEmailErrorTextView.visibility = View.GONE
                }
            }
        }
        viewModel.isSignedUp.observe(viewLifecycleOwner) { signedUp ->
            signedUp?.let {
                if(signedUp) {
                    val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
                    bottomNavigationView?.visibility = View.VISIBLE
                    goToSignIn()
                    Toast.makeText(
                        context,
                        "Ya estás registrado. Ahora puedes hacer login!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (viewModel.emailLiveData.value!!.isNotEmpty()) {
                        Toast
                            .makeText(context, "Error, intente nuevamente", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

        }
    }
    private fun goToSignIn() {
        findNavController().navigate(R.id.action_register_to_homepage)
    }
}

