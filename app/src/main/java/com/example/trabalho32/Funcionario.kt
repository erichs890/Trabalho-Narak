package com.example.trabalho32

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trabalho32.databinding.FragmentFuncionarioBinding
import com.google.android.material.snackbar.Snackbar

class Funcionario : Fragment() {

    private lateinit var binding: FragmentFuncionarioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFuncionarioBinding.inflate(inflater, container, false)

        binding.btLogin.setOnClickListener {
            val emailInput = binding.editCadastro.text.toString()
            val senhaInput = binding.editSenha.text.toString()
            val emailPredefinido = "eitakk"
            val senhaPredefinida = "123456"

            when {
                emailInput.isEmpty() -> {
                    mensagem(it, "Preencha o campo de email!", "#FF0000")
                }
                senhaInput.isEmpty() -> {
                    mensagem(it, "Preencha o campo de senha!", "#FF0000")
                }
                emailInput != emailPredefinido -> {
                    mensagem(it, "Email incorreto!", "#FF0000")
                }
                senhaInput != senhaPredefinida -> {
                    mensagem(it, "Senha incorreta!", "#FF0000")
                }
                else -> {
                    val intent = Intent(requireContext(), MainActivityFunc::class.java)
                    intent.putExtra("email", emailInput)
                    startActivity(intent)
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
}