package cl.uandes.pichangapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.data.datasources.ApiDataSource
import cl.uandes.pichangapp.data.datasources.PichangappLocalDataSource
import cl.uandes.pichangapp.data.model.Match
import cl.uandes.pichangapp.data.repository.PichangappRepository
import cl.uandes.pichangapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

var contextSituation = ""
var isLoggedIn = false
var elementId = "dhsixbwjsldk"
var loggedId = 1
var n = 0
var re = 1                               //AQUI AGREGUE UN 1 PARA EL USERID
var data = arrayOf<Match>(
    Match(
        1,
        1,
        teamOne = "",
        teamTwo = "",
        date = "",
        hour = "",
        place = "",
        category = "",
        rules = "",
        teamOnePoints = 0,
        teamTwoPoints = 0,
        time = 0,
        apiId = 0
    )
)
var detail = ""
var id_create_match = 0
var userId = 0L
var token = ""
var mail = ""
var beforeProfile = ""
var homepageData = Match(
    -1,
    1,
    teamOne = "",
    teamTwo = "",
    date = "",
    hour = "",
    place = "",
    category = "",
    rules = "",
    teamOnePoints = 0,
    teamTwoPoints = 0,
    time = 9999999999999,
    apiId = 0
)
val repository = PichangappRepository(
    PichangappLocalDataSource(),
    ApiDataSource()
)

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Buscamos el navigation view
        val navigationView: BottomNavigationView = binding?.navigationView!!

        // buscamos el nav host
        navigationController = findNavController(R.id.nav_host_fragment_activity_main)
//    NavigationUI.setupActionBarWithNavController(this, navigationController)

        // seteamos las navegaciones que van en la barra
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_graph)
        )
        // seteamos el action bar con el navigation controller y la confiuración creada
        //setupActionBarWithNavController(navigationController, appBarConfiguration)
        // le decimos al navigation view cual va a ser su controlador
        navigationView.setupWithNavController(navigationController)

        if (!isLoggedIn) {
            navigationController.navigate(R.id.login)
            navigationView.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController, null)
    }
}
