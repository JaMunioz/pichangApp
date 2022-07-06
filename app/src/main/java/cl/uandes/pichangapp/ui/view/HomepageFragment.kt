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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.data.model.Match
import cl.uandes.pichangapp.databinding.HomepageBinding
import cl.uandes.pichangapp.ui.viewmodel.HomepageViewModel

class HomepageFragment : Fragment(), ItemAdapter.ActionListener {
    private lateinit var detailsItemAdapter: ItemAdapter
    private var mLastClickTime = System.currentTimeMillis()
    private val CLICK_TIME_INTERVAL: Long = 300
    private lateinit var binding: HomepageBinding
    private lateinit var viewModel: HomepageViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[HomepageViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.homepage, container, false
        )
        binding.lifecycleOwner = this
        val allFighters = repository.getAllMatches().value
        detailsItemAdapter = ItemAdapter(allFighters!!.toMutableList(), this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fighterListView = binding.fighterListView
        fighterListView.layoutManager = LinearLayoutManager(context)
        fighterListView.adapter = detailsItemAdapter

        contextSituation = "homepage"

        val text0 = view.findViewById<TextView>(R.id.Siguente_pichanga)
        val text1 = view.findViewById<TextView>(R.id.organizerHomepage)
        val text2 = view.findViewById<TextView>(R.id.VisitorHomepage)
        val text3 = view.findViewById<TextView>(R.id.categoryHomepage)
        val text4 = view.findViewById<TextView>(R.id.dateHomepage)
        val text5 = view.findViewById<TextView>(R.id.hourHomepage)
        val text6 = view.findViewById<TextView>(R.id.placeHomepage)
        val text7 = view.findViewById<TextView>(R.id.rulesHomepage)
        val text8 = view.findViewById<TextView>(R.id.noMatchUp)
        val image1 = view.findViewById<ImageView>(R.id.imageView4)

        var s1 = "Organizador: "
        text1.text = concat(s1, homepageData.teamOne)
        s1 = "Visita: "
        text2.text = concat(s1, homepageData.teamTwo)
        s1 = "Categoria: "
        text3.text = concat(s1, homepageData.category)
        s1 = "Fecha: "
        text4.text = concat(s1, homepageData.date)
        s1 = "Hora: "
        text5.text = concat(s1, homepageData.hour)
        s1 = "Lugar: "
        text6.text = concat(s1, homepageData.place)
        s1 = "Reglas: "
        text7.text = concat(s1, homepageData.rules)

        if (homepageData.id.toInt() == -1) {
            text0.visibility = View.INVISIBLE
            text1.visibility = View.INVISIBLE
            text2.visibility = View.INVISIBLE
            text3.visibility = View.INVISIBLE
            text4.visibility = View.INVISIBLE
            text5.visibility = View.INVISIBLE
            text6.visibility = View.INVISIBLE
            text7.visibility = View.INVISIBLE
            text8.visibility = View.VISIBLE
            image1.visibility = View.VISIBLE
        } else {
            text0.visibility = View.VISIBLE
            text1.visibility = View.VISIBLE
            text2.visibility = View.VISIBLE
            text3.visibility = View.VISIBLE
            text4.visibility = View.VISIBLE
            text5.visibility = View.VISIBLE
            text6.visibility = View.VISIBLE
            text7.visibility = View.VISIBLE
            text8.visibility = View.INVISIBLE
            image1.visibility = View.INVISIBLE
        }

        val btn1 = view.findViewById<ImageView>(R.id.logoutHomepage);
        btn1.setOnClickListener {
            elementId = ""
            val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
            bottomNavigationView?.visibility = View.GONE
            Navigation.findNavController(it).navigate(R.id.login)
        }

        val btn2 = view.findViewById<ImageView>(R.id.profileHomepage);
        btn2.setOnClickListener {
            beforeProfile = "homepage"
            Navigation.findNavController(it).navigate(R.id.profile)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
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

        // aqui mandamos la info del partido      AQUI AGREGUE UN fighter.userId PARA EL USERID
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
        detail = "homepage"
        // llamamos la accion para navegar
        findNavController().navigate(R.id.action_homepage_to_details)
    }
}