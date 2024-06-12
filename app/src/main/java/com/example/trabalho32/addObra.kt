package com.example.trabalho32

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class addObra : Fragment() {

    private var nomeObra: EditText? = null
    private var autorObra: EditText? = null
    private var descricaoObra: EditText? = null
    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_obra, container, false)
        nomeObra = view.findViewById<EditText>(R.id.NomeObra)
        imageView = view.findViewById<ImageView>(R.id.imageViewObra)
        autorObra = view.findViewById<EditText>(R.id.AutorObra)
        descricaoObra = view.findViewById<EditText>(R.id.DescricaoObra)

        imageView.setOnClickListener {
            openGallery()
        }

        view.findViewById<Button>(R.id.buttonSalvar).setOnClickListener {
            when {
                nomeObra?.text.isNullOrEmpty() -> {
                    mensagem(it, "Preencha o campo: Nome da Obra!", "#004af5")
                }
                autorObra?.text.isNullOrEmpty() -> {
                    mensagem(it, "Preencha o campo: Autor da Obra!", "#004af5")
                }
                descricaoObra?.text.isNullOrEmpty() -> {
                    mensagem(it, "Preencha o campo: Descrição da Obra!", "#004af5")
                }
                imageUri == null -> {
                    mensagem(it, "Selecione uma imagem!", "#004af5")
                }
                else -> {
                    uploadImageToFirebaseStorage(imageUri!!)
                }
            }
        }

        return view
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == android.app.Activity.RESULT_OK && data != null) {
            imageUri = data.data
            imageView.setImageURI(imageUri)
        }
    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri) {

            val storageReference = FirebaseStorage.getInstance().reference
            val fileReference = storageReference.child("obras/${System.currentTimeMillis()}.jpg")

            fileReference.putFile(imageUri).addOnSuccessListener {
                fileReference.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    saveImageUrlToFirestore(imageUrl)
                }
            }.addOnFailureListener { e ->
                Log.e("FirebaseStorage", "Failed to upload image", e)
            }

    }


    private fun saveImageUrlToFirestore(imageUrl: String) {
        val nome = nomeObra?.text.toString()
        val autor = autorObra?.text.toString()
        val descricao = descricaoObra?.text.toString()

        val obra = mapOf(
            "nome" to nome,
            "imagem" to imageUrl,
            "autor" to autor,
            "descricao" to descricao
        )

        Firebase.firestore.collection("Obras2").add(obra).addOnSuccessListener {
            view?.let { mensagem(it, "Obra salva!", "#008000") } // green color for success
        }.addOnFailureListener { e ->
            view?.let { mensagem(it, "Erro ao salvar a obra!", "#ff0000") } // red color for error
        }
    }

    private fun mensagem(view: View, mensagem: String, cor: String) {
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(cor))
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()
    }
}
