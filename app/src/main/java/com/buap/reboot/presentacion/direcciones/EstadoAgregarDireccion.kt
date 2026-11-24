package com.buap.reboot.presentacion.direcciones

import com.buap.reboot.dominio.modelo.DireccionEnvio

data class EstadoAgregarDireccion(
    val direccionAEditar: DireccionEnvio? = null,
    val cargando: Boolean = false,
    val error: String? = null,
    val guardadaExitosamente: Boolean = false
)
