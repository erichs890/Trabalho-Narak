package com.example.trabalho32.adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trabalho32.ListaObras
import com.example.trabalho32.R
import com.example.trabalho32.model.Obras

class ObrasAdapter(
    private var listaObras: ArrayList<Obras>,
    private val clickListener: (Obras) -> Unit
) : RecyclerView.Adapter<ObrasAdapter.ObrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObrasViewHolder {
        return ObrasViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.obras_item, parent, false)
        ) {
            clickListener(listaObras[it])
        }
    }

    override fun getItemCount(): Int {
        return listaObras.size
    }

    override fun onBindViewHolder(holder: ObrasViewHolder, position: Int) {
        val obra = listaObras[position]
        holder.txtObra.text = obra.nome
        Glide.with(holder.imgObra.context)
            .load(obra.imagem)
            .into(holder.imgObra)
    }

    fun updateList(newList: ArrayList<Obras>) {
        listaObras = newList
        notifyDataSetChanged()
    }

    inner class ObrasViewHolder(itemView: View, clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val imgObra: ImageView = itemView.findViewById(R.id.imgObra)
        val txtObra: TextView = itemView.findViewById(R.id.txtObra)

        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
    }
}
