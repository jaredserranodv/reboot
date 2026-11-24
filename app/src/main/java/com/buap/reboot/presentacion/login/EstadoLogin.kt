package com.buap.reboot.presentacion.login

data class EstadoLogin(
    val cargando: Boolean = false,
    val error: String? = null,
    val sesionIniciada: Boolean = false
)
