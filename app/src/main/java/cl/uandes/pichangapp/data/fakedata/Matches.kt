package cl.uandes.pichangapp.data.fakedata

import cl.uandes.pichangapp.data.model.Match

class Matches {
  companion object {
    fun MyNextMatches() : MutableList<Match> {
      val Characters = ArrayList<Match>()
      return Characters.toMutableList()
    }
  }
}