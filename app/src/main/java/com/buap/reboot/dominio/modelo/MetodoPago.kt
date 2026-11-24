package com.buap.reboot.dominio.modelo

// Nunca se guarda el numero completo de tarjeta ni el CVV, solo los ultimos 4 digitos.
data class MetodoPago(
    val id: Long,
    val idUsuario: Long,
    val tipoTarjeta: TipoTarjeta,
    val ultimosCuatroDigitos: String,
    val mesVencimiento: Int,
    val anioVencimiento: Int,
    val esPredeterminado: Boolean
)
