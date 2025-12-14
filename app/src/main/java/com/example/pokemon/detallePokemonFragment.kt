package com.example.pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.pokemon.Model.Pokemon
import com.example.pokemon.ViewModel.PokemonViewModel
import com.example.pokemon.databinding.FragmentDetallePokemonBinding



class detallePokemonFragment : Fragment() {
    private var _binding: FragmentDetallePokemonBinding?=null
    private val binding get()=_binding!!
    private var pokemon: Pokemon?=null
    private var viewModel: PokemonViewModel?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentDetallePokemonBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java)
        viewModel?.pokemonSeleccionado?.observe(getViewLifecycleOwner(), { pokemon ->
            if (pokemon != null) {
                // Si ha llegado un pokemon, cargamos sus datos en el layout
                //cambiar a 001
                val idFormateado = String.format("#%03d", pokemon.numeroPk)
                binding.tvid.text = idFormateado
                binding.tvNombreDetalle.text = pokemon.nombre
                binding.ivDetalle.setImageResource(pokemon.imagen)
                binding.tvDescripcion.text = pokemon.descripcion
            } else {
                // En caso de error, podríamos volver atrás o mostrar un mensaje
                Toast.makeText(
                    requireContext(),
                    "No se pudo cargar el detalle del pokemon",
                    Toast.LENGTH_SHORT
                ).show()
                view.post {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel?.seleccionarPokemon(null)
    }
}