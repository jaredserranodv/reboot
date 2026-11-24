package com.buap.reboot.presentacion.recuperarContrasena

data class EstadoRecuperarContrasena(
    val paso: Int = 1,
    val correo: String = "",
    val preguntaSeguridad: String? = null,
    val cargando: Boolean = false,
    val error: String? = null,
    val restablecidoExitosamente: Boolean = false
)
