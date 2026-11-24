package com.buap.reboot.presentacion.recuperarContrasena

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.RecuperarContrasenaCasoUso
import com.buap.reboot.nucleo.Resultado

class RecuperarContrasenaViewModel(
    private val recuperarContrasenaCasoUso: RecuperarContrasenaCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoRecuperarContrasena())
    val estado: LiveData<EstadoRecuperarContrasena> = _estado

    /** Devuelve el mensaje con el codigo simulado para que la UI lo muestre en un Toast/Snackbar. */
    fun enviarCodigo(correo: String): Resultado<String> {
        val actual = _estado.value ?: EstadoRecuperarContrasena()
        _estado.value = actual.copy(cargando = true, error = null)

        return when (val resultado = recuperarContrasenaCasoUso.solicitarCodigo(correo)) {
            is Resultado.Exito -> {
                _estado.value = EstadoRecuperarContrasena(
                    paso = 2,
                    correo = correo,
                    preguntaSeguridad = resultado.datos.preguntaSeguridad
                )
                Resultado.Exito("Tu codigo de verificacion es ${resultado.datos.codigoGenerado}")
            }
            is Resultado.Error -> {
                _estado.value = actual.copy(cargando = false, error = resultado.mensaje)
                resultado
            }
        }
    }

    fun validarCodigo(codigoIngresado: String, respuestaSeguridad: String) {
        val actual = _estado.value ?: return
        when (val resultado = recuperarContrasenaCasoUso.validarCodigoYPregunta(actual.correo, codigoIngresado, respuestaSeguridad)) {
            is Resultado.Exito -> _estado.value = actual.copy(paso = 3, error = null)
            is Resultado.Error -> _estado.value = actual.copy(error = resultado.mensaje)
        }
    }

    fun restablecerContrasena(nuevaContrasena: String, confirmacion: String) {
        val actual = _estado.value ?: return
        when (val resultado = recuperarContrasenaCasoUso.restablecerContrasena(actual.correo, nuevaContrasena, confirmacion)) {
            is Resultado.Exito -> _estado.value = actual.copy(restablecidoExitosamente = true, error = null)
            is Resultado.Error -> _estado.value = actual.copy(error = resultado.mensaje)
        }
    }
}
