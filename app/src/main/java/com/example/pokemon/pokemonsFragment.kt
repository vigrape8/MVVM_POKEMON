package com.example.pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemon.Adapter.PokemonAdapter
import com.example.pokemon.Model.Pokemon
import com.example.pokemon.ViewModel.PokemonViewModel
import com.example.pokemon.databinding.FragmentPokemonsBinding

class pokemonsFragment : Fragment() {

    private var viewModel: PokemonViewModel?=null
    private lateinit var binding: FragmentPokemonsBinding
    private lateinit var adapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentPokemonsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //inicializar viewModel
        viewModel= ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java)
        //inicializar adapter
        adapter= PokemonAdapter(requireContext(),
            ArrayList<Pokemon?>() as ArrayList<Pokemon>? as ArrayList<Pokemon?>?,viewModel)
        //configurar recyclerview
        binding.rvPokemons.adapter=adapter
        binding.rvPokemons.layoutManager= GridLayoutManager(requireContext(),2)
        viewModel?.pokemones?.observe(getViewLifecycleOwner(),{lista->adapter.establecerLista(lista as MutableList<Pokemon>?)})
    }
}