package cl.uandes.pichangapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.uandes.pichangapp.data.model.Match
import cl.uandes.pichangapp.data.model.ObtainedRemoteMatch
import cl.uandes.pichangapp.data.model.RemoteMatch
import cl.uandes.pichangapp.ui.view.*
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailsItemViewModel(application: Application) : AndroidViewModel(application) {

    val current = LocalDateTime.now() //Esto es para sacar mi tiempo actual
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
    val formatted = current.format(formatter).toLong()
    var finded = 0

    fun actualMatches() {
        viewModelScope.launch {
            var a = repository.getMatches()
            fun register(
                Id: Long,
                userId: Long,
                Team: String,
                enemy: String,
                Date: String,
                Hour: String,
                Place: String,
                Category: String,
                Rules: String,
                PointsOne: Int,
                PointsTwo: Int,
                Time: Long,
                apiId: Long
            ) {//
                viewModelScope.launch {
                    repository.createMatch(
                        Match(
                            Id,
                            userId,
                            Team,
                            enemy,
                            Date,
                            Hour,
                            Place,
                            Category,
                            Rules,
                            PointsOne,
                            PointsTwo,
                            Time,
                            apiId
                        )
                    )
                }
            }
            repository.refreshMatches()
            for (rm: ObtainedRemoteMatch in a) {
                var enemy = ""
                if (rm.visitor_team == null) {
                    enemy = ""
                } else {
                    enemy = rm.visitor_team.name
                }
                if (rm.game_date == null) {
                    var time = 444444444444
                    register(
                        rm.id!!,
                        rm.home_team.id!!,
                        rm.home_team.name,
                        enemy,
                        "por definir",
                        "por definir",
                        rm.location.place_name,
                        rm.home_team.category,
                        rm.instructions,
                        -1,
                        -1,
                        time,
                        rm.id!!
                    )

                } else {
                    val totalDate = rm.game_date.split("T")
                    val year = totalDate[0].split("-")[0]
                    val mes = totalDate[0].split("-")[1]
                    val dia = totalDate[0].split("-")[2]
                    val hour = totalDate[1].split(".")[0].split(":")[0]
                    val minute = totalDate[1].split(".")[0].split(":")[1]
                    val date = StringBuilder((year)).append("-").append(mes).append("-").append(dia)
                        .toString()
                    val hourDay = StringBuilder((hour)).append(":").append(minute).toString()
                    val time =
                        StringBuilder(year).append(mes).append(dia).append(hour).append(minute)
                            .toString()
                    var pointOne = 0
                    var pointTwo = 0
                    try {
                        pointOne = rm.results!!.split("-")[0].toInt()
                        pointTwo = rm.results!!.split("-")[1].toInt()
                    } catch (e: Exception) {
                        pointOne = -1
                        pointTwo = -1
                    }
                    register(
                        rm.id!!,
                        rm.home_team.id!!,
                        rm.home_team.name,
                        enemy,
                        date,
                        hourDay,
                        rm.location.place_name,
                        rm.home_team.category,
                        rm.instructions,
                        pointOne,
                        pointTwo,
                        time.toLong(),
                        rm.id!!
                    )

                    var visitorTeam = ""
                    if (rm.visitor_team == null) {
                        visitorTeam = "No hay de momento"
                    } else {
                        visitorTeam = rm.visitor_team.name
                    }
                    if (((elementId == rm.home_team.name) || (elementId == visitorTeam))) {
                        var totalDate = rm.game_date.split("T")
                        var year = totalDate[0].split("-")[0]
                        var mes = totalDate[0].split("-")[1]
                        var dia = totalDate[0].split("-")[2]
                        var hour = totalDate[1].split(".")[0].split(":")[0]
                        var minute = totalDate[1].split(".")[0].split(":")[1]
                        var comparedTime = year + mes + dia + hour + minute
                        if (formatted < comparedTime.toLong() && homepageData.time > comparedTime.toLong()) {
                            finded = 1
                            homepageData = Match(
                                1,
                                1,
                                teamOne = rm.home_team.name,
                                teamTwo = visitorTeam,
                                date = year + "-" + mes + "-" + dia,
                                hour = hour + ":" + minute,
                                place = rm.location.place_name,
                                category = rm.home_team.category,
                                rules = rm.instructions,
                                teamOnePoints = -1,
                                teamTwoPoints = -1,
                                time = comparedTime.toLong(),
                                apiId = 0
                            )
                        }
                    }
                }
            }
            if (finded == 0) {
                homepageData = Match(
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
            }
        }
    }

    val matches = repository.getAllMatches().value
    var pointsOneLiveData = MutableLiveData("")
    var pointsTwoLiveData = MutableLiveData("")


    fun retireMatch(
        ID: Long,
        userId: Long,
        TeamOne: String,
        TeamTwo: String,
        Date: String,
        Hour: String,
        Place: String,
        Category: String,
        Rules: String?,
        TeamOnePoints: Int,
        TeamTwoPoints: Int,
        Time: Long,
        apiId: Long
    ) {
        viewModelScope.launch {
            //replace  isReady
            repository.deleteMatch(
                Match(
                    ID,
                    userId,
                    TeamOne,
                    TeamTwo,
                    Date,
                    Hour,
                    Place,
                    Category,
                    Rules,
                    TeamOnePoints,
                    TeamTwoPoints,
                    Time,
                    apiId
                )
            )
            var a = repository.getMatch(apiId.toString())
            repository.delMatch(a!!.id.toString())
            repository.addMatchExternally(
                RemoteMatch(
                    a.id,
                    a.home_team.id!!.toLong(),
                    null,
                    a.location.id!!.toLong(),
                    a.instructions,
                    a.game_date,
                    a.results
                )
            )
            actualMatches()
        }
    }

    fun editMatch(
        ID: Long,
        userId: Long,
        TeamOne: String,
        TeamTwo: String,
        Date: String,
        Hour: String,
        Place: String,
        Category: String,
        Rules: String?,
        TeamOnePoints: Int,
        TeamTwoPoints: Int,
        Time: Long,
        apiId: Long
    ) {
        viewModelScope.launch {
            //replace
            if (TeamOnePoints > -1) {
                repository.deleteMatch(
                    Match(
                        ID,
                        userId,
                        TeamOne,
                        TeamTwo,
                        Date,
                        Hour,
                        Place,
                        Category,
                        Rules,
                        -1,
                        -1,
                        Time,
                        apiId
                    )
                )
            } else {
                repository.deleteMatch(
                    Match(
                        ID,
                        userId,
                        TeamOne,
                        "",
                        Date,
                        Hour,
                        Place,
                        Category,
                        Rules,
                        TeamOnePoints,
                        TeamTwoPoints,
                        Time,
                        apiId
                    )
                )
            }
            var a = repository.getMatch(apiId.toString())
            repository.delMatch(a!!.id.toString())
            var punt = ""
            if (TeamOnePoints >= 0) {
                punt = StringBuilder().append(TeamOnePoints).append("-").append(TeamTwoPoints)
                    .toString()
            }
            if (a.visitor_team == null) {
                repository.addMatchExternally(
                    RemoteMatch(
                        a.id,
                        a.home_team.id!!.toLong(),
                        loggedId.toLong(),
                        a.location.id!!.toLong(),
                        a.instructions,
                        a.game_date,
                        null
                    )
                )
            } else {
                repository.addMatchExternally(
                    RemoteMatch(
                        a.id,
                        a.home_team.id!!.toLong(),
                        a.visitor_team.id,
                        a.location.id!!.toLong(),
                        a.instructions,
                        a.game_date,
                        punt
                    )
                )
            }
            actualMatches()
        }
    }

    fun removeMatch(
        ID: Long,
        userId: Long,
        TeamOne: String,
        TeamTwo: String,
        Date: String,
        Hour: String,
        Place: String,
        Category: String,
        Rules: String?,
        TeamOnePoints: Int,
        TeamTwoPoints: Int,
        Time: Long,
        apiId: Long
    ) {
        viewModelScope.launch {
            //remove isReady
            repository.deleteMatch(
                Match(
                    ID,
                    userId,
                    TeamOne,
                    TeamTwo,
                    Date,
                    Hour,
                    Place,
                    Category,
                    Rules,
                    TeamOnePoints,
                    TeamTwoPoints,
                    Time,
                    apiId
                )
            )
            var a = repository.getMatch(apiId.toString())
            repository.delMatch(a!!.id.toString())
            actualMatches()
        }
    }
}