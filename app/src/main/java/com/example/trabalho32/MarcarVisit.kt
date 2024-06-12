package com.example.trabalho32

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.trabalho32.databinding.FragmentMarcarVisitBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class MarcarVisit : Fragment() {

    private lateinit var binding: FragmentMarcarVisitBinding
    private val calendar: Calendar = Calendar.getInstance()
    private var data: String = ""
    private var hora: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarcarVisitBinding.inflate(inflater, container, false)

        val datePicker = binding.datePicker
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            var dia = dayOfMonth.toString()
            val mes: String

            if (dayOfMonth < 10) {
                dia = "0$dayOfMonth"
            }
            if (monthOfYear < 10){
                mes = "" + (monthOfYear+1)
            }else{
                mes = (monthOfYear +1).toString()
            }

            data = "$dia / $mes / $year"
        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->

            val minuto: String

            if (minute < 10) {
                minuto = "0$minute"
            }else{
                minuto = minute.toString()
            }

            hora = "$hourOfDay:$minuto"

        }
        binding.timePicker.setIs24HourView(true)

        binding.btAgendar.setOnClickListener {

            val visitaguiada = binding.visitaguiada
            val visitacego = binding.visitacego
            val visitaingles = binding.visitaingles

            when{
                hora.isEmpty() -> {
                    mensagem(it, "Preencha o horário!", "#004af5")
                }
                hora < "9:00" && hora > "19:00" -> {
                    mensagem(it, "Centro Cultural está fechado - horário de visita das 09:00 às 19:00!", "#004af5")
                }

                data.isEmpty() -> {
                    mensagem(it, "Coloque uma data!", "#FF0000")
                }

                visitaguiada.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it, "Agendamento salvo com sucesso!", data, hora, "Visitaguiada")
                }

                visitacego.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it,"Agendamento salvo com sucesso!", data, hora, "VisitaLibras")
                }

                visitaingles.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it, "Agendamento salvo com sucesso!", data, hora, "VisitaBilingue")
                }
                else -> {
                    mensagem(it, "Escolha um tipo de Visita!", "#004af5")
                }
            }

        }

        return binding.root
    }

    private fun mensagem(view: View, mensagem: String, cor: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()
    }
    private fun salvarAgendamento(view: View,mensagem: String, data: String, hora: String, tipo: String) {
        val db = FirebaseFirestore.getInstance()
        val dadosUsuario = hashMapOf(
            "data" to data,
            "hora" to hora,
            "tipo" to tipo
        )
        db.collection("agendamentos").add(dadosUsuario).addOnSuccessListener {
            mensagem(view, "Agendamento realizado com sucesso!", "#004af5")
        }.addOnFailureListener {
            mensagem(view, "Erro ao agendar!", "#FF0000")
        }
    }
}