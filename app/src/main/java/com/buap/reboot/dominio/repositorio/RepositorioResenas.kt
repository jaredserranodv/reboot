package com.buap.reboot.dominio.repositorio

import com.buap.reboot.dominio.modelo.Resena

interface RepositorioResenas {
    fun registrar(resena: Resena): Long
    fun obtenerPorVendedor(idVendedor: Long): List<Resena>
    fun obtenerPorTransaccion(idTransaccion: Long): Resena?
}
