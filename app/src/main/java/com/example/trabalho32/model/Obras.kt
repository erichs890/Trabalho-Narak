package com.example.trabalho32.model

import java.io.Serializable

data class Obras (
    val descricao: String? = null,
    val autor: String? = null,
    val imagem: String? = null,
    val nome: String? = null,
    val id: String? = null,
) : Serializable