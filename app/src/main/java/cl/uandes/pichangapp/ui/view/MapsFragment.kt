package cl.uandes.pichangapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.databinding.MapsBinding
import cl.uandes.pichangapp.ui.viewmodel.MapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class Maps : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: MapsBinding
    private lateinit var viewModel: MapsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[MapsViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.maps, container, false
        )
        binding.lifecycleOwner = this
        val mapFragment = childFragmentManager.findFragmentById(R.id.maps3) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return binding.root


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        var placeMaps = LatLng(-33.415047, -70.512191)
        var googlePlex = CameraPosition.builder()
            .target(placeMaps)
            .zoom(12.0f)
            .bearing(0f)
            .tilt(0f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3000, null)
        placeMaps = LatLng(-33.395047, -70.502191)
        mMap.addMarker(MarkerOptions().position(placeMaps).title("Fortín Cruzado"))
        placeMaps = LatLng(-33.4288519, -70.5321971)

        mMap.addMarker(MarkerOptions().position(placeMaps).title("Club Ote. de Fútbol"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapsViewModel = viewModel


        val goBack = view.findViewById<Button>(R.id.gobackButton2)
        val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
        goBack.setOnClickListener {
            bottomNavigationView?.visibility = View.VISIBLE
            Navigation.findNavController(it).navigate(R.id.pichanga_search)

        }
    }
}