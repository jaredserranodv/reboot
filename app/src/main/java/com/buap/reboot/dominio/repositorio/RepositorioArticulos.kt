package com.buap.reboot.dominio.repositorio

import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.dominio.modelo.FiltrosBusqueda

interface RepositorioArticulos {
    fun buscar(filtros: FiltrosBusqueda): List<Articulo>
    fun obtenerPorId(id: Long): Articulo?
    fun obtenerPorVendedor(idVendedor: Long): List<Articulo>
    fun publicar(articulo: Articulo): Long
    fun actualizar(articulo: Articulo)
    fun eliminar(idArticulo: Long)
}
