package com.example.pokemon.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon.Model.Pokemon
import com.example.pokemon.Repository.PokemonRepository

class PokemonViewModel : ViewModel() {
    //Inicializar el repository
    private val repository: PokemonRepository = PokemonRepository()

    //LiveData para la lista de pokemons
    private val _pokemones = MutableLiveData<List<Pokemon>?>()
    val pokemones: LiveData<List<Pokemon>> = _pokemones as LiveData<List<Pokemon>>

    //LiveData para el pokemon seleccionado
    private val _pokemonSeleccionado = MutableLiveData<Pokemon?>()
    val pokemonSeleccionado: LiveData<Pokemon?> = _pokemonSeleccionado

    //Inicializar los datos
    init {
        _pokemones.value = repository.getPokemons()
    }

    fun seleccionarPokemon(pokemon: Pokemon?) {
        _pokemonSeleccionado.value = pokemon
    }

    fun borrarPokemon(posicion: Int) {
        val listaActual = _pokemones.value

        // Comprobamos que la lista existe y que la posición es válida
        if (listaActual != null && posicion in listaActual.indices) {

            // Recuperamos el pokemon que queremos eliminar
            val eliminado = listaActual[posicion]

            // Lo eliminamos del repositorio (fuente de datos)
            repository.eliminarPokemon(eliminado)

            // Actualizamos el LiveData con la nueva lista
            _pokemones.value = repository.getPokemons()
        }
    }
}
