package com.buap.reboot.dominio.modelo

data class Notificacion(
    val id: Long,
    val idUsuario: Long,
    val tipo: TipoNotificacion,
    val titulo: String,
    val mensaje: String,
    val fecha: String,
    val leida: Boolean,
    val idArticulo: Long? = null
)
