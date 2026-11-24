package com.buap.reboot.presentacion.editarArticulo

import com.buap.reboot.dominio.modelo.Articulo

data class EstadoEditarArticulo(
    val articulo: Articulo? = null,
    val cargando: Boolean = false,
    val error: String? = null,
    val guardadoExitosamente: Boolean = false,
    val eliminadoExitosamente: Boolean = false
)
