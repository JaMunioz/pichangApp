package cl.uandes.pichangapp.data.fakedata

import cl.uandes.pichangapp.data.model.User

class Users {
    companion object {
        fun profileUser() : MutableList<User> {
            val profiles = ArrayList<User>()
            return profiles.toMutableList()
        }
    }
}