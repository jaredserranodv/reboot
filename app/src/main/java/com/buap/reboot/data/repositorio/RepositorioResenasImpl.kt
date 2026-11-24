package com.buap.reboot.data.repositorio

import com.buap.reboot.data.bd.entidades.ResenaEntity
import com.buap.reboot.data.dao.ResenaDao
import com.buap.reboot.dominio.modelo.Resena
import com.buap.reboot.dominio.repositorio.RepositorioResenas

class RepositorioResenasImpl(private val resenaDao: ResenaDao) : RepositorioResenas {

    override fun registrar(resena: Resena): Long =
        enIO { resenaDao.insertar(resena.aEntidad()) }

    override fun obtenerPorVendedor(idVendedor: Long): List<Resena> =
        enIO { resenaDao.obtenerPorVendedor(idVendedor) }.map { it.aDominio() }

    override fun obtenerPorTransaccion(idTransaccion: Long): Resena? =
        enIO { resenaDao.obtenerPorTransaccion(idTransaccion) }?.aDominio()

    private fun ResenaEntity.aDominio() = Resena(
        id = id,
        idTransaccion = idTransaccion,
        idVendedor = idVendedor,
        idComprador = idComprador,
        calificacion = calificacion,
        comentario = comentario,
        recomendado = recomendado,
        fecha = fecha
    )

    private fun Resena.aEntidad() = ResenaEntity(
        idTransaccion = idTransaccion,
        idVendedor = idVendedor,
        idComprador = idComprador,
        calificacion = calificacion,
        comentario = comentario,
        recomendado = recomendado,
        fecha = fecha
    )
}
