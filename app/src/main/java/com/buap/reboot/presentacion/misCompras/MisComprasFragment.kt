package com.buap.reboot.presentacion.misCompras

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentMisComprasBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

class MisComprasFragment : FragmentBase<FragmentMisComprasBinding>(FragmentMisComprasBinding::inflate) {

    private val viewModel: MisComprasViewModel by viewModels {
        FabricaViewModel { MisComprasViewModel(inyector.obtenerMisComprasCasoUso, inyector.obtenerSesionActualCasoUso) }
    }

    private val adapter = CompraAdapter { compra ->
        findNavController().navigate(
            R.id.accion_compras_a_resena,
            Bundle().apply {
                putLong("idTransaccion", compra.transaccion.id)
                putLong("idArticulo", compra.articulo.id)
                putLong("idVendedor", compra.articulo.idVendedor)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listaCompras.layoutManager = LinearLayoutManager(requireContext())
        binding.listaCompras.adapter = adapter

        binding.pestanas.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.seleccionarPestana(soloEntregadas = tab.position == 1)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) = Unit
            override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            val visibles = estado.comprasVisibles
            adapter.submitList(visibles)
            binding.textoVacio.visibility = if (!estado.cargando && visibles.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.cargar()
    }
}
