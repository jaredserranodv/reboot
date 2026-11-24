package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.EstadoArticulo
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.nucleo.Validadores

class EditarArticuloCasoUso(
    private val repositorioArticulos: RepositorioArticulos
) {
    fun ejecutar(
        idArticulo: Long,
        titulo: String,
        descripcion: String,
        categoria: String,
        precio: Double,
        estadoArticulo: EstadoArticulo,
        ciudad: String,
        rutaImagen: String?
    ): Resultado<Unit> {
        if (!Validadores.esCampoNoVacio(titulo) || !Validadores.esCampoNoVacio(descripcion)) {
            return Resultado.Error("Titulo y descripcion son obligatorios")
        }
        if (precio <= 0) {
            return Resultado.Error("El precio debe ser mayor a cero")
        }

        val articulo = repositorioArticulos.obtenerPorId(idArticulo)
            ?: return Resultado.Error("El articulo ya no existe")

        repositorioArticulos.actualizar(
            articulo.copy(
                titulo = titulo.trim(),
                descripcion = descripcion.trim(),
                categoria = categoria.trim(),
                precio = precio,
                estadoArticulo = estadoArticulo,
                ciudad = ciudad.trim(),
                rutaImagen = rutaImagen ?: articulo.rutaImagen
            )
        )
        return Resultado.Exito(Unit)
    }
}
