package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Usuario
import com.buap.reboot.dominio.repositorio.RepositorioUsuarios
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.nucleo.Validadores

class RegistrarUsuarioCasoUso(
    private val repositorioUsuarios: RepositorioUsuarios
) {
    fun ejecutar(
        nombreCompleto: String,
        correo: String,
        telefono: String,
        preguntaSeguridad: String,
        respuestaSeguridad: String,
        contrasena: String,
        confirmacionContrasena: String,
        fechaRegistro: String
    ): Resultado<Long> {
        if (!Validadores.esCampoNoVacio(nombreCompleto)) {
            return Resultado.Error("El nombre completo es obligatorio")
        }
        if (!Validadores.esCorreoValido(correo)) {
            return Resultado.Error("El correo no tiene un formato valido")
        }
        if (!Validadores.esTelefonoValido(telefono)) {
            return Resultado.Error("El telefono debe tener 10 digitos")
        }
        if (!Validadores.esCampoNoVacio(preguntaSeguridad) || !Validadores.esCampoNoVacio(respuestaSeguridad)) {
            return Resultado.Error("La pregunta y respuesta de seguridad son obligatorias")
        }
        if (!Validadores.esContrasenaValida(contrasena)) {
            return Resultado.Error("La contrasena no cumple los requisitos minimos")
        }
        if (!Validadores.contrasenasCoinciden(contrasena, confirmacionContrasena)) {
            return Resultado.Error("Las contrasenas no coinciden")
        }
        if (repositorioUsuarios.existeCorreo(correo)) {
            return Resultado.Error("Ya existe una cuenta con ese correo")
        }

        // TODO: aplicar hashing real (ej. BCrypt) en vez de guardar la contrasena en texto plano
        val usuario = Usuario(
            id = 0,
            nombreCompleto = nombreCompleto.trim(),
            correo = correo.trim(),
            telefono = telefono,
            contrasenaHash = contrasena,
            preguntaSeguridad = preguntaSeguridad,
            respuestaSeguridad = respuestaSeguridad,
            fechaRegistro = fechaRegistro
        )
        val id = repositorioUsuarios.registrar(usuario)
        return Resultado.Exito(id)
    }
}
