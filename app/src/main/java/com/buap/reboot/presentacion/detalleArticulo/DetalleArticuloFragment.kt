package com.buap.reboot.presentacion.detalleArticulo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentDetalleArticuloBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase
import com.buap.reboot.presentacion.comun.cargarFotoArticulo

class DetalleArticuloFragment :
    FragmentBase<FragmentDetalleArticuloBinding>(FragmentDetalleArticuloBinding::inflate) {

    private val viewModel: DetalleArticuloViewModel by viewModels {
        FabricaViewModel { DetalleArticuloViewModel(inyector.obtenerDetalleArticuloCasoUso) }
    }

    private val idArticulo: Long get() = arguments?.getLong("idArticulo") ?: 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.botonComprarAhora.setOnClickListener {
            findNavController().navigate(
                R.id.accion_detalle_a_pago,
                Bundle().apply { putLong("idArticulo", idArticulo) }
            )
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE

            estado.detalle?.let { detalle ->
                binding.tituloArticulo.text = detalle.articulo.titulo
                binding.precioArticulo.text = "$${detalle.articulo.precio.toInt()}"
                binding.descripcionArticulo.text = detalle.articulo.descripcion
                binding.nombreVendedor.text = detalle.nombreVendedor
                binding.ratingVendedor.rating = detalle.ratingVendedor
                binding.imagenArticulo.cargarFotoArticulo(detalle.articulo.rutaImagen)
            }
        }

        viewModel.cargar(idArticulo)
    }
}
