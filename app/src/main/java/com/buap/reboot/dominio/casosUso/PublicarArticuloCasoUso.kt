package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.dominio.modelo.EstadoArticulo
import com.buap.reboot.dominio.repositorio.RepositorioArticulos
import com.buap.reboot.nucleo.Resultado
import com.buap.reboot.nucleo.Validadores

class PublicarArticuloCasoUso(
    private val repositorioArticulos: RepositorioArticulos
) {
    fun ejecutar(
        idVendedor: Long,
        titulo: String,
        descripcion: String,
        categoria: String,
        precio: Double,
        estadoArticulo: EstadoArticulo,
        ciudad: String,
        fechaPublicacion: String,
        rutaImagen: String?
    ): Resultado<Long> {
        if (!Validadores.esCampoNoVacio(titulo) || !Validadores.esCampoNoVacio(descripcion)) {
            return Resultado.Error("Titulo y descripcion son obligatorios")
        }
        if (!Validadores.esCampoNoVacio(categoria) || !Validadores.esCampoNoVacio(ciudad)) {
            return Resultado.Error("Categoria y ciudad son obligatorias")
        }
        if (precio <= 0) {
            return Resultado.Error("El precio debe ser mayor a cero")
        }

        val articulo = Articulo(
            id = 0,
            idVendedor = idVendedor,
            titulo = titulo.trim(),
            descripcion = descripcion.trim(),
            categoria = categoria,
            precio = precio,
            estadoArticulo = estadoArticulo,
            ciudad = ciudad.trim(),
            fechaPublicacion = fechaPublicacion,
            rutaImagen = rutaImagen
        )
        return Resultado.Exito(repositorioArticulos.publicar(articulo))
    }
}
