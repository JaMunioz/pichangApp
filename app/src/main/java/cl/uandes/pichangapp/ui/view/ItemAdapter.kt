package cl.uandes.pichangapp.ui.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.data.model.Match
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun concat(s1: String, s2: String?): String? {
    return StringBuilder(s1).append(s2).toString()
}

class ItemAdapter(
    private val recycler: MutableList<Match>,
    private val actionListener: ActionListener
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TeamOne = itemView.findViewById<TextView>(R.id.TeamOneName)!!
        val TeamTwo = itemView.findViewById<TextView>(R.id.TeamTwoName)!!
        val ImageOne = itemView.findViewById<ImageView>(R.id.fighterImageView)!!
        val ImageTwo = itemView.findViewById<ImageView>(R.id.fighterImageView2)!!
        val DateMatch = itemView.findViewById<TextView>(R.id.date)!!
        val PointOne = itemView.findViewById<TextView>(R.id.pointone)!!
        val PointTwo = itemView.findViewById<TextView>(R.id.pointtwo)!!
        val ViewItem = itemView.findViewById<ViewGroup>(R.id.viewItem)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // obtenemos el contexto del parent
        val context = parent.context
        // le pedimos el inflater al parent
        val inflater = LayoutInflater.from(context)

        // aqui "inflamos" la vista del parent con nuestro los items
        val itemsView: View = inflater.inflate(R.layout.item, parent, false)

        // para crear el viewholder, le pasamos la vista
        return ViewHolder(itemsView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        recycler.sortBy { it.time } //ordeno la lista en tiempo de manera antiguo -> actual
        if (re == 1) { //ordena los id de manera adecuada
            for (item: Int in recycler.indices) {
                recycler[item].id = item.toLong()
            }
        }
        re = 0
        val Element: Match = recycler[position]

        val current = LocalDateTime.now() //Esto es para sacar mi tiempo actual
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
        val formatted = current.format(formatter).toLong()
        val teamone = holder.TeamOne
        val teamtwo = holder.TeamTwo
        val image = holder.ImageOne
        val image2 = holder.ImageTwo
        val date = holder.DateMatch
        val pointone = holder.PointOne
        val pointtwo = holder.PointTwo
        val detailsButton = holder.ViewItem

        fun clean() {
            detailsButton.visibility = View.GONE
            val params = detailsButton.layoutParams
            params.height = 0
            params.width = 0
            detailsButton.layoutParams = params
        }

        detailsButton.setOnClickListener {
            actionListener.goToItemDetails(Element)
        }

        if (contextSituation == "homepage") {
            if (formatted < Element.time && n == 0 && ((elementId == Element.teamOne) || (elementId == Element.teamTwo))) {
                homepageData = Element
                n = 1
                teamone.text = Element.teamOne
                teamtwo.text = Element.teamTwo
                image.setImageResource(R.mipmap.team1)
                if (Element.teamTwo != "") {
                    image2.setImageResource(R.mipmap.team1)
                } else {
                    image2.setImageResource(R.mipmap.empty)
                }
                val s1 = "Fecha: "
                date.text = concat(s1, Element.date)
                pointone.visibility = View.INVISIBLE
                pointtwo.visibility = View.INVISIBLE
            } else {
                clean()
            }
        } else {
            if (contextSituation == "search") {
                if ((formatted < Element.time) && (elementId != Element.teamOne) && (elementId != Element.teamTwo)) {
                    teamone.text = Element.teamOne
                    teamtwo.text = Element.teamTwo
                    image.setImageResource(R.mipmap.team1)
                    if (Element.teamTwo != "") {
                        image2.setImageResource(R.mipmap.team1)
                    } else {
                        image2.setImageResource(R.mipmap.empty)
                    }
                    val s1 = "Fecha: "
                    date.text = concat(s1, Element.date)
                    pointone.visibility = View.INVISIBLE
                    pointtwo.visibility = View.INVISIBLE
                } else {
                    clean()
                }
            } else {
                if (contextSituation == "history") {
                    if (formatted > Element.time && ((elementId == Element.teamOne) || (elementId == Element.teamTwo)) && Element.teamOnePoints >= 0) {
                        teamone.text = Element.teamOne
                        teamtwo.text = Element.teamTwo
                        image.setImageResource(R.mipmap.team1)
                        image2.setImageResource(R.mipmap.team1)
                        val s1 = "Fecha: "
                        date.text = concat(s1, Element.date)
                        pointone.text = Element.teamOnePoints.toString()
                        pointtwo.text = Element.teamTwoPoints.toString()
                    } else {
                        clean()
                    }
                } else {
                    if (contextSituation == "rate") {
                        if (formatted > Element.time && ((elementId == Element.teamOne) || (elementId == Element.teamTwo)) && Element.teamOnePoints <= -1) {
                            teamone.text = Element.teamOne
                            teamtwo.text = Element.teamTwo
                            image.setImageResource(R.mipmap.team1)
                            image2.setImageResource(R.mipmap.team1)
                            val s1 = "Fecha: "
                            date.text = concat(s1, Element.date)
                            pointone.visibility = View.INVISIBLE
                            pointtwo.visibility = View.INVISIBLE
                        } else {
                            clean()
                        }
                    } else {
                        if (contextSituation == "ourPichangas") {
                            if (formatted < Element.time && ((elementId == Element.teamOne) || (elementId == Element.teamTwo))) {
                                teamone.text = Element.teamOne
                                teamtwo.text = Element.teamTwo
                                image.setImageResource(R.mipmap.team1)
                                if (Element.teamTwo != "") {
                                    image2.setImageResource(R.mipmap.team1)
                                } else {
                                    image2.setImageResource(R.mipmap.empty)
                                }
                                val s1 = "Fecha: "
                                date.text = concat(s1, Element.date)
                                pointone.visibility = View.INVISIBLE
                                pointtwo.visibility = View.INVISIBLE
                            } else {
                                clean()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return recycler.size
    }

    // esta funcion notifica al adapter cuando cambia el
    // dataset definido al principio, en el constructor
    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newFighters: List<Match>) {
        recycler.clear()
        recycler.addAll(newFighters)
        notifyDataSetChanged()
    }

    interface ActionListener {
        fun goToItemDetails(fighter: Match)
    }
}
