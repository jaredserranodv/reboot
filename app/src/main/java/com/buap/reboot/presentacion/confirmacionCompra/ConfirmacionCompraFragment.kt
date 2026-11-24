package com.buap.reboot.presentacion.confirmacionCompra

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentConfirmacionCompraBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

class ConfirmacionCompraFragment :
    FragmentBase<FragmentConfirmacionCompraBinding>(FragmentConfirmacionCompraBinding::inflate) {

    private val viewModel: ConfirmacionCompraViewModel by viewModels {
        FabricaViewModel { ConfirmacionCompraViewModel(inyector.obtenerConfirmacionCompraCasoUso) }
    }

    private val idTransaccion: Long get() = arguments?.getLong("idTransaccion") ?: 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.botonVerCompras.setOnClickListener {
            findNavController().navigate(R.id.accion_confirmacion_a_compras)
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            estado.confirmacion?.let { confirmacion ->
                binding.tituloArticulo.text = confirmacion.articulo.titulo
                binding.totalPagado.text = "$${confirmacion.totalPagado.toInt()}"
                binding.direccionEnvio.text = confirmacion.direccionEnvio
            }
        }

        viewModel.cargar(idTransaccion)
    }
}
