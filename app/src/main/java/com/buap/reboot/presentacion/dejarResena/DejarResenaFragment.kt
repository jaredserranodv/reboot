package com.buap.reboot.presentacion.dejarResena

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.databinding.FragmentDejarResenaBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

class DejarResenaFragment : FragmentBase<FragmentDejarResenaBinding>(FragmentDejarResenaBinding::inflate) {

    private val viewModel: DejarResenaViewModel by viewModels {
        FabricaViewModel { DejarResenaViewModel(inyector.obtenerDetalleArticuloCasoUso, inyector.enviarResenaCasoUso) }
    }

    private val idTransaccion: Long get() = arguments?.getLong("idTransaccion") ?: 0L
    private val idArticulo: Long get() = arguments?.getLong("idArticulo") ?: 0L
    private val idVendedor: Long get() = arguments?.getLong("idVendedor") ?: 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.botonEnviarResena.setOnClickListener {
            val idComprador = inyector.obtenerSesionActualCasoUso.ejecutar()?.id
            if (idComprador == null) {
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
                Toast.makeText(requireContext(), com.buap.reboot.R.string.resena_enviada, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        viewModel.cargarArticulo(idArticulo)
    }
}
