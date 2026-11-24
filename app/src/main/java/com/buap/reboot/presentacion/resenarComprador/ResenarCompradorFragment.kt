package com.buap.reboot.presentacion.resenarComprador

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentResenarCompradorBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase
import com.buap.reboot.presentacion.dejarResena.DejarResenaViewModel

/** Mismo mecanismo que "Dejar resena" (12), con los roles invertidos: aqui califica el vendedor al comprador. */
class ResenarCompradorFragment :
    FragmentBase<FragmentResenarCompradorBinding>(FragmentResenarCompradorBinding::inflate) {

    private val viewModel: DejarResenaViewModel by viewModels {
        FabricaViewModel { DejarResenaViewModel(inyector.obtenerDetalleArticuloCasoUso, inyector.enviarResenaCasoUso) }
    }

    private val idTransaccion: Long get() = arguments?.getLong("idTransaccion") ?: 0L
    private val idArticulo: Long get() = arguments?.getLong("idArticulo") ?: 0L
    private val idComprador: Long get() = arguments?.getLong("idComprador") ?: 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.botonEnviarResena.setOnClickListener {
            val idVendedor = inyector.obtenerSesionActualCasoUso.ejecutar()?.id
            if (idVendedor == null) {
                Toast.makeText(requireContext(), "No hay sesion activa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.enviar(
                idTransaccion = idTransaccion,
                idVendedor = idVendedor,
                idComprador = idComprador,
                calificacion = binding.selectorEstrellas.rating.toInt(),
                comentario = binding.campoComentario.text?.toString().orEmpty(),
                recomendado = binding.casillaRecomendado.isChecked
            )
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            binding.botonEnviarResena.isEnabled = !estado.cargando

            estado.articulo?.let {
                binding.tituloArticulo.text = it.titulo
                binding.precioArticulo.text = "$${it.precio.toInt()}"
            }
            estado.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }

            if (estado.enviadaExitosamente) {
                Toast.makeText(requireContext(), R.string.resena_enviada, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        viewModel.cargarArticulo(idArticulo)
    }
}
