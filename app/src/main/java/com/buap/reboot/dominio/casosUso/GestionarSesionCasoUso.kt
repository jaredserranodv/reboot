package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Usuario
import com.buap.reboot.dominio.repositorio.RepositorioSesion
import com.buap.reboot.dominio.repositorio.RepositorioUsuarios

class ObtenerSesionActualCasoUso(
    private val repositorioSesion: RepositorioSesion,
    private val repositorioUsuarios: RepositorioUsuarios
) {
    fun ejecutar(): Usuario? {
        val idUsuario = repositorioSesion.obtenerIdUsuarioActual() ?: return null
        return repositorioUsuarios.buscarPorId(idUsuario)
    }
}

class CerrarSesionCasoUso(
    private val repositorioSesion: RepositorioSesion
) {
    fun ejecutar() {
        repositorioSesion.cerrarSesion()
    }
}
