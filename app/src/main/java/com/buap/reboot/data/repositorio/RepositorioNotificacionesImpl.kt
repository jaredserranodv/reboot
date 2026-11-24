package com.buap.reboot.data.repositorio

import com.buap.reboot.data.bd.entidades.NotificacionEntity
import com.buap.reboot.data.dao.NotificacionDao
import com.buap.reboot.dominio.modelo.Notificacion
import com.buap.reboot.dominio.modelo.TipoNotificacion
import com.buap.reboot.dominio.repositorio.RepositorioNotificaciones

class RepositorioNotificacionesImpl(private val notificacionDao: NotificacionDao) : RepositorioNotificaciones {

    override fun obtenerPorUsuario(idUsuario: Long): List<Notificacion> =
        enIO { notificacionDao.obtenerPorUsuario(idUsuario) }.map { it.aDominio() }

    override fun marcarComoLeida(idNotificacion: Long) {
        enIO { notificacionDao.marcarComoLeida(idNotificacion) }
    }

    override fun registrar(notificacion: Notificacion): Long =
        enIO { notificacionDao.insertar(notificacion.aEntidad()) }

    private fun NotificacionEntity.aDominio() = Notificacion(
        id = id,
        idUsuario = idUsuario,
        tipo = TipoNotificacion.desdeEtiqueta(tipo),
        titulo = titulo,
        mensaje = mensaje,
        fecha = fecha,
        leida = leida,
        idArticulo = idArticulo
    )

    private fun Notificacion.aEntidad() = NotificacionEntity(
        idUsuario = idUsuario,
        tipo = tipo.etiqueta,
        titulo = titulo,
        mensaje = mensaje,
        fecha = fecha,
        leida = leida,
        idArticulo = idArticulo
    )
}
