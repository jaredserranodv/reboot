package com.buap.reboot.dominio.repositorio

import com.buap.reboot.dominio.modelo.MetodoPago

interface RepositorioMetodosPago {
    fun obtenerPorUsuario(idUsuario: Long): List<MetodoPago>
    fun obtenerPredeterminado(idUsuario: Long): MetodoPago?
    fun registrar(metodoPago: MetodoPago): Long
    fun eliminar(idMetodoPago: Long)
    fun marcarComoPredeterminado(idUsuario: Long, idMetodoPago: Long)
}
