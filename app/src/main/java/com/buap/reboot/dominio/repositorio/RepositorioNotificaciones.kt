package com.buap.reboot.dominio.repositorio

import com.buap.reboot.dominio.modelo.Notificacion

interface RepositorioNotificaciones {
    fun obtenerPorUsuario(idUsuario: Long): List<Notificacion>
    fun marcarComoLeida(idNotificacion: Long)
    fun registrar(notificacion: Notificacion): Long
}
