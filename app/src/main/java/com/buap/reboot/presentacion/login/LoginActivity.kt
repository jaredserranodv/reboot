package com.buap.reboot.presentacion.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.buap.reboot.AplicacionReboot
import com.buap.reboot.MainActivity
import com.buap.reboot.databinding.ActivityLoginBinding
import com.buap.reboot.presentacion.comun.ActividadBase
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.recuperarContrasena.RecuperarContrasenaActivity
import com.buap.reboot.presentacion.registro.RegistroActivity

class LoginActivity : ActividadBase() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels {
        val inyector = (application as AplicacionReboot).inyector
        FabricaViewModel { LoginViewModel(inyector.iniciarSesionCasoUso) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonEntrar.setOnClickListener {
            viewModel.iniciarSesion(
                binding.campoCorreo.text?.toString().orEmpty(),
                binding.campoContrasena.text?.toString().orEmpty()
            )
        }
        binding.enlaceRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
        binding.enlaceOlvideContrasena.setOnClickListener {
            startActivity(Intent(this, RecuperarContrasenaActivity::class.java))
        }

        viewModel.estado.observe(this) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) android.view.View.VISIBLE else android.view.View.GONE
            binding.botonEntrar.isEnabled = !estado.cargando

            estado.error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }

            if (estado.sesionIniciada) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun aplicarInsetsSeguros() {
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        val paddingSuperiorOriginal = binding.root.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val statusBarTop = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.updatePadding(top = paddingSuperiorOriginal + statusBarTop)
            insets
        }
        ViewCompat.requestApplyInsets(binding.root)
    }
}
