package cl.uandes.pichangapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import cl.uandes.pichangapp.R
import cl.uandes.pichangapp.databinding.VoucherBinding
import cl.uandes.pichangapp.ui.viewmodel.VoucherViewModel


class VoucherFragment : Fragment() {

    private lateinit var binding: VoucherBinding
    private lateinit var viewModel: VoucherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[VoucherViewModel::class.java]
        binding = DataBindingUtil.inflate(
            inflater, R.layout.voucher, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text1 = view.findViewById<TextView>(R.id.organizer)
        val text2 = view.findViewById<TextView>(R.id.date)
        val text3 = view.findViewById<TextView>(R.id.hour)
        val text4 = view.findViewById<TextView>(R.id.place)
        val text5 = view.findViewById<TextView>(R.id.category)
        val text6 = view.findViewById<TextView>(R.id.rules)

        var s1 = "Equipo Organizador: "
        text1.text =  concat(s1, viewModel.matches!![id_create_match].teamOne)
        s1 = "Fecha: "
        text2.text =  concat(s1, viewModel.matches!![id_create_match].date)
        s1 = "Hora: "
        text3.text =  concat(s1, viewModel.matches!![id_create_match].hour)
        s1 = "Lugar: "
        text4.text =  concat(s1, viewModel.matches!![id_create_match].place)
        s1 = "Categoria: "
        text5.text =  concat(s1, viewModel.matches!![id_create_match].category)
        s1 = "Reglas: "
        text6.text =  concat(s1, viewModel.matches!![id_create_match].rules)

        val btn1 = view.findViewById<Button>(R.id.from_voucher_to_homepage);
        btn1.setOnClickListener {
            val bottomNavigationView = activity?.findViewById<View>(R.id.navigation_view)
            bottomNavigationView?.visibility = View.VISIBLE
            Navigation.findNavController(it).navigate(R.id.create_pichanga)
        }
    }
}