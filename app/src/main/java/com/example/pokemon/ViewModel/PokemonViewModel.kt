package com.example.pokemon.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemon.Model.Pokemon
import com.example.pokemon.Repository.PokemonRepository

class PokemonViewModel : ViewModel() {
    //Inicializar el repository
    private val repository: PokemonRepository = PokemonRepository()
    //arreglo para que actualice la lista en caso de seleccionar un fav dentro de la busqueda
    private var listaActualizada: String=""

    //LiveData para la lista de pokemons
    private val _pokemones = MutableLiveData<List<Pokemon>?>()
    val pokemones: LiveData<List<Pokemon>> = _pokemones as LiveData<List<Pokemon>>

    //LiveData para el pokemon seleccionado
    private val _pokemonSeleccionado = MutableLiveData<Pokemon?>()
    val pokemonSeleccionado: LiveData<Pokemon?> = _pokemonSeleccionado

    //Inicializar los datos
    init {
        cargarTodos()
    }

    fun cargarTodos(){
        listaActualizada=""
        _pokemones.value = repository.getPokemons()
    }

    //Cargar lista favoritos
    fun cargarFavoritos() {
        listaActualizada = ""
        _pokemones.value = repository.getPokemonsFavoritos()
    }

    fun seleccionarPokemon(pokemon: Pokemon?) {
        _pokemonSeleccionado.value = pokemon
    }
    //Actualiza en base a la lista que este seleccionada
    fun actualizarPokemon(pokemon: Pokemon,esPantallaFav:Boolean=false) {
        repository.actualizarPokemon(pokemon)
        //modificado para actualizar la lista en caso de que haya busqueda o no
        //si estamos en la pantalla fav cargamos la lista de fav
        if(esPantallaFav){
            if(listaActualizada.isEmpty()){
                cargarFavoritos()
            }else{
                buscarEnFavoritos(listaActualizada)
            }
        }else{//si no carga normal
            if(listaActualizada.isEmpty()){
                cargarTodos()
            }else{
                buscarPokemonPorNombre(listaActualizada)
            }
        }

    }
    fun borrarPokemon(posicion: Int,esPantallaFav: Boolean=false) {
        val listaActual = _pokemones.value

        // Comprobamos que la lista existe y que la posición es válida
        if (listaActual != null && posicion in listaActual.indices) {

            // Recuperamos el pokemon que queremos eliminar
            val eliminado = listaActual[posicion]

            // Lo eliminamos del repositorio
            repository.eliminarPokemon(eliminado)

            //modificado para actualizar la lista en caso de que haya busqueda o no
            if(esPantallaFav){
                if(listaActualizada.isEmpty()){
                    cargarFavoritos()
                }else{
                    buscarEnFavoritos(listaActualizada)
                }
            }else{//si no carga normal
                if(listaActualizada.isEmpty()){
                    cargarTodos()
                }else{
                    buscarPokemonPorNombre(listaActualizada)
                }
            }
        }
    }
    fun buscarPokemonPorNombre(nombre: String?){
        if (nombre != null) {
            listaActualizada=nombre
        }
        _pokemones.value=repository.getPokemonPorNombre(nombre)
    }

    //Si encuentra pokemons dentro de la lista de favoritos los muestra
    fun buscarEnFavoritos(nombre: String) {
        listaActualizada = nombre
        val listaFavs = repository.getPokemonsFavoritos()
        if (nombre.isEmpty()) {
            _pokemones.value = listaFavs
        } else {
            var resultado=mutableListOf<Pokemon>()
            for(pokemon in listaFavs){
                if(pokemon.nombre.lowercase().contains(nombre.lowercase())){
                    resultado.add(pokemon)
                }
            }
            _pokemones.value=resultado
        }
    }
}
