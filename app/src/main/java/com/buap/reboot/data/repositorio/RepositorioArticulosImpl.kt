package com.buap.reboot.data.repositorio

import com.buap.reboot.data.bd.entidades.ArticuloEntity
import com.buap.reboot.data.dao.ArticuloDao
import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.dominio.modelo.EstadoArticulo
import com.buap.reboot.dominio.modelo.FiltrosBusqueda
import com.buap.reboot.dominio.repositorio.RepositorioArticulos

class RepositorioArticulosImpl(private val articuloDao: ArticuloDao) : RepositorioArticulos {

    override fun buscar(filtros: FiltrosBusqueda): List<Articulo> = enIO {
        articuloDao.buscar(
            texto = filtros.texto.takeIf { it.isNotBlank() },
            categoria = filtros.categoria,
            ciudad = filtros.ciudad,
            estadoArticulo = filtros.estadoArticulo?.etiqueta,
            precioMinimo = filtros.precioMinimo,
            precioMaximo = filtros.precioMaximo
        )
    }.map { it.aDominio() }

    override fun obtenerPorId(id: Long): Articulo? =
        enIO { articuloDao.obtenerPorId(id) }?.aDominio()

    override fun obtenerPorVendedor(idVendedor: Long): List<Articulo> =
        enIO { articuloDao.obtenerPorVendedor(idVendedor) }.map { it.aDominio() }

    override fun publicar(articulo: Articulo): Long =
        enIO { articuloDao.insertar(articulo.aEntidad()) }

    override fun actualizar(articulo: Articulo) {
        enIO { articuloDao.actualizar(articulo.aEntidad()) }
    }

    override fun eliminar(idArticulo: Long) {
        enIO { articuloDao.eliminar(idArticulo) }
    }

    private fun ArticuloEntity.aDominio() = Articulo(
        id = id,
        idVendedor = idVendedor,
        titulo = titulo,
        descripcion = descripcion,
        categoria = categoria,
        precio = precio,
        estadoArticulo = EstadoArticulo.desdeEtiqueta(estadoArticulo),
        ciudad = ciudad,
        fechaPublicacion = fechaPublicacion,
        rutaImagen = rutaImagen,
        disponible = disponible
    )

    private fun Articulo.aEntidad() = ArticuloEntity(
        id = id,
        idVendedor = idVendedor,
        titulo = titulo,
        descripcion = descripcion,
        categoria = categoria,
        precio = precio,
        estadoArticulo = estadoArticulo.etiqueta,
        ciudad = ciudad,
        fechaPublicacion = fechaPublicacion,
        rutaImagen = rutaImagen,
        disponible = disponible
    )
}
