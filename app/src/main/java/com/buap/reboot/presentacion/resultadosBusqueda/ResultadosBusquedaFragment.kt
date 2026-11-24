package com.buap.reboot.presentacion.resultadosBusqueda

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentResultadosBusquedaBinding
import com.buap.reboot.dominio.modelo.EstadoArticulo
import com.buap.reboot.dominio.modelo.FiltrosBusqueda
import com.buap.reboot.presentacion.comun.ArticuloAdapter
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

private const val COLUMNAS_GRID = 2

class ResultadosBusquedaFragment :
    FragmentBase<FragmentResultadosBusquedaBinding>(FragmentResultadosBusquedaBinding::inflate) {

    private val viewModel: ResultadosBusquedaViewModel by viewModels {
        FabricaViewModel { ResultadosBusquedaViewModel(inyector.buscarArticulosCasoUso) }
    }

    private val adapter = ArticuloAdapter { articulo ->
        findNavController().navigate(
            R.id.accion_resultados_a_detalle,
            Bundle().apply { putLong("idArticulo", articulo.id) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listaArticulos.layoutManager = GridLayoutManager(requireContext(), COLUMNAS_GRID)
        binding.listaArticulos.adapter = adapter

        binding.campoCategoria.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.categorias_populares))
        )
        binding.campoEstado.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.estados_articulo))
        )

        binding.botonAplicarFiltros.setOnClickListener { buscarConFiltrosActuales() }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            adapter.submitList(estado.articulos)
            binding.textoVacio.visibility = if (!estado.cargando && estado.articulos.isEmpty()) View.VISIBLE else View.GONE
        }

        val textoInicial = arguments?.getString("texto").orEmpty()
        val categoriaInicial = arguments?.getString("categoria").orEmpty()
        if (categoriaInicial.isNotEmpty()) {
            binding.campoCategoria.setText(categoriaInicial, false)
        }
        viewModel.buscar(FiltrosBusqueda(texto = textoInicial, categoria = categoriaInicial.takeIf { it.isNotEmpty() }))
    }

    private fun buscarConFiltrosActuales() {
        val precioMinimo = binding.campoPrecioMinimo.text?.toString()?.toDoubleOrNull()
        val precioMaximo = binding.campoPrecioMaximo.text?.toString()?.toDoubleOrNull()
        val estado = binding.campoEstado.text?.toString()?.takeIf { it.isNotBlank() }
            ?.let { EstadoArticulo.desdeEtiqueta(it) }

        viewModel.buscar(
            FiltrosBusqueda(
                texto = arguments?.getString("texto").orEmpty(),
                categoria = binding.campoCategoria.text?.toString()?.takeIf { it.isNotBlank() },
                precioMinimo = precioMinimo,
                precioMaximo = precioMaximo,
                ciudad = binding.campoCiudad.text?.toString()?.takeIf { it.isNotBlank() },
                estadoArticulo = estado
            )
        )
    }
}
