package com.example.trabalho32.adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trabalho32.ListaObras
import com.example.trabalho32.R
import com.example.trabalho32.model.Obras
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ObrasAdapterADM(
    private var listaObras: ArrayList<Obras>,
    private val clickListener: (Obras) -> Unit
) : RecyclerView.Adapter<ObrasAdapterADM.ObrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObrasViewHolder {
        return ObrasViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.obrasitemadm, parent, false)
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
        holder.btnExcluir.setOnClickListener {
            Firebase.firestore.collection("Obras2").get().addOnSuccessListener {
                result ->
                for (doc in result){
                    if(doc.get("nome").toString() == listaObras[position].nome){
                        doc.reference.delete()
                    }
                }
            }
        }
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
            val btnExcluir = itemView.findViewById<Button   >(R.id.btnDeletar)
        val imgObra: ImageView = itemView.findViewById(R.id.imgObra)
        val txtObra: TextView = itemView.findViewById(R.id.txtObra)

        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
    }
}
