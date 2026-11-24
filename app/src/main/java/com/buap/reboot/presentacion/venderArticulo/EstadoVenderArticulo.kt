package com.buap.reboot.presentacion.venderArticulo

data class EstadoVenderArticulo(
    val cargando: Boolean = false,
    val error: String? = null,
    val publicadoExitosamente: Boolean = false
)
