package cl.uandes.pichangapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cl.uandes.pichangapp.ui.view.repository

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val users = repository.getAllUsers().value
}