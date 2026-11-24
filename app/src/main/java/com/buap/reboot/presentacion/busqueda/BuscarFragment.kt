package com.buap.reboot.presentacion.busqueda

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentBuscarBinding
import com.buap.reboot.presentacion.comun.FragmentBase

class BuscarFragment : FragmentBase<FragmentBuscarBinding>(FragmentBuscarBinding::inflate) {

    private val viewModel: BuscarViewModel by viewModels()

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.campoBusqueda.setOnEditorActionListener { campo, accion, _ ->
            if (accion == EditorInfo.IME_ACTION_SEARCH) {
                buscar(campo.text.toString())
                true
            } else {
                false
            }
        }

        resources.getStringArray(R.array.categorias_populares).forEach { categoria ->
            binding.grupoCategorias.addView(crearChip(categoria) { buscar(categoria) })
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.grupoRecientes.removeAllViews()
            estado.busquedasRecientes.forEach { texto ->
                binding.grupoRecientes.addView(crearChip(texto) { buscar(texto) })
            }
        }
    }

    private fun buscar(texto: String) {
        if (texto.isBlank()) return
        viewModel.registrarBusqueda(texto)
        findNavController().navigate(
            R.id.accion_buscar_a_resultados,
            android.os.Bundle().apply { putString("texto", texto) }
        )
    }

    private fun crearChip(texto: String, alHacerClic: () -> Unit): Chip =
        Chip(requireContext()).apply {
            text = texto
            isClickable = true
            setOnClickListener { alHacerClic() }
        }
}
