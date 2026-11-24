package com.buap.reboot.presentacion.registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.RegistrarUsuarioCasoUso
import com.buap.reboot.nucleo.Resultado
import java.time.LocalDate

class RegistroViewModel(
    private val registrarUsuarioCasoUso: RegistrarUsuarioCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoRegistro())
    val estado: LiveData<EstadoRegistro> = _estado

    fun registrar(
        nombreCompleto: String,
        correo: String,
        telefono: String,
        preguntaSeguridad: String,
        respuestaSeguridad: String,
        contrasena: String,
        confirmacionContrasena: String,
        aceptaTerminos: Boolean
    ) {
        if (!aceptaTerminos) {
            _estado.value = EstadoRegistro(error = "Debes aceptar los terminos y condiciones")
            return
        }

        _estado.value = EstadoRegistro(cargando = true)
        val resultado = registrarUsuarioCasoUso.ejecutar(
            nombreCompleto = nombreCompleto,
            correo = correo,
            telefono = telefono,
            preguntaSeguridad = preguntaSeguridad,
            respuestaSeguridad = respuestaSeguridad,
            contrasena = contrasena,
            confirmacionContrasena = confirmacionContrasena,
            fechaRegistro = LocalDate.now().toString()
        )
        _estado.value = when (resultado) {
            is Resultado.Exito -> EstadoRegistro(registroExitoso = true)
            is Resultado.Error -> EstadoRegistro(error = resultado.mensaje)
        }
    }
}
