package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.PerfilUsuario
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.dominio.repositorio.RepositorioResenas
import com.buap.reboot.dominio.repositorio.RepositorioTransacciones
import com.buap.reboot.dominio.repositorio.RepositorioUsuarios
import com.buap.reboot.nucleo.Resultado

class ObtenerPerfilCasoUso(
    private val repositorioUsuarios: RepositorioUsuarios,
    private val repositorioArticulos: RepositorioArticulos,
    private val repositorioTransacciones: RepositorioTransacciones,
    private val repositorioResenas: RepositorioResenas
) {
    fun ejecutar(idUsuario: Long): Resultado<PerfilUsuario> {
        val usuario = repositorioUsuarios.buscarPorId(idUsuario)
            ?: return Resultado.Error("No se encontro el usuario")

        val calificaciones = repositorioResenas.obtenerPorVendedor(idUsuario).map { it.calificacion }

        return Resultado.Exito(
            PerfilUsuario(
                usuario = usuario,
                ratingPromedio = if (calificaciones.isEmpty()) 0f else calificaciones.average().toFloat(),
                totalPublicaciones = repositorioArticulos.obtenerPorVendedor(idUsuario).size,
                totalCompras = repositorioTransacciones.obtenerPorComprador(idUsuario).size
            )
        )
    }
}
