package com.buap.reboot.presentacion.direcciones

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentAgregarDireccionBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

private const val SIN_DIRECCION = -1L

class AgregarDireccionFragment :
    FragmentBase<FragmentAgregarDireccionBinding>(FragmentAgregarDireccionBinding::inflate) {

    private val viewModel: AgregarDireccionViewModel by viewModels {
        FabricaViewModel {
            AgregarDireccionViewModel(
                inyector.obtenerDireccionPorIdCasoUso,
                inyector.guardarDireccionCasoUso,
                inyector.obtenerSesionActualCasoUso
            )
        }
    }

    private val idDireccion: Long get() = arguments?.getLong("idDireccion") ?: SIN_DIRECCION
    private val esEdicion: Boolean get() = idDireccion != SIN_DIRECCION

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (esEdicion) {
            viewModel.cargarParaEditar(idDireccion)
        }

        binding.botonGuardar.setOnClickListener {
            viewModel.guardar(
                idDireccionEnEdicion = if (esEdicion) idDireccion else 0L,
                etiqueta = binding.campoEtiqueta.text?.toString().orEmpty(),
                calleYNumero = binding.campoCalle.text?.toString().orEmpty(),
                colonia = binding.campoColonia.text?.toString().orEmpty(),
                ciudad = binding.campoCiudad.text?.toString().orEmpty(),
                codigoPostal = binding.campoCodigoPostal.text?.toString().orEmpty(),
                telefonoContacto = binding.campoTelefono.text?.toString().orEmpty(),
                esPredeterminada = binding.casillaPredeterminada.isChecked
            )
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            binding.botonGuardar.isEnabled = !estado.cargando

            estado.direccionAEditar?.let { direccion ->
                if (binding.campoEtiqueta.text.isNullOrEmpty()) binding.campoEtiqueta.setText(direccion.etiqueta)
                if (binding.campoCalle.text.isNullOrEmpty()) binding.campoCalle.setText(direccion.calleYNumero)
                if (binding.campoColonia.text.isNullOrEmpty()) binding.campoColonia.setText(direccion.colonia)
                if (binding.campoCiudad.text.isNullOrEmpty()) binding.campoCiudad.setText(direccion.ciudad)
                if (binding.campoCodigoPostal.text.isNullOrEmpty()) binding.campoCodigoPostal.setText(direccion.codigoPostal)
                if (binding.campoTelefono.text.isNullOrEmpty()) binding.campoTelefono.setText(direccion.telefonoContacto)
                binding.casillaPredeterminada.isChecked = direccion.esPredeterminada
            }

            estado.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }

            if (estado.guardadaExitosamente) {
                findNavController().popBackStack()
            }
        }
    }
}
