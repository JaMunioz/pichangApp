package cl.uandes.pichangapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.data.model.Match
import cl.uandes.pichangapp.databinding.UserPichangasBinding
import cl.uandes.pichangapp.ui.viewmodel.UserPichangasViewModel

class UserPichangasFragment : Fragment(), ItemAdapter.ActionListener {
    private lateinit var detailsItemAdapter: ItemAdapter

    //private var allFighters = repository.getAllMatches().value
    private var mLastClickTime = System.currentTimeMillis()
    private val CLICK_TIME_INTERVAL: Long = 300
    private lateinit var binding: UserPichangasBinding
    private lateinit var viewModel: UserPichangasViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[UserPichangasViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.user_pichangas, container, false
        )
        binding.lifecycleOwner = this
        val allFighters = repository.getAllMatches().value
        detailsItemAdapter = ItemAdapter(allFighters!!.toMutableList(), this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextSituation = "ourPichangas"
        n = 0
        val btn1 = view.findViewById<Button>(R.id.from_userpichangas_to_rate);
        btn1.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.rate_pichangas)
        }

        val btn2 = view.findViewById<Button>(R.id.from_userpichangas_to_history);
        btn2.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.history_pichangas)
        }

        val btn3 = view.findViewById<ImageView>(R.id.logoutOurPichangas);
        btn3.setOnClickListener {
            elementId = ""
            val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
            bottomNavigationView?.visibility = View.GONE
            Navigation.findNavController(it).navigate(R.id.login)
        }

        val btn4 = view.findViewById<ImageView>(R.id.profileOurPichangas);
        btn4.setOnClickListener {
            beforeProfile = "ourPichangas"
            Navigation.findNavController(it).navigate(R.id.profile)
        }


        //recyclerview
        val fighterListView = binding.fighterListView
        fighterListView.layoutManager = LinearLayoutManager(context)
        fighterListView.adapter = detailsItemAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun goToItemDetails(fighter: Match) {
        /* -> El siguiente codigo es para que al apretar dos items, no se caiga la app */
        val now = System.currentTimeMillis()
        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
            return;
        }

        mLastClickTime = now;
        /* -> hasta aqui <- */

        // aqui mandamos la info del partido   AQUI AGREGUE UN fighter.userId PARA EL USERID
        data = arrayOf(
            Match(
                id = fighter.id,
                userId = fighter.userId,
                teamOne = fighter.teamOne.toString(),
                teamTwo = fighter.teamTwo.toString(),
                date = fighter.date.toString(),
                hour = fighter.hour.toString(),
                place = fighter.place.toString(),
                category = fighter.category.toString(),
                rules = fighter.rules.toString(),
                teamOnePoints = fighter.teamOnePoints,
                teamTwoPoints = fighter.teamTwoPoints,
                time = fighter.time,
                apiId = fighter.apiId
            )
        )
        detail = "OurPichangas"
        // llamamos la accion para navegar
        findNavController().navigate(R.id.action_user_pichangas_to_details)
    }
}