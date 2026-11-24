package com.buap.reboot.presentacion.inicio

import com.buap.reboot.dominio.modelo.Articulo

data class EstadoInicio(
    val cargando: Boolean = true,
    val articulos: List<Articulo> = emptyList(),
    val error: String? = null
)
