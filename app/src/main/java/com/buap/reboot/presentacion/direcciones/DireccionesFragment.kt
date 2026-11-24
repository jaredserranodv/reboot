package com.buap.reboot.presentacion.direcciones

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentDireccionesBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

private const val SIN_DIRECCION = -1L

class DireccionesFragment : FragmentBase<FragmentDireccionesBinding>(FragmentDireccionesBinding::inflate) {

    private val viewModel: DireccionesViewModel by viewModels {
        FabricaViewModel {
            DireccionesViewModel(
                inyector.obtenerDireccionesCasoUso,
                inyector.eliminarDireccionCasoUso,
                inyector.marcarDireccionPredeterminadaCasoUso,
                inyector.obtenerSesionActualCasoUso
            )
        }
    }

    private val adapter = DireccionAdapter(
        alHacerClicEditar = { navegarAAgregarDireccion(it.id) },
        alHacerClicEliminar = { viewModel.eliminar(it.id) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listaDirecciones.layoutManager = LinearLayoutManager(requireContext())
        binding.listaDirecciones.adapter = adapter

        binding.botonAgregar.setOnClickListener { navegarAAgregarDireccion(SIN_DIRECCION) }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            adapter.submitList(estado.direcciones)
            binding.textoVacio.visibility = if (!estado.cargando && estado.direcciones.isEmpty()) View.VISIBLE else View.GONE

            estado.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }

        viewModel.cargar()
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargar()
    }

    private fun navegarAAgregarDireccion(idDireccion: Long) {
        findNavController().navigate(
            R.id.accion_direcciones_a_agregar,
            Bundle().apply { putLong("idDireccion", idDireccion) }
        )
    }
}
