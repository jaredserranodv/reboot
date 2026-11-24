package com.buap.reboot.presentacion.metodosPago

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentMetodosPagoBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

class MetodosPagoFragment : FragmentBase<FragmentMetodosPagoBinding>(FragmentMetodosPagoBinding::inflate) {

    private val viewModel: MetodosPagoViewModel by viewModels {
        FabricaViewModel {
            MetodosPagoViewModel(
                inyector.obtenerMetodosPagoCasoUso,
                inyector.eliminarMetodoPagoCasoUso,
                inyector.obtenerSesionActualCasoUso
            )
        }
    }

    private val adapter = MetodoPagoAdapter(alHacerClicEliminar = { viewModel.eliminar(it.id) })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listaMetodosPago.layoutManager = LinearLayoutManager(requireContext())
        binding.listaMetodosPago.adapter = adapter

        binding.botonAgregar.setOnClickListener {
            findNavController().navigate(R.id.accion_metodos_pago_a_agregar)
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            adapter.submitList(estado.metodosPago)
            binding.textoVacio.visibility = if (!estado.cargando && estado.metodosPago.isEmpty()) View.VISIBLE else View.GONE

            estado.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        }

        viewModel.cargar()
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargar()
    }
}
