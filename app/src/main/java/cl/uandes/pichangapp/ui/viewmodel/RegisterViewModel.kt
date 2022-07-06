package cl.uandes.pichangapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.pichangapp.data.datasources.ApiDataSource
import cl.uandes.pichangapp.data.datasources.PichangappLocalDataSource
import cl.uandes.pichangapp.data.model.User
import cl.uandes.pichangapp.data.model.RemoteUser
import cl.uandes.pichangapp.data.repository.PichangappRepository
import cl.uandes.pichangapp.ui.view.elementId
import cl.uandes.pichangapp.ui.view.loggedId
import kotlinx.coroutines.launch


class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    val repository = PichangappRepository(PichangappLocalDataSource(), ApiDataSource())
    var emailLiveData = MutableLiveData("")
    var passwordLiveData = MutableLiveData("")
    var nameTeamLiveData = MutableLiveData("")
    var categoryLiveData = ""
    val isSignedUp = MutableLiveData(false)

    fun set(x: Int) {
        if (x == 1) {
            categoryLiveData = "masculina"
        } else {
            if (x == 2) {
                categoryLiveData = "femenina"
            } else {
                categoryLiveData = "mixta"
            }
        }
    }

    fun signUp() {
        viewModelScope.launch {
            val id = cl.uandes.pichangapp.ui.view.repository.getAllUsers().value!!.size
            elementId = nameTeamLiveData.value.toString()
            cl.uandes.pichangapp.ui.view.repository.createUser(
                User(
                    id,
                    emailLiveData.value.toString(),
                    nameTeamLiveData.value.toString(),
                    passwordLiveData.value.toString(),
                    categoryLiveData
                )
            )
            var newUser = RemoteUser(
                null, emailLiveData.value.toString(), passwordLiveData.value.toString(),
                nameTeamLiveData.value.toString(), categoryLiveData
            )
            repository.addUserExternally(newUser)
            var a = repository.getUsers()
            for (rm: User in a) {
                if (rm.email == emailLiveData.value.toString()) {
                    elementId = rm.name
                    loggedId = rm.id
                    repository.createUser(
                        User(
                            rm.id,
                            rm.email,
                            rm.name,
                            "*********",
                            rm.category
                        )
                    )
                    break
                }
            }
            newUser = RemoteUser(
                null, emailLiveData.value.toString(), passwordLiveData.value.toString(),
                null, null
            )
            isSignedUp.postValue(cl.uandes.pichangapp.ui.view.repository.login(newUser))
        }
    }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}