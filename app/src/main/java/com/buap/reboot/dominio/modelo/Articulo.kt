package com.buap.reboot.dominio.modelo

data class Articulo(
    val id: Long,
    val idVendedor: Long,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val precio: Double,
    val estadoArticulo: EstadoArticulo,
    val ciudad: String,
    val fechaPublicacion: String,
    val rutaImagen: String?,
    val disponible: Boolean = true
)
