package com.buap.reboot.presentacion.metodosPago

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.databinding.FragmentAgregarTarjetaBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase

class AgregarTarjetaFragment : FragmentBase<FragmentAgregarTarjetaBinding>(FragmentAgregarTarjetaBinding::inflate) {

    private val viewModel: AgregarTarjetaViewModel by viewModels {
        FabricaViewModel { AgregarTarjetaViewModel(inyector.agregarMetodoPagoCasoUso, inyector.obtenerSesionActualCasoUso) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.botonGuardar.setOnClickListener {
            viewModel.guardar(
                numeroTarjeta = binding.campoNumero.text?.toString().orEmpty(),
                vencimiento = binding.campoVencimiento.text?.toString().orEmpty(),
                cvv = binding.campoCvv.text?.toString().orEmpty(),
                esPredeterminada = binding.casillaPredeterminado.isChecked
            )
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            binding.botonGuardar.isEnabled = !estado.cargando

            estado.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }

            if (estado.guardadaExitosamente) {
                findNavController().popBackStack()
            }
        }
    }
}
