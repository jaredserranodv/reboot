package com.buap.reboot.dominio.repositorio

import com.buap.reboot.dominio.modelo.Transaccion

interface RepositorioTransacciones {
    fun registrar(transaccion: Transaccion): Long
    fun obtenerPorComprador(idComprador: Long): List<Transaccion>
    fun obtenerPorArticulo(idArticulo: Long): Transaccion?
    fun obtenerPorId(idTransaccion: Long): Transaccion?
}
