package com.buap.reboot.presentacion.pago

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentPagoBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

class PagoFragment : FragmentBase<FragmentPagoBinding>(FragmentPagoBinding::inflate) {

    private val viewModel: PagoViewModel by viewModels {
        FabricaViewModel {
            PagoViewModel(
                inyector.obtenerDetalleArticuloCasoUso,
                inyector.obtenerSesionActualCasoUso,
                inyector.obtenerDireccionPredeterminadaCasoUso,
                inyector.realizarCompraCasoUso
            )
        }
    }

    private val idArticulo: Long get() = arguments?.getLong("idArticulo") ?: 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.botonConfirmarPago.setOnClickListener {
            viewModel.confirmarPago(
                idArticulo = idArticulo,
                numeroTarjeta = binding.campoNumeroTarjeta.text?.toString().orEmpty(),
                fechaVencimiento = binding.campoFechaVencimiento.text?.toString().orEmpty(),
                cvv = binding.campoCvv.text?.toString().orEmpty(),
                direccionEnvio = binding.campoDireccionEnvio.text?.toString().orEmpty()
            )
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            binding.botonConfirmarPago.isEnabled = !estado.cargando

            estado.articulo?.let {
                binding.tituloArticulo.text = it.titulo
                binding.totalPagar.text = "$${it.precio.toInt()}"
            }
            if (binding.campoDireccionEnvio.text.isNullOrEmpty()) {
                estado.direccionPredeterminada?.let { binding.campoDireccionEnvio.setText(it) }
            }
            estado.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }

            if (estado.pagoConfirmado) {
                findNavController().navigate(
                    R.id.accion_pago_a_confirmacion,
                    Bundle().apply { putLong("idTransaccion", estado.idTransaccionConfirmada) }
                )
            }
        }

        viewModel.cargarArticulo(idArticulo)
    }
}
