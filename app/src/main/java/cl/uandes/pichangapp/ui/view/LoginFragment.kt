package cl.uandes.pichangapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.databinding.LoginBinding
import cl.uandes.pichangapp.ui.viewmodel.LoginViewModel
import okhttp3.internal.wait

class LoginFragment : Fragment() {
    private lateinit var binding: LoginBinding
    private lateinit var viewModel: LoginViewModel
    var initial = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.login, container, false
        )

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginViewModel = viewModel
        n = 0
        observeViewModel()
        val register = view.findViewById<TextView>(R.id.from_login_to_register)
        register.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.register)
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
                if (signedUp) {
                    viewModel.actualMatches()
                    val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
                    bottomNavigationView?.visibility = View.VISIBLE
                    goToSignIn()
                    Toast.makeText(
                        context,
                        "Bienvenido",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (initial == 1) {
                        Toast
                            .makeText(context, "Error, intente nuevamente", Toast.LENGTH_LONG)
                            .show()
                    }
                    initial = 1
                }
            }
        }
    }

    private fun goToSignIn() {
        findNavController().navigate(R.id.action_login_to_homepage)
    }
}