package cl.uandes.pichangapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.databinding.ProfileBinding
import cl.uandes.pichangapp.ui.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: ProfileBinding
    private lateinit var viewModel: ProfileViewModel

    //    private val users = User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.profile, container, false
        )

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
        bottomNavigationView?.visibility = View.GONE
        n = 0
        val text1 = view.findViewById<TextView>(R.id.NameOfTeam)
        val text2 = view.findViewById<TextView>(R.id.emailProfile)
        val text3 = view.findViewById<TextView>(R.id.passwordProfile)
        val text4 = view.findViewById<TextView>(R.id.categoryProfile)
        val image = view.findViewById<ImageView>(R.id.equipo)

        image.setImageResource(R.mipmap.team1)
        val user = viewModel.users?.find { it.name == elementId }
        if (user != null) {
            text1.text = user.name
            text2.text = user.email
            text3.text = user.password
            text4.text = user.category
        }

        val btn1 = view.findViewById<Button>(R.id.from_perfil_to_homepage);
        btn1.setOnClickListener {
            bottomNavigationView?.visibility = View.VISIBLE
            if (beforeProfile == "homepage") {
                Navigation.findNavController(it).navigate(R.id.homepage)
            } else {
                if (beforeProfile == "create") {
                    Navigation.findNavController(it).navigate(R.id.create_pichanga)
                } else {
                    if (beforeProfile == "history") {
                        Navigation.findNavController(it).navigate(R.id.history_pichangas)
                    } else {
                        if (beforeProfile == "search") {
                            Navigation.findNavController(it).navigate(R.id.pichanga_search)
                        } else {
                            if (beforeProfile == "rate") {
                                Navigation.findNavController(it).navigate(R.id.rate_pichangas)
                            } else {
                                if (beforeProfile == "ourPichangas") {
                                    Navigation.findNavController(it).navigate(R.id.user_pichangas)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


