package com.buap.reboot.data.repositorio

import com.buap.reboot.data.bd.entidades.TransaccionEntity
import com.buap.reboot.data.dao.TransaccionDao
import com.buap.reboot.dominio.modelo.EstadoTransaccion
import com.buap.reboot.dominio.modelo.Transaccion
import com.buap.reboot.dominio.repositorio.RepositorioTransacciones

class RepositorioTransaccionesImpl(private val transaccionDao: TransaccionDao) : RepositorioTransacciones {

    override fun registrar(transaccion: Transaccion): Long =
        enIO { transaccionDao.insertar(transaccion.aEntidad()) }

    override fun obtenerPorComprador(idComprador: Long): List<Transaccion> =
        enIO { transaccionDao.obtenerPorComprador(idComprador) }.map { it.aDominio() }

    override fun obtenerPorArticulo(idArticulo: Long): Transaccion? =
        enIO { transaccionDao.obtenerPorArticulo(idArticulo) }?.aDominio()

    override fun obtenerPorId(idTransaccion: Long): Transaccion? =
        enIO { transaccionDao.obtenerPorId(idTransaccion) }?.aDominio()

    private fun TransaccionEntity.aDominio() = Transaccion(
        id = id,
        idArticulo = idArticulo,
        idComprador = idComprador,
        totalPagado = totalPagado,
        estado = EstadoTransaccion.desdeEtiqueta(estado),
        fecha = fecha,
        direccionEnvio = direccionEnvio
    )

    private fun Transaccion.aEntidad() = TransaccionEntity(
        idArticulo = idArticulo,
        idComprador = idComprador,
        totalPagado = totalPagado,
        estado = estado.etiqueta,
        fecha = fecha,
        direccionEnvio = direccionEnvio
    )
}
