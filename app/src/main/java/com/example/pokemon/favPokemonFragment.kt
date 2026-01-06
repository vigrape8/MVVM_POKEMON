package com.example.pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.Adapter.PokemonAdapter
import com.example.pokemon.Model.Pokemon
import com.example.pokemon.ViewModel.PokemonViewModel
import androidx.appcompat.widget.SearchView
import com.example.pokemon.databinding.FragmentFavPokemonBinding

class favPokemonFragment : Fragment() {

    private var viewModel: PokemonViewModel?=null
    private lateinit var binding: FragmentFavPokemonBinding
    private lateinit var adapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavPokemonBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //inicializar viewModel
        viewModel= ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java)
        //inicializar adapter
        adapter= PokemonAdapter(requireContext(),
            ArrayList<Pokemon?>() as ArrayList<Pokemon>? as ArrayList<Pokemon?>?,viewModel,R.id.action_favPokemonFragment_to_detallePokemonFragment)
        //configurar recyclerview
        binding.rvPokemons.adapter=adapter
        binding.rvPokemons.layoutManager= GridLayoutManager(requireContext(),2)
        //eliminar pokemon
        eventoEliminarElto()
        //Solo cogemos los favoritos
        viewModel?.cargarFavoritos()
        //actualizar lista
        viewModel?.pokemones?.observe(getViewLifecycleOwner(),{lista->adapter.establecerLista(lista as MutableList<Pokemon>?)})
        //Buscar por nombre
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel?.buscarEnFavoritos(newText?: "")
                return true
            }
        })
    }
    private fun eventoEliminarElto() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0, // No se permite mover (drag)
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // Permite deslizar hacia ambos lados
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // No necesitamos movimiento (drag & drop)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    // Pedimos al ViewModel que elimine el pokemon de esa posición
                    viewModel?.borrarPokemon(position)
                }
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.rvPokemons)
    }
}