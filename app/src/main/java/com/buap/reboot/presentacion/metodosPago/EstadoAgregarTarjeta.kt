package com.buap.reboot.presentacion.metodosPago

data class EstadoAgregarTarjeta(
    val cargando: Boolean = false,
    val error: String? = null,
    val guardadaExitosamente: Boolean = false
)
