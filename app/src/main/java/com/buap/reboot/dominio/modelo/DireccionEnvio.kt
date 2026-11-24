package com.buap.reboot.dominio.modelo

data class DireccionEnvio(
    val id: Long,
    val idUsuario: Long,
    val etiqueta: String,
    val calleYNumero: String,
    val colonia: String,
    val ciudad: String,
    val codigoPostal: String,
    val telefonoContacto: String,
    val esPredeterminada: Boolean
)

/** Texto armado a partir de los campos separados, para mostrar en Mis direcciones y en Pago. */
fun DireccionEnvio.direccionCompleta(): String =
    "$calleYNumero, $colonia, $ciudad, $codigoPostal"
