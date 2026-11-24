package com.buap.reboot.presentacion.misPublicaciones

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentMisPublicacionesBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

class MisPublicacionesFragment :
    FragmentBase<FragmentMisPublicacionesBinding>(FragmentMisPublicacionesBinding::inflate) {

    private val viewModel: MisPublicacionesViewModel by viewModels {
        FabricaViewModel {
            MisPublicacionesViewModel(
                inyector.obtenerMisPublicacionesCasoUso,
                inyector.cambiarDisponibilidadArticuloCasoUso,
                inyector.obtenerSesionActualCasoUso
            )
        }
    }

    private val adapter = PublicacionAdapter(
        alHacerClicEditar = { articulo ->
            findNavController().navigate(
                R.id.accion_publicaciones_a_editar_articulo,
                Bundle().apply { putLong("idArticulo", articulo.id) }
            )
        },
        alHacerClicReactivar = { articulo -> viewModel.reactivar(articulo.id) },
        alHacerClicResenarComprador = { articulo -> navegarAResenarComprador(articulo.id) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listaPublicaciones.layoutManager = LinearLayoutManager(requireContext())
        binding.listaPublicaciones.adapter = adapter

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            adapter.submitList(estado.publicaciones)
            binding.textoVacio.visibility = if (!estado.cargando && estado.publicaciones.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.cargar()
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargar()
    }

    private fun navegarAResenarComprador(idArticulo: Long) {
        val transaccion = inyector.obtenerTransaccionPorArticuloCasoUso.ejecutar(idArticulo)
        if (transaccion == null) {
            Toast.makeText(requireContext(), "No se encontro la compra de este articulo", Toast.LENGTH_SHORT).show()
            return
        }
        findNavController().navigate(
            R.id.accion_publicaciones_a_resenar_comprador,
            Bundle().apply {
                putLong("idTransaccion", transaccion.id)
                putLong("idArticulo", idArticulo)
                putLong("idComprador", transaccion.idComprador)
            }
        )
    }
}
