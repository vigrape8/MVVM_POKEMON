package com.example.pokemon.Model
import java.io.Serializable
//Para poder pasar el objeto por bits
data class Pokemon(
    val numeroPk:Int,
    val nombre: String,
    val imagen: Int,
    val descripcion:String,
    var favorito: Boolean
): Serializable

