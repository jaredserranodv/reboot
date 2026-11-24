package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.repositorio.RepositorioUsuarios
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.nucleo.Validadores

class ActualizarPerfilCasoUso(
    private val repositorioUsuarios: RepositorioUsuarios
) {
    fun ejecutar(idUsuario: Long, nombreCompleto: String, telefono: String): Resultado<Unit> {
        if (!Validadores.esCampoNoVacio(nombreCompleto)) {
            return Resultado.Error("El nombre completo es obligatorio")
        }
        if (!Validadores.esTelefonoValido(telefono)) {
            return Resultado.Error("El telefono debe tener 10 digitos")
        }

        val usuario = repositorioUsuarios.buscarPorId(idUsuario)
            ?: return Resultado.Error("No se encontro el usuario")

        repositorioUsuarios.actualizarPerfil(usuario.copy(nombreCompleto = nombreCompleto.trim(), telefono = telefono))
        return Resultado.Exito(Unit)
    }
}
