package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.repositorio.RepositorioNotificaciones

class MarcarNotificacionLeidaCasoUso(
    private val repositorioNotificaciones: RepositorioNotificaciones
) {
    fun ejecutar(idNotificacion: Long) {
        repositorioNotificaciones.marcarComoLeida(idNotificacion)
    }
}
