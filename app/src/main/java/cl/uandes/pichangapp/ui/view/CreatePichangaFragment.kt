package cl.uandes.pichangapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.databinding.CreatePichangaBinding
import cl.uandes.pichangapp.ui.viewmodel.CreatePichangaViewModel


class CreatePichangaFragment : Fragment() {

    private lateinit var binding: CreatePichangaBinding
    private lateinit var viewModel: CreatePichangaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[CreatePichangaViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.create_pichanga, container, false
        )

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createPichangaViewModel = viewModel
        n = 0
        observeViewModel()

        val btn1 = view.findViewById<ImageView>(R.id.logoutCreatePichanga);
        btn1.setOnClickListener {
            elementId = ""
            val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
            bottomNavigationView?.visibility = View.GONE
            Navigation.findNavController(it).navigate(R.id.login)
        }

        val btn2 = view.findViewById<ImageView>(R.id.profileCreate);
        btn2.setOnClickListener {
            beforeProfile = "create"
            Navigation.findNavController(it).navigate(R.id.profile)
        }

    }

    private fun observeViewModel() {
        viewModel.isSignedUp.observe(viewLifecycleOwner) { signedUp ->
            signedUp?.let {
                if (signedUp) {
                    goToSignIn()
                    Toast.makeText(
                        context,
                        "La pichanga a sido creada con exito.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    private fun goToSignIn() {
        findNavController().navigate(R.id.action_create_pichanga_self)
    }
}



