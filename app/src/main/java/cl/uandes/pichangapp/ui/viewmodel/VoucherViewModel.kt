package cl.uandes.pichangapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cl.uandes.pichangapp.ui.view.repository

class VoucherViewModel(application: Application) : AndroidViewModel(application) {
    val matches = repository.getAllMatches().value
}