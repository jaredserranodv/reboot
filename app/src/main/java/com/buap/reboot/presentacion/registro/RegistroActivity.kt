package com.buap.reboot.presentacion.registro

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.buap.reboot.AplicacionReboot
import com.buap.reboot.R
import com.buap.reboot.databinding.ActivityRegistroBinding
import com.buap.reboot.presentacion.comun.FabricaViewModel

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding

    private val viewModel: RegistroViewModel by viewModels {
        val inyector = (application as AplicacionReboot).inyector
        FabricaViewModel { RegistroViewModel(inyector.registrarUsuarioCasoUso) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.campoPreguntaSeguridad.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.preguntas_seguridad))
        )

        binding.botonCrearCuenta.setOnClickListener {
            viewModel.registrar(
                nombreCompleto = binding.campoNombre.text?.toString().orEmpty(),
                correo = binding.campoCorreo.text?.toString().orEmpty(),
                telefono = binding.campoTelefono.text?.toString().orEmpty(),
                preguntaSeguridad = binding.campoPreguntaSeguridad.text?.toString().orEmpty(),
                respuestaSeguridad = binding.campoRespuestaSeguridad.text?.toString().orEmpty(),
                contrasena = binding.campoContrasena.text?.toString().orEmpty(),
                confirmacionContrasena = binding.campoConfirmarContrasena.text?.toString().orEmpty(),
                aceptaTerminos = binding.casillaTerminos.isChecked
            )
        }
        binding.enlaceLogin.setOnClickListener { finish() }

        viewModel.estado.observe(this) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) android.view.View.VISIBLE else android.view.View.GONE
            binding.botonCrearCuenta.isEnabled = !estado.cargando

            estado.error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }

            if (estado.registroExitoso) {
                Toast.makeText(this, "Cuenta creada, ahora inicia sesion", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
