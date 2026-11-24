package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Usuario
import com.buap.reboot.dominio.repositorio.RepositorioSesion
import com.buap.reboot.dominio.repositorio.RepositorioUsuarios
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.nucleo.Validadores

class IniciarSesionCasoUso(
    private val repositorioUsuarios: RepositorioUsuarios,
    private val repositorioSesion: RepositorioSesion
) {
    fun ejecutar(correo: String, contrasena: String): Resultado<Usuario> {
        if (!Validadores.esCorreoValido(correo) || !Validadores.esCampoNoVacio(contrasena)) {
            return Resultado.Error("Correo o contrasena invalidos")
        }
        val usuario = repositorioUsuarios.buscarPorCorreo(correo)
            ?: return Resultado.Error("No existe una cuenta con ese correo")

        // TODO: comparar hash real una vez definido el algoritmo de hashing
        if (usuario.contrasenaHash != contrasena) {
            return Resultado.Error("Contrasena incorrecta")
        }

        repositorioSesion.iniciarSesion(usuario.id)
        return Resultado.Exito(usuario)
    }
}
