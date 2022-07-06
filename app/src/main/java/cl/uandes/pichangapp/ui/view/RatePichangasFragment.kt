package cl.uandes.pichangapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import cl.uandes.pichangapp.databinding.RatePichangasBinding
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.data.model.Match
import cl.uandes.pichangapp.ui.viewmodel.RatePichangasViewModel

class RatePichangasFragment : Fragment(), ItemAdapter.ActionListener {
    private lateinit var detailsItemAdapter: ItemAdapter
    private var mLastClickTime = System.currentTimeMillis()
    private val CLICK_TIME_INTERVAL: Long = 300
    private lateinit var binding: RatePichangasBinding
    private lateinit var viewModel: RatePichangasViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[RatePichangasViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.rate_pichangas, container, false
        )
        binding.lifecycleOwner = this
        val allFighters = repository.getAllMatches().value
        detailsItemAdapter = ItemAdapter(allFighters!!.toMutableList(), this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextSituation = "rate"

        val btn1 = view.findViewById<Button>(R.id.from_rate_to_history);
        btn1.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.history_pichangas)
        }

        val btn2 = view.findViewById<Button>(R.id.from_rate_to_userpichangas);
        btn2.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.user_pichangas)
        }

        val btn3 = view.findViewById<ImageView>(R.id.logoutRate);
        btn3.setOnClickListener {
            elementId = ""
            val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
            bottomNavigationView?.visibility = View.GONE
            Navigation.findNavController(it).navigate(R.id.login)
        }

        val btn4 = view.findViewById<ImageView>(R.id.profileRate);
        btn4.setOnClickListener {
            beforeProfile = "rate"
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


    override fun goToItemDetails(fighter: Match) {
        /* -> El siguiente codigo es para que al apretar dos items, no se caiga la app */
        val now = System.currentTimeMillis()
        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
            return;
        }

        mLastClickTime = now;
        /* -> hasta aqui <- */

        // aqui mandamos la info del partido    AQUI AGREGUE UN 1 PARA EL USERID
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
        detail = "rate"
        // llamamos la accion para navegar
        findNavController().navigate(R.id.action_rate_pichangas_to_details)
    }
}