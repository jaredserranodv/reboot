package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.SolicitudRecuperacion
import com.buap.reboot.dominio.repositorio.RepositorioUsuarios
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.nucleo.Validadores

private const val CODIGO_MINIMO = 100000
private const val CODIGO_MAXIMO = 999999

/**
 * No hay backend ni envio real de correo/SMS: el codigo se genera localmente y se
 * entrega a la UI para que lo muestre (Toast/Snackbar) simulando la notificacion.
 * El codigo vive solo en memoria por correo mientras dura el proceso.
 */
class RecuperarContrasenaCasoUso(
    private val repositorioUsuarios: RepositorioUsuarios
) {
    private val codigosPorCorreo = mutableMapOf<String, String>()

    fun solicitarCodigo(correo: String): Resultado<SolicitudRecuperacion> {
        val usuario = repositorioUsuarios.buscarPorCorreo(correo)
            ?: return Resultado.Error("No existe una cuenta con ese correo")

        val codigo = (CODIGO_MINIMO..CODIGO_MAXIMO).random().toString()
        codigosPorCorreo[correo] = codigo
        return Resultado.Exito(SolicitudRecuperacion(usuario.preguntaSeguridad, codigo))
    }

    fun validarCodigoYPregunta(correo: String, codigoIngresado: String, respuestaSeguridad: String): Resultado<Unit> {
        val codigoEsperado = codigosPorCorreo[correo]
            ?: return Resultado.Error("Solicita un nuevo codigo de verificacion")
        if (codigoEsperado != codigoIngresado.trim()) {
            return Resultado.Error("El codigo de verificacion es incorrecto")
        }

        val usuario = repositorioUsuarios.buscarPorCorreo(correo)
            ?: return Resultado.Error("No existe una cuenta con ese correo")
        if (!usuario.respuestaSeguridad.equals(respuestaSeguridad.trim(), ignoreCase = true)) {
            return Resultado.Error("La respuesta de seguridad es incorrecta")
        }

        return Resultado.Exito(Unit)
    }

    fun restablecerContrasena(correo: String, nuevaContrasena: String, confirmacion: String): Resultado<Unit> {
        if (!Validadores.esContrasenaValida(nuevaContrasena)) {
            return Resultado.Error("La contrasena no cumple los requisitos minimos")
        }
        if (!Validadores.contrasenasCoinciden(nuevaContrasena, confirmacion)) {
            return Resultado.Error("Las contrasenas no coinciden")
        }
        val usuario = repositorioUsuarios.buscarPorCorreo(correo)
            ?: return Resultado.Error("No existe una cuenta con ese correo")

        // TODO: aplicar hashing real (ej. BCrypt) en vez de guardar la contrasena en texto plano
        repositorioUsuarios.actualizarContrasena(usuario.id, nuevaContrasena)
        codigosPorCorreo.remove(correo)
        return Resultado.Exito(Unit)
    }
}
