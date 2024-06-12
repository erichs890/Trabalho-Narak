package com.example.trabalho32.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabalho32.R
import com.example.trabalho32.model.Visitas

class VisitasAdapter (private val visitList: ArrayList<Visitas>) : RecyclerView.Adapter<VisitasAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.visitaslayout, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val atualVisita = visitList[position]
        holder.tvListTipo.text = atualVisita.tipo
        holder.tvListData.text = atualVisita.data
        holder.tvListHora.text = atualVisita.hora
    }
    override fun getItemCount(): Int {
        return visitList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvListTipo : TextView = itemView.findViewById(R.id.tipovisita)
        val tvListData : TextView = itemView.findViewById(R.id.datavisita)
        val tvListHora : TextView = itemView.findViewById(R.id.horavisita)

    }

}

