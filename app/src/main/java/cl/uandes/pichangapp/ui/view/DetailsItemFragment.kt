package cl.uandes.pichangapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.databinding.DetailsItemBinding
import cl.uandes.pichangapp.ui.viewmodel.DetailsItemViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class Details : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: DetailsItemBinding
    private lateinit var viewModel: DetailsItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[DetailsItemViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater,R.layout.details_item, container, false
        )
        binding.lifecycleOwner = this
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return binding.root


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var googlePlex = CameraPosition.builder()
            .target(LatLng(0.0, 0.0))
            .zoom(14.0f)
            .bearing(0f)
            .tilt(45f)
            .build()
        var placeMaps = LatLng(0.0, 0.0)

        if (data[0].place == "Fortín Cruzado"){
            placeMaps = LatLng(-33.395047, -70.502191)
            googlePlex = CameraPosition.builder()
                .target(placeMaps)
                .zoom(14.0f)
                .bearing(0f)
                .tilt(0f)
                .build()
            mMap.addMarker(MarkerOptions().position(placeMaps).title("Fortín Cruzado"))
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null)
        }else {
            placeMaps = LatLng(-33.4288519, -70.5321971)
            googlePlex = CameraPosition.builder()
                .target(placeMaps)
                .zoom(14.0f)
                .bearing(0f)
                .tilt(0f)
                .build()
            mMap.addMarker(MarkerOptions().position(placeMaps).title("Club Ote. de Fútbol"))
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailsItemViewModel = viewModel

        val text1 = view.findViewById<TextView>(R.id.TeamOneName)
        val text2 = view.findViewById<TextView>(R.id.TeamTwoName)
        val text3 = view.findViewById<TextView>(R.id.pointone)
        val text4 = view.findViewById<TextView>(R.id.pointtwo)
        val text5 = view.findViewById<TextView>(R.id.date)
        val text6 = view.findViewById<TextView>(R.id.hour)
        val text7 = view.findViewById<TextView>(R.id.place)
        val text8 = view.findViewById<TextView>(R.id.rules)
        val text9 = view.findViewById<TextView>(R.id.category)
        val button = view.findViewById<Button>(R.id.actionbutton)
        val goBack = view.findViewById<Button>(R.id.gobackButton)
        val image1 = view.findViewById<ImageView>(R.id.fighterImageView)
        val image2 = view.findViewById<ImageView>(R.id.fighterImageView2)
        val input1 = view.findViewById<EditText>(R.id.pointOneInputText)
        val input2 = view.findViewById<EditText>(R.id.pointTwoInputText)

        val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
        bottomNavigationView?.visibility = View.GONE

        image1.setImageResource(R.mipmap.team1)
        if (data[0].teamTwo != "") {
            image2.setImageResource(R.mipmap.team1)
        } else {
            image2.setImageResource(R.mipmap.empty)
        }
        text1.text = data[0].teamOne
        text2.text = data[0].teamTwo
        text3.text = data[0].teamOnePoints.toString()
        text4.text = data[0].teamTwoPoints.toString()
        text3.visibility = View.INVISIBLE
        text4.visibility = View.INVISIBLE
        input1.visibility = View.INVISIBLE
        input2.visibility = View.INVISIBLE
        var s1 = "Fecha: "
        text5.text = concat(s1, data[0].date)
        s1 = "Hora: "
        text6.text = concat(s1, data[0].hour)
        s1 = "Lugar: "
        text7.text = concat(s1, data[0].place)
        s1 = "Reglas: "
        text8.text = concat(s1, data[0].rules)
        s1 = "Categoria: "
        text9.text = concat(s1, data[0].category)
        if (detail == "OurPichangas") {
            if (elementId == data[0].teamOne) {
                button.text = "Cancelar"
            } else {
                button.text = "Retirarse"
            }
            button.setOnClickListener {
                bottomNavigationView?.visibility = View.VISIBLE
                if (elementId == data[0].teamOne) {
                    viewModel.removeMatch(
                        data[0].id,
                        data[0].userId,
                        data[0].teamOne,
                        data[0].teamTwo,
                        data[0].date,
                        data[0].hour,
                        data[0].place,
                        data[0].category,
                        data[0].rules,
                        data[0].teamOnePoints,
                        data[0].teamTwoPoints,
                        data[0].time,
                        data[0].apiId
                    )
                    Navigation.findNavController(it).navigate(R.id.action_details_to_user_pichangas)
                } else {
                    viewModel.retireMatch(
                        data[0].id,
                        data[0].userId,
                        data[0].teamOne,
                        data[0].teamTwo,
                        data[0].date,
                        data[0].hour,
                        data[0].place,
                        data[0].category,
                        data[0].rules,
                        data[0].teamOnePoints,
                        data[0].teamTwoPoints,
                        data[0].time,
                        data[0].apiId
                    )
                    Navigation.findNavController(it).navigate(R.id.action_details_to_user_pichangas)
                }
            }
            goBack.setOnClickListener {
                bottomNavigationView?.visibility = View.VISIBLE
                Navigation.findNavController(it).navigate(R.id.action_details_to_user_pichangas)
            }
        } else {
            if (detail == "homepage") {
                if (elementId == data[0].teamOne) {
                    button.text = "Cancelar"
                } else {
                    button.text = "Retirarse"
                }
                text3.visibility = View.INVISIBLE
                text4.visibility = View.INVISIBLE
                button.setOnClickListener {
                    bottomNavigationView?.visibility = View.VISIBLE
                    if (elementId == data[0].teamOne) {
                        viewModel.removeMatch(
                            data[0].id,
                            data[0].userId,
                            data[0].teamOne,
                            data[0].teamTwo,
                            data[0].date,
                            data[0].hour,
                            data[0].place,
                            data[0].category,
                            data[0].rules,
                            data[0].teamOnePoints,
                            data[0].teamTwoPoints,
                            data[0].time,
                            data[0].apiId
                        )
                        Navigation.findNavController(it).navigate(R.id.action_details_to_homepage)
                    } else {
                        viewModel.retireMatch(
                            data[0].id,
                            data[0].userId,
                            data[0].teamOne,
                            data[0].teamTwo,
                            data[0].date,
                            data[0].hour,
                            data[0].place,
                            data[0].category,
                            data[0].rules,
                            data[0].teamOnePoints,
                            data[0].teamTwoPoints,
                            data[0].time,
                            data[0].apiId
                        )
                        Navigation.findNavController(it).navigate(R.id.action_details_to_homepage)
                    }
                }
                goBack.setOnClickListener {
                    bottomNavigationView?.visibility = View.VISIBLE
                    Navigation.findNavController(it).navigate(R.id.action_details_to_homepage)
                }
            } else {
                if (detail == "rate") {
                    button.text = "Calificar"
                    input1.visibility = View.VISIBLE
                    input2.visibility = View.VISIBLE
                    button.setOnClickListener {
                        bottomNavigationView?.visibility = View.VISIBLE
                        viewModel.editMatch(
                            data[0].id,
                            data[0].userId,
                            data[0].teamOne,
                            data[0].teamTwo,
                            data[0].date,
                            data[0].hour,
                            data[0].place,
                            data[0].category,
                            data[0].rules,
                            viewModel.pointsOneLiveData.value!!.toInt(),
                            viewModel.pointsTwoLiveData.value!!.toInt(),
                            data[0].time,
                            data[0].apiId
                        )
                        Navigation.findNavController(it)
                            .navigate(R.id.action_details_to_rate_pichangas)
                    }
                    goBack.setOnClickListener {
                        bottomNavigationView?.visibility = View.VISIBLE
                        Navigation.findNavController(it)
                            .navigate(R.id.action_details_to_rate_pichangas)
                    }
                } else {
                    if (detail == "history") {
                        button.visibility = View.INVISIBLE
                        text3.visibility = View.VISIBLE
                        text4.visibility = View.VISIBLE
                        goBack.setOnClickListener {
                            bottomNavigationView?.visibility = View.VISIBLE
                            Navigation.findNavController(it)
                                .navigate(R.id.action_details_to_history_pichangas)
                        }
                    } else {
                        if (detail == "search") {
                            button.text = "Unirse"
                            if (data[0].teamTwo != "") {
                                button.visibility = View.INVISIBLE
                            }
                            button.setOnClickListener {
                                bottomNavigationView?.visibility = View.VISIBLE
                                viewModel.editMatch(
                                    data[0].id,
                                    data[0].userId,
                                    data[0].teamOne,
                                    elementId,
                                    data[0].date,
                                    data[0].hour,
                                    data[0].place,
                                    data[0].category,
                                    data[0].rules,
                                    data[0].teamOnePoints,
                                    data[0].teamTwoPoints,
                                    data[0].time,
                                    data[0].apiId
                                )
                                Navigation.findNavController(it)
                                    .navigate(R.id.action_details_to_pichanga_search)
                            }
                            goBack.setOnClickListener {
                                bottomNavigationView?.visibility = View.VISIBLE
                                Navigation.findNavController(it)
                                    .navigate(R.id.action_details_to_pichanga_search)
                            }
                        }
                    }
                }
            }
        }
    }
}