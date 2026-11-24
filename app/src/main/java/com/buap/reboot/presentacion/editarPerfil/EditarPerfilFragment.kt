package com.buap.reboot.presentacion.editarPerfil

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentEditarPerfilBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

class EditarPerfilFragment : FragmentBase<FragmentEditarPerfilBinding>(FragmentEditarPerfilBinding::inflate) {

    private val viewModel: EditarPerfilViewModel by viewModels {
        FabricaViewModel { EditarPerfilViewModel(inyector.obtenerSesionActualCasoUso, inyector.actualizarPerfilCasoUso) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.botonGuardar.setOnClickListener {
            viewModel.guardar(
                nombreCompleto = binding.campoNombre.text?.toString().orEmpty(),
                telefono = binding.campoTelefono.text?.toString().orEmpty()
            )
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            binding.botonGuardar.isEnabled = !estado.cargando

            estado.usuario?.let { usuario ->
                if (binding.campoNombre.text.isNullOrEmpty()) binding.campoNombre.setText(usuario.nombreCompleto)
                if (binding.campoTelefono.text.isNullOrEmpty()) binding.campoTelefono.setText(usuario.telefono)
                binding.campoCorreo.setText(usuario.correo)
            }
            estado.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }

            if (estado.guardadoExitosamente) {
                Toast.makeText(requireContext(), R.string.editar_perfil_guardado, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        viewModel.cargar()
    }
}
