package com.buap.reboot.presentacion.confirmacionCompra

import com.buap.reboot.dominio.modelo.ConfirmacionCompra

data class EstadoConfirmacionCompra(
    val confirmacion: ConfirmacionCompra? = null,
    val error: String? = null
)
