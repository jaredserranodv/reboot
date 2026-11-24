package com.buap.reboot.dominio.repositorio

import com.buap.reboot.dominio.modelo.DireccionEnvio

interface RepositorioDirecciones {
    fun obtenerPorUsuario(idUsuario: Long): List<DireccionEnvio>
    fun obtenerPredeterminada(idUsuario: Long): DireccionEnvio?
    fun obtenerPorId(idDireccion: Long): DireccionEnvio?
    fun registrar(direccion: DireccionEnvio): Long
    fun actualizar(direccion: DireccionEnvio)
    fun eliminar(idDireccion: Long)
    fun marcarComoPredeterminada(idUsuario: Long, idDireccion: Long)
}
