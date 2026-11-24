package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Notificacion
import com.buap.reboot.dominio.repositorio.RepositorioNotificaciones
import com.buap.reboot.nucleo.Resultado

class ObtenerNotificacionesCasoUso(
    private val repositorioNotificaciones: RepositorioNotificaciones
) {
    fun ejecutar(idUsuario: Long): Resultado<List<Notificacion>> =
        Resultado.Exito(repositorioNotificaciones.obtenerPorUsuario(idUsuario))
}
