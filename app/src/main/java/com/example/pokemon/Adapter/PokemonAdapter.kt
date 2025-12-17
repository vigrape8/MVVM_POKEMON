package com.example.pokemon.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.Model.Pokemon
import com.example.pokemon.R
import com.example.pokemon.ViewModel.PokemonViewModel
import com.example.pokemon.databinding.ViewholderPokemonBinding


class PokemonAdapter(
    context: Context?,
    pokemons: ArrayList<Pokemon?>?, // Recibimos la lista inicial
    viewModel: PokemonViewModel?

) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>(){
    private var pokemones: MutableList<Pokemon>?
    private var inflater: LayoutInflater
    private var viewModel: PokemonViewModel?=null

    init {
        this.pokemones= pokemons as MutableList<Pokemon>?
        this.inflater= LayoutInflater.from(context)
        this.viewModel=viewModel
    }

    //crea un nuevo ViewHolder cuando el RecyclerView lo necesita
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.viewholder_pokemon,parent,false)
        return PokemonViewHolder(view)
    }
    //rellena los datos en el ViewHolder que corresponde a la posicion
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon=pokemones!![position]
        //cambiar a 001
        val numeroFormateado = String.format("#%03d", pokemon.numeroPk)
        holder.binding.tvNumero.text = numeroFormateado
        holder.binding.tvNombre.text=pokemon.nombre
        holder.binding.ivPokemon.setImageResource(pokemon.imagen)
        //establecemos el favorito
        establecerIconoFavorito(pokemon, holder)
        //cambiamos la estrella al clickar
        holder.binding.iconFavorite.setOnClickListener {
            marcarFavorito(pokemon, holder)
        }
        //ir al fragmento detalle
        holder.itemView.setOnClickListener {v ->
            //guarda el pokemon en el viewmodel
            viewModel?.seleccionarPokemon(pokemon)
            //pasamos al fragmento detalle
            val navController=findNavController(v)
            navController.navigate(R.id.action_pokemonsFragment_to_detallePokemonFragment)
        }
    }
    //cuantos elementos hay en la lista
    override fun getItemCount(): Int {
        return if(pokemones!=null) pokemones!!.size else 0
    }

    // Permite actualizar la lista completa desde fuera del adaptador
    fun establecerLista(pokemones: MutableList<Pokemon>?) {
        this.pokemones = pokemones
        notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado
    }

    // Clase interna ViewHolder que representa un solo elemento con ViewBinding
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ViewholderPokemonBinding

        // El constructor recibe la vista del layout inflado
        init {
            // Asociamos el objeto binding con la vista
            binding = ViewholderPokemonBinding.bind(itemView)
        }
    }

    private fun marcarFavorito(pokemon: Pokemon, holder: PokemonViewHolder?) {
        // Cambiamos el estado del pokemon
        pokemon.favorito = !pokemon.favorito

        establecerIconoFavorito(pokemon, holder)

        viewModel?.actualizarPokemon(pokemon)
    }
    private fun establecerIconoFavorito(pokemon: Pokemon, holder: PokemonViewHolder?) {
        if (pokemon.favorito) {
            holder?.binding?.iconFavorite?.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            holder?.binding?.iconFavorite?.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }
}