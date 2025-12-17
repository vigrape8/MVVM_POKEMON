package com.example.pokemon.Repository

import com.example.pokemon.Model.Pokemon
import com.example.pokemon.R
class PokemonRepository {
    private val listaPokemon: MutableList<Pokemon> =mutableListOf()
    init{
        listaPokemon.add(Pokemon(1,"Bulbasaur",R.drawable.bulbasaur,"Tras nacer, crece alimentándose durante un tiempo de los nutrientes que contiene el bulbo de su lomo.",false))
        listaPokemon.add(Pokemon(2,"Ivysaur",R.drawable.ivysaur,"Cuanta más luz solar recibe, más aumenta su fuerza y más se desarrolla el capullo que tiene en el lomo.",false))
        listaPokemon.add(Pokemon(3,"Venasaur",R.drawable.venasaur,"Puede convertir la luz del sol en energía. Por esa razón, es más poderoso en verano.",false))
        listaPokemon.add(Pokemon(4,"Charmander",R.drawable.charmander,"La llama de su cola indica su fuerza vital. Si está débil, la llama arderá más tenue.",false))
        listaPokemon.add(Pokemon(5,"Charmeleon",R.drawable.charmeleon,"Al agitar su ardiente cola, eleva poco a poco la temperatura a su alrededor para sofocar a sus rivales.",false))
        listaPokemon.add(Pokemon(6,"Charizard",R.drawable.charizard,"Cuando se enfurece de verdad, la llama de la punta de su cola se vuelve de color azul claro.",false))
        listaPokemon.add(Pokemon(7,"Squirtle",R.drawable.squirtle,"Tras nacer, se le hincha el lomo y se le forma un caparazón. Escupe poderosa espuma por la boca.",false))
        listaPokemon.add(Pokemon(8,"Wartortle",R.drawable.wartortle,"Tiene una cola larga y peluda que simboliza la longevidad y lo hace popular entre los mayores.",false))
        listaPokemon.add(Pokemon(9,"Blastoise",R.drawable.blastoise,"Aumenta de peso deliberadamente para contrarrestar la fuerza de los chorros de agua que dispara.",false))
    }
    fun getPokemon(position: Int): Pokemon?{
        if (position>=0 && position<listaPokemon.size){
            return listaPokemon.get(position)
        }
        return null
    }
    fun getPokemons(): MutableList<Pokemon>{
        return listaPokemon
    }
    fun eliminarPokemon(pokemon: Pokemon?){
        listaPokemon.remove(pokemon)
    }
    fun actualizarPokemon(pokemon: Pokemon) {
        // Buscamos la posición del pokemon en la lista actual
        val posicion = listaPokemon.indexOfFirst { it.numeroPk == pokemon.numeroPk }
        // Si el pokemon existe en la lista (index != -1)
        if (posicion != -1) {
            // Reemplazamos el pokemon antiguo por el modificado
            listaPokemon[posicion] = pokemon
        }
    }
}