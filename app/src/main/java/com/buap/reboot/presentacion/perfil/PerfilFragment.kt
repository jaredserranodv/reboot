package com.buap.reboot.presentacion.perfil

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentPerfilBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase
import com.buap.reboot.presentacion.login.LoginActivity

class PerfilFragment : FragmentBase<FragmentPerfilBinding>(FragmentPerfilBinding::inflate) {

    private val viewModel: PerfilViewModel by viewModels {
        FabricaViewModel {
            PerfilViewModel(
                inyector.obtenerPerfilCasoUso,
                inyector.obtenerSesionActualCasoUso,
                inyector.cerrarSesionCasoUso
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.botonEditarPerfil.setOnClickListener {
            findNavController().navigate(R.id.accion_perfil_a_editar_perfil)
        }

        binding.accesoMisPublicaciones.setOnClickListener {
            findNavController().navigate(R.id.accion_perfil_a_publicaciones)
        }
        binding.accesoMisCompras.setOnClickListener {
            findNavController().navigate(R.id.accion_perfil_a_compras)
        }
        binding.accesoMetodosPago.setOnClickListener {
            findNavController().navigate(R.id.accion_perfil_a_metodos_pago)
        }
        binding.accesoDireccionesEnvio.setOnClickListener {
            findNavController().navigate(R.id.accion_perfil_a_direcciones)
        }

        binding.botonCerrarSesion.setOnClickListener { viewModel.cerrarSesion() }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE

            estado.perfil?.let { perfil ->
                binding.nombreUsuario.text = perfil.usuario.nombreCompleto
                binding.correoUsuario.text = perfil.usuario.correo
                binding.ratingUsuario.rating = perfil.ratingPromedio
                binding.totalPublicaciones.text = perfil.totalPublicaciones.toString()
                binding.totalCompras.text = perfil.totalCompras.toString()
            }

            if (estado.sesionCerrada) {
                startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        }

        viewModel.cargar()
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargar()
    }
}
