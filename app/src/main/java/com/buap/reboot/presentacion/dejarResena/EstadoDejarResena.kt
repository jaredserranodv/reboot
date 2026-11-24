package com.buap.reboot.presentacion.dejarResena

import com.buap.reboot.dominio.modelo.Articulo

data class EstadoDejarResena(
    val articulo: Articulo? = null,
    val cargando: Boolean = false,
    val error: String? = null,
    val enviadaExitosamente: Boolean = false
)
