package com.example.trabalho32

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RemoverObra : Fragment() {

//    private lateinit var databaseReference: DatabaseReference
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_remover_obra2, container, false)
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        databaseReference = FirebaseDatabase.getInstance().getReference("Obras2")
//
//        val deleteButton = view.findViewById<Button>(R.id.Delete)
//        deleteButton.setOnClickListener {
//            val idEditText = view.findViewById<EditText>(R.id.Deleteobra)
//            val id = idEditText.text.toString()
//            deleteObra(id)
//        }
//    }
//
//    private fun deleteObra(nome: String) {
//        databaseReference.child("Obras2/$id").removeValue().addOnSuccessListener {
//            val idEditText = view?.findViewById<EditText>(R.id.Deleteobra)
//            idEditText!!.text.clear()
//            Toast.makeText(context, "Obra removida com sucesso!", Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener {
//            Toast.makeText(context, "Erro ao remover a obra!", Toast.LENGTH_SHORT).show()
//        }
//    }
}