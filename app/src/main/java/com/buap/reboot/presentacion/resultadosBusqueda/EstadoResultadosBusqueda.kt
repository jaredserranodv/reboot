package com.buap.reboot.presentacion.resultadosBusqueda

import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.dominio.modelo.FiltrosBusqueda

data class EstadoResultadosBusqueda(
    val cargando: Boolean = false,
    val articulos: List<Articulo> = emptyList(),
    val filtros: FiltrosBusqueda = FiltrosBusqueda(),
    val error: String? = null
)
