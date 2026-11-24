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

        binding.campoCategoria.setOnItemClickListener { _, _, _, _ -> buscarConFiltrosActuales() }
        binding.campoEstado.setOnItemClickListener { _, _, _, _ -> buscarConFiltrosActuales() }
        binding.botonAplicarFiltros.setOnClickListener { buscarConFiltrosActuales() }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            adapter.submitList(estado.articulos)
            binding.textoVacio.visibility = if (!estado.cargando && estado.articulos.isEmpty()) View.VISIBLE else View.GONE
        }

        val textoInicial = arguments?.getString("texto")?.trim().orEmpty()
        val categoriaInicial = arguments?.getString("categoria")?.trim().orEmpty()
        if (categoriaInicial.isNotEmpty()) {
            binding.campoCategoria.setText(categoriaInicial, false)
        }
        val textoFiltro = if (categoriaInicial.isNotEmpty() && textoInicial.equals(categoriaInicial, ignoreCase = true)) {
            ""
        } else {
            textoInicial
        }
        viewModel.buscar(FiltrosBusqueda(texto = textoFiltro, categoria = categoriaInicial.takeIf { it.isNotEmpty() }))
    }

    private fun buscarConFiltrosActuales() {
        val precioMinimo = binding.campoPrecioMinimo.text?.toString()?.toDoubleOrNull()
        val precioMaximo = binding.campoPrecioMaximo.text?.toString()?.toDoubleOrNull()
        val estado = binding.campoEstado.text?.toString()?.trim()?.takeIf { it.isNotBlank() }
            ?.let { EstadoArticulo.desdeEtiqueta(it) }
        val categoria = binding.campoCategoria.text?.toString()?.trim()?.takeIf { it.isNotBlank() }
        val textoArg = arguments?.getString("texto")?.trim().orEmpty()
        val texto = if (categoria != null && textoArg.equals(categoria, ignoreCase = true)) {
            ""
        } else {
            textoArg
        }

        viewModel.buscar(
            FiltrosBusqueda(
                texto = texto,
                categoria = categoria,
                precioMinimo = precioMinimo,
                precioMaximo = precioMaximo,
                ciudad = binding.campoCiudad.text?.toString()?.trim()?.takeIf { it.isNotBlank() },
                estadoArticulo = estado
            )
        )
    }
}
