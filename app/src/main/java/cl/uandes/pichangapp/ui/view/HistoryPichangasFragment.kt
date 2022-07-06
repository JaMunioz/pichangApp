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
import androidx.navigation.fragment.findNavController
import cl.uandes.pichangapp.databinding.HistoryPichangasBinding
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.data.model.Match
import cl.uandes.pichangapp.ui.viewmodel.HistoryPichangasViewModel

class HistoryPichangasFragment : Fragment(), ItemAdapter.ActionListener {
    private lateinit var detailsItemAdapter: ItemAdapter
    private lateinit var binding: HistoryPichangasBinding
    private lateinit var viewModel: HistoryPichangasViewModel
    private var mLastClickTime = System.currentTimeMillis()
    private val CLICK_TIME_INTERVAL: Long = 300

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[HistoryPichangasViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.history_pichangas, container, false
        )
        binding.lifecycleOwner = this
        val allFighters = repository.getAllMatches().value
        detailsItemAdapter = ItemAdapter(allFighters!!.toMutableList(), this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextSituation = "history"

        val btn1 = view.findViewById<Button>(R.id.from_history_to_rate);
        btn1.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.rate_pichangas)
        }

        val btn2 = view.findViewById<Button>(R.id.from_history_to_userpichangas);
        btn2.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.user_pichangas)
        }

        val btn3 = view.findViewById<ImageView>(R.id.logoutHistory);
        btn3.setOnClickListener {
            elementId = ""
            val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
            bottomNavigationView?.visibility = View.GONE
            Navigation.findNavController(it).navigate(R.id.login)
        }

        val btn4 = view.findViewById<ImageView>(R.id.profileHistory);
        btn4.setOnClickListener {
            beforeProfile = "history"
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

        // aqui mandamos la info del partido      AQUI AGREGUE UN 1 PARA EL USERID
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
        detail = "history"

        findNavController().navigate(R.id.action_history_pichangas_to_details)
    }
}