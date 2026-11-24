package com.buap.reboot.dominio.modelo

data class Usuario(
    val id: Long,
    val nombreCompleto: String,
    val correo: String,
    val telefono: String,
    val contrasenaHash: String,
    val preguntaSeguridad: String,
    val respuestaSeguridad: String,
    val fechaRegistro: String,
    val rutaFotoPerfil: String? = null
)
