package com.buap.reboot.presentacion.notificaciones

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.buap.reboot.databinding.FragmentBuzonBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase
import com.buap.reboot.presentacion.comun.NotificacionAdapter

class BuzonFragment : FragmentBase<FragmentBuzonBinding>(FragmentBuzonBinding::inflate) {

    private val viewModel: BuzonViewModel by viewModels {
        FabricaViewModel {
            BuzonViewModel(
                inyector.obtenerNotificacionesCasoUso,
                inyector.marcarNotificacionLeidaCasoUso,
                inyector.obtenerSesionActualCasoUso
            )
        }
    }

    private val adapter = NotificacionAdapter { notificacion -> viewModel.marcarComoLeida(notificacion.id) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listaNotificaciones.layoutManager = LinearLayoutManager(requireContext())
        binding.listaNotificaciones.adapter = adapter

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            adapter.submitList(estado.notificaciones)
            binding.textoVacio.visibility = if (!estado.cargando && estado.notificaciones.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.cargar()
    }
}
