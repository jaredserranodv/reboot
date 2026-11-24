package com.buap.reboot.presentacion.recuperarContrasena

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.buap.reboot.AplicacionReboot
import com.buap.reboot.databinding.ActivityRecuperarContrasenaBinding
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.presentacion.comun.ActividadBase
import com.buap.reboot.presentacion.comun.FabricaViewModel

class RecuperarContrasenaActivity : ActividadBase() {

    private lateinit var binding: ActivityRecuperarContrasenaBinding

    private val viewModel: RecuperarContrasenaViewModel by viewModels {
        val inyector = (application as AplicacionReboot).inyector
        FabricaViewModel { RecuperarContrasenaViewModel(inyector.recuperarContrasenaCasoUso) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.botonEnviarCodigo.setOnClickListener {
            when (val resultado = viewModel.enviarCodigo(binding.campoCorreo.text?.toString().orEmpty())) {
                is Resultado.Exito -> Toast.makeText(this, resultado.datos, Toast.LENGTH_LONG).show()
                is Resultado.Error -> Toast.makeText(this, resultado.mensaje, Toast.LENGTH_SHORT).show()
            }
        }

        binding.botonVerificar.setOnClickListener {
            viewModel.validarCodigo(
                binding.campoCodigo.text?.toString().orEmpty(),
                binding.campoRespuesta.text?.toString().orEmpty()
            )
        }

        binding.botonRestablecer.setOnClickListener {
            viewModel.restablecerContrasena(
                binding.campoNuevaContrasena.text?.toString().orEmpty(),
                binding.campoConfirmarContrasena.text?.toString().orEmpty()
            )
        }

        viewModel.estado.observe(this) { estado ->
            binding.contenedorPaso2.visibility = if (estado.paso >= 2) View.VISIBLE else View.GONE
            binding.contenedorPaso3.visibility = if (estado.paso >= 3) View.VISIBLE else View.GONE
            binding.preguntaSeguridad.text = estado.preguntaSeguridad.orEmpty()

            estado.error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }

            if (estado.restablecidoExitosamente) {
                Toast.makeText(this, getString(com.buap.reboot.R.string.recuperar_exito), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
