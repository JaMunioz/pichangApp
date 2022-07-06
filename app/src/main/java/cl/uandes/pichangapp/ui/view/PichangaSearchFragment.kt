package cl.uandes.pichangapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cl.uandes.pichangapp.databinding.PichangaSearchBinding
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.data.model.Match
import cl.uandes.pichangapp.ui.viewmodel.PichangaSearchViewModel

class PichangaSearchFragment : Fragment(), ItemAdapter.ActionListener {
    private lateinit var detailsItemAdapter: ItemAdapter
    private val allFighters = repository.getAllMatches().value
    private lateinit var binding: PichangaSearchBinding
    private lateinit var viewModel: PichangaSearchViewModel
    private var mLastClickTime = System.currentTimeMillis()
    private val CLICK_TIME_INTERVAL: Long = 300

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[PichangaSearchViewModel::class.java]

        binding = DataBindingUtil.inflate(
            inflater, R.layout.pichanga_search, container, false
        )
        binding.lifecycleOwner = this
        val allFighters = repository.getAllMatches().value
        detailsItemAdapter = ItemAdapter(allFighters!!.toMutableList(), this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextSituation = "search"
        n = 0

        binding.fighterListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailsItemAdapter
        }

        val btn1 = view.findViewById<ImageView>(R.id.logoutSearch);
        btn1.setOnClickListener {
            elementId = ""
            val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
            bottomNavigationView?.visibility = View.GONE
            Navigation.findNavController(it).navigate(R.id.login)
        }

        val btn2 = view.findViewById<ImageView>(R.id.profileSearch);
        btn2.setOnClickListener {
            beforeProfile = "search"
            Navigation.findNavController(it).navigate(R.id.profile)
        }

        val btn3 = view.findViewById<ImageView>(R.id.maps);
        btn3.setOnClickListener {
            val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
            bottomNavigationView?.visibility = View.GONE
            Navigation.findNavController(it).navigate(R.id.maps2)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var searchText = p0.toString().lowercase()
                filterFighters(searchText)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun filterFighters(searchText: String) {
        val filteredFighters = allFighters!!.filter {
            it.teamOne!!
                .lowercase()
                .contains(searchText)
        }
        detailsItemAdapter.updateItems(filteredFighters)
    }

    override fun goToItemDetails(fighter: Match) {
        /* -> El siguiente codigo es para que al apretar dos items, no se caiga la app */
        val now = System.currentTimeMillis()
        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
            return;
        }

        mLastClickTime = now;
        /* -> hasta aqui <- */

        // aqui mandamos la info del partido     AQUI AGREGUE UN 1 PARA EL USERID
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
        detail = "search"
        // llamamos la accion para navegar
        findNavController().navigate(R.id.action_pichanga_search_to_details)
    }

}