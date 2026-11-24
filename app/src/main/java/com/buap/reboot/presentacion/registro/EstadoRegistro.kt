package com.buap.reboot.presentacion.registro

data class EstadoRegistro(
    val cargando: Boolean = false,
    val error: String? = null,
    val registroExitoso: Boolean = false
)
