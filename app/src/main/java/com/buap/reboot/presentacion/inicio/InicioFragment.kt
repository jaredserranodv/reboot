package com.buap.reboot.presentacion.inicio

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentInicioBinding
import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.presentacion.comun.ArticuloAdapter
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

private const val COLUMNAS_GRID = 2

class InicioFragment : FragmentBase<FragmentInicioBinding>(FragmentInicioBinding::inflate) {

    private val viewModel: InicioViewModel by viewModels {
        FabricaViewModel { InicioViewModel(inyector.obtenerArticulosPublicadosCasoUso) }
    }

    private val adapter = ArticuloAdapter { articulo -> irADetalle(articulo) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { root, windowInsets ->
            val barraEstado = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            root.updatePadding(top = barraEstado.top)
            windowInsets
        }

        binding.listaArticulos.layoutManager = GridLayoutManager(requireContext(), COLUMNAS_GRID)
        binding.listaArticulos.adapter = adapter

        binding.campoBusqueda.setOnClickListener {
            findNavController().navigate(R.id.accion_inicio_a_buscar)
        }
        binding.contenedorBusqueda.setEndIconOnClickListener {
            findNavController().navigate(R.id.accion_inicio_a_buscar)
        }

        resources.getStringArray(R.array.categorias_populares).forEach { categoria ->
            binding.grupoCategorias.addView(crearChipCategoria(categoria))
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            adapter.submitList(estado.articulos)
            binding.textoVacio.visibility = if (!estado.cargando && estado.articulos.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.cargar()
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargar()
    }

    private fun irADetalle(articulo: Articulo) {
        findNavController().navigate(
            R.id.accion_inicio_a_detalle,
            Bundle().apply { putLong("idArticulo", articulo.id) }
        )
    }

    private fun crearChipCategoria(categoria: String): Chip =
        Chip(requireContext()).apply {
            text = categoria
            isClickable = true
            setOnClickListener {
                findNavController().navigate(
                    R.id.accion_inicio_a_resultados,
                    Bundle().apply { putString("categoria", categoria) }
                )
            }
        }
}
